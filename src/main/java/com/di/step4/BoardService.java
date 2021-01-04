package com.di.step4;

public class BoardService implements IBoardDao {
	
	private IBoardDao boardDao;
	
	public void setBoardDao(IBoardDao boardDao) {
		this.boardDao = boardDao;
	}
	
	public void myInit() {
		// 객체에서 필요한 초기화 작업
		System.out.println("초기화 작업을 하였습니다.");
	}
	
	public void myDestroy() {
		// 객체가 소멸될 때 불필요한 자원 정리 작업
		System.out.println("자원 정리가 끝났습니다.");
	}
	
	@Override
	public void getBoardList() {
		System.out.println("boardDao = " + boardDao);
		boardDao.getBoardList();
	}
	
	
}
