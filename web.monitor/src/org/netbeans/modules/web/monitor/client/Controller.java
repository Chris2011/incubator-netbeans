/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2002 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

/**
 * Controller.java
 *
 *
 * Created: Fri Jan 19 16:21:06 2001
 *
 * @author Ana von Klopp
 * @version
 */

/* 
 * PENDING: 

 * This class currently holds a hashtable full of beans corresponding
 * to the transaction data. I'm not sure what the best thing to do is
 * w.r.t. keeping those beans in memory or not - that might be
 * huge. Need to consort with somebody that's good at that sort of
 * thing. Perhaps like the last five or so that the user has been
 * looking at are a good idea to keep. 
 *
 * The reason for doing that was to have a quick fix w.r.t. reading in 
 * files. Once I have figured out a way to parse the XML file quickly
 * for just the monitor attributes that should be unnecessary. 
 * 
 */

package  org.netbeans.modules.web.monitor.client;

import java.util.*;
import java.io.*;
import java.net.*;

import java.text.MessageFormat; 
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import org.openide.DialogDisplayer;
import org.openide.ErrorManager;
import org.openide.NotifyDescriptor;
import org.openide.awt.HtmlBrowser;
import org.openide.cookies.InstanceCookie;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.filesystems.FileAlreadyLockedException;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.Repository;
import org.openide.nodes.Node;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Children.SortedArray;
import org.openide.options.*;
import org.openide.util.HttpServer;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

import org.netbeans.modules.web.monitor.server.Constants;
import org.netbeans.modules.web.monitor.data.*;

public class Controller  {


    // REPLAY strings - must be coordinated with server.MonitorFilter
    public final static String REPLAY="netbeans.replay"; //NOI18N
    public final static String PORT="netbeans.replay.port"; //NOI18N
    public final static String REPLAYSTATUS="netbeans.replay.status"; //NOI18N
    public final static String REPLAYSESSION="netbeans.replay.session"; //NOI18N
    public static final boolean debug = false;
    //private transient static boolean starting = true;

    // Test server location and port
    // Should use InetAddress.getLocalhost() instead
    private transient static String server = "localhost"; //NOI18N
    private transient static int port = 8080;

    // Location of the files
    private static FileObject monDir = null;
    private static FileObject currDir = null;
    private static FileObject saveDir = null;
    private static FileObject replayDir = null;

    public final static String monDirStr = "HTTPMonitor"; // NOI18N
    public final static String currDirStr = "current"; // NOI18N
    public final static String saveDirStr = "save"; // NOI18N
    public final static String replayDirStr = "replay"; // NOI18N

    // Constant nodes etc we need to know about
    private transient  NavigateNode root = null;
    private Children.SortedArray currTrans = null;
    private Children.SortedArray  savedTrans = null;

    // These are the ones that should go. 
    private Hashtable currBeans = null;
    private Hashtable saveBeans = null;
    
    private transient Comparator comp = null;

    private HtmlBrowser.BrowserComponent browser = null;
    private SettingsListener browserListener = null;
    private SystemOption settings = null;

    private boolean useBrowserCookie = true;
    
    public Controller() {

	currBeans = new Hashtable();
	saveBeans = new Hashtable();
	createNodeStructure();
	registerBrowserListener();
    }

    /**
     * Invoked at startup, creates the root folder and the folder for
     * current and saved transactions (and their children arrays).
     */
    private void createNodeStructure() {

	comp = new CompTime(true);
	currTrans = new Children.SortedArray();
	currTrans.setComparator(comp);
	savedTrans = new Children.SortedArray();
	savedTrans.setComparator(comp);

	CurrNode currNode = new CurrNode(currTrans);
	SavedNode savedNode = new SavedNode(savedTrans);

	Node[] kids = new Node[2];
	kids[0] = currNode;
	kids[1] = savedNode;

	Children children = new Children.Array();
	children.add(kids);
	root = new NavigateNode(children);

	    
    }

    public void cleanup() {
	deleteDirectory(currDirStr);
	removeBrowserListener();
    }

    /**
     * Adds a transaction to the list of current transactions.
     */
    void addTransaction(String str) {
	TransactionNode[] nodes = new TransactionNode[1];
	nodes[0] = createTransactionNode(str);
	currTrans.add(nodes);
    }

    /**
     * Adds a transaction to the list of current transactions.
     */
    protected NavigateNode getRoot() {
	return root;
    }


    protected static FileObject getMonDir() throws FileNotFoundException {
	
	if(monDir == null || !monDir.isFolder()) {
	    try {
		createDirectories();
	    }
	    catch(FileNotFoundException ex) {
		throw ex;
	    }
	}
	return monDir;
    }
    

    protected static FileObject getCurrDir() throws FileNotFoundException {
	 
	if(currDir == null || !currDir.isFolder()) {
	    try{
		createDirectories();
	    }
	    catch(FileNotFoundException ex) {
		throw ex;
	    }
	}
	return currDir;
    }

    protected static FileObject getReplayDir() throws FileNotFoundException {
	 
	if(replayDir == null || !replayDir.isFolder()) {
	    try{
		createDirectories();
	    }
	    catch(FileNotFoundException ex) {
		throw ex;
	    }
	}
	return replayDir;
    }
    

    protected static FileObject getSaveDir() throws FileNotFoundException {
	 
	if(saveDir == null || !saveDir.isFolder()) {
	    try{
		createDirectories();
	    }
	    catch(FileNotFoundException ex) {
		throw ex;
	    }
	}
	return saveDir;
    }

