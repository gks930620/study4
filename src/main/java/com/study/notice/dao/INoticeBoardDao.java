package com.study.notice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study.notice.vo.NoticeBoardSearchVO;
import com.study.notice.vo.NoticeBoardVO;

@Mapper
public interface INoticeBoardDao {
	
	public int getBoardCount(NoticeBoardSearchVO searchVO);
	public int insertBoard(NoticeBoardVO board);
	public int updateBoard(NoticeBoardVO board);
	public int deleteBoard(NoticeBoardVO board);
	public NoticeBoardVO getBoard(int noNo);
	public List<NoticeBoardVO> getBoardList(NoticeBoardSearchVO searchVO);
	public int increaseHit(int noNo);
	// 추가
	public List<NoticeBoardVO> getNoticeList(NoticeBoardSearchVO searchVO);
}