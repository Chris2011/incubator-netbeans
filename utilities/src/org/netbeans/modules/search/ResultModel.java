/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2008 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */


package org.netbeans.modules.search;

import java.nio.charset.Charset;
import java.util.List;
import org.netbeans.modules.search.Constants.Limit;
import org.openide.ErrorManager;
import org.openide.filesystems.FileObject;
import org.openide.nodes.Node;


/**
 * Holds search result data.
 *
 * @author  Petr Kuzel
 * @author  Marian Petras
 */
public final class ResultModel {

    /** Common search root */
    private FileObject commonSearchRoot;

    /** */
    private final long creationTime;   
    /** */
    private int totalDetailsCount = 0;
    /**
     */
    private ResultTreeModel treeModel;
    /** */
    private ResultViewPanel resultView;
    
    /**
     * limit (number of found files or matches) reached during search
     */
    private Limit limitReached = null;
    
    /**
     * are all search types defined in the {@code SearchGroup} those
     * defined in the Utilities module?
     */
    final boolean isBasicCriteriaOnly = true;
    /** */
    final BasicSearchCriteria basicCriteria;
    /** */
    private final boolean isFullText;
    /** */
    final String replaceString;
    /** */
    final boolean searchAndReplace;
    /** list of matching objects (usually {@code DataObject}s) */
    private final List<MatchingObject> matchingObjects =
            new ArraySet<MatchingObject>(Constants.COUNT_LIMIT).ordering(true).
            nullIsAllowed(false);

    /** Contains optional finnish message often reason why finished. */
    private String finishMessage;

    /** Creates new <code>ResultModel</code>. */
    ResultModel(BasicSearchCriteria basicSearchCriteria,
                       String replaceString) {

        this.replaceString = replaceString;
        this.searchAndReplace = (replaceString != null);

	basicCriteria = basicSearchCriteria;
	isFullText = (basicCriteria != null) && basicCriteria.isFullText();        
        creationTime = System.currentTimeMillis();
    }
    
    /**
     */
    long getCreationTime() {
        return creationTime;
    }
    
    /**
     * Sets an observer which will be notified whenever an object is found.
     *
     * @param  observer  observer or <code>null</code>
     */
    void setObserver(ResultTreeModel observer) {
        this.treeModel = observer;
    }
    
    /**
     * Sets an observer which will be notified whenever an object is found.
     *
     * @param  observer  observer or <code>null</code>
     */
    void setObserver(ResultViewPanel observer) {
        this.resultView = observer;
    }

    /**
     * Clean the allocated resources. Do not rely on GC there as we are often
     * referenced from various objects. So keep leak as small as possible.
     * */
    synchronized void close() {
        if ((matchingObjects != null) && !matchingObjects.isEmpty()) {
            for (MatchingObject matchingObj : matchingObjects) {
                matchingObj.cleanup();
            }
        }
        
        // eliminate search group content
        // no other way then leaving it on GC, it should work because
        // search group is always recreated by a it's factory and
        // nobody keeps reference to it. 7th May 2004
    }

    /**
     * Notifies ths result model of a newly found matching object.
     *
     * @param  object  matching object
     * @return  {@code true} if this result model can accept more objects,
     *          {@code false} if number of found objects reached the limit, or
     *          the specified {@code object} already exists in the result model.
     * @param  charset  charset used for full-text search of the object,
     *                  or {@code null} if the object was not full-text searched
     */
    synchronized boolean objectFound(Object object, Charset charset,
            List<TextDetail> textDetails) {
        assert limitReached == null;
        assert treeModel != null;
        assert resultView != null;
        MatchingObject mo = new MatchingObject(this, object, charset,
                textDetails);
        if(add(mo)) {
            totalDetailsCount += getDetailsCount(mo);
            treeModel.objectFound(mo, matchingObjects.indexOf(mo));
            resultView.objectFound(mo, totalDetailsCount);
            return !checkLimits();
        } else {
            mo.cleanup();
        }
        return false; // MatchingObject already exists
    }

    private boolean add(MatchingObject matchingObject) {
        try {
            return matchingObjects.add(matchingObject);
        } catch (IllegalStateException ise) {
            limitReached = Limit.FILES_COUNT_LIMIT;
            return false;
        } catch(IllegalArgumentException iae) {
            return false; // matchingObject already added.
        }
    }

    private boolean checkLimits() {
// ArraySet and add(MatchingObject matchingObject) do it.
//        if (size() >= COUNT_LIMIT) {
//            limitReached = Limit.FILES_COUNT_LIMIT;
//            return true;
//        }
//        else
        if (totalDetailsCount >= Constants.DETAILS_COUNT_LIMIT) {
            limitReached = Limit.MATCHES_COUNT_LIMIT;
            return true;
        }
        return false;
    }

    /**
     */
    synchronized void objectBecameInvalid(MatchingObject matchingObj) {
        
        /* may be called from non-EDT thread */
        
        int index = matchingObjects.indexOf(matchingObj);
        assert index != -1;
        
        treeModel.objectBecameInvalid(matchingObj);
    }
    
    /**
     */
    synchronized int getTotalDetailsCount() {
        return totalDetailsCount;
    }
    
    /**
     * @return a list of the {@code MatchingObject}s associated to this
     * {@code ResultModel}.
     */
    synchronized List<MatchingObject> getMatchingObjects() {
        return matchingObjects;
    }
    
