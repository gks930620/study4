package com.study.notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.notice.dao.INoticeBoardDao;
import com.study.notice.vo.NoticeBoardSearchVO;
import com.study.notice.vo.NoticeBoardVO;

@Service
public class NoticeBoardServiceImpl implements INoticeBoardService {
	
	@Autowired
	private INoticeBoardDao noticeBoardDao;

	@Override
	public List<NoticeBoardVO> getBoardList(NoticeBoardSearchVO searchVO) {
		int cnt = noticeBoardDao.getBoardCount(searchVO);
		searchVO.setTotalRowCount(cnt);
		searchVO.pageSetting();

		List<NoticeBoardVO> list = noticeBoardDao.getBoardList(searchVO);
		return list;
	}
	
	@Override
	public NoticeBoardVO getBoard(int noNo) throws BizNotFoundException {
		NoticeBoardVO vo = noticeBoardDao.getBoard(noNo);
		if (vo == null) {
			throw new BizNotFoundException("[" + noNo + "] 조회 실패");
		}
		return vo;
	}

	@Override
	public void registBoard(NoticeBoardVO board) {
		noticeBoardDao.insertBoard(board);
	}

	@Override
	public void modifyBoard(NoticeBoardVO board)
			throws BizNotFoundException, BizNotEffectedException {
		NoticeBoardVO vo = noticeBoardDao.getBoard(board.getNoNo());
		if (vo == null) {
			throw new BizNotFoundException("[" + board.getNoNo() + "] 수정 실패");
		}
		int cnt = noticeBoardDao.updateBoard(board);
		if (cnt < 1) {
			throw new BizNotEffectedException("[" + board.getNoNo() + "] 수정 실패");
		}
	}

	@Override
	public void removeBoard(NoticeBoardVO board)
			throws BizNotFoundException, BizNotEffectedException {
		NoticeBoardVO vo = noticeBoardDao.getBoard(board.getNoNo());
		if (vo == null) {
			throw new BizNotFoundException("[" + board.getNoNo() + "] 수정 실패");
		}
		int cnt = noticeBoardDao.deleteBoard(board);
		if (cnt < 1) {
			throw new BizNotEffectedException("[" + board.getNoNo() + "] 수정 실패");
		}
	}

	@Override
	public void increaseHit(int noNo) throws BizNotEffectedException {
		int cnt = noticeBoardDao.increaseHit(noNo);
		
	}

	@Override
	public List<NoticeBoardVO> getNoticeList(NoticeBoardSearchVO searchVO) {
		int cnt = noticeBoardDao.getBoardCount(searchVO);
		searchVO.setTotalRowCount(cnt);
		searchVO.pageSetting();

		List<NoticeBoardVO> list = noticeBoardDao.getNoticeList(searchVO);
		return list;
	}
	
	
}