 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	//	response.sendRedirect(request.getContextPath()+"/jsp/index.jsp");//重定向，改变路径
		request.getRequestDispatcher("/index?").forward(request,response);//不改变路径(同BaseServlet)
	%>
  </body>
</html>
