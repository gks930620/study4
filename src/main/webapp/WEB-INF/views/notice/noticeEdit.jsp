<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@ include file="/WEB-INF/inc/header.jsp" %>
	<title>noticeEdit.jsp </title>
</head>
<body>
<%@include file="/WEB-INF/inc/top.jsp"%>
 <div class="container">	
	<h3>공지사항 수정</h3>	
	<form:form action="noticeModify.wow" modelAttribute="board">
		<form:hidden path="noNo"/>	
	<table class="table table-striped table-bordered">
		<tbody>
			<tr>
				<th>글번호</th>
				<td>${board.noNo}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>
				<form:input path="noTitle" cssClass="form-control input-sm"/>
				<form:errors path="noTitle" />
				</td>
			</tr>
			<tr>
				<th>분류</th>
				<td>
					<form:select path="noTopYn" cssClass="form-control input-sm">
						<option value="" >카테고리를 선택하세요</option>
						<form:option value="0">일반공지</form:option>
	   					<form:option value="1">중요공지</form:option>
					</form:select>
					<form:errors path="noTopYn" />				
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea rows="10" name="noContents" class="form-control input-sm">${board.noContents}</textarea>
				<form:errors path="noContents" />
				</td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${board.noHit}</td>
			</tr>
			<tr>
				<th>등록일자</th>
				<td>${board.noRegDate}</td>
			</tr>
			<tr>
				<td colspan="2">
		          <div class="pull-left">
		              <a href="noticeList.wow" class="btn btn-default btn-sm"> 
		                <span class="glyphicon glyphicon-list" aria-hidden="true"></span>
		                &nbsp;&nbsp;목록
		              </a>
		            </div>
		            <div class="pull-right">
		              <!-- 문제점 : 사용자가 입력박스에서 엔터를 치면 첫번째 submit의 formaction 방향으로 이동한다.  -->
		              <button type="submit"  formaction="noticeDelete.wow" class="btn btn-sm btn-danger"> 
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