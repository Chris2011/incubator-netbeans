/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
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
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
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

package org.netbeans.modules.welcome.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import org.netbeans.modules.welcome.content.BundleSupport;
import org.netbeans.modules.welcome.content.Constants;
import org.netbeans.modules.welcome.content.RSSFeed;
import org.netbeans.modules.welcome.content.RSSFeedReaderPanel;
import org.netbeans.modules.welcome.content.Utils;
import org.netbeans.modules.welcome.content.WebLink;
import org.openide.awt.Mnemonics;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Utilities;

/**
 *
 * @author S. Aubrecht
 */
class DemoPanel extends RSSFeedReaderPanel {

    public DemoPanel() {
        super( BundleSupport.getURL( "Demo" ) ); //NOI18N

        //add( buildBottomContent(), BorderLayout.SOUTH );
    }

    @Override
    protected JComponent buildContent(String url, boolean showProxyButton) {
        JPanel res = new JPanel( new GridBagLayout() );
        res.setOpaque( false );
        
        DemoRSSFeed feed = new DemoRSSFeed( url );
        res.add( feed, new GridBagConstraints(0,0,1,1,1.0,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0) );
        res.add( buildBottomContent(), new GridBagConstraints(0,1,1,1,0.0,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0),0,0) );
        res.add( new JLabel(), new GridBagConstraints(0,2,1,1,0.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0) );
        
