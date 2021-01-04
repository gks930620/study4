<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@ include file="/WEB-INF/inc/header.jsp" %>
	<title>freeEdit.jsp </title>
</head>
<body>
<%@include file="/WEB-INF/inc/top.jsp"%>
 <div class="container">	
	<h3>게시글 수정</h3>	
	<form:form action="freeModify.wow" modelAttribute="board">
		<form:hidden path="boNo"/>	<%-- 동일한 역할 <input name="boNo" value='${board.boNo}' type="hidden"> --%>
	<table class="table table-striped table-bordered">
		<tbody>
			<tr>
				<th>글번호</th>
				<td>${board.boNo}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>
				<%-- <input type="text" name="boTitle" class="form-control input-sm" value="${board.boTitle}" > --%>
				<form:input path="boTitle" cssClass="form-control input-sm"/>
				<form:errors path="boTitle" />
				</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>
				<%-- <input type="text" name="boWriter" class="form-control input-sm" value="${board.boWriter}" > --%>
				<form:input path="boWriter" cssClass="form-control input-sm"/>
				<form:errors path="boWriter" />
				</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td>
					<input type="password" name="boPass" class="form-control input-sm" > 
					<%-- <form:passwoard path="boPass" cssClass="form-control input-sm"/> --%>
					<form:errors path="boPass" />
					<span class="text-danger">
						<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true">글 등록시에 입력한 비밀번호를 입력하세요.</span>
					</span>
				</td>
			</tr>
			<tr>
				<th>분류</th>
				<td>
					<%-- <form:select path="boCategory" items="${cateList}" itemLabel="commNm" itemValue="commCd"
								cssClass="form-control input-sm"/> --%>
					<form:select path="boCategory" cssClass="form-control input-sm">
						<option value="" >카테고리를 선택하세요</option>
						<form:options items="${cateList}" itemLabel="commNm" itemValue="commCd" />
					</form:select>
					<%-- <select name="boCategory" class="form-control input-sm">
						<option value="">-- 선택하세요--</option>					
						<c:forEach items="${cateList}" var="code">
							<option value="${code.commCd}" ${code.commCd eq board.boCategory ? "selected='selected'": "" }>${code.commNm}</option>
						</c:forEach>
					</select>	 --%>
				<form:errors path="boCategory" />				
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea rows="10" name="boContent" class="form-control input-sm">${board.boContent}</textarea>
				<form:errors path="boContent" />
				</td>
			</tr>
			<tr>
				<th>등록 IP</th>
				<td>${board.boIp}</td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${board.boHit}</td>
			</tr>
			<tr>
				<th>등록일자</th>
				<td>${board.boRegDate}</td>
			</tr>
			<tr>
				<td colspan="2">
		          <div class="pull-left">
		              <a href="freeList.wow" class="btn btn-default btn-sm"> 
		                <span class="glyphicon glyphicon-list" aria-hidden="true"></span>
		                &nbsp;&nbsp;목록
		              </a>
		            </div>
		            <div class="pull-right">
		              <!-- 문제점 : 사용자가 입력박스에서 엔터를 치면 첫번째 submit의 formaction 방향으로 이동한다.  -->
		              <button type="submit"  formaction="freeDelete.wow" class="btn btn-sm btn-danger"> 
		                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
		                &nbsp;&nbsp;삭제
		              </button>
		              <button type="submit" class="btn btn-sm btn-primary" > 
		                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
		                &nbsp;&nbsp;저장
		              </button>
				</td>
			</tr>
		</tbody>	
	</table>
	</form:form>
</div>
</body>
</html>