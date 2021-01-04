<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/header.jsp"%>
<title>freeView.jsp</title>
</head>
<body>
<%@ include file="/WEB-INF/inc/top.jsp" %>
<div class="container">
	<div class="page-header">
		<h3>공지사항 - <small>${board.noTitle}</small></h3>
	</div>
		<table class="table table-striped table-bordered">
			<tbody>
				<tr>
					<th>글번호</th>
					<td>${board.noNo}</td>
				</tr>
				<tr>
					<th>글제목</th>
					<td>${board.noTitle}</td>
				</tr>
				<tr>
					<th>내용</th>
					<td><textarea rows="10" name="noContents" class="form-control input-sm">${board.noContents}</textarea>	</td>
				</tr>
				<tr>
					<th>조회수</th>
					<td>${board.noHit}</td>
				</tr>
				<tr>
					<th>등록일자</th>
					<td>
					<c:choose>
					<c:when test="${board.noModDate != null}">${board.noModDate}</c:when>
					<c:when test="${board.noModDate == null}">${board.noRegDate}</c:when>
					</c:choose>
					</td>
				</tr>
				<tr>
					<td colspan="2">
					  <div class="pull-left">
							<a href="noticeList.wow" class="btn btn-default btn-sm"> 
								<span class="glyphicon glyphicon-list" aria-hidden="true"></span>
								&nbsp;&nbsp;목록
							</a>
						</div>
						<mytag:sec hasRole="MANAGER">
						<div class="pull-right">
							<a href="noticeEdit.wow?noNo=${board.noNo}" class="btn btn-success btn-sm"> 
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
								&nbsp;&nbsp;수정
						  </a>
						</div>
						</mytag:sec>
					</td>					  
				</tr>
			</tbody>
		</table>
</div><!-- container -->

</body>
</html>






