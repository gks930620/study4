package com.di.step1;

public class BoardService implements IBoardDao {

	// 기존에 인터페이스 없었을 때 코딩
	// private BoardDaoOracle boardDaoOracle = new BoardDaoOracle();
//	private BoardDaoMySql boardDaoMySql = new BoardDaoMySql();
	
	// IBoardDao 인터페이스를 만들어서, Oracle이나 MySql로 변경시 아래 부분 바꿔야 함
	// 인터페이스 없을 때보다 간단하지긴 했지만
	// 퍼시스턴트 계층이 바뀌었는데 왜 서비스를 바꿔야 하는가?
	// 서비스가 수십개 있으면 전부 바꿔줘야 하는데..
//	private IBoardDao boardDao = new BoardDaoMySql();
	private IBoardDao boardDao = new BoardDaoOracle();
	
	@Override
	public void getBoardList() {
		boardDao.getBoardList();
	}
	
	
}
