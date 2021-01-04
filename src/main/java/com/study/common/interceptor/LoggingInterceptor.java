package com.study.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoggingInterceptor implements HandlerInterceptor {

	final Logger logger = LoggerFactory. getLogger(getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) // 요청을 컨트롤러 실행 전에 처리
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			logger.info("preHandle() 실행 : {}", method);
		} else {
			logger.info("preHandle() 실행 : {}", handler);
		}
		request.setAttribute("interceptor.startTime", System.currentTimeMillis());
		return true; // 반환이 true 이면 계속 진행, false 이면 요청 종료
	} // preHandle

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, //컨트롤러가 요청을 실행한 뒤에 처리
			ModelAndView modelAndView) throws Exception {
		// 할게없지만 구현은 해야함
		
	} // postHandle

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) // 뷰단에서의 결과 생성까지 모든 작업이 마무리되고 나서 실행
			throws Exception {
		Long startTime = (Long) request.getAttribute("interceptor.startTime");
		long duration = System.currentTimeMillis() - startTime;
		logger.info("{} : 수행시간 = {}", request.getRequestURI(), duration);
		if (ex != null) {
			logger.error("afterCompletion : {}", ex.getMessage(), ex);
		}
	} // afterCompletion

}
