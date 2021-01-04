<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/header.jsp"%>
<title>memberView.jsp</title>
</head>
<body>
	<%@include file="/WEB-INF/inc/top.jsp"%>
	<div class="container">
		<h3>회원 상세 정보</h3>
		<table class="table table-striped table-bordered">
			<tbody>
				<tr>
					<th>아이디</th>
					<td>${member.memId}</td>
				</tr>
				<tr>
					<th>회원명</th>
					<td>${member.memName}</td>
				</tr>
				<tr>
					<th>우편번호</th>
					<td>${member.memZip}</td>
				</tr>
				<tr>
					<th>주소</th>
					<td>${member.memAdd1}${member.memAdd2}</td>
				</tr>
				<tr>
					<th>생일</th>
					<td>${member.memBir}</td>
				</tr>
				<tr>
					<th>핸드폰번호</th>
					<td>${member.memHp}</td>
				</tr>
				<tr>
					<th>직업</th>
					<td>${member.memJobNm},${member.memJob}</td>
				</tr>
				<tr>
					<th>취미</th>
					<td>${member.memLikeNm},${member.memLike}</td>
				</tr>
				<tr>
					<th>마일리지</th>
					<td>${member.memMileage}</td>
				</tr>
				<tr>
					<th>탈퇴여부</th>
					<td>${member.memDelete}</td>
				</tr>
				<tr>
					<td colspan="2">
						<form action="" method="post">
							<a href="memberList.wow?" class="btn btn-default btn-sm"> <span
								class="glyphicon glyphicon-list" aria-hidden="true"></span>
								&nbsp;목록
							</a>
							<%-- <%=request.getParameter("memId") %>, <%=memId %>, ${param.memId}, <%=rs.getString("mem_id") %> 다 똑같음 --%>
							<mytag:sec hasRole="MANAGER">
								<%-- MANAGER권한이 있는 회원만 수정 버튼 볼 수 있음 --%>
								<a href="memberEdit.wow?memId=${param.memId}"
									class="btn btn-info btn-sm"> <span
									class="glyphicon glyphicon-king" aria-hidden="true"></span>
									&nbsp;수정
								</a>
								<input type="hidden" name="memId" value="${param.memId}">
								<button type="submit" formaction="memberDelete.wow"
									class="btn btn-sm btn-danger">
									<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
									&nbsp;&nbsp;탈퇴
								</button>
							</mytag:sec>
						</form>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>