    public boolean haveDirectories() {
	if(currDir == null) {
	    try {
		currDir = getCurrDir();
	    }
	    catch(Exception ex) {
		return false;
	    }
	}
	
	if(saveDir == null) {
	    try {
		saveDir = getSaveDir();
	    }
	    catch(Exception ex) {
		return false;
	    }
	}
	return true;
    }
    

    private static void createDirectories() throws FileNotFoundException {

	if(debug) log("Now in createDirectories()"); // NOI18N
	
	FileObject rootdir = 
	    Repository.getDefault().getDefaultFileSystem().getRoot();
	if(debug) {
	    log("Root directory is " + rootdir.getName()); // NOI18N
	    File rootF = FileUtil.toFile(rootdir);
	    log("Root directory abs path " + // NOI18N
		rootF.getAbsolutePath());
	}

	FileLock lock = null;

	if(monDir == null || !monDir.isFolder()) {
	    try {
		monDir = rootdir.getFileObject(monDirStr);
	    }
	    catch(Exception ex) {
	    }
	    
	    if(monDir == null || !monDir.isFolder()) {
		if(monDir != null) {
		    try {
			lock = monDir.lock();
			monDir.delete(lock);
		    }
		    catch(FileAlreadyLockedException falex) {
			throw new FileNotFoundException();
		    }
		    catch(IOException ex) {
			throw new FileNotFoundException();
		    }
		    finally { 
			if(lock != null) lock.releaseLock();
		    }
		}
		try {
		    monDir = rootdir.createFolder(monDirStr);
		}
		catch(IOException ioex) {
		    if(debug) ioex.printStackTrace();
		}
	    }
	    if(monDir == null || !monDir.isFolder()) 
		throw new FileNotFoundException();
	}

	if(debug) 
	    log("monitor directory is " + monDir.getName());// NOI18N

	// Current directory

	if(currDir == null || !currDir.isFolder()) {

	    try {
		currDir = monDir.getFileObject(currDirStr);
	    }
	    catch(Exception ex) { }
	    
	    if(currDir == null || !currDir.isFolder()) {
		lock = null;
		if(currDir != null) {
		    try {
			lock = currDir.lock();
			currDir.delete(lock);
		    }
		    catch(FileAlreadyLockedException falex) {
			throw new FileNotFoundException();
		    }
		    catch(IOException ex) {
			throw new FileNotFoundException();
		    }
		    finally { 
			if(lock != null) lock.releaseLock();
		    }
		}
		try {
		    currDir = monDir.createFolder(currDirStr);
		}
		catch(IOException ex) {
		    if(debug) ex.printStackTrace();
		}
	    }
	    if(currDir == null || !currDir.isFolder()) 
		throw new FileNotFoundException();
	}
	
	if(debug) log("curr directory is " + currDir.getName()); // NOI18N

	// Save Directory
	if(saveDir == null || !saveDir.isFolder()) {
	    try {
		saveDir = monDir.getFileObject(saveDirStr);
	    }
	    catch(Exception ex) { }
	    
	    if(saveDir == null || !saveDir.isFolder()) {
		if(saveDir != null) {
		    lock = null;
		    try {
			lock = saveDir.lock();
			saveDir.delete(lock);
		    }
		    catch(FileAlreadyLockedException falex) {
			throw new FileNotFoundException();
		    }
		    catch(IOException ex) {
			throw new FileNotFoundException();
		    }
		    finally { 
			if(lock != null) lock.releaseLock();
		    }
		}
		try {
		    saveDir = monDir.createFolder(saveDirStr);
		}
		catch(IOException ex) {
		    if(debug) ex.printStackTrace();
		}
	    }
	    if(saveDir == null || !saveDir.isFolder()) 
		throw new FileNotFoundException();

	    if(debug) 
		log("save directory is " + saveDir.getName()); // NOI18N
	}

	// Replay Directory

	if(replayDir == null || !replayDir.isFolder()) {

	    try {
		replayDir = monDir.getFileObject(replayDirStr);
	    }
	    catch(Exception ex) { }
	    
	    if(replayDir == null || !replayDir.isFolder()) {
		if(replayDir != null) {
		    lock = null;
		    try {
			lock = replayDir.lock();
			replayDir.delete(lock);
		    }
		    catch(FileAlreadyLockedException falex) {
			throw new FileNotFoundException();
		    }
		    catch(IOException ex) {
			throw new FileNotFoundException();
		    }
		    finally { 
			if(lock != null) lock.releaseLock();
		    }
		}
		try {
		    replayDir = monDir.createFolder(replayDirStr);
		}
		catch(Exception ex) {
		    if(debug) ex.printStackTrace();
		}
	    }
	    if(replayDir == null || !replayDir.isFolder()) 
		throw new FileNotFoundException();

	    if(debug) 
		log("replay directory is " + replayDir.getName());// NOI18N
	}
    }


