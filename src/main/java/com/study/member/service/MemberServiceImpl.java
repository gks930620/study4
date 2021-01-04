package com.study.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.exception.BizDuplicateKeyException;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.member.dao.IMemberDao;
import com.study.member.vo.MemberSearchVO;
import com.study.member.vo.MemberVO;

@Service
public class MemberServiceImpl implements IMemberService {
	
	@Autowired
	private IMemberDao memberDao;
	
	@Override
	public MemberVO getMember(String memId) throws BizNotFoundException {
		MemberVO vo = memberDao.getMember(memId);
		if (vo == null) {
			throw new BizNotFoundException("[" + memId + "] 조회 실패");
		}
		return vo;
	}

	@Override
	public List<MemberVO> getMemberList(MemberSearchVO searchVO) {
		// 건수를 구해서 searchVO에 설정하고, searchVO에 pageSetting()을 부르고 list 호출
		int cnt = memberDao.getMemberCount(searchVO);
		searchVO.setTotalRowCount(cnt);
		searchVO.pageSetting();

		List<MemberVO> list = memberDao.getMemberList(searchVO);
		return list;
	}

	@Override
	public void registMember(MemberVO member) throws BizDuplicateKeyException {
		// 회원이 존재하는지 먼저 조회
		MemberVO vo = memberDao.getMember(member.getMemId());
		if (vo != null) {
			throw new BizDuplicateKeyException();
		}
		memberDao.insertMember(member);
	}

	@Override
	public void modifyMember(MemberVO member) throws BizNotEffectedException, BizNotFoundException {
		MemberVO vo = memberDao.getMember(member.getMemId());
		if (vo == null) {
			throw new BizNotFoundException("[" + member.getMemId() + "] 조회 실패");
		}
		int cnt = memberDao.updateMember(member);
		if (cnt < 1) {
			throw new BizNotEffectedException("[" + member.getMemId() + "] 수정 실패");
		}
	}

	@Override
	public void removeMember(MemberVO member) throws BizNotEffectedException, BizNotFoundException {
		MemberVO vo = memberDao.getMember(member.getMemId());
		if (vo == null) {
			throw new BizNotFoundException("[" + member.getMemId() + "] 삭제 실패");
		}
		int cnt = memberDao.deleteMember(member);
		if (cnt < 1) {
			throw new BizNotEffectedException("[" + member.getMemId() + "] 삭제 실패");
		}
	}

	@Override
	public void checkedMemberDelete(String[] checkedMemIds) {
		memberDao.updateMemberDelete(checkedMemIds);
	}

}