<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@include file="/WEB-INF/inc/header.jsp" %>
<title>noticeList.jsp</title>
</head>
<body>
<%@include file="/WEB-INF/inc/top.jsp"%>
<div class="container">
	<div class="page-header">
		<h3>공지사항</h3>
	</div>
		<!-- START : 검색 폼  --> 
	<div class="panel panel-default collapse in" id="id_search_area">
	  <div class="panel-body">
	    <form name="frm_search" action="noticeList.wow" method="post" class="form-horizontal">
	        <input type="hidden" name="curPage" value="${searchVO.curPage}">
	        <input type="hidden" name="rowSizePerPage" value="${searchVO.rowSizePerPage}">
	          <div class="form-group">
	            <label for="id_searchType" class="col-sm-2 control-label">검색</label>
	            <div class="col-sm-2">
	                <select id="id_searchType" name="searchType" class="form-control input-sm">	
                		<option value="T" ${"T" eq searchVO.searchType ? "selected='selected'": "" }>제목</option>
                		<option value="C" ${"C" eq searchVO.searchType ? "selected='selected'": "" }>내용</option>
	                </select>
	            </div>
	            <div class="col-sm-2">	
	              <input type="text" name="searchWord" class="form-control input-sm" value="${searchVO.searchWord}" placeholder="검색어" >			      
	            </div>
	            <%-- <label for="id_searchCategory" class="col-sm-2 col-sm-offset-2 control-label">분류</label>
	            <div class="col-sm-2">
	                <select id="id_searchCategory" name="searchCategory" class="form-control input-sm">
	                    <option value="">-- 전체 --</option>
	                    <c:forEach items="${cateList}" var="code">
	                        <option value="${code.commCd}" ${code.commCd eq searchVO.searchCategory ? "selected='selected'": "" }>${code.commNm}</option>
	                    </c:forEach>
	                </select>
	            </div> --%>
	          </div>
	          <div class="form-group">
	            <div class="col-sm-2 col-sm-offset-9 text-right" >
	              <button type="button" id="id_btn_reset" class="btn btn-sm btn-default">
	                    <i class="fa fa-sync"></i>
	                    &nbsp;&nbsp;초기화 
	                </button>
	            </div>     
	            <div class="col-sm-1 text-right" >
	                <button type="submit" class="btn btn-sm btn-primary ">
	                    <i class="fa fa-search"></i>
	                    &nbsp;&nbsp;검 색
	                </button>
	            </div>
	          </div>
	    </form>
	  </div>
	</div>
	<!-- END : 검색 폼  -->

	<!-- START : 목록건수 및 새글쓰기 버튼  --> 
	<div class="row" style="margin-bottom: 10px;">
	    <div class="col-sm-3 form-inline"  >
	        전체 ${searchVO.totalRowCount } 건 조회
	        <select id="id_rowSizePerPage" name="rowSizePerPage" class="form-control input-sm" >
	            <option value="10" >10</option>
	            <option value="20" >20</option>
	            <option value="30" >30</option>
	            <option value="50" >50</option>
	        </select>
	    </div>
	    <div class="col-sm-2 col-sm-offset-6 text-right" >
	        <a id="id_seach_show" href="#id_search_area" class="btn btn-info btn-sm"  data-toggle="collapse"  aria-expanded="false" aria-controls="collapseExample"> 
	        <i id="searchTap" class="fas fa-chevron-up"></i>
	            <span>&nbsp;검색닫기</span>
	            
			</a>
	    </div>
	    <mytag:sec hasRole="MANAGER">
	    <div class="col-sm-1 text-right" >
	        <a href="noticeForm.wow" class="btn btn-primary btn-sm"> 
	            <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
	            &nbsp;새글쓰기
			</a>
	    </div>
	    </mytag:sec>
	</div>
	<!-- END : 목록건수 및 새글쓰기 버튼  --> 

	<table class="greed table table-striped table-bordered table-hover">
	<colgroup>
		<col width="10%" />
		<col width="30%" />
		<col width="15%" />
		<col width="10%" />
	</colgroup>
	<thead>
		<tr>
			<th>글번호</th>
			<th>제목</th>
			<th>등록일</th>
			<th>조회수</th>
		</tr>
	</thead>	
	<tbody>
		<!-- 중요공지 상단 고정 -->
		<c:forEach var="notice" items="${notice}">
			<tr class="text-center">
			<c:choose>
			<c:when test="${notice.noTopYn == 1}">
				<td style="background-color: yellow"><span style="color: red">!!중요공지!!</span></td>
				<td class="text-left" style="background-color: yellow">
					<a href="noticeView.wow?noNo=${notice.noNo}">${notice.noTitle}</a>
				</td>
				
				<td style="background-color: yellow">
				<c:choose>
				<c:when test="${notice.noModDate != null}">${notice.noModDate}</c:when>
				<c:when test="${notice.noModDate == null}">${notice.noRegDate}</c:when>
				</c:choose>
				</td>
				
				<td style="background-color: yellow">${notice.noHit}</td>
			</c:when>
			</c:choose>
			</tr>
		</c:forEach>
		
		<c:forEach var="vo" items="${board}">
		<tr class="text-center">
			<td>${vo.noNo}</td>
			<td class="text-left">
				<a href="noticeView.wow?noNo=${vo.noNo}">${vo.noTitle}</a>
			</td>
			
			<td>
			<c:choose>
			<c:when test="${vo.noModDate != null}">${vo.noModDate}</c:when>
			<c:when test="${vo.noModDate == null}">${vo.noRegDate}</c:when>
			</c:choose>
			</td>
			
			<td>${vo.noHit}</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
	
		<!-- START : 페이지네이션  --> 
	<nav class="text-center">
		<mytag:paging pagingVO="${searchVO}" linkPage="noticeList.wow" />
	</nav>
	<!-- END : 페이지네이션  --> 

	</div>