    /**
     * Invoked by ReplayAction. Replays the transaction corresponding to
     * the selected node.
     *
     * PENDING - it would be better if the nodes know which server
     * they were processed on. This would be the case if we listed the 
     * nodes separately depending on the server that collected the
     * data. 
     *
     */
    public void replayTransaction(Node node) {

	if(debug) 
	    log("Replay transaction from node " + node.getName()); // NOI18N

	if(!checkServer(true)) return;

	TransactionNode tn = null; 
	try {
	    tn = (TransactionNode)node;
	}
	catch(ClassCastException cce) {
	    if(debug) 
		log("Selected node was not a transaction node");//NOI18N
	    return;
	}
	
	MonitorData md = getMonitorData(tn, false, false);
	if(!useBrowserCookie) 
	    md.getRequestData().setReplaceSessionCookie(true);

	if(debug) { 
	    log("Replace is " +  // NOI18N
		String.valueOf(md.getRequestData().getReplaceSessionCookie()));
	    String fname = md.createTempFile("control-replay.xml"); // NOI18N
	    log("Wrote replay data to " + fname);// NOI18N 
	}
    
	String status;
	if(tn.isCurrent()) status = currDirStr; 
	else status = saveDirStr; 
	try {
	    replayTransaction(md, status);
	}
	catch(UnknownHostException uhe) {
	    // Notify the user that there is no host

	    Object[] options = {
		NbBundle.getBundle(Controller.class).getString("MON_OK"),
	    };

	    Object[] args = {
		md.getServerName(),
	    };
	    
	    MessageFormat msgFormat = new MessageFormat
		(NbBundle.getBundle(Controller.class).getString("MON_Exec_server_no_host"));  
	    NotifyDescriptor noServerDialog = 
		new NotifyDescriptor(msgFormat.format(args),
				     NbBundle.getBundle(Controller.class).getString("MON_Exec_server"),
				     NotifyDescriptor.DEFAULT_OPTION,
				     NotifyDescriptor.INFORMATION_MESSAGE,
				     options,
				     options[0]);
	    DialogDisplayer.getDefault().notify(noServerDialog);

	}
	catch(IOException ioe) {

	    // Notify the user that the server is not running
	    Object[] options = {
		NbBundle.getBundle(Controller.class).getString("MON_OK"),
	    };

	    Object[] args = {
		md.getServerAndPort(),
	    };

	    MessageFormat msgFormat = new MessageFormat
		(NbBundle.getBundle(Controller.class).getString("MON_Exec_server_start")); 

	    NotifyDescriptor noServerDialog = 
		new NotifyDescriptor(msgFormat.format(args), 
				     NbBundle.getBundle(Controller.class).getString("MON_Exec_server"),
				     NotifyDescriptor.DEFAULT_OPTION,
				     NotifyDescriptor.INFORMATION_MESSAGE,
				     options,
				     options[0]);
	    DialogDisplayer.getDefault().notify(noServerDialog);
	}
    }

    /**
     * Invoked by EditPanel. Replays the transaction corresponding to
     * the selected node. 
     */
    public void replayTransaction(MonitorData md) 
	throws UnknownHostException, IOException  {

	// PENDING - can't make UI changes right now for Sierra
	// any exception thrown in this method indicates that we
	// couldn't even get to the monitor data, and we should add an
	// additional panel to that effect. Also, unreadable monitor
	// data should cause the transaction to be removed from the
	// pane. 
	
	if(debug) log("replayTransaction(MD)"); //NOI18N


	FileObject fo; 	
	FileLock lock = null;
	OutputStream out = null;
	PrintWriter pw = null;

	if(debug) log("Creating record for replay"); //NOI18N

	String id = md.getAttributeValue("id"); // NOI18N

	try {
	    fo = getReplayDir().createData(id, "xml"); //NOI18N
	}
	catch(IOException ioex) { 
	    throw ioex;
	} 

	try { 
	    lock = fo.lock();
	} 
	catch(FileAlreadyLockedException fale) { 
	    throw new IOException(); 
	} 

	try { 
	    out = fo.getOutputStream(lock);
	    pw = new PrintWriter(out);
	    md.write(pw);	    
	    if(debug) log("...record complete"); //NOI18N

	    if(debug) {
		String fname = 
		    md.createTempFile("control-record.xml"); // NOI18N
		log("Wrote replay data to " + fname); // NOI18N
	    }
	}
	catch(IOException ioex) {
	    throw ioex;
	}
	finally {
	    if(lock != null) lock.releaseLock(); 
	    try {
		pw.close();
	    }
	    catch(Throwable t) {
	    }  
	    try {
		out.close();
	    }
	    catch(Throwable t) {
	    }  
	}
	
	try {
	    replayTransaction(md, replayDirStr); 
	}
	catch(UnknownHostException uhe) {
	    throw uhe;
	}
	catch(IOException ioe) {
	    throw ioe;
	}
    }
    
    /**
     *
     */
    public void replayTransaction(MonitorData md, String status)
	throws UnknownHostException, IOException  {
	
	if(debug) 
	    log("Replay transaction from transaction file "); //NOI18N 
	URL url = null;
	try {
	    String name = md.getServerName();
	    int port = md.getServerPort();
	    
	    StringBuffer uriBuf = new StringBuffer(128);
	    uriBuf.append(md.getRequestData().getAttributeValue("uri")); //NOI18N 
	    uriBuf.append("?"); //NOI18N 
	    uriBuf.append(REPLAY); 
	    uriBuf.append("="); //NOI18N 
	    uriBuf.append(md.getAttributeValue("id")); //NOI18N 
	    uriBuf.append("&"); //NOI18N 
	    uriBuf.append(REPLAYSTATUS); 
	    uriBuf.append("="); //NOI18N 
	    uriBuf.append(status);

	    String portS = null; 
	    try { 
		portS = 
		    String.valueOf(HttpServer.getRepositoryRoot().getPort());
	    }
	    catch(Exception ex) {
		// No internal HTTP server, do nothing
	    } 
	    if(portS != null) { 
		uriBuf.append("&"); //NOI18N 
		uriBuf.append(PORT); 
		uriBuf.append("="); //NOI18N 
		uriBuf.append(portS);
	    }


	    if(md.getRequestData().getReplaceSessionCookie()) { 
		uriBuf.append("&"); //NOI18N 
		uriBuf.append(REPLAYSESSION); 
		uriBuf.append("="); //NOI18N 
		uriBuf.append(md.getRequestData().getSessionID());
	    }
	    url = new URL("http", name, port, uriBuf.toString()); //NOI18N 
	}
	catch(MalformedURLException me) { 
	    if(debug) log(me.getMessage());
	}
	catch(NumberFormatException ne) { 
	    if(debug) log(ne.getMessage());
	}

	// Send the url to the browser.
	try {
	    showReplay(url);
	}
	catch(UnknownHostException uhe) {
	    throw uhe;
	}
	catch(IOException ioe) {
	    throw ioe;
	}
    }

