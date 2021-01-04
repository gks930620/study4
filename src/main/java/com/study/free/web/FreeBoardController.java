package com.study.free.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.study.attach.vo.AttachVO;
import com.study.code.service.ICommonCodeService;
import com.study.code.vo.CodeVO;
import com.study.common.util.StudyAttachUtils;
import com.study.common.valid.ModifyType;
import com.study.common.valid.RegistType;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.exception.DaoDuplicateKeyException;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

@Controller			// bean에 등록된 것
@RequestMapping("/free")	// type level에서 URL을 정의
public class FreeBoardController {
	
//	IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
//	ICommonCodeService codeService = new CommonCodeServiceImpl();
	// ↓ bean에 등록해서 사용하려면 아래처럼 해야 함. FreeBoardServiceImpl에 @Service 등록
	@Autowired
	private IFreeBoardService freeBoardService;

	@Autowired
	private ICommonCodeService codeService;
	
	@Autowired
	private StudyAttachUtils attachUtils;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());	// 앞으로 controller에는 계속 들어가야
	
	// @ModelAttribute를 통해 공통으로 사용되는 모델객체 (일반적으로 공통 코드목록)를 지정
	// 요청메서드에 진입하기 전에 호출됨
	@ModelAttribute("cateList")
	public List<CodeVO> getCategoryList() {
		System.out.println("getCategoryList Call");
		List<CodeVO> cateList = codeService.getCodeListByParent("BC00");
		return cateList;
	}
	
	// method level
	// 클라이언트의 요청과 메서드를 매핑. 클라이언트가 /free/freeList.wow를 요청하면 이 메서드 실행
	@RequestMapping(value = {"/freeList.wow" , "/"})	
													// 두 개 등록해도 각각 똑같은 화면으로 가야함
	public String freeList(@ModelAttribute("searchVO") FreeBoardSearchVO searchVO
						 , ModelMap model) throws Exception {
//		System.out.println("freeList 메서드 Call");
		// 파라미터에 선언한 커맨드 객체는 자동으로 모델에 저장
		// 이름은 첫글자 소문자 클래스명
		// 모델정보를 저장할때는 request보다는 ModelMap, Model, Map을 활용
		logger.debug("FreeBoardSearchVO={}", searchVO);
	
		
		List<FreeBoardVO> board = freeBoardService.getBoardList(searchVO);
		model.addAttribute("board", board);	// 결과를 속성에 저장하는 부분
		
		
		return "free/freeList";
	}
	
	@RequestMapping("/freeView.wow")
	public String freeView(@RequestParam(value = "boNo") int no
							// 변수 이름과 파라미터 이름이 다를 때 매핑. 속성 value와 name은 같은역할
						 , @RequestParam(value = "mode", required = false, defaultValue = "test") String mode 
						 	// test용. 파라미터로 안넘어와도 되지만, 없는 경우 넘어오는 값 지정해준 것
						 , ModelMap model) throws Exception {
		logger.debug("boNo={}", no);
		try {
//	 		System.out.println(mode);
	 		FreeBoardVO free = freeBoardService.getBoard(no);
	 		if(free != null) {
	 			freeBoardService.increaseHit(no);
	 		}
		 	
	 		model.addAttribute("free", free);
	 		return "free/freeView";
		 } catch(Exception ex){
			 logger.error(ex.getMessage(),ex);
			 ResultMessageVO message = new ResultMessageVO();
			 message.setResult(false)
			 		.setTitle("조회 실패")
			 		.setMessage("해당 글이 존재하지 않습니다.")
			 		.setUrl("/free/freeList.wow")
			 		.setUrlTitle("목록으로");
			 model.addAttribute("massageVO", message);
			 return "common.message";
		 }
	}
	
	@RequestMapping("/freeEdit.wow")
	public String freeEdit(int boNo, ModelMap model) throws Exception {
		logger.debug("boNo={}", boNo);
		try {
			// @ModelAttribute를 통해 처리되었음
//			List<CodeVO> cateList = codeService.getCodeListByParent("BC00");
//		 	model.addAttribute("cateList", cateList);
		 	
		 	FreeBoardVO board = freeBoardService.getBoard(boNo);
		 	model.addAttribute("board", board);
		 	return "free/freeEdit";
		 } catch(BizNotFoundException ex){
			 logger.error(ex.getMessage(),ex);
			 ResultMessageVO message = new ResultMessageVO();
			 message.setResult(false)
			 		.setTitle("조회 실패")
			 		.setMessage("해당 글이 존재하지 않습니다.")
			 		.setUrl("/free/freeList.wow")
			 		.setUrlTitle("목록으로");
			 model.addAttribute("messageVO", message);
			 return "common.message";
		 }
	}
	
	@RequestMapping("/freeModify.wow")							// ↓ 여기에 있는 그룹만 검증함
	public String freeModify(
			@ModelAttribute("board") @Validated({Default.class, ModifyType.class}) FreeBoardVO board	// 사용자가 입력한 정보가 커맨드객체에 자동으로 담겨있긴 한데
																										// freeBoardVO로 담기므로 @ModelAttribute("board") 해주는 것
						   , BindingResult errors
						  // BindingResult는 꼭 커맨드객체 바로 뒤에 위치시켜야 함
						   , ModelMap model) throws Exception {
		
		logger.debug("board={}", board);
		
		if (errors.hasErrors()) {			// 에러가 있다면 (유효성 검증 실패)
			return "free/freeEdit";			// edit 화면으로 뷰 이동
		}
		
		ResultMessageVO messageVO = new ResultMessageVO();
		try {
			freeBoardService.modifyBoard(board);
			
			return "redirect:/free/freeView.wow?boNo=" + board.getBoNo();
			
		} catch (BizNotFoundException e) {
			logger.error(e.getMessage(),e);
			messageVO.setResult(false)
					 .setTitle("글 수정 실패")
					 .setMessage("올바르게 접근해주세요.")
					 .setUrl("/free/freeList.wow")
					 .setUrlTitle("목록으로");
			
		} catch (BizPasswordNotMatchedException e) {
			logger.error(e.getMessage(),e);
			messageVO.setResult(false)
					 .setTitle("글 수정 실패")
					 .setMessage("비밀번호가 틀립니다.")
					 .setUrl("/free/freeList.wow")
					 .setUrlTitle("목록으로");
		}
		// 속성에 messageVO로 저장
		model.addAttribute("messageVO", messageVO);
		return "common/message";
	}
	
	@RequestMapping("/freeForm.wow")
	public void freeForm(@ModelAttribute("board") FreeBoardVO board, ModelMap model) throws Exception {
						// ↑ 담겨있는건 없지만 FBVO 정의해줘야 함. 스프링 폼태그 사용시 해당 모델 이름으로 속성에 저장이 되어있어야 하므로
						// @ModelAttribute("board")를 사용하여 모델 저장
		// ↑ 뷰 이름을 현재 요청 URI로 자동으로 결정하겠다
		
		// 상단에 @ModelAttribute("cateList")를 통해 처리되었음
//		List<CodeVO> cateList = codeService.getCodeListByParent("BC00");
//		model.addAttribute("cateList", cateList);
		
	}
	
	@RequestMapping(path = "/freeRegist.wow"
				  , method = {RequestMethod.POST, RequestMethod.PUT})
							// GET방식으로 URL로 파라미터 넘겨서 글 등록되면 안되니까
	public ModelAndView freeRegist(@ModelAttribute("board") @Validated({Default.class, RegistType.class}) FreeBoardVO board
							 , BindingResult errors
							 , @RequestParam(name = "boFiles", required = false) MultipartFile[] boFiles
							 , ModelMap model
							 , HttpServletRequest req) throws Exception {
		logger.debug("FreeBoardVO={}", board);
		
		ModelAndView mav = new ModelAndView();
		// 모델정보와 뷰 객체를 담음
		ResultMessageVO messageVO = new ResultMessageVO();
		
		if (errors.hasErrors()) {				// 에러가 있다면 (유효성 검증 실패)
			mav.setViewName("free/freeForm");	// Form 화면으로 뷰 이동
			return mav;
		}
		
		if (boFiles != null) {
//			for(MultipartFile f : boFiles) {
//				logger.debug("--------------------------------------");
//				logger.debug(ToStringBuilder.reflectionToString(f, ToStringStyle.MULTI_LINE_STYLE));
//				logger.debug("--------------------------------------");
//			}
			List<AttachVO> attaches = attachUtils.getAttachListByMultiparts(boFiles, "FREE", "free");
			
			board.setAttaches(attaches);
		}
		
		try {
			board.setBoIp(req.getRemoteAddr());
			
			// vo에 저장된 업로드정보는 서비스단에서 처리한다.
			freeBoardService.registBoard(board);
//			return "redirect:/free/freeList.wow";
			mav.setViewName("redirect:/free/freeList.wow");

		} catch (DaoDuplicateKeyException e) {
			logger.error(e.getMessage(),e);
			messageVO.setResult(false)
					 .setTitle("글 등록 실패")
					 .setMessage("해당 글번호가 이미 존재합니다.")
					 .setUrl("/free/freeList.wow")
					 .setUrlTitle("목록으로");

			// 속성에 messageVO로 저장
			mav.addObject("messageVO", messageVO);
			mav.setViewName("common/message");
		}
		// 속성에 messageVO로 저장
//		model.addAttribute("messageVO", messageVO);
//		return "common/message";
		return mav;
	}
	
	@RequestMapping("/freeDelete.wow")
	public String freeDelete(FreeBoardVO board, ModelMap model) throws Exception {
		logger.debug("FreeBoardVO={}", board);
		
		ResultMessageVO messageVO = new ResultMessageVO();
		try {
			freeBoardService.removeBoard(board);
			
			messageVO.setResult(true)
					 .setTitle("글 삭제 성공")
					 .setMessage("게시물이 삭제되었습니다.")
					 .setUrl("/free/freeList.wow")
					 .setUrlTitle("목록으로");
			
		} catch (BizNotFoundException e) {
			logger.error(e.getMessage(),e);
			messageVO.setResult(false)
					 .setTitle("글 삭제 실패")
					 .setMessage("올바르게 접근해주세요.")
					 .setUrl("/free/freeList.wow")
					 .setUrlTitle("목록으로");
			
		} catch (BizPasswordNotMatchedException e) {
			logger.error(e.getMessage(),e);
			messageVO.setResult(false)
					 .setTitle("글 삭제 실패")
					 .setMessage("비밀번호가 틀립니다.")
					 .setUrl("/free/freeList.wow")
					 .setUrlTitle("목록으로");
		}
		model.addAttribute("messageVO", messageVO);
		return  "common/message";
	}
}
