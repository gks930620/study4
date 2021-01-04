package com.study.free.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.code.vo.CodeVO;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;
import com.study.servlet.IController;

public class FreeListAjaxController implements IController {
	
	IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
	ICommonCodeService codeService = new CommonCodeServiceImpl();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		FreeBoardSearchVO searchVO = new FreeBoardSearchVO();
		BeanUtils.populate(searchVO, req.getParameterMap());
		
		List<FreeBoardVO> board = freeBoardService.getBoardList(searchVO);
	 	List<CodeVO> cateList = codeService.getCodeListByParent("BC00");
	 	
	 	Map<String, Object> modeMap = new HashedMap();
	 	modeMap.put("search", searchVO);
	 	modeMap.put("boardList", board);
	 	modeMap.put("cateList", cateList);
	 	
	 	req.setAttribute("model", modeMap);
	 	// model은 jsonView에, jsonView라는 이름은 StudyViewResolver에 정해져있음
		return "jsonView";
	}

}
