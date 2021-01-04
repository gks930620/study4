<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@include file="/WEB-INF/inc/header.jsp"%>
<title>공지사항 등록</title>
</head>
<body>
<%@include file="/WEB-INF/inc/top.jsp"%>
<div class="mainContainer">
	<div class="page-header">
		<h3>공지사항 등록</h3>
	</div>
	<div class="row" >
	<form:form action="noticeRegist.wow" modelAttribute="board">
						  <%-- modelAttribute에 board를 지정함으로써 form path에 board.noTitle 대신 noTitle만 쓸 수 있음 --%>
		<%-- <div class="pull-right">
			<table class="table table-striped table-bordered">
				<tr>
					<td>
						중요공지 체크 <form:checkbox path="noTopYn" value="중요공지" />
					</td>
				</tr>
			</table>
		</div> --%>
		<table class="table table-striped table-bordered">
		<colgroup>
			<col width="20%" />
			<col/>
		</colgroup>
		<tr>
			<th>공지 분류</th>
			<td>
				<form:select path="noTopYn" cssClass="form-control input-sm">
					<option value="" >카테고리를 선택하세요</option>
					<form:option value="0">일반공지</form:option>
   					<form:option value="1">중요공지</form:option>
				</form:select>
				<%-- <select name="boCategory" class="form-control input-sm" required="required">
					<option value="">-- 선택하세요--</option>					
					<c:forEach items="${cateList}" var="code">
						<option value="${code.commCd}">${code.commNm}</option>
					</c:forEach>
				</select>	 --%>
				<form:errors path="noTopYn"/>
			</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>
				<%-- <input type="text" name="noTitle" value="" class="form-control input-sm" > --%>
				<form:input path="noTitle" cssClass="form-control input-sm"/>
				<form:errors path="noTitle" />
			</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>
				<%-- <textarea rows="10" name="boContent" class="form-control"></textarea> --%>
				<form:textarea path="noContents" cssClass="form-control" rows="10"/>
				<form:errors path="noContents" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div class="pull-left">
					<a href="noticeList.wow" class="btn btn-sm btn-default">목록으로</a>
				</div>
				<div class="pull-right">
					<button type="submit" class="btn btn-sm btn-primary">저장하기</button>
				</div>
			</td>
		</tr>
	</table>
	</form:form>
	</div>
	
</div>
</body>
</html>


