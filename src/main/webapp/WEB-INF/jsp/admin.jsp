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

	<style type="text/css">

		.adminMenu {
			/*float: left;*/
			width: 600px;
			padding: 10px;
			border: solid 1px #dddddd;
		}

		.adminMenu .headline {
			font-weight: bold;
		}

		.adminMenuLink {
			color: #0089ff;
		}



		.adminEdit {
			width: 600px;
			padding: 10px;
			border: solid 1px #a4a4a4;
		}

		.adminEdit .pageHtml {
			border: solid 2px #515bbb;
		}

	</style>
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

		<div class="adminEdit">

			name: ${menuItem.name}<br>
			label: ${menuItem.label}<br>
			pageId: ${menuItem.pageId}<br>
			active: ${menuItem.active}<br>
			selected: ${menuItem.selected}<br>

		</div>

	</c:if>

	<c:if test="${page ne null}">

		<div class="adminEdit">

			id: ${page.id}<br>
			name: ${page.name}<br>
			active: ${page.active}<br>
			templateId: ${page.templateId}<br>
			creationDate: ${page.creationDate}<br>
			modifyDate: ${page.modifyDate}<br>

			html:
			<div class="pageHtml">
				${page.html}
			</div>

			images:
			<div class="pageImages">
				<c:forEach var="pageImageEntry" items="${page.images}">
					${pageImageEntry.fileName}<br>
				</c:forEach>
			</div>

			texts:
			<div class="pageTexts">
				<c:forEach var="pageTextsEntry" items="${page.texts}">
					${pageTextsEntry.content}<br><br>
				</c:forEach>
			</div>

		</div>

	</c:if>

	<c:if test="${pageTemplate ne null}">

		<div class="adminEdit">

			id: ${pageTemplate.id}<br>
			name: ${pageTemplate.name}<br>
			template: ${pageTemplate.template}<br>
			creationDate: ${pageTemplate.creationDate}<br>
			modifyDate: ${pageTemplate.modifyDate}<br>

		</div>
		
	</c:if>

</body>
</html>