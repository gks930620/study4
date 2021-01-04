package com.di.step5;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class TestBoardService {

	public static void main(String[] args) {
		
		// 스프링답게.. 스프링 설정을 읽어서 해당 빈을 받아와서 실행
//		AbstractApplicationContext context = new GenericXmlApplicationContext("spring/step3.xml");
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(JavaConfigScan.class);
		
		BoardService service = context.getBean("boardService",BoardService.class);
											  // ↑ 특별히 이름 지정하지 않으면 클래스의 첫글자만 소문자로 바꾼게 이름이 됨
		service.getBoardList();
		
		context.close();
	}
}
