package com.di.step5;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BoardService {

	private IBoardDao boardDao;
	
	@Autowired		// setter에 해도 되지만 필드단에 하면 setter가 필요 없으므로 필드에 걸어준다
	@Qualifier("oracle")		// bean이 두 개 이상 없을때 component에 지정한 이름을 알려줌
			// 이름 지정 안 한 경우에는 클래스의 앞글자만 소문자로 바꾼 이름으로
	public void setBoardDao(IBoardDao boardDao) {
		this.boardDao = boardDao;
	}
	
	@PostConstruct
	public void myInit() {
		// 객체에서 필요한 초기화 작업
		System.out.println("초기화 작업을 하였습니다.");
	}
	
	@PreDestroy
	public void myDestroy() {
		// 객체가 소멸될 때 불필요한 자원 정리 작업
		System.out.println("자원 정리가 끝났습니다.");
	}
	
	public void getBoardList() {
		boardDao.getBoardList();
	}
	
	
}
