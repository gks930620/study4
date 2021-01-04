package com.di.step4;

public class BoardDaoMySql implements IBoardDao {
	private String driver = "com.mysql.jdbc.Driver";
	private String username = "java";
	private String password = "oracle";
//	private String url = "jdbc:mysql://localhost/dev";		// dev라는 데이터베이스 기준 (오라클은 계정 기준)
	
	// url 정보를 setter를 통해 받도록 변경
	private String url;	
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public void getBoardList() {
		System.out.printf("커넥션정보[%s]연결성공 %n", url);
		System.out.println("[MySql] 게시판 정보를 조회 했습니다.");
	}
	
}
