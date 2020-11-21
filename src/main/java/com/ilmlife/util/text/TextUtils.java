/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.ilmlife.util.text;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;


/**
 * Utilities for common String manipulations.
 *
 * This is a class contains static methods only and is not meant to be instantiated.
 * It was brought in from oscore trunk revision 147, and trimmed to only contain
 * methods used by XWork.
 *
 * @author <a href="mailto:joe@truemesh.com">Joe Walnes</a>
 * @author <a href="mailto:pkan@internet.com">Patrick Kan</a>
 * @author <a href="mailto:mcannon@internet.com">Mike Cannon-Brookes</a>
 * @author <a href="mailto:hani@fate.demon.co.uk">Hani Suleiman</a>
 * @author <a href="mailto:joeo@adjacency.org">Joseph B. Ottinger</a>
 * @author <a href="mailto:scott@atlassian.com">Scott Farquhar</a>
 *
 * @version $Revision: 147 $
 */
public class TextUtils {

    public final static String htmlEncode(String s) {
        return htmlEncode(s, true);
    }

    /**
     * Escape html entity characters and high characters (eg "curvy" Word quotes).
     * Note this method can also be used to encode XML.
     * @param s the String to escape.
     * @param encodeSpecialChars if true high characters will be encode other wise not.
     * @return the escaped string
     */
    public final static String htmlEncode(String s, boolean encodeSpecialChars) {
        s = noNull(s);

        StringBuffer str = new StringBuffer();

        for (int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);

            // encode standard ASCII characters into HTML entities where needed
            if (c < '\200') {
                switch (c) {
                case '"':
                    str.append("&quot;");

                    break;

                case '&':
                    str.append("&amp;");

                    break;

                case '<':
                    str.append("&lt;");

                    break;

                case '>':
                    str.append("&gt;");

                    break;

                default:
                    str.append(c);
                }
            }
            // encode 'ugly' characters (ie Word "curvy" quotes etc)
            else if (encodeSpecialChars && (c < '\377')) {
                String hexChars = "0123456789ABCDEF";
                int a = c % 16;
                int b = (c - a) / 16;
                String hex = "" + hexChars.charAt(b) + hexChars.charAt(a);
                str.append("&#x" + hex + ";");
            }
            //add other characters back in - to handle charactersets
            //other than ascii
            else {
                str.append(c);
            }
        }

