package com.di.step3;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class TestBoardService {

	public static void main(String[] args) {
		
//		BoardService boardService = new BoardService();
//		boardService.getBoardList();
		
		// 스프링답게.. 스프링 설정을 읽어서 해당 빈을 받아와서 실행
		AbstractApplicationContext context = new GenericXmlApplicationContext("spring/step3.xml");
		
		BoardService service = context.getBean("boardService",BoardService.class);
		service.getBoardList();
		
		context.close();
	}
}