    public void saveTransaction(Node[] nodes) {

	if(!haveDirectories()) {
	    // PENDING - report the error properly
	    // This should not happen
	    log("Couldn't get the directory"); //NOI18N
	    return;
	}

	Node[] newNodes = new Node[nodes.length];
	TransactionNode mvNode; 
	String id;
	 
	boolean error = false; 

	for(int i=0; i < nodes.length; ++i) {
	    
	    mvNode = (TransactionNode)nodes[i];
	    id = mvNode.getID();
	    
	    if(debug) log(" Saving " + id); //NOI18N 

	    if(currBeans.containsKey(id)) 
		saveBeans.put(id, currBeans.remove(id)); 
	    
	    // Note I didn't load the bean here yet. Will only do that 
	    // if the data is displayed. 
		
	    FileLock lock = null; 
	    try {
		FileObject fold = 
		    currDir.getFileObject(id, "xml"); //NOI18N
		lock = fold.lock();
		fold.copy(saveDir, id, "xml"); //NOI18N
		if(debug) log(fold.getName());
		fold.delete(lock);
		mvNode.setCurrent(false);
		newNodes[i] = mvNode;
	    }
	    catch(FileAlreadyLockedException falex) {
		error = true;
		// PENDING report properly
	    }
	    catch(IOException ioex) {
		error = true;
		// PENDING report properly
	    }
	    catch(Exception ex) {
		error = true; 
		// PENDING report properly
	    }
	    finally { 
		if(lock != null) lock.releaseLock(); 
	    }
	    
	}
	if(!error) currTrans.remove(nodes);
	savedTrans.add(newNodes);
    }
  
    /**
     * Invoked by DeleteAction.  Deletes a saved transaction 
     */

    public void deleteTransaction(Node[] nodes) {

	if(!haveDirectories()) {
	    // PENDING - report the error property
	    // This should not happen
	    log("Couldn't get the directory"); //NOI18N 
	    return;
	}

	// PENDING
	if((nodes == null) || (nodes.length == 0)) return;

	TransactionNode n = null;
	for(int i=0; i < nodes.length; ++i) {
	    
	    n = (TransactionNode)nodes[i];
	    if(debug) 
		log("Deleting :" + n.toString()); //NOI18N 
	    
	    if(n.isCurrent()) delete(n, currTrans, currBeans, true); 
	    else delete(n, savedTrans, saveBeans, false); 
	} 
    }

    private void delete(TransactionNode node, 
			Children.SortedArray transactions, 
			Hashtable beans,
			boolean current) { 

	FileObject fold = null;
	FileLock lock = null;

	try { 
	    if(current) 
		fold = currDir.getFileObject(node.getID(), "xml"); //NOI18N
	    else 
		fold = saveDir.getFileObject(node.getID(), "xml"); //NOI18N
	    lock = fold.lock();
	    if(debug) log("Deleting: " + fold.getName()); //NOI18N 
	    fold.delete(lock); 
	    // We only do this if we could delete the file. 
	    Node[] nodes = { node };
	    transactions.remove(nodes);
	    beans.remove(node.getID());
	}
	catch(FileAlreadyLockedException ex) {
	    // PENDING report properly
	    if(debug) log("Couldn't lock file:" + node.getID()); //NOI18N 
	}
	catch(IOException ex) {
	    // PENDING report properly
	    if(debug) log("Couldn't delete file:" + node.getID()); //NOI18N 
	}
	finally { 
	    if(lock != null) lock.releaseLock();
	}
    }

    void deleteDirectory(String dir) {

	if(!haveDirectories()) {
	    // PENDING - report the error property
	    // This should not happen
	    log("Couldn't get the directory"); //NOI18N 
	    return;
	}

	FileObject directory = null;
	if(dir.equals(saveDirStr)) {
	    directory = saveDir;
	    savedTrans.remove(savedTrans.getNodes());
	    saveBeans.clear();
	}
	
	else {   
	    directory = currDir;
	    currTrans.remove(currTrans.getNodes());
	    currBeans.clear();
	}
	
	FileLock lock = null;
	Enumeration e = directory.getData(false);
	while(e.hasMoreElements()) {
	    FileObject fo = (FileObject) e.nextElement();
	    lock = null;
	    try {
		lock = fo.lock();
		fo.delete(lock);
	    }
	    catch(FileAlreadyLockedException falex) {
		// PENDING report properly
	    }
	    catch(IOException IOex) {
		// PENDING report properly
	    }
	    finally { 
		if(lock != null) lock.releaseLock();
	    }
	    
	}
    }

    void deleteTransactions() {
	deleteDirectory(Constants.Files.save);
	deleteDirectory(Constants.Files.current);
	savedTrans.remove(savedTrans.getNodes());
	currTrans.remove(currTrans.getNodes());
    }


