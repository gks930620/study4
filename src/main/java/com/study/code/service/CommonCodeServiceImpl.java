package com.study.code.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.code.dao.ICommonCodeDao;
import com.study.code.vo.CodeVO;

@Service
public class CommonCodeServiceImpl implements ICommonCodeService {
//	private ICommonCodeDao codeDao = new CommonCodeDaoOracle();
//	SqlSessionFactory factory = MybatisSqlSessionFactory.getSqlSessionFactory();
	
	@Autowired
	private ICommonCodeDao codeDao;

	@Override
	public List<CodeVO> getCodeListByParent(String parentCode) {
		List<CodeVO> list = codeDao.getCodeListByParent(parentCode);
		return list;
	}

}
