package com.di.step5;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("oracle")
public class BoardDaoOracle implements IBoardDao {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String username = "java";
	private String password = "oracle";

	// url 정보를 setter를 통해 받도록 변경
//	private String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	@Value("SQLD는 Oracle만 사랑해")
	private String url;
	
	// step3. setter 없애고 클래스에 @Component 추가, 필드에 @Value 추가
//	public void setUrl(String url) {
//		this.url = url;
//	}
	
	public void getBoardList() {
		System.out.printf("커넥션정보[%s]연결성공 %n", url);
		System.out.println("[오라클] 게시판 정보를 조회 했습니다.");
	}
}
