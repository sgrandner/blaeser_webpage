<%@ page language="java" contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>

<%@taglib prefix="rbt" uri="urn:org:glassfish:jersey:servlet:mvc" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="menu" value="${it.menu}" />
<c:set var="page" value="${it.page}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

</head>

<body>

	${menu.menuItemList}

	${page.html}

</body>
</html>