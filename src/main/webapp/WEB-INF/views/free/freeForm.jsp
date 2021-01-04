<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@include file="/WEB-INF/inc/header.jsp"%>
<title>글 등록</title>

<script type="text/javascript">

	$(document).ready(function() {		// 스크립트를 위쪽에 쓸 경우
										// body가 준비 되고 스크립트 실행되도록 해주는 것 
		// 파일 추가 버튼 클릭 이벤트
		$('#id_btn_new_file').click(function(){
			$('.file_area').append('<div class="form-inline">'
				+ '<input type="file" name="boFiles" class="form-control">'
				+ ' <button type="button" class="btn_delete btn btn-sm">삭제</button>'
				+ '</div>');
		});		// #id_btn_new_file 클릭 이벤트
		
		// 파일 삭제 버튼 클릭 이벤트 (동적으로 추가된 객체의 이벤트 등록시 on 사용)
		$('.file_area').on('click','.btn_delete', function(){	// 문서 로딩 할 당시에 존재하지 않아서
																// (.btn_delete).click(function	사용 불가
								// 문서 로딩 할 때부터 존재하던.. btn_delete 상위의 .file_area를 이용해서 이벤트를 거는 것
			$(this).closest('div').remove();
		});		// btn_delete 클릭 이벤트
		
	});	//document.ready
	
</script>

</head>
<body>
<%@include file="/WEB-INF/inc/top.jsp"%>
<div class="container">
	<div class="page-header">
		<h3>글 등록</h3>
	</div>
	<div class="row" >
	<form:form action="freeRegist.wow" modelAttribute="board" method="post" enctype="multipart/form-data">
								  <%-- modelAttribute에 board를 지정함으로써 form path에 board.boTitle 대신 boTitle만 쓸 수 있음 --%>
	<table class="table table-striped table-bordered">
		<colgroup>
			<col width="20%" />
			<col/>
		</colgroup>
		<tr>
			<th>제목</th>
			<td>
				<%-- <input type="text" name="boTitle" value="" class="form-control input-sm" > --%>
				<form:input path="boTitle" cssClass="form-control input-sm"/>
				<form:errors path="boTitle" />
			</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>
				<%-- <input type="text" name="boWriter" value=""  class="form-control input-sm" > --%>
				<form:input path="boWriter" cssClass="form-control input-sm"/>
				<form:errors path="boWriter" />
			</td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td>
				<%-- <input type="password" name="boPass" value="" class="form-control input-sm"> --%>
				<form:password path="boPass" cssClass="form-control input-sm"/>
				<form:errors path="boPass" />
				<span >수정 또는 삭제시에 필요합니다.</span>
			</td>
		</tr>
		<tr>
			<th>분류</th>
			<td>
				<form:select path="boCategory" cssClass="form-control input-sm">
					<option value="" >카테고리를 선택하세요</option>
					<form:options items="${cateList}" itemLabel="commNm" itemValue="commCd" />
				</form:select>
				<%-- <select name="boCategory" class="form-control input-sm" required="required">
					<option value="">-- 선택하세요--</option>					
					<c:forEach items="${cateList}" var="code">
						<option value="${code.commCd}">${code.commNm}</option>
					</c:forEach>
				</select>	 --%>
				<form:errors path="boCategory"/>
			</td>
		</tr>
		<tr>
			<th>IP</th>
			<td><%=request.getRemoteAddr()%></td>
		</tr>	
			<tr>
			<th>첨부파일<button type="button" id="id_btn_new_file">추가</button></th>
			<td class="file_area">
				<div class="form-inline">
					<input type="file" name="boFiles" class="form-control" >
					<button type="button" class="btn_delete btn btn-sm" > 삭제</button>
				</div>
			</td>
			</tr>		
		<tr>
			<th>내용</th>
			<td>
				<%-- <textarea rows="10" name="boContent" class="form-control"></textarea> --%>
				<form:textarea path="boContent" cssClass="form-control" rows="10"/>
				<form:errors path="boContent" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
					<div class="pull-left">
						<a href="freeList.wow" class="btn btn-sm btn-default">목록으로</a>
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
<!-- <script type="text/javascript">
	$('#id_btn_new_file').click(function(){
		$('.file_area').append('<div class="form-inline">'
		+ '<input type="file" name="boFiles" class="form-control">'
		+ ' <button type="button" class="btn_delete btn btn-sm">삭제</button>'
		+ '</div>');
		});
		$('.file_area').on('click','.btn_delete', function(){
		$(this).closest('div').remove();
		});
</script> -->
</body>
</html>