    /**
     */
    boolean hasDetails() {
        return totalDetailsCount != 0;      //PENDING - synchronization?
    }
    
    /**
     * Performs a quick check whether
     * {@linkplain MatchingObject matching objects} contained in this model
     * can have details.
     * 
     * @return  {@code Boolean.TRUE} if all matching objects have details,
     *          {@code Boolean.FALSE} if no matching object has details,
     *          {@code null} if matching objects may have details
     *                         (if more time consuming check would be necessary)
     */
    Boolean canHaveDetails() {
        Boolean ret;
        if (isFullText) {
            ret = Boolean.TRUE;
        } else if (isBasicCriteriaOnly) {
            ret = Boolean.FALSE;
        } else {
            ret = null;
        }
        return ret;
    }
    
    /*
     * A cache exists for information about a single MatchingObject
     * to prevent from repetitive calls of time-consuming queries on
     * number of details and list of details. These calls are initiated
     * by the node renderer (class NodeRenderer).
     */
    
    private MatchingObject infoCacheMatchingObject;
    private Boolean infoCacheHasDetails;
    private int infoCacheDetailsCount;
    private Node[] infoCacheDetailNodes;
    private final Node[] EMPTY_NODES_ARRAY = new Node[0];
    
    /**
     */
    private void prepareCacheFor(MatchingObject matchingObject) {
        if (matchingObject != infoCacheMatchingObject) {
            infoCacheHasDetails = null;
            infoCacheDetailsCount = -1;
            infoCacheDetailNodes = null;
            infoCacheMatchingObject = matchingObject;
        }
    }
    
    /**
     */
    synchronized int getDetailsCount(MatchingObject matchingObject) {
        prepareCacheFor(matchingObject);
        if (infoCacheDetailsCount == -1) {
            infoCacheDetailsCount = getDetailsCountReal(matchingObject);
            if (infoCacheDetailsCount == 0) {
                infoCacheDetailNodes = EMPTY_NODES_ARRAY;
            }
        }
        
        assert infoCacheDetailsCount >= 0;
        return infoCacheDetailsCount;
    }
    
    /**
     * Returns number of detail nodes available to the given found object.
     *
     * @param  foundObject  object matching the search criteria
     * @return  number of detail items (represented by individual nodes)
     *          available for the given object (usually {@code DataObject})
     */
    private int getDetailsCountReal(MatchingObject matchingObject) {
        int count = isFullText ? 
                matchingObject.getDetailsCount() : 0;
        
        return count;
    }
    
    /**
     * Gets detail nodes associated with the specified {@code MatchingObject}.
     * @param matchingObject the {@code MatchingObject} or {@code null}.
     * @return  non-empty array of detail nodes
     *          or {@code null} if either there are no associated detail nodes
     *          or {@code matchingObject} is {@code null}.
     */
    synchronized Node[] getDetails(MatchingObject matchingObject) {
        if(matchingObject == null) {
            return null;
        }
        prepareCacheFor(matchingObject);
        Node[] detailNodes;
        if (infoCacheDetailNodes == null) {
            detailNodes = getDetailsReal(matchingObject);
            infoCacheDetailNodes = (detailNodes != null)
                                   ? detailNodes
                                   : EMPTY_NODES_ARRAY;
            infoCacheDetailsCount = infoCacheDetailNodes.length;
        } else {
            detailNodes = (infoCacheDetailNodes != EMPTY_NODES_ARRAY)
                          ? infoCacheDetailNodes
                          : null;
        }
        
        assert (infoCacheDetailNodes != null)
               && ((infoCacheDetailNodes == EMPTY_NODES_ARRAY)
                   || (infoCacheDetailNodes.length > 0));
        assert (detailNodes == null) || (detailNodes.length > 0);
        return detailNodes;
    }
    
    /**
     * 
     * @return  non-empty array of detail nodes
     *          or {@code null} if there are no detail nodes
     */
    private Node[] getDetailsReal(MatchingObject matchingObject) {
        Node[] nodesTotal = null;
        if (basicCriteria != null) {
            nodesTotal = basicCriteria.isFullText()
                         ? matchingObject.getDetails()
                         : null;
	}
        
        return nodesTotal;                
    }
    
    /**
     */
    synchronized int size() {
        return matchingObjects.size();
    }

    /**
     */
    synchronized boolean wasLimitReached() {
        return limitReached != null;
    }

    /**
     */
    String getLimitDisplayName() {
        return (limitReached != null) ? limitReached.getDisplayName() : null;
    }

    /** This exception stoped search */
    synchronized void searchException(RuntimeException ex) {
        ErrorManager.Annotation[] annotations =
                ErrorManager.getDefault().findAnnotations(ex);
        for (int i = 0; i < annotations.length; i++) {
            ErrorManager.Annotation annotation = annotations[i];
            if (annotation.getSeverity() == ErrorManager.USER) {
                finishMessage = annotation.getLocalizedMessage();
                if (finishMessage != null) return;
            }
        }
        finishMessage = ex.getLocalizedMessage();
    }
    
    /**
     */
    synchronized String getExceptionMsg() {
        return finishMessage;
    }

    ResultViewPanel getResultView(){
        return resultView;
    }

    /**
     * Get common search folder. Can be null.
     */
    synchronized FileObject getCommonSearchFolder() {
        return commonSearchRoot;
    }

    /**
     * Set common search null. Can be null.
     */
    synchronized void setCommonSearchFolder(FileObject fo) {
        this.commonSearchRoot = fo;
    }
}
