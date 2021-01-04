package com.study.reply.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.exception.BizAccessFailException;
import com.study.exception.BizException;
import com.study.exception.BizNotFoundException;
import com.study.reply.dao.IReplyDao;
import com.study.reply.vo.ReplySearchVO;
import com.study.reply.vo.ReplyVO;

@Service
public class ReplyServiceImpl implements IReplyService {

//	SqlSessionFactory factory = MybatisSqlSessionFactory.getSqlSessionFactory();
	
	@Autowired
	private IReplyDao replyDao;
	
	@Override
	public List<ReplyVO> getReplyListByParent(ReplySearchVO searchVO) {
//		try (SqlSession sqlSession = factory.openSession()) {			// mybatis가 옛날방식이라 해줘야하는 부분
//			IReplyDao replyDao = sqlSession.getMapper(IReplyDao.class); //
			
			int rowCount = replyDao.getReplyCountByParent(searchVO);
			searchVO.setTotalRowCount(rowCount);
			searchVO.pageSetting();
			return replyDao.getReplyListByParent(searchVO);
//		}
	}

	@Override
	public void registReply(ReplyVO reply) throws BizException {
//		try (SqlSession sqlSession = factory.openSession()) {
//			IReplyDao replyDao = sqlSession.getMapper(IReplyDao.class);
			
			replyDao.insertReply(reply);
//			sqlSession.commit();
//		}
	}

	@Override
	public void modifyReply(ReplyVO reply) throws BizNotFoundException, BizAccessFailException {
//		try (SqlSession sqlSession = factory.openSession()) {
//			IReplyDao replyDao = sqlSession.getMapper(IReplyDao.class);
			
			ReplyVO vo = replyDao.getReply(reply.getReNo());
			if (vo == null) {
				throw new BizNotFoundException("글번호[" + reply.getReNo() + "]을 조회하지 못했습니다.");
			}
			if (!vo.getReMemId().equals(reply.getReMemId())) {
				throw new BizAccessFailException("해당 글의 작성자가 아닙니다.");
			}
			replyDao.updateReply(reply);
//			sqlSession.commit();
//		}
	}

	@Override
	public void removeReply(ReplyVO reply) throws BizNotFoundException, BizAccessFailException {
//		try (SqlSession sqlSession = factory.openSession()) {
//			IReplyDao replyDao = sqlSession.getMapper(IReplyDao.class);
			
			ReplyVO vo = replyDao.getReply(reply.getReNo());
			if (vo == null) {
				throw new BizNotFoundException("글번호[" + reply.getReNo() + "]을 조회하지 못했습니다.");
			}
			if (!vo.getReMemId().equals(reply.getReMemId())) {
				throw new BizAccessFailException("해당 글의 작성자가 아닙니다.");
			}
			replyDao.deleteReply(reply);
//			sqlSession.commit();
//		}
	}

}
