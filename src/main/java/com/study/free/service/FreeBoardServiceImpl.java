package com.study.free.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.attach.dao.IAttachDao;
import com.study.attach.vo.AttachVO;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.free.dao.IFreeBoardDao;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

@Service
public class FreeBoardServiceImpl implements IFreeBoardService {
	
//	 private IFreeBoardDao freeBoardDao = new FreeBoardDaoOracle();
//	SqlSessionFactory factory = MybatisSqlSessionFactory.getSqlSessionFactory();
	
	@Autowired	// 주입. inject 써도 됨
	private IFreeBoardDao freeBoardDao;
	
	@Autowired
	private IAttachDao attachDao;
	
	@Override
	public FreeBoardVO getBoard(int boNo) throws BizNotFoundException {		// 상세보기
		FreeBoardVO vo = freeBoardDao.getBoard(boNo);
		if (vo == null) {
			throw new BizNotFoundException("글번호 [" + boNo + "] 조회 실패");
		}
		
		/*  
		// 1. 서비스에서 각 DAO를 사용하여 직접 처리
		// 해당 게시물의 첨부파일 가져오기
		AttachVO attach = new AttachVO();
		attach.setAtchCategory("FREE");
		attach.setAtchParentNo(boNo);
		
		vo.setAttaches(attachDao.getAttachByParentNoList(attach));
		 * */
		
		return vo;
	}

	@Override
	public List<FreeBoardVO> getBoardList(FreeBoardSearchVO searchVO) {
		// 건수를 구해서 searchVO에 설정하고, searchVO에 pageSetting()을 부르고 list 호출
		int cnt = freeBoardDao.getBoardCount(searchVO);
		searchVO.setTotalRowCount(cnt);
		searchVO.pageSetting();

		List<FreeBoardVO> list = freeBoardDao.getBoardList(searchVO);
		return list;
	}

	@Override
	public void registBoard(FreeBoardVO board) {
		freeBoardDao.insertBoard(board);
		
		List<AttachVO> attaches = board.getAttaches();
		if(attaches != null) {
			for(AttachVO vo : attaches) {
				vo.setAtchParentNo(board.getBoNo());
				attachDao.insertAttach(vo);
			}
		}
	}

	@Override
	public void modifyBoard(FreeBoardVO board)
			throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {
		FreeBoardVO vo = freeBoardDao.getBoard(board.getBoNo());
		if (vo == null) {
			throw new BizNotFoundException("[" + board.getBoNo() + "] 수정 실패");
		}
		if (!(vo.getBoPass()).equals(board.getBoPass())) {
			throw new BizPasswordNotMatchedException("[" + board.getBoNo() + "] 수정 실패. 비밀번호가 틀립니다.");
		}
		int cnt = freeBoardDao.updateBoard(board);
		if (cnt < 1) {
			throw new BizNotEffectedException("[" + board.getBoNo() + "] 수정 실패");
		}
	}

	@Override
	public void removeBoard(FreeBoardVO board)
			throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {
		FreeBoardVO vo = freeBoardDao.getBoard(board.getBoNo());
		if (vo == null) {
			throw new BizNotFoundException("[" + board.getBoNo() + "] 수정 실패");
		}
		if (!(vo.getBoPass()).equals(board.getBoPass())) {
			throw new BizPasswordNotMatchedException("[" + board.getBoNo() + "] 수정 실패");
		}
		int cnt = freeBoardDao.deleteBoard(board);
		if (cnt < 1) {
			throw new BizNotEffectedException("[" + board.getBoNo() + "] 수정 실패");
		}
	}

	@Override
	public void increaseHit(int boNo) throws BizNotEffectedException {
		int cnt = freeBoardDao.increaseHit(boNo);
	}
	
}