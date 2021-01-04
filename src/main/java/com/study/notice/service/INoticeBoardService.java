package com.study.notice.service;

import java.util.List;

import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.notice.vo.NoticeBoardSearchVO;
import com.study.notice.vo.NoticeBoardVO;

public interface INoticeBoardService {
	
	public List<NoticeBoardVO> getBoardList(NoticeBoardSearchVO searchVO);
	public List<NoticeBoardVO> getNoticeList(NoticeBoardSearchVO searchVO);
	public NoticeBoardVO getBoard(int noNo) throws BizNotFoundException;	
	public void registBoard(NoticeBoardVO board);	
	public void modifyBoard(NoticeBoardVO board) throws BizNotFoundException, BizNotEffectedException;	
	public void removeBoard(NoticeBoardVO board) throws BizNotFoundException, BizNotEffectedException; 	
	public void increaseHit(int noNo) throws BizNotEffectedException;
}
