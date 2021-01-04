package com.di.step1;

public class TestBoardService {

	public static void main(String[] args) {
		IBoardDao boardService = new BoardService();
		boardService.getBoardList();
	}
	
}
