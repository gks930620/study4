package com.di.step2;

public class BoardService implements IBoardDao {
	/* 싱글턴 실험
//	private static BoardService() instance;
//	private BoardService() {
//		
//	}
//	
//	public static BoardService getInstance() {
//		if(instance == null) {
//			instance = new BoradService();
//		}
//		return instance;
//	}
	 */

	
	// 기존에 인터페이스 없었을 때 코딩
	// private BoardDaoOracle boardDaoOracle = new BoardDaoOracle();
//	private BoardDaoMySql boardDaoMySql = new BoardDaoMySql();
	
	
	// IBoardDao 인터페이스를 만들어서, Oracle이나 MySql로 변경시 아래 부분 바꿔야 함
//	private IBoardDao boardDao = new BoardDaoMySql();
//	private IBoardDao boardDao = new BoardDaoOracle();
	// 인터페이스 없을 때보다 간단하지긴 했지만
	// 퍼시스턴트 계층이 바뀌었는데 왜 서비스를 바꿔야 하는가?
	// 서비스가 수십개 있으면 전부 바꿔줘야 하는데..
	
	// step2
	// IBoardDao의 객체를 생성자 또는 setter를 통해서 받도록 변경
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