        return res;
    }

    protected JComponent buildBottomContent() {
        WebLink allBlogs = new WebLink( "AllDemos", false ); // NOI18N
        BundleSupport.setAccessibilityProperties( allBlogs, "AllDemos" ); //NOI18N

        JPanel panel = new JPanel( new GridBagLayout() );
        panel.setOpaque( false );
        panel.add( allBlogs, new GridBagConstraints(1,0,1,1,0.0,0.0,GridBagConstraints.SOUTHEAST,GridBagConstraints.HORIZONTAL,new Insets(5,5,0,5),0,0) );
        panel.add( new JLabel(), new GridBagConstraints(0,0,1,1,1.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0),0,0) );

        return panel;
    }
    
    class DemoRSSFeed extends RSSFeed {
        public DemoRSSFeed( String url ) {
            super( url, false );
        }

        @Override
        protected Component createFeedItemComponent(FeedItem item) {
            JPanel panel = new JPanel( new GridBagLayout() );
            panel.setOpaque( false );
            int row = 0;

            if( item.isValid() ) {
                WebLink linkButton = new WebLink( item.title, item.link, false );
                linkButton.getAccessibleContext().setAccessibleName( 
                        BundleSupport.getAccessibilityName( "WebLink", item.title ) ); //NOI18N
                linkButton.getAccessibleContext().setAccessibleDescription( 
                        BundleSupport.getAccessibilityDescription( "WebLink", item.link ) ); //NOI18N
                linkButton.setFont( BUTTON_FONT );
                panel.add( linkButton, new GridBagConstraints(0,row++,1,1,0.0,0.0,
                        GridBagConstraints.WEST,GridBagConstraints.NONE,
                        new Insets(0,5,2,TEXT_INSETS_RIGHT),0,0 ) );


                if( item.enclosureUrl != null ) {
                    panel.add( new ImageLabel( item.link, getImage( item.enclosureUrl ), item.description ), new GridBagConstraints(0,row++,1,1,0.0,0.0,
                            GridBagConstraints.CENTER,GridBagConstraints.NONE,
                            new Insets(10,5,5,5),0,0 ) );
                } else {
                    JLabel label = new JLabel( BundleSupport.getLabel("NoScreenShot") ); //NOI18N
                    label.setHorizontalAlignment( JLabel.CENTER );
                    label.setVerticalAlignment( JLabel.CENTER );
                    panel.add( label, new GridBagConstraints(0,row++,1,1,0.0,0.0,
                            GridBagConstraints.CENTER,GridBagConstraints.NONE,
                            new Insets(0,TEXT_INSETS_LEFT+5,0,TEXT_INSETS_RIGHT),0,0 ) );
                }
            } else {
                panel.add( new JLabel(BundleSupport.getLabel("ErrLoadingFeed")),  // NOI18N
                        new GridBagConstraints(0,row++,1,1,0.0,0.0,
                        GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(5,10,10,5),0,0 ) );
                JButton button = new JButton();
                Mnemonics.setLocalizedText( button, BundleSupport.getLabel( "Reload" ) );  // NOI18N
                button.setOpaque( false );
                button.addActionListener( new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lastReload = 0;
                        reload();
                    }
                });
                panel.add( button, new GridBagConstraints(0,row++,1,1,0.0,0.0,
                        GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(5,10,10,5),0,0 ) );
            }
            
            return panel;
        }

        @Override
        protected int getMaxItemCount() {
            return 1;
        }
        
        ImageIcon getImage( String urlString ) {
            URL url = null;
            try {
                url = new URL( urlString );
            } catch( MalformedURLException mfuE ) {
                mfuE.printStackTrace();
            }
            ImageIcon image = null;
            if( isContentCached() ) {
                ObjectInputStream input = null;
                try {
                    input = new ObjectInputStream( new FileInputStream( getCacheFilePath() ) );
                    image = (ImageIcon)input.readObject();
                    Logger.getLogger( DemoPanel.class.getName() ).log( Level.FINE, 
                            "Demo image loaded from: " + getCacheFilePath() ); //NOI18N
                }catch( Exception e ) {
                    image = null;
                } finally {
                    if( null != input )
                        try { input.close(); } catch( IOException e ) {}
                }
            }

            if( null == image ) {
                image = new ImageIcon( url );
                ObjectOutputStream output = null;
                try {
                    output = new ObjectOutputStream( new FileOutputStream( getCacheFilePath() ) );
                    output.writeObject( image );
                } catch( Exception e ) {
                    Logger.getLogger( DemoPanel.class.getName() ).log( Level.FINE, 
                            "Error while caching Welcome Page demo image", e ); //NOI18N
                    image = new ImageIcon( Utilities.loadImage( Constants.BROKEN_IMAGE ) );
                } finally {
                    if( null != output ) {
                        try { output.close(); } catch( IOException e ) {}
                    }
                }
            }
            
            return image;
        }
        
        private File getCacheFilePath() throws IOException {
            File cacheStore = Utils.getCacheStore();
            cacheStore = new File(cacheStore, "demoimage" ); //NOI18N
            cacheStore.getParentFile().mkdirs();
            cacheStore.createNewFile();
            return cacheStore;
        }
    }
    
    private static class ImageLabel extends JLabel implements Constants, MouseListener {
        private String url;
        private boolean visited = false;
        
        public ImageLabel( String url, ImageIcon img, String description ) {
            super( new MaxSizeImageIcon(img) );
            this.url = url;
            if( null != description )
                setToolTipText( "<html>" + description ); //NOI18N
            setOpaque( false );
            setBorder( BorderFactory.createEmptyBorder(1,1,1,1) );
            addMouseListener( this );
            setCursor( Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) );
        }

        public void mouseClicked(MouseEvent e) {
            if( !e.isPopupTrigger() ) {
                visited = true;
                Utils.showURL( url );
                mouseEntered( null );
            }
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
            Color borderColor = Utils.getColor( visited ? VISITED_LINK_COLOR : MOUSE_OVER_LINK_COLOR  );
            setBorder( BorderFactory.createLineBorder(borderColor, 1) );
            StatusDisplayer.getDefault().setStatusText( url );
        }

        public void mouseExited(MouseEvent e) {
            setBorder( BorderFactory.createEmptyBorder(1,1,1,1) );
            StatusDisplayer.getDefault().setStatusText( "" );
        }

        @Override
        public JToolTip createToolTip() {
            JToolTip tip = super.createToolTip();
            JLabel lbl = new JLabel( getToolTipText() );
            Dimension preferredSize = lbl.getPreferredSize();
            
            FontMetrics fm = tip.getFontMetrics( tip.getFont() );
            int lines = preferredSize.width / 500;
            if( preferredSize.width % 500 > 0 )
                lines++;
            preferredSize.height =  Math.min( lines * fm.getHeight() + 10, 300 );
            preferredSize.width = 500;
            tip.setPreferredSize( preferredSize );
            return tip;
        }
    }
    
    private static class MaxSizeImageIcon implements Icon, Constants {
        private static final int MAX_IMAGE_WIDTH = 202;
        private static final int MAX_IMAGE_HEIGHT = 142;
        private Image frame;
        
        ImageIcon orig;
        public MaxSizeImageIcon( ImageIcon orig ) {
            this.orig = orig;
            frame = Utilities.loadImage( IMAGE_PICTURE_FRAME );
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            if( orig.getIconWidth() > MAX_IMAGE_WIDTH )
                x -= (orig.getIconWidth() - MAX_IMAGE_WIDTH) / 2;
            if( orig.getIconHeight() > MAX_IMAGE_HEIGHT )
                y -= (orig.getIconHeight() - MAX_IMAGE_HEIGHT) / 2;
            g.setClip(0,0,MAX_IMAGE_WIDTH,MAX_IMAGE_HEIGHT);
            orig.paintIcon(c, g, x, y);
            g.drawImage(frame, 0, 0, c);
        }

        public int getIconWidth() {
            return Math.min( orig.getIconWidth(), MAX_IMAGE_WIDTH );
        }

        public int getIconHeight() {
            return Math.min( orig.getIconHeight(), MAX_IMAGE_HEIGHT );
        }
    }
}
