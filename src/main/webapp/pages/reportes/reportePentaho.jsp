<%@page import="gob.ositran.siged.config.SigedProperties" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Pragma" content="no-cache">
<title>Pentaho</title>
</head>
<body>
	<iframe src="<%=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.PENTAHO_URL)%>" id="pentaho" name="pentaho" height="1000px" width="100%" frameborder="0" scrolling="false">
	</iframe>
</body>
</html>