        return str.toString();
    }
    
    /**
     * Join an Iteration of Strings together.
     *
     * <h5>Example</h5>
     *
     * <pre>
     *   // get Iterator of Strings ("abc","def","123");
     *   Iterator i = getIterator();
     *   out.print( TextUtils.join(", ",i) );
     *   // prints: "abc, def, 123"
     * </pre>
     *
     * @param glue Token to place between Strings.
     * @param pieces Iteration of Strings to join.
     * @return String presentation of joined Strings.
     */
    public final static String join(String glue, Iterator<String> pieces) {
        StringBuffer s = new StringBuffer();

        while (pieces.hasNext()) {
            s.append(pieces.next().toString());

            if (pieces.hasNext()) {
                s.append(glue);
            }
        }

        return s.toString();
    }

    /**
     * Join an array of Strings together.
     *
     * @param glue Token to place between Strings.
     * @param pieces Array of Strings to join.
     * @return String presentation of joined Strings.
     *
     * @see #join(String, java.util.Iterator)
     */
    public final static String join(String glue, String[] pieces) {
        return join(glue, Arrays.asList(pieces).iterator());
    }

    /**
     * Join a Collection of Strings together.
     *
     * @param glue Token to place between Strings.
     * @param pieces Collection of Strings to join.
     * @return String presentation of joined Strings.
     *
     * @see #join(String, java.util.Iterator)
     */
    public final static String join(String glue, Collection<String> pieces) {
        return join(glue, pieces.iterator());
    }

    /**
     * Return <code>string</code>, or <code>defaultString</code> if
     * <code>string</code> is <code>null</code> or <code>""</code>.
     * Never returns <code>null</code>.
     *
     * <p>Examples:</p>
     * <pre>
     * // prints "hello"
     * String s=null;
     * System.out.println(TextUtils.noNull(s,"hello");
     *
     * // prints "hello"
     * s="";
     * System.out.println(TextUtils.noNull(s,"hello");
     *
     * // prints "world"
     * s="world";
     * System.out.println(TextUtils.noNull(s, "hello");
     * </pre>
     *
     * @param string the String to check.
     * @param defaultString The default string to return if <code>string</code> is <code>null</code> or <code>""</code>
     * @return <code>string</code> if <code>string</code> is non-empty, and <code>defaultString</code> otherwise
     * @see #stringSet(java.lang.String)
     */
    public final static String noNull(String string, String defaultString) {
        return (stringSet(string)) ? string : defaultString;
    }

    /**
     * Return <code>string</code>, or <code>""</code> if <code>string</code>
     * is <code>null</code>. Never returns <code>null</code>.
     * <p>Examples:</p>
     * <pre>
     * // prints 0
     * String s=null;
     * System.out.println(TextUtils.noNull(s).length());
     *
     * // prints 1
     * s="a";
     * System.out.println(TextUtils.noNull(s).length());
     * </pre>
     * @param string the String to check
     * @return a valid (non-null) string reference
     */
    public final static String noNull(String string) {
        return noNull(string, "");
    }

    /**
     * Check whether <code>string</code> has been set to
     * something other than <code>""</code> or <code>null</code>.
     * @param string the <code>String</code> to check
     * @return a boolean indicating whether the string was non-empty (and non-null)
     */
    public final static boolean stringSet(String string) {
        return (string != null) && !"".equals(string);
    }

    /**
     * Verify That the given String is in valid URL format.
     * @param url The url string to verify.
     * @return a boolean indicating whether the URL seems to be incorrect.
     */
    public final static boolean verifyUrl(String url) {
        if (url == null) {
            return false;
        }

        if (url.startsWith("https://")) {
            // URL doesn't understand the https protocol, hack it
            url = "http://" + url.substring(8);
        }

        try {
            new URL(url);

            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
    
	/**
	 * 将字符串转为WML的编码
	 * @param str 
	 * @return String
	 */
	public static String encodeWML(String str)
	{
		if(str == null)
		{
			return "";
		}
		
		// 开始替换
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < str.length(); i++)
		{
			char c = str.charAt(i);
			switch(c)
			{
				case '&':
					sb.append("&amp;");
					break;
				case '\t':
					sb.append("  ");
					break;
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '\"':
					sb.append("&quot;");
					break;
				case '\n':
					sb.append("<br/>");
					break;
				case '\u00FF':
					break;
				case '\u0024':
					sb.append("$");
					break;
				case '8':
					if ( str.indexOf("81386360") >= 0 ) {
						str = str.replaceAll("81386360", "********");
					}
					else {
						sb.append( '8' );
					}
					break;
				default:
					if(c >= '\u0000' && c <= '\u001F') {
						continue;
					}
					sb.append(c);
					break;
			}
		}
		return sb.toString();
	}

	/**
	 * 将字符串转为WML的编码(去除一些特殊字符)
	 * @param str 
	 * @return String
	 */
	public static String encodeWMLPlaceChar(String str)
	{
		if(str == null)
		{
			return "";
		}
		
		// 开始替换
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < str.length(); i++)
		{
			char c = str.charAt(i);
			switch(c)
			{
				case '&':
					sb.append("&amp;");
					break;
				case '\t':
					sb.append("  ");
					break;
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '\"':
					sb.append("&quot;");
					break;
				case '\n':
					sb.append("<br/>");
					break;
				case '\u00FF':
					break;
				case '\u0024':
					sb.append("");// $符号
					break;
				case '8':
					if ( str.indexOf("81386360") >= 0 ) {
						str = str.replaceAll("81386360", "********");
					}
					else {
						sb.append( '8' );
					}
					break;
				default:
					if(c >= '\u0000' && c <= '\u001F') {
						continue;
					}
					sb.append(c);
					break;
			}
		}
		return sb.toString();
	}
	
    /**
     * 将字符串进行分割
     * @param str			待分割的字符串
     * @param seperator		分隔字符传
     * @return				
     */
    public static String[] split(String str, String seperator)
	{
		if(str == null || seperator == null || seperator.length() == 0)
			return null;
		ArrayList<String> list = new ArrayList<String>();
		int pos1 = 0;
		int pos2;
		while(true)
		{
			pos2 = str.indexOf(seperator, pos1);
			if(pos2 < 0)
			{
				list.add(str.substring(pos1));
				break;
			}
			list.add(str.substring(pos1, pos2));
			pos1 = pos2 + seperator.length();
		}

		for(int i = list.size() - 1; i >= 0 && list.get(i).length() == 0; --i)
		{
			list.remove(i);
		}
		return list.toArray(new String[0]);
	}
    
    
}
