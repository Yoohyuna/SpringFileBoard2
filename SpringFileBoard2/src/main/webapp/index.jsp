<%
//request.getContextPath() ->http~SpringFileBoard(프로젝트이름)
  response.sendRedirect(request.getContextPath()+"/board/list.do");
%>
