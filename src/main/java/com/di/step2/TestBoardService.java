package com.di.step2;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class TestBoardService {

	public static void main(String[] args) {
		
		// 싱글턴
//		BoardService boardService = BoardService.getInstance(); // new BoardService();
//		System.out.println(boardService);
//
//		BoardService boardService2 = BoardService.getInstance(); //new BoardService();
//		System.out.println(boardService2);
		
		
//		BoardService boardService = new BoardService();
//		boardService.getBoardList();
		
		// 스프링답게.. 스프링 설정을 읽어서 해당 빈을 받아와서 실행
		AbstractApplicationContext context = new GenericXmlApplicationContext("spring/step2.xml");
		
		BoardService service = context.getBean("boardService",BoardService.class);
		System.out.println(service);
		service.getBoardList();
		
		BoardService service2 = context.getBean("boardService2",BoardService.class);
		System.out.println(service2);
		service2.getBoardList();
		
		context.close();
	}
}
