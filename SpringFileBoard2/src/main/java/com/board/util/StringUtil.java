package com.board.util;

//글내용보기 -><textarea><pre><%=content%></pre></textarea>
public class StringUtil {
	public static String parseBr(String msg){
		
		if(msg == null) return null;
		
		return msg.replace("\r\n", "<br>")
                         .replace("\n", "<br>");
	}
}
