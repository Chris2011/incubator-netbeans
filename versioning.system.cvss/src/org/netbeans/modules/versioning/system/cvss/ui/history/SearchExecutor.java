/*
 *                 Sun Public License Notice
 *
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 *
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2005 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.versioning.system.cvss.ui.history;

import org.netbeans.lib.cvsclient.command.log.RlogCommand;
import org.netbeans.lib.cvsclient.command.log.LogInformation;
import org.netbeans.lib.cvsclient.command.log.LogCommand;
import org.netbeans.modules.versioning.system.cvss.ui.actions.log.RLogExecutor;
import org.netbeans.modules.versioning.system.cvss.ui.actions.log.LogExecutor;
import org.netbeans.modules.versioning.system.cvss.ExecutorSupport;
import org.netbeans.modules.versioning.system.cvss.ExecutorGroup;

import javax.swing.*;
import java.io.File;
import java.util.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.openide.util.NbBundle;

/**
 * Executes searches in Search History panel.
 * 
 * @author Maros Sandor
 */
class SearchExecutor implements Runnable {

    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");  // NOI18N
    
    private static final SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");  // NOI18N
    private static final DateFormat [] dateFormats = new DateFormat[] {
        fullDateFormat,
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),  // NOI18N
        simpleDateFormat,
        new SimpleDateFormat("yyyy-MM-dd"), // NOI18N
    };
    
    private final SearchHistoryPanel    master;
    private final File[]                folders;
    private final File[]                files;
    private final SearchCriteriaPanel   criteria;
    
    private List                        results;

    public SearchExecutor(SearchHistoryPanel master) {
        this.master = master;
        File [] roots = master.getRoots();
        
        Set foldersSet = new HashSet(roots.length); 
        Set filesSet = new HashSet(roots.length); 
        for (int i = 0; i < roots.length; i++) {
            File root = roots[i];
            if (root.isFile()) {
                filesSet.add(root);
            } else {
                foldersSet.add(root);
            }
        }
        files = (File[]) filesSet.toArray(new File[filesSet.size()]);
        folders = (File[]) foldersSet.toArray(new File[foldersSet.size()]);
        criteria = master.getCriteria();
    }

    public void run() {
        String from = criteria.getFrom();
        String to = criteria.getTo();
        Date fromDate = parseDate(from);
        Date toDate = parseDate(to);

        RlogCommand rcmd = new RlogCommand();
        LogCommand lcmd = new LogCommand();

        if (fromDate != null || toDate != null) {
            String dateFilter = ""; // NOI18N
            if (fromDate != null) {
                dateFilter = fullDateFormat.format(fromDate);
            }
            dateFilter += "<="; // NOI18N
            if (toDate != null) {
                dateFilter += fullDateFormat.format(toDate);
            }
            rcmd.setDateFilter(dateFilter);
            lcmd.setDateFilter(dateFilter);
        } else if (from != null || to != null) {
            String revFilter = ""; // NOI18N
            if (from != null) {
                revFilter = from;
            }
            revFilter += ":"; // NOI18N
            if (to != null) {
                revFilter += to;
            }
            rcmd.setRevisionFilter(revFilter);
            lcmd.setRevisionFilter(revFilter);
        }
        
        rcmd.setNoTags(true);
        rcmd.setUserFilter(criteria.getUsername());
        lcmd.setUserFilter(criteria.getUsername());

        ExecutorGroup group = new ExecutorGroup(NbBundle.getMessage(SearchExecutor.class, "BK0001"));
        RLogExecutor [] rexecutors;
        if (folders.length > 0) {
            rexecutors = RLogExecutor.splitCommand(rcmd, folders, null);
        } else {
            rexecutors = new RLogExecutor[0];
        }
        group.addExecutors(rexecutors);

        LogExecutor [] lexecutors;
        if (files.length > 0) {
            lcmd.setFiles(files);
            lexecutors = LogExecutor.splitCommand(lcmd, null);
        } else {
            lexecutors = new LogExecutor[0];
        }
        group.addExecutors(lexecutors);

        final RLogExecutor [] frexecutors = rexecutors;
        final LogExecutor [] flexecutors = lexecutors;
        Runnable action = new Runnable() {
            public void run() {
                results = processResults(frexecutors, flexecutors);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        master.setResults(results);
                    }
                });
            }
        };
        group.addBarrier(action);
        group.execute();

    }

    private List processResults(RLogExecutor[] rexecutors, LogExecutor[] lexecutors) {
        List log = new ArrayList(200);
        for (int i = 0; i < rexecutors.length; i++) {
            RLogExecutor executor = rexecutors[i];
            log.addAll(executor.getLogEntries());
        }
        for (int i = 0; i < lexecutors.length; i++) {
            LogExecutor executor = lexecutors[i];
            log.addAll(executor.getLogEntries());
        }
        String commitMessage = criteria.getCommitMessage();

        List newResults = new ArrayList(log.size());
        for (Iterator i = log.iterator(); i.hasNext();) {
            LogInformation info = (LogInformation) i.next();
            newResults.addAll(info.getRevisionList());
        }

        if (commitMessage != null) {
            for (Iterator i = newResults.iterator(); i.hasNext();) {
                LogInformation.Revision revision = (LogInformation.Revision) i.next();
                String msg = revision.getMessage();
                if (msg.indexOf(commitMessage) == -1) {
                    i.remove();
                }
            }
        }

        return newResults;
    }
    
    private Date parseDate(String s) {
        if (s == null) return null;
        for (int i = 0; i < dateFormats.length; i++) {
            DateFormat dateformat = dateFormats[i];
            try {
                return dateformat.parse(s);
            } catch (ParseException e) {
                // try the next one
            }
        }
        return null;
    }

}
