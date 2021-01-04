package com.study.servlet.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestToViewNameTranslator extends View {
	public RequestToViewNameTranslator(StudyViewResolver viewResolver) {
		this.viewResolver = viewResolver;
	}
	/*
	 * 
	public static void main(String[] args) {
		String uri = "/study31/free/freeList.wow;JSESSIONID=MILKIS1004";
		String ctp = "/study31";
		String uriViewName = uri;
		
		uriViewName = uriViewName.substring(ctp.length());
		
		if(uriViewName.startsWith("/")) {
//			String[] spl = uriViewName.split("/");
//			uriViewName = spl[2] + "/" + spl[3];
			uriViewName = uriViewName.substring(1);
		}
		
		if(uriViewName.contains(".")) {
			uriViewName = uriViewName.substring(0,uriViewName.indexOf("."));
		}
//		System.out.println(uriViewName);
	}
	 * */
	
	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 뷰이름이 지정되지 않은 경우 현재 요청 URI 에서 뷰이름을 생성한다.
		String uri = req.getRequestURI();
		String uriViewName = uri;
		String ctp = req.getContextPath();
		
		// 과제 : 아래의 조건을 만족하도록 변수 uriViewName 을 변경하시오.
		// uriViewName에서 컨텍스트 경로, 확장자, 세미콜론이 있다면 제거
		// 예 : "/study31/free/freeList.wow;JSESSIONID=MILKIS1004" -> "free/freeList"
		
		if (ctp.length() > 0) {
			uriViewName = uriViewName.substring(ctp.length()+1);
		}
		
		if(uriViewName.contains(".")) {
			uriViewName = uriViewName.substring(0,uriViewName.indexOf("."));
			// 더 정확하게는 마지막에 있는 / 다음의 . 을 찾아야 함
		}
		
		if(uriViewName.contains(";")) {			// 확장자명이 없을 수도 있음
			uriViewName = uriViewName.substring(0,uriViewName.indexOf(":"));
			// 더 정확하게는 마지막에 있는 / 다음의 ; 을 찾아야 함
		}
		String jspPath = viewResolver.getPrefix() + uriViewName + viewResolver.getSuffix();
		
		logger.debug("URI=" + uri + ", RequestToViewNameTranslator=" + jspPath);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher(jspPath);
		dispatcher.forward(req, resp);
	}
}