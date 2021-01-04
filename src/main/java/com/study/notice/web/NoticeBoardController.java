package com.study.notice.web;

import java.util.List;

import javax.validation.groups.Default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.study.common.valid.ModifyType;
import com.study.common.valid.RegistType;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotFoundException;
import com.study.exception.DaoDuplicateKeyException;
import com.study.notice.service.INoticeBoardService;
import com.study.notice.vo.NoticeBoardSearchVO;
import com.study.notice.vo.NoticeBoardVO;

@Controller			// bean에 등록된 것
@RequestMapping("/notice")	// type level에서 URL을 정의
public class NoticeBoardController {
	
	@Autowired
	INoticeBoardService noticeBoardService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());	// 앞으로 controller에는 계속 들어가야
	
	@RequestMapping(value = {"/noticeList.wow", "/"})
	public String noticeList(@ModelAttribute("searchVO") NoticeBoardSearchVO searchVO, ModelMap model) {
		logger.debug("NoticeBoardSearchVO={}", searchVO);
		List<NoticeBoardVO> board = noticeBoardService.getBoardList(searchVO);
		model.addAttribute("board", board);
		
		
		// 실험용
		List<NoticeBoardVO> notice = noticeBoardService.getNoticeList(searchVO);
		model.addAttribute("notice", notice);	// 결과를 속성에 저장하는 부분
		//
		
		return "notice/noticeList";
	}
	
	/* AJAX로 받기
	@ResponseBody
	@RequestMapping(value = {"/noticeList.wow", "/"})
	public List<NoticeBoardVO> noticeList(@ModelAttribute("searchVO") NoticeBoardSearchVO searchVO, ModelMap model) {
		logger.debug("NoticeBoardSearchVO={}", searchVO);
		List<NoticeBoardVO> board = noticeBoardService.getBoardList(searchVO);
//		model.addAttribute("board", board);
		
		
		// 실험용
		List<NoticeBoardVO> notice = noticeBoardService.getNoticeList(searchVO);
//		model.addAttribute("notice", notice);	// 결과를 속성에 저장하는 부분
		//
		
//		return "notice/noticeList";
		return notice; 
	}
	 * */
	
	@RequestMapping("/noticeView.wow")
	public String noticeView(@RequestParam(value = "noNo") int no
							// 변수 이름과 파라미터 이름이 다를 때 매핑. 속성 value와 name은 같은역할
						 , @RequestParam(value = "mode", required = false, defaultValue = "test") String mode 
						 	// test용. 파라미터로 안넘어와도 되지만, 없는 경우 넘어오는 값 지정해준 것
						 , ModelMap model) throws Exception {
		logger.debug("noNo={}", no);
		try {
//	 		System.out.println(mode);
	 		NoticeBoardVO board = noticeBoardService.getBoard(no);
	 		if(board != null) {
	 			noticeBoardService.increaseHit(no);
	 		}
		 	
	 		model.addAttribute("board", board);
	 		return "notice/noticeView";
		 } catch(Exception ex){
			 logger.error(ex.getMessage(),ex);
			 ResultMessageVO message = new ResultMessageVO();
			 message.setResult(false)
			 		.setTitle("조회 실패")
			 		.setMessage("해당 글이 존재하지 않습니다.")
			 		.setUrl("/notice/noticeList.wow")
			 		.setUrlTitle("목록으로");
			 model.addAttribute("massageVO", message);
			 return "common.massage";
		 }
	}
	
	@RequestMapping("/noticeForm.wow")
	public void noticeForm(@ModelAttribute("board") NoticeBoardVO board, ModelMap model) {
		
	}
	
	@RequestMapping(path = "/noticeRegist.wow"
				  , method = {RequestMethod.POST, RequestMethod.PUT})
	public ModelAndView noticeRegist(@ModelAttribute("board") @Validated({Default.class, RegistType.class}) NoticeBoardVO board
			 , BindingResult errors, ModelMap model) {
		logger.debug("NoticeBoardVO={}", board);
		
		ModelAndView mav = new ModelAndView();
		// 모델정보와 뷰 객체를 담음
		ResultMessageVO messageVO = new ResultMessageVO();
		
		if (errors.hasErrors()) {				// 에러가 있다면 (유효성 검증 실패)
			mav.setViewName("notice/noticeForm");	// Form 화면으로 뷰 이동
			return mav;
		}
		
		try {
			noticeBoardService.registBoard(board);
			mav.setViewName("redirect:/notice/noticeList.wow");

		} catch (DaoDuplicateKeyException e) {
			logger.error(e.getMessage(),e);
			messageVO.setResult(false)
					 .setTitle("글 등록 실패")
					 .setMessage("해당 글번호가 이미 존재합니다.")
					 .setUrl("/notice/noticeList.wow")
					 .setUrlTitle("목록으로");

			// 속성에 messageVO로 저장
			mav.addObject("messageVO", messageVO);
			mav.setViewName("common/message");
		}
		return mav;
	}
	
	@RequestMapping("/noticeEdit.wow")
	public String noticeEdit(int noNo, ModelMap model) throws Exception {
		logger.debug("noNo={}", noNo);
		try {
			NoticeBoardVO board = noticeBoardService.getBoard(noNo);
		 	model.addAttribute("board", board);
		 	return "notice/noticeEdit";
		 } catch(BizNotFoundException ex){
			 logger.error(ex.getMessage(),ex);
			 ResultMessageVO message = new ResultMessageVO();
			 message.setResult(false)
			 		.setTitle("조회 실패")
			 		.setMessage("해당 글이 존재하지 않습니다.")
			 		.setUrl("/notice/noticeList.wow")
			 		.setUrlTitle("목록으로");
			 model.addAttribute("massageVO", message);
			 return "common.massage";
		 }
	}
	
	@RequestMapping("/noticeModify.wow")							// ↓ 여기에 있는 그룹만 검증함
	public String freeModify(
			@ModelAttribute("board") @Validated({Default.class, ModifyType.class}) NoticeBoardVO board	// 사용자가 입력한 정보가 커맨드객체에 자동으로 담겨있긴 한데
																										// freeBoardVO로 담기므로 @ModelAttribute("board") 해주는 것
						   , BindingResult errors
						  // BindingResult는 꼭 커맨드객체 바로 뒤에 위치시켜야 함
						   , ModelMap model) throws Exception {
		
		logger.debug("board={}", board);
		
		if (errors.hasErrors()) {			// 에러가 있다면 (유효성 검증 실패)
			return "notice/noticeEdit";			// edit 화면으로 뷰 이동
		}
		
		ResultMessageVO messageVO = new ResultMessageVO();
		try {
			noticeBoardService.modifyBoard(board);
			
			return "redirect:/notice/noticeView.wow?noNo=" + board.getNoNo();
			
		} catch (BizNotFoundException e) {
			logger.error(e.getMessage(),e);
			messageVO.setResult(false)
					 .setTitle("글 수정 실패")
					 .setMessage("올바르게 접근해주세요.")
					 .setUrl("/notice/noticeList.wow")
					 .setUrlTitle("목록으로");
			
		} 
		// 속성에 messageVO로 저장
		model.addAttribute("messageVO", messageVO);
		return "common/message";
	}
	
	@RequestMapping("/noticeDelete.wow")
	public String noticeDelete(NoticeBoardVO board, ModelMap model) throws Exception {
		logger.debug("NoticeBoardVO={}", board);
		
		ResultMessageVO messageVO = new ResultMessageVO();
		try {
			noticeBoardService.removeBoard(board);
			
			messageVO.setResult(true)
					 .setTitle("글 삭제 성공")
					 .setMessage("게시물이 삭제되었습니다.")
					 .setUrl("/notice/noticeList.wow")
					 .setUrlTitle("목록으로");
			
		} catch (BizNotFoundException e) {
			logger.error(e.getMessage(),e);
			messageVO.setResult(false)
					 .setTitle("글 삭제 실패")
					 .setMessage("올바르게 접근해주세요.")
					 .setUrl("/notice/noticeList.wow")
					 .setUrlTitle("목록으로");
			
		} 
		model.addAttribute("messageVO", messageVO);
		return  "common/message";
	}
	
}