<script type="text/javascript">
	// 변수 정의
	var f = document.forms['frm_search'];
	
	// 함수 정의
	
	
	// 각 이벤트 등록
	// 페이지 링크 클릭
	$('ul.pagination li a[data-page]').click(function(e) {
		e.preventDefault();		// 이벤트 전파 방지,  <a>의 기본 클릭이벤트를 막기위함
		// data-page에 있는 값을 f.curPage.value에 설정, 서브밋
		f.curPage.value = $(this).data('page');
		f.submit();
	});		// ul.pagination li a[data-page]
	
	// 검색 버튼 클릭
	$(f).submit(function(e) {
		e.preventDefault();	
		f.curPage.value = 1;
		f.submit();
	});	// f.submit
	
	// 목록 갯수 변경
	// id_rowSizePerPage 변경되었을 때
	// 페이지 1, 목록수 = 현재값 으로 변경 후 서브밋
	$('#id_rowSizePerPage').change(function(e) {
		f.curPage.value = 1;
		f.rowSizePerPage.value = this.value;
		f.submit();
	});		// '#id_rowSizePerPage'.change
	
	// 초기화 버튼 클릭
	$('#id_btn_reset').click(function() {
		f.curPage.value = 1;
		f.searchWord.value = "";
		f.searchType.options[0].selected = true;
		f.searchCategory.options[0].selected = true;
	}); // #id_btn_reset.click
	
	// jQuery에서 클래스 삭제 및 추가, 내용 변경
	$('#id_search_area').on('shown.bs.collapse', function() {
		$('#searchTap').removeClass('fa-chevron-down').addClass('fa-chevron-up');
		$('#id_seach_show span').html('&nbsp; 검색열기');
	});

	$('#id_search_area').on('hidden.bs.collapse', function() {
		$('#searchTap').removeClass('fa-chevron-up').addClass('fa-chevron-down');
		$('#id_seach_show span').html('&nbsp; 검색열기');
	});
	
</script>
</body>
</html>




