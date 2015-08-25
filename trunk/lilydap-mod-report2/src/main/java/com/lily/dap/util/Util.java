/*
 * Copyright 1999-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package com.lily.dap.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class Util {

    public static final int HIGHEST_SPECIAL = '>';
    public static char[][] specialCharactersRepresentation = new char[HIGHEST_SPECIAL + 1][];
    static {
        specialCharactersRepresentation['&'] = "&amp;".toCharArray();
        specialCharactersRepresentation['<'] = "&lt;".toCharArray();
        specialCharactersRepresentation['>'] = "&gt;".toCharArray();
        specialCharactersRepresentation['"'] = "&#034;".toCharArray();
        specialCharactersRepresentation['\''] = "&#039;".toCharArray();
    }

    /**
     * Performs the following substring replacements
     * (to facilitate output to XML/HTML pages):
     *
     *    & -> &amp;
     *    < -> &lt;
     *    > -> &gt;
     *    " -> &#034;
     *    ' -> &#039;
     *
     * See also OutSupport.writeEscapedXml().
     */
    public static String escapeXml(String buffer) {
        int start = 0;
        int length = buffer.length();
        char[] arrayBuffer = buffer.toCharArray();
        StringBuffer escapedBuffer = null;

        for (int i = 0; i < length; i++) {
            char c = arrayBuffer[i];
            if (c <= HIGHEST_SPECIAL) {
                char[] escaped = specialCharactersRepresentation[c];
                if (escaped != null) {
                    // create StringBuffer to hold escaped xml string
                    if (start == 0) {
                        escapedBuffer = new StringBuffer(length + 5);
                    }
                    // add unescaped portion
                    if (start < i) {
                        escapedBuffer.append(arrayBuffer,start,i-start);
                    }
                    start = i + 1;
                    // add escaped xml
                    escapedBuffer.append(escaped);
                }
            }
        }
        // no xml escaping was necessary
        if (start == 0) {
            return buffer;
        }
        // add rest of unescaped portion
        if (start < length) {
            escapedBuffer.append(arrayBuffer,start,length-start);
        }
        return escapedBuffer.toString();
    }

    /**
     * Get the value associated with a content-type attribute.
     * Syntax defined in RFC 2045, section 5.1.
     */
    public static String getContentTypeAttribute(String input, String name) {
	int begin;
	int end;
        int index = input.toUpperCase().indexOf(name.toUpperCase());
        if (index == -1) return null;
        index = index + name.length(); // positioned after the attribute name
        index = input.indexOf('=', index); // positioned at the '='
        if (index == -1) return null;
        index += 1; // positioned after the '='
        input = input.substring(index).trim();
        
        if (input.charAt(0) == '"') {
            // attribute value is a quoted string
            begin = 1;
            end = input.indexOf('"', begin);
            if (end == -1) return null;
        } else {
            begin = 0;
            end = input.indexOf(';');
            if (end == -1) end = input.indexOf(' ');
            if (end == -1) end = input.length();
        }
        return input.substring(begin, end).trim();
    }
    
    public static String decode(String s) {
    	s = EscapeUtils.unescape(s);
    	try {
			s = new String(s.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			
			return "";
		}

    	return s;
    }
    
    /**
     * URL encodes a string, based on the supplied character encoding.
     * This performs the same function as java.next.URLEncode.encode
     * in J2SDK1.4, and should be removed if the only platform supported
     * is 1.4 or higher.
     * @param s The String to be URL encoded.
     * @param enc The character encoding 
     * @return The URL encoded String
     * [taken from jakarta-tomcat-jasper/jasper2
     *  org.apache.jasper.runtime.JspRuntimeLibrary.java]
     */
    public static String URLEncode(String s, String enc) {

	if (s == null) {
	    return "null";
	}

	if (enc == null) {
	    enc = "UTF-8";	// Is this right?
	}

	StringBuffer out = new StringBuffer(s.length());
	ByteArrayOutputStream buf = new ByteArrayOutputStream();
	OutputStreamWriter writer = null;
	try {
	    writer = new OutputStreamWriter(buf, enc);
	} catch (java.io.UnsupportedEncodingException ex) {
	    // Use the default encoding?
	    writer = new OutputStreamWriter(buf);
	}
	
	for (int i = 0; i < s.length(); i++) {
	    int c = s.charAt(i);
	    if (c == ' ') {
		out.append('+');
	    } else if (isSafeChar(c)) {
		out.append((char)c);
	    } else {
		// convert to external encoding before hex conversion
		try {
		    writer.write(c);
		    writer.flush();
		} catch(IOException e) {
		    buf.reset();
		    continue;
		}
		byte[] ba = buf.toByteArray();
		for (int j = 0; j < ba.length; j++) {
		    out.append('%');
		    // Converting each byte in the buffer
		    out.append(Character.forDigit((ba[j]>>4) & 0xf, 16));
		    out.append(Character.forDigit(ba[j] & 0xf, 16));
		}
		buf.reset();
	    }
	}
	return out.toString();
    }

    private static boolean isSafeChar(int c) {
	if (c >= 'a' && c <= 'z') {
	    return true;
	}
	if (c >= 'A' && c <= 'Z') {
	    return true;
	}
	if (c >= '0' && c <= '9') {
	    return true;
	}
	if (c == '-' || c == '_' || c == '.' || c == '!' ||
	    c == '~' || c == '*' || c == '\'' || c == '(' || c == ')') {
	    return true;
	}
	return false;
    }    

    public static URL getResource(String name) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = null; 
        	
        if ((name != null) && (name.indexOf(":/") > -1)) {
            try {
            	url = new URL(name);
            } catch (Exception e) {
            }
        }
        
        if (url == null) {
            try {
            	url = classLoader.getResource(name);
            } catch (Exception e) {
            }
        }

        if (url == null) {
            try {
            	url = classLoader.getResource('/' + name);
            } catch (Exception e) {
            }
        }

        if (url == null) {
            try {
            	url = classLoader.getResource("META-INF/" + name);
            } catch (Exception e) {
            }
        }
        	
        return url;
    }
    
    public static InputStream getInputStream(String name) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = null;

        if ((name != null) && (name.indexOf(":/") > -1)) {
            try {
                is = new URL(name).openStream();
            } catch (Exception e) {
            }
        }

        URL url = classLoader.getResource(name);

        if (is == null) {
            try {
                is = classLoader.getResourceAsStream(name);
            } catch (Exception e) {
            }
        }

        if (is == null) {
            try {
                is = classLoader.getResourceAsStream('/' + name);
            } catch (Exception e) {
            }
        }

        if (is == null) {
            try {
                is = classLoader.getResourceAsStream("META-INF/" + name);
            } catch (Exception e) {
            }
        }
        
        if (is == null) {
        	try {
				is = new FileInputStream(name);
			} catch (FileNotFoundException e) {
			}
        }

        return is;
    }
}
