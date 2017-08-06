<%@ page language="java" contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>

<%@taglib prefix="rbt" uri="urn:org:glassfish:jersey:servlet:mvc" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="menuItems" value="${it.menuItems}" />
<c:set var="pages" value="${it.pages}" />
<c:set var="pageTemplates" value="${it.pageTemplates}" />

<c:set var="menuItem" value="${it.menuItem}" />
<c:set var="page" value="${it.page}" />
<c:set var="pageTemplate" value="${it.pageTemplate}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

	<%-- NOTE ${pageContext.request.contextPath} also possible instead of c:url --%>
	<link rel="stylesheet" href="<c:url value="/resources/css/adminEdit.css" />" type="text/css">

	<script type="text/javascript" src="<c:url value="/resources/js/adminEdit.js" />"></script>

</head>


<body>

	<div class="adminMenu">

		<div>

			<div class="headline">
				menu items
			</div>
			<c:forEach var="menuItemEntry" items="${menuItems}">
				<div>
					<a href="http://localhost:8080/admin/m/${menuItemEntry.name}" class="adminMenuLink">
						${menuItemEntry.name}
					</a>
				</div>
			</c:forEach>

		</div>

		<div>

			<div class="headline">
				pages
			</div>
			<c:forEach var="pagesEntry" items="${pages}">
				<div>
					<a href="http://localhost:8080/admin/p/${pagesEntry.name}" class="adminMenuLink">
						${pagesEntry.name}
					</a>
				</div>
			</c:forEach>

		</div>

		<div>

			<div class="headline">
				page templates
			</div>
			<c:forEach var="pageTemplateEntry" items="${pageTemplates}">
				<div>
					<a href="http://localhost:8080/admin/pt/${pageTemplateEntry.name}" class="adminMenuLink">
						${pageTemplateEntry.name}
					</a>
				</div>
			</c:forEach>

		</div>

	</div>

	<c:if test="${menuItem ne null}">

		<form class="adminEdit" action="">

			id: ${menuItem.id}<br>
			name: <input type="text" name="name${menuItem.id}" value="${menuItem.name}"><br>
			label: <input type="text" name="label${menuItem.id}" value="${menuItem.label}"><br>
			pageId: <input type="text" name="pageId${menuItem.id}" value="${menuItem.pageId}"><br>
			active: ${menuItem.active}<br>
			selected: ${menuItem.selected}<br>

			<input type="submit" value="Speichern" onclick="editMenuItem.submitChanges()">

		</form>

	</c:if>

	<c:if test="${page ne null}">

		<form class="adminEdit" action="">

			id: ${page.id}<br>
			name: <input type="text" name="name${page.id}" value="${page.name}"><br>
			templateId: <input type="text" name="templateId${page.id}" value="${page.templateId}"><br>
			creationDate: ${page.creationDate}<br>
			modifyDate: ${page.modifyDate}<br>

			html:
			<div class="pageHtml">
				${page.html}
			</div>

			images:
			<div class="pageImages">
				<c:forEach var="pageImageEntry" items="${page.images}">
					<input type="text" name="image${pageImageEntry.id}" value="${pageImageEntry.fileName}"><br>
				</c:forEach>
			</div>

			texts:
			<div class="pageTexts">
				<c:forEach var="pageTextsEntry" items="${page.texts}">
					<input type="text" name="text${pageTextsEntry.id}" value="${pageTextsEntry.content}"><br>
				</c:forEach>
			</div>

			<input type="submit" value="Speichern" onclick="editPage.submitChanges()">

		</form>

	</c:if>

	<c:if test="${pageTemplate ne null}">

		<form class="adminEdit" action="">

			id: ${pageTemplate.id}<br>
			name: <input type="text" name="name${pageTemplate.id}" value="${pageTemplate.name}"><br>
			template: <input type="text" name="template${pageTemplate.id}" value="${pageTemplate.template}"><br>
			creationDate: ${pageTemplate.creationDate}<br>
			modifyDate: ${pageTemplate.modifyDate}<br>

			<input type="submit" value="Speichern" onclick="editPageTemplate.submitChanges()">

		</form>
		
	</c:if>

</body>
</html>