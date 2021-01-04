package com.study.servlet;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardVO;

@Controller
//@RestController			// RestController를 하면 메소드에 모두 @ResponseBody가 붙는다고 생각하면 됨
public class RestTestController {
	final Logger logger = LoggerFactory.getLogger(getClass());
	IFreeBoardService boardService = new FreeBoardServiceImpl();
	
	// book/12/view	, book/35/view , book/35/delete
							// {} 안에 있는 내용을 변수로 받을 수 있음
	@RequestMapping(value = "/book/{no}/{mode}")
	public String p1(@PathVariable("no") int boNo, @PathVariable("mode") String mode, ModelMap model) throws Exception {
		
		logger.debug("no={}, mode={}", boNo, mode);
		
		FreeBoardVO board = boardService.getBoard(boNo);
		model.addAttribute("board", board);
		return "test";
		
	}

	@RequestMapping(value = "/test.nhn", produces = "text/html; charset=utf-8")		// 한글 깨질 때 설정
												  // ↑ 브라우저가 인식할 수 없는 mytype을 넣으면 파일을 download한다
	public String t1() {
//		return "test 한글..";			// @ResponseBody 붙어서 다시 테스트
		return "test";					// 반환이 String이면 return값이 뷰 이름이 됨
	}
	
	@RequestMapping("/test.daum")
	public void t2() {					// 반환이 void이면 뷰이름을 RequestMapping으로 결정함
		
	}
	
	@RequestMapping("/test.kakao")
	public Map<String, Object> t3() {	// 반환이 Map이나 Model인 경우에도 RequestMapping으로 뷰 이름 결정
		Map<String, Object> sjMap = new HashedMap();
		sjMap.put("msg", "Map은 RequestMapping이 뷰 이름..");
		return sjMap;
	}
	/*
	@RequestMapping("/test.kbs")
	@ResponseBody
	public String t4() {
		return "test";					// @ResponseBody가 붙었기때문에 "test"자체만 응답으로 화면에 찍힘
	}
	
	@RequestMapping("/test.mbc")
	public @ResponseBody Map t5() {
		Map<String, Object> sjMap = new HashedMap();
		sjMap.put("msg", "@ResponseBody ..");
		return sjMap;					// @ResponseBody로 인해서 화면 -> {"msg":"@ResponseBody .."}
	}
	 * */
	
	
}
