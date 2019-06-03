<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
    //요청을 받아서 처리해줄때 컨텍스트이름을 얻어온다.
    // /board/->가상경로
%>
<ul>
	<li><a href="<%=contextPath%>/board/list.do">목록</a></li>
	<li><a href="<%=contextPath%>/board/write.do">글쓰기</a></li>
</ul>