    void getTransactions() {

	if(debug) log("getTransactions"); //NOI18N 
       
	if(!haveDirectories()) {
	    // PENDING - report the error property
	    // This should not happen
	    log("Couldn't get the directory"); //NOI18N 
	    return;
	}

	Enumeration e = null;
	Vector nodes = new Vector(); 
	int numtns = 0;
	TransactionNode[] tns = null;
	FileObject fo = null;
	String id = null;
	MonitorData md = null;
	
	currTrans.remove(currTrans.getNodes());
	if(debug) log("getTransactions removed old nodes"); //NOI18N 

	e = currDir.getData(false);
	while(e.hasMoreElements()) {

	    fo = (FileObject)e.nextElement();
	    id = fo.getName();
	    if(debug) 
		log("getting current transaction: " + id); //NOI18N 
		    
	    // Retrieve the monitordata
	    md = retrieveMonitorData(id, currDir); 
	    nodes.add(createTransactionNode(md, true)); 
	}
	    
	numtns = nodes.size();
 	tns = new TransactionNode[numtns]; 
	for(int i=0;i<numtns;++i) 
	    tns[i] = (TransactionNode)nodes.elementAt(i);
	currTrans.add(tns);


	savedTrans.remove(savedTrans.getNodes());
	nodes = new Vector();
	e = saveDir.getData(false);
	while(e.hasMoreElements()) {

	    fo = (FileObject)e.nextElement();
	    id = fo.getName();
	    if(debug) 
		log("getting current transaction: " + id); //NOI18N 
	    // Retrieve the monitordata 
	    md = retrieveMonitorData(id, saveDir); 
	    nodes.add(createTransactionNode(md, false)); 
	}
	 
	numtns = nodes.size();
	tns = new TransactionNode[numtns]; 
	for(int i=0;i<numtns;++i) {
	    tns[i] = (TransactionNode)nodes.elementAt(i);
	    if(debug) 
		log("Adding saved node" + tns[i].toString()); //NOI18N 
		    
	}
	savedTrans.add(tns);
    }
	    
    private TransactionNode createTransactionNode(String str) {

	if(debug) log("createTransactionNode(String)"); //NOI18N 
	String id = str.substring(0, str.indexOf("|")); //NOI18N
	if(debug) log ("id is " + id); //NOI18N 
	// Retrieve the monitordata 
	MonitorData md = retrieveMonitorData(id, currDirStr); 
	return createTransactionNode(md, true);
    }
    

    private TransactionNode createTransactionNode(MonitorData md, boolean current) {

	if(debug) log("createTransactionNode(MonitorData)"); //NOI18N 
	Dispatches dis = md.getDispatches();
	TransactionNode node = null;
	
	// No dispatched requests, we add a regular transaction node
	if(dis == null || dis.sizeDispatchData() == 0 ) {
	    
	    if(debug) log("No dispatched requests"); //NOI18N 
	    
	    node = new TransactionNode(md.getAttributeValue("id"), // NOI18N
				       md.getAttributeValue("method"), // NOI18N
				       md.getAttributeValue("resource"), //NOI18N
				       current); // NOI18N
	}
	else {

	    int numChildren = dis.sizeDispatchData();
	    if(debug) log("We had some dispatched requests: " + //NOI18N 
			  String.valueOf(numChildren));
	    if(debug) log("\t for id " + //NOI18N 
			  md.getAttributeValue("resource")); //NOI18N 
	    // Create all the children. 1
	    Children.Array nested = new Children.Array();
	    
	    // First we create an array of children that has the same
	    // size as the set of nodes. 
	    NestedNode[] nds = new NestedNode[numChildren];
	    for(int i=0; i<numChildren; ++i) {
		if(debug) { 
		    log("Getting a new monitor data object"); //NOI18N 
		    log(dis.getDispatchData(i).getAttributeValue("resource")); //NOI18N 
		}
		nds[i] = createNestedNode(dis.getDispatchData(i),
					  md.getAttributeValue("method"), // NOI18N
					  null, i); 
	    }
	    
	    nested.add(nds);
	    node = new TransactionNode(md.getAttributeValue("id"), // NOI18N
				       md.getAttributeValue("method"), // NOI18N
				       md.getAttributeValue("resource"), //NOI18N
				       nested, current); // NOI18N

	}
	return node;
    }
    

    private NestedNode createNestedNode(DispatchData dd, 
					String method,
					int[] locator,
					int index) {


	Dispatches dis = dd.getDispatches();
	NestedNode node = null;

	int[] newloc = null;
	if(locator != null) {
	    newloc = new int[locator.length + 1];
	    int j=0;
	    while(j<locator.length) { 
		newloc[j] = locator[j];
		++j;
	    }
	    newloc[j]=index;
	}
	else {
	    newloc = new int[1]; 
	    newloc[0] = index;
	}
	
	// No dispatched requests, we add a regular transaction node
	if(dis == null || dis.sizeDispatchData() == 0 ) {
	    node = new NestedNode(dd.getAttributeValue("resource"),// NOI18N
				  method, newloc); 
	}
	else {
	    int numChildren = dis.sizeDispatchData();
	    Children.Array nested = new Children.Array();
	    NestedNode[] nds = new NestedNode[numChildren];
	    for(int i=0; i<numChildren; ++i) {
		nds[i] = createNestedNode(dis.getDispatchData(i),
					  method, newloc, i); 
	    }
	    
	    nested.add(nds);
	    node = new NestedNode(dd.getAttributeValue("resource"), // NOI18N
				  method, nested, newloc); 
	}
	return node;
    }


