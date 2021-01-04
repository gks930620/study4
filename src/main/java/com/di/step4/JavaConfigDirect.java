package com.di.step4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfigDirect {
	// step4
	// Bean 등록을 xml파일로 하지않고 java파일로 하는 것
	
	@Bean
	public BoardDaoOracle getBoardDaoOracle() {
		BoardDaoOracle oracle = new BoardDaoOracle();
		oracle.setUrl("jdbc:oracle");
		return oracle;
	}
	
	@Bean
	public BoardDaoMySql getBoardDaoMySql() {
		BoardDaoMySql mySql = new BoardDaoMySql();
		mySql.setUrl("jdbc:mysql");
		return mySql;
	}
	
	@Bean(initMethod = "myInit", destroyMethod = "myDestroy")
	public BoardService getBoardService() {		// name 설정을 안하면 getBoardService가 이름
		BoardService service = new BoardService();
		service.setBoardDao(getBoardDaoMySql());
		return service;
	}
}
