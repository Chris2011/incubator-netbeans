/*
 * RMIAuthenticatedUser.java
 *
 * Created on April 26, 2005, 10:44 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.netbeans.modules.jmx.configwizard;

import java.io.ByteArrayInputStream;
import java.util.Properties;

/**
 *
 * @author jfdenise
 */
public class RMIAuthenticatedUser {
    private String name;
    private String password;
    private String access = "readonly";
    /** Creates a new instance of RMIAuthenticatedUser */
    public RMIAuthenticatedUser() {
    }
    
    private static boolean isValidKey(String key) {
       if(key == null || key.equals("")) return false;
        
       boolean precedingBackslash = false;
       char[] val = key.toCharArray();
       int offset = 0;
       while(offset < val.length) {
           char c = val[offset];
           if ((c > 59) && (c < 127)) {
               if (c == '\\') {
                   char aChar = val[offset++];
                   if(aChar == 'u') {
                       // Read the xxxx
                       int value=0;
                       for (int i=0; i<4; i++) {
                           aChar = val[offset++];
                           switch (aChar) {
                               case '0': case '1': case '2': case '3': case '4':
                               case '5': case '6': case '7': case '8': case '9':
                               case 'a': case 'b': case 'c':
                               case 'd': case 'e': case 'f':
                               case 'A': case 'B': case 'C':
                               case 'D': case 'E': case 'F':
                                   break;
                               default:
                                   return false;
                           }
                       }
                   }
                   precedingBackslash = !precedingBackslash;
               } else
                   precedingBackslash = false;
               
               offset++;
               continue;
           }
          
          switch(c) {
              case ':':
              case '=':
              case '#':
              case ' ':    
                if(!precedingBackslash) return false;
          }
          offset++;
       }
       return true;
    }
    
    private String cleanPassword() {  
       if(password == null || password.equals("")) return null; 
       return password;
    }
    
    public boolean isValid() {
        return (isValidKey(name)) && (cleanPassword() != null);
    }
    
    public Object getValueAt(int col) {
        switch(col) {
            case 0: return name;
            case 1: return password;
            case 2: return access;
        }
        return null;
    }
    
    public void setValueAt(Object value, int col) {
        switch(col) {
            case 0:  name = (String) value;
            break;
            case 1:  password = (String) value;
            break;
            case 2:  access = (String) value;
            break;
        }
    }
    
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getAccess() {
        return access;
    }
    
}