    /**
     * Sets the machine name and port of the web server. Not used in
     * this version, we do not support remote debugging.
     */
    public static void setServer(String loc, int p) {
	port = p;
	server = loc;
	return;
    }

    public void setComparator(Comparator comp) {
	currTrans.setComparator(comp);
	savedTrans.setComparator(comp);
    }

    public void setUseBrowserCookie(boolean value) { 
	useBrowserCookie = value;
	if(debug) 
	    log("Setting useBrowserCookie to " + //NOI18N
		String.valueOf(useBrowserCookie));
    }

    public boolean getUseBrowserCookie() { 
	return useBrowserCookie; 
    }
    
    /**
     * @param node A node on the Monitor GUI
     * @return a data record
     * Convenience method - this gets the DataRecord corresponding to
     * a node on the TransactionView panel from the cache if it is
     * present. This is used to display the data from the node. 
     */
    public DataRecord getDataRecord(AbstractNode node) {
	return getDataRecord(node, true);
    }
        
    /**
     * @param node A node on the Monitor GUI
     * @param fromCache true if it is OK to get the data record from
     * the cache
     * @return a data record
     */
    public DataRecord getDataRecord(AbstractNode anode, boolean fromCache) {

	if(debug) log("Entered getDataRecord()"); //NOI18N
	 
	if(anode instanceof TransactionNode) {

	    if(debug) log("TransactionNode"); //NOI18N
	    
	    // Since this method is used to retrieve data records for
	    // the purposes of displaying the transaction, we cache
	    // the result
	    return (DataRecord)(getMonitorData((TransactionNode)anode,
					       fromCache, true));
	}
	else if(anode instanceof NestedNode) {

	    NestedNode node = (NestedNode)anode;
	    
	    if(debug) log(node.toString()); 

	    int index[] = node.getIndex();

	    AbstractNode parent = (AbstractNode)node.getParentNode();
	    if(parent == null) {
		if(debug) log("null parent, something went wrong!"); //NOI18N
		return null;
	    }
	    
	    while(parent != null && !(parent instanceof TransactionNode)) {
		if(debug) log("Parent is not transaction node"); //NOI18N
		if(debug) log(parent.toString()); 
		parent = (AbstractNode)(parent.getParentNode());
	    }
	    
	    if(debug) log("We got the transaction node"); //NOI18N

	    // We get the data record, from cache if it is present,
	    // and cache the node also
	    DataRecord dr = 
		(DataRecord)(getMonitorData((TransactionNode)parent,
					    true, true));
	    int[] nodeindex = node.getIndex();
	    
	    int c = 0;
	    while(c<nodeindex.length) {
		if(debug) log("Doing the data record cycle"); //NOI18N
		if(debug) log(String.valueOf(c) + ":" + //NOI18N
			      String.valueOf(nodeindex[c])); 
		Dispatches dis = dr.getDispatches();
		dr = (DataRecord)dis.getDispatchData(nodeindex[c]);
		++c;
	    }
	    return dr;
	}
	return null;
    }
    
    /**
     * @param node A node on the Monitor GUI
     * @param fromCache true if it is OK to get the data record from
     * the cache
     * @param cacheIt true if it is OK to cache the data that we
     * retrieve 
     * @return a data record
     */
    public MonitorData getMonitorData(TransactionNode node, 
				      boolean fromCache,
				      boolean cacheIt) {

	String id = node.getID();
	Hashtable ht = null;
	FileObject dir = null;
	 
	if(node.isCurrent()) {
	    ht = currBeans;
	    dir = currDir;
	    if(debug) log("node is current"); //NOI18N 
	}
	else {
	    ht = saveBeans;
	    dir = saveDir;
	}
	
	if(debug) {
	    log("node id is " + node.getID()); //NOI18N 
	    log("using directory " + dir.getName()); //NOI18N 
	}

	if(fromCache && ht.containsKey(id)) 
	    return (MonitorData)(ht.get(id));
	    
	MonitorData md = retrieveMonitorData(id, dir);
	if(cacheIt) ht.put(id, md);
	return md;
    }

    /**
     * @param id The ID of the record
     * @param dirS The name of the directory in which the transaction
     * resides 
     **/    
    MonitorData retrieveMonitorData(String id, String dirS) {

	if(debug) 
	    log("retrieveMonitorData(String, String)"); //NOI18N 
	if(!haveDirectories()) {
	    // PENDING - report the error property
	    log("Couldn't get the directory"); //NOI18N 
	    return null;
	}
	
	FileObject dir = null;
	
	if (dirS.equalsIgnoreCase(currDirStr))  dir = currDir;
	else if (dirS.equalsIgnoreCase(saveDirStr)) dir = saveDir;
	else if (dirS.equalsIgnoreCase(replayDirStr)) dir = replayDir;

	if(debug) log("Directory = " + dir.getName()); //NOI18N 
	return retrieveMonitorData(id, dir);
    }
    

