<%@ page language="java" contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>

<%@taglib prefix="rbt" uri="urn:org:glassfish:jersey:servlet:mvc" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--<jsp:useBean id="it" type="" />--%>

<c:set var="menu" value="${it.menu}" />
<c:set var="page" value="${it.page}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

	<style type="text/css">

		.menu {
			padding: 10px;
		}

		.menuLink {
			font-family: Verdana, Arial, sans-serif;
			font-weight: bold;
			color: #515bf1;
			text-decoration: none;
		}

		.menuLink.selected {
			color: #32c2f1;
		}

		.menuEntry {
			padding: 10px;
		}

		.menuEntry.selected {
		}

		.page {
			padding: 10px;
			font-family: Verdana, Arial, sans-serif;
			font-weight: normal;
			color: #555555;
		}

	</style>
</head>

<body>

	<c:if test="${menu ne null}">

		<div class="menu">
			<c:forEach var="menuItem" items="${menu.menuItemList}">

				<c:choose>
					<c:when test="${menuItem.selected eq 'true'}">
						<c:set var="menuSelected" value="selected" />
					</c:when>
					<c:otherwise>
						<c:set var="menuSelected" value="" />
					</c:otherwise>
				</c:choose>

				<%-- TODO domain --%>
				<%--<c:url ...--%>


				<a href="http://localhost:8080/page/${menuItem.name}" class="menuLink ${menuSelected}">
					<span class="menuEntry ${menuSelected}">
						${menuItem.label}
					</span>
				</a>

			</c:forEach>
		</div>

	</c:if>

	<div class="page">
		${page.html}
	</div>

</body>
</html>