    MonitorData retrieveMonitorData(String id, FileObject dir) {

	// PENDING - this method needs an error reporting mechanism in
	// case the monitor data cannot be retrieved. Now it will just
	// return null. 
	if(debug)
	    log("retrieveMonitorData(String, FileObject)"); //NOI18N 
	if(!haveDirectories()) {
	    // PENDING - report the error property
	    log("Couldn't get the directory"); //NOI18N 
	    return null;
	}
	
	MonitorData md = null;
	FileObject fo = null;
	FileLock lock = null; 
	InputStreamReader in = null;
	
	try {
	    fo = dir.getFileObject(id, "xml"); // NOI18N
	    if(debug) log("From file: " + //NOI18N 
			  FileUtil.toFile(fo).getAbsolutePath()); 
	    if(debug) log("Locking it..."); //NOI18N 
	    lock = fo.lock();
	    if(debug) log("Getting InputStreamReader"); //NOI18N 
	    in = new InputStreamReader(fo.getInputStream()); 
	    if(debug) log("Creating monitor data"); //NOI18N 
	    md = MonitorData.createGraph(in);
	    try {
		if(dir == replayDir) fo.delete(lock);
	    }
	    catch(IOException ioex2) {} 
	} 
	catch(FileAlreadyLockedException falex) {
	    if(debug) { 
		log("File is locked: " + fo.getNameExt()); //NOI18N 
		falex.printStackTrace();
	    }
	}
	catch(IOException ioex) {
	    if(debug) { 
		log("Couldn't read data file: " + fo.getNameExt()); //NOI18N 
		ioex.printStackTrace();
	    }
	}
	catch(Exception ex) {
	    if(debug) { 
		log("Something went wrong when retrieving record"); //NOI18N 
		ex.printStackTrace();
	    }
	}
	finally {
	    try { in.close(); }
	    catch(Throwable t) {}
	    if(lock != null) lock.releaseLock();
	}
	if(debug) log("We're done!"); //NOI18N 
	return md;
    }

    private void showReplay(URL url) throws UnknownHostException,
	                                    IOException {
	
	if(debug) log("showReplay()"); // NOI18N
	if(debug) log("showReplay() url is " + url.toString()); // NOI18N
	// First we check that we can find a host of the name that's
	// specified 
	ServerCheck sc = new ServerCheck(url.getHost());
	Thread t = new Thread(sc);
	t.start();
	try {
	    t.join(2000);
	}
	catch(InterruptedException ie) {
	}
	t = null; 
	if(!sc.isServerGood()) {
	    if(debug) 
		log("showReplay(): No host"); // NOI18N
	    throw new UnknownHostException();
	}
	
	if(debug) log("performed server check"); // NOI18N

	// Next we see if we can connect to the server
	try {
	    Socket server = new Socket(url.getHost(), url.getPort());
	    server.close();
	    server = null;
	}
	catch(UnknownHostException uhe) {
	    if(debug) log("showReplay(): uhe2"); // NOI18N
	    throw uhe;
	}
	catch(IOException ioe) {
	    if(debug) 
		log("showReplay(): No service"); // NOI18N
	    throw ioe;
	}
	
	if(debug) log("showReplay(): reaching the end..."); // NOI18N

	if(browser == null) 
	    browser = 
		new HtmlBrowser.BrowserComponent(getFactory(), true, true);
	
	if(browser != null) {
	    browser.setURL(url);
	    browser.open();
	    if(!browser.isShowing()) browser.setVisible(true);
	    
	}
    }

    /* 
     * Get a factory objects for browsers. 
     */
    private  HtmlBrowser.Factory getFactory() {

	if(debug) log("getFactory()"); //NOI18N
	try {

	    FileObject fo = 
		Repository.getDefault().getDefaultFileSystem()
		.findResource("Services/Browsers"); //NOI18N
	    DataFolder folder = DataFolder.findFolder(fo);
	    DataObject[] dobjs = folder.getChildren();
	    for(int i = 0; i<dobjs.length; ++i) {
		Object attr = 
		    dobjs[i].getPrimaryFile()
		    .getAttribute("DEFAULT_BROWSER"); //NOI18N
		if(attr instanceof Boolean) {
		    if(Boolean.TRUE.equals(attr)) {
			try {
			    Object factory = 
				((InstanceCookie)dobjs[i].getCookie
				 (InstanceCookie.class)).instanceCreate(); 
			    if(debug) log("found the factory"); //NOI18N
			    return (HtmlBrowser.Factory)factory;
			}
			catch (java.io.IOException ex) {}
			catch (ClassNotFoundException ex) {}
		    }
		}
	    }
	    // There was no default browser set yet. Use the first 
	    // attribute that is not hidden. 
	    for (int i = 0; i<dobjs.length; ++i) {
		Object attr = 
		    dobjs[i].getPrimaryFile().getAttribute("hidden"); // NOI18N
		if(!Boolean.TRUE.equals(attr)) {
		    try {
			Object factory = 
			    ((InstanceCookie)dobjs[i].getCookie 
			     (InstanceCookie.class)).instanceCreate ();
			if(debug) log("Found a factory"); // NOI18N
			return(HtmlBrowser.Factory)factory;
		    }
		    catch (java.io.IOException ex) {}
		    catch (ClassNotFoundException ex) {}
		}
	    }
                
	    Lookup.Result result = 
		Lookup.getDefault().lookup
		(new Lookup.Template (HtmlBrowser.Factory.class));
	    java.util.Iterator it = result.allInstances().iterator();
	    if(it.hasNext()) {
		if(debug) log("used lookup"); //NOI18N
		return (HtmlBrowser.Factory)it.next ();
	    }
	    else return null;
	}
	catch (Exception ex) {
	    ErrorManager.getDefault().notify(ex);
	}
	return null;	 
    }

    /** 
     * Registers a listener to core events so that we know if the
     * browser has changed on the system. 
     */
    private void registerBrowserListener() {
        FileObject fo =	Repository.getDefault().getDefaultFileSystem()
	    .findResource("Services/org-netbeans-core-IDESettings.settings"); // NOI18N
        if (fo != null) {
            try {
                DataObject dobj = DataObject.find(fo);
                InstanceCookie.Of ic = 
		    (InstanceCookie.Of)dobj.getCookie(InstanceCookie.Of.class);
                if(ic.instanceOf(SystemOption.class)) {
                    try {
                        settings = (SystemOption)ic.instanceCreate();
                        browserListener = new SettingsListener(settings);
			settings.addPropertyChangeListener(browserListener);
		    }
                    catch (IOException ex) {
                    }
                    catch (ClassNotFoundException ex) {
		    }
                }
            }
            catch (DataObjectNotFoundException ex) {
                if(debug) ErrorManager.getDefault().notify(ex);
            }
        }
    }


    /** 
     * Removes the listener which detectes whether the browser setting
     * has changed on the system.
     */
    private void removeBrowserListener() {
	if(settings == null || browserListener == null) return;
	try {
	    settings.removePropertyChangeListener(browserListener);
	}
	catch(Exception ex) {
	}
    }

    // PENDING - use the logger instead
    private static void log(final String s) {
	System.out.println("Controller::" + s); //NOI18N
    }


    boolean checkServer(boolean replay) { 
	try { 
	    HttpServer.getRepositoryRoot();
	    return true;
	}
	catch(Throwable t) { 
	    Object[] options = {
		NbBundle.getBundle(Controller.class).getString("MON_OK"),
	    };
	    String msg = null;
	    if(replay) 
		msg = NbBundle.getBundle(Controller.class).getString("MON_CantReplay"); 
	    else
		msg = NbBundle.getBundle(Controller.class).getString("MON_NoServer");

	    
	    NotifyDescriptor noServerDialog = 
		new NotifyDescriptor(msg,
				     NbBundle.getBundle(Controller.class).getString("MON_NoServerTitle"),
				     NotifyDescriptor.DEFAULT_OPTION,
				     NotifyDescriptor.INFORMATION_MESSAGE,
				     options,
				     options[0]);
	    DialogDisplayer.getDefault().notify(noServerDialog);
	}
	return false;
    }
    

    class SettingsListener implements PropertyChangeListener {
	
	private SystemOption source;

	public SettingsListener(SystemOption source) {
	    this.source = source;
	}
	
	public void propertyChange(PropertyChangeEvent evt)  {
	    if(debug) 
		log("SettingsListener got property change event"); //NOI18N
	    if("WWWBrowser".equals(evt.getPropertyName())) { //NOI18N
		browser = null;
	    }
	}
    }
     
    
    /**
     * Does the server we try to replay on exist? 
     */
    class ServerCheck implements Runnable {	 

	boolean serverGood = false;
	String serverName = null;
	
	public ServerCheck(String name) {
	    serverName = name;
	}
	
	public void run() {
	    try {
		InetAddress.getByName(serverName);
		serverGood = true;
		
	    }
	    catch (UnknownHostException e) {
		serverGood = false; 
	    }	 
	}
	
	public boolean isServerGood() {
	    return serverGood;
	}
	
    }

    /**
     * Sort by time
     */
    class CompTime implements Comparator {

	boolean descend = true;

	CompTime(boolean descend) {
	    this.descend = descend;
	}

	public int compare(Object o1, Object o2) {

	    if(debug) log("In compareTime"); //NOI18N
	    TransactionNode n1 = (TransactionNode)o1;
	    TransactionNode n2 = (TransactionNode)o2;

	    if(debug) log("Cast the nodes"); //NOI18N
	    if(debug) {
		log("Comparing " + String.valueOf(o1) + //NOI18N
		    " and " + String.valueOf(o2)); //NOI18N
		try {
		    log(n1.getID());
		    log(n2.getID());
		}
		catch(Exception ex) {};
	    }
	    int result;
	    if(descend)
		result = n1.getID().compareTo(n2.getID());
	    else result = n2.getID().compareTo(n1.getID());
	    if(debug) log("End of compareTime"); //NOI18N
	    return result;
	}
    }

    // Really dumb way of forcing this, but I couldn't get the tree to 
    // repaint... Will remove this method when that works. 
    public void updateNodeNames() {
	
	TransactionNode tn;
	
	Node[] nodes = currTrans.getNodes();
	int size = nodes.length;
	for(int i=0; i<size; ++i) {
	    tn = (TransactionNode)nodes[i];
	    tn.setNameString();
	}
	
	nodes = savedTrans.getNodes();
	size = nodes.length;
	for(int i=0; i<size; ++i) {
	    tn = (TransactionNode)nodes[i];
	    tn.setNameString();
	}
    }
    
    /**
     * Sort alphabetically
     */
    class CompAlpha implements Comparator {

	public int compare(Object o1, Object o2) {
	    if(debug) log("In compareAlpha"); //NOI18N
	    TransactionNode n1 = (TransactionNode)o1;
	    TransactionNode n2 = (TransactionNode)o2;
	    if(debug) log("cast the nodes"); //NOI18N
	    if(debug) {
		log("Comparing " + String.valueOf(o1) + //NOI18N
		    " and " + String.valueOf(o2)); //NOI18N
		try {
		    log("names"); //NOI18N
		    log(n1.getName());
		    log(n2.getName());
		    log("IDs");  //NOI18N
		    log(n1.getID());
		    log(n2.getID());
		}
		catch(Exception ex) {};
	    }
	    int diff = n1.getName().compareTo(n2.getName());
	    if(diff == 0)
		return n1.getID().compareTo(n2.getID());
	    else
		return diff;
	}
    }
} // Controller
