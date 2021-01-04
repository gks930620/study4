package com.study.member.web;

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

import com.study.code.service.ICommonCodeService;
import com.study.code.vo.CodeVO;
import com.study.common.valid.ModifyType;
import com.study.common.valid.RegistType;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizDuplicateKeyException;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.member.service.IMemberService;
import com.study.member.vo.MemberSearchVO;
import com.study.member.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {

//	private IMemberService memberService = new MemberServiceImpl();
//	private ICommonCodeService codeService = new CommonCodeServiceImpl();
	
	@Autowired
	private IMemberService memberService ;
	@Autowired
	private ICommonCodeService codeService ;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());	// 앞으로 controller에는 계속 들어가야
	
	// @ModelAttribute를 통해 공통으로 사용되는 모델객체 (일반적으로 공통 코드목록)를 지정
	// 요청메서드에 진입하기 전에 호출됨
	@ModelAttribute("jobList")
	public List<CodeVO> getJobList() {
		System.out.println("getJobList Call");
		List<CodeVO> jobList = codeService.getCodeListByParent("JB00");
		return jobList;
	}
	
	@ModelAttribute("hobbyList")
	public List<CodeVO> getHobbyList() {
		System.out.println("getHobbyList Call");
		List<CodeVO> hobbyList = codeService.getCodeListByParent("HB00");
		return hobbyList;
	}
	
	@RequestMapping("/memberList.wow")
	public void memberList(@ModelAttribute("searchVO") MemberSearchVO memberVO, ModelMap model) throws Exception {
		logger.debug("memberVO={}",memberVO);
		List<MemberVO> members = memberService.getMemberList(memberVO);
		model.addAttribute("members", members);
		
//		ICommonCodeService codeService = new CommonCodeServiceImpl();
//	 	List<CodeVO> jobList = codeService.getCodeListByParent("JB00");
//	 	model.addAttribute("jobList", jobList);
//	 	List<CodeVO> likeList = codeService.getCodeListByParent("HB00");
//	 	model.addAttribute("likeList", likeList);
	 	
//		return "member/memberList";
	}
	
	@RequestMapping(path = "/memberView.wow", params = "memId")
				// @RequestMapping -> 조건. 만족해야 아래 메서드 실행
				// memberView를 요청받고, 파라미터 중에 memId가 꼭 있어야 한다
				// path = value. 4.2 이하 버전에서는 모두 value로 사용
	public String memberView(String memId 
							 , ModelMap model) throws Exception {
		logger.debug("memId={}", memId);
		try {
		 	MemberVO member = memberService.getMember(memId);
		 	model.addAttribute("member", member);
		 	return "member/memberView";
		 	
		 } catch(BizNotFoundException ex){
			 logger.error(ex.getMessage(),ex);
			 ResultMessageVO message = new ResultMessageVO();
			 message.setResult(false)
			 		.setTitle("조회 실패")
			 		.setMessage("해당 회원이 존재하지 않습니다.")
			 		.setUrl("/member/memberList.wow")
			 		.setUrlTitle("목록으로");
			 model.addAttribute("massageVO", message);
			 return "common/massage";
		 }
	}
	
	@RequestMapping(value = "/memberEdit.wow", params = "memId")
	public String memberEdit(String memId, ModelMap model) throws Exception {
//		List<CodeVO> jobList = codeService.getCodeListByParent("JB00");
//	 	List<CodeVO> hobbyList = codeService.getCodeListByParent("HB00");
//	 	model.addAttribute("jobList", jobList);
//	 	model.addAttribute("hobbyList", hobbyList);
		logger.debug("memId={}", memId);
	 	try {
		 	MemberVO member = memberService.getMember(memId);
		 	model.addAttribute("member", member);
		 	return "member/memberEdit";
		 } catch(BizNotFoundException ex){
			 ex.printStackTrace();
			 ResultMessageVO message = new ResultMessageVO();
			 message.setResult(false)
			 		.setTitle("조회 실패")
			 		.setMessage("해당 회원이 존재하지 않습니다.")
			 		.setUrl("/member/memberList.wow")
			 		.setUrlTitle("목록으로");
			 model.addAttribute("massageVO", message);
		 }
	 	 return "common.massage";
	}
	
	@RequestMapping(path = "/memberModify.wow" , params = "memId", method = RequestMethod.POST)
	public String memberModify(
			@ModelAttribute("member") @Validated({Default.class, ModifyType.class}) MemberVO member
			, BindingResult errors, String memId, ModelMap model) throws Exception {
		
		logger.debug("MemberVO={}", member);
		
		if (errors.hasErrors()) {			// 에러가 있다면 (유효성 검증 실패)
			logger.debug("errors={}", errors);
			
			return "member/memberEdit";		// edit 화면으로 뷰 이동
		}
		
		ResultMessageVO messageVO = new ResultMessageVO();
		try {
			memberService.modifyMember(member);
			
			return "redirect:/member/memberView.wow?memId=" + member.getMemId();
			
		} catch(BizNotEffectedException ex) {
			logger.error(ex.getMessage(),ex);
			messageVO.setResult(false)
					 .setTitle("수정에 실패했습니다.")
					 .setMessage("아이디나 비밀번호를 확인해주세요.")
					 .setUrl("/member/memberList.wow")
					 .setUrlTitle("목록으로");
					
		} catch(BizNotFoundException ex) {
			logger.error(ex.getMessage(),ex);
			messageVO.setResult(false)
					 .setTitle("수정에 실패했습니다.")
					 .setMessage("회원정보가 변경되지 않았습니다.")
					 .setUrl("/member/memberList.wow")
					 .setUrlTitle("목록으로");
		}
		// 속성에 messageVO로 저장
		model.addAttribute("messageVO", messageVO);
		return "common/message";
	}
	
	@RequestMapping("/memberForm.wow")
	public void memberForm(@ModelAttribute("member") MemberVO member, ModelMap model) throws Exception {
//		List<CodeVO> jobList = codeService.getCodeListByParent("JB00");
//	 	List<CodeVO> hobbyList = codeService.getCodeListByParent("HB00");
//	 	model.addAttribute("jobList", jobList);
//	 	model.addAttribute("hobbyList", hobbyList);
	 	
//		return "member/memberForm";
	}
	
	@RequestMapping(path = "/memberRegist.wow" , method = {RequestMethod.POST, RequestMethod.PUT})
	public ModelAndView memberRegist(
			@ModelAttribute("member") @Validated({Default.class, RegistType.class}) MemberVO member
			, BindingResult errors, ModelMap model) throws Exception {
		
		logger.debug("MemberVO={}", member);
		ModelAndView mav = new ModelAndView();
		// 모델정보와 뷰 객체를 담음
		ResultMessageVO messageVO = new ResultMessageVO();
		
		if (errors.hasErrors()) {				// 에러가 있다면 (유효성 검증 실패)
			logger.debug("errors={}", errors);
			mav.setViewName("member/memberForm");	// Form 화면으로 뷰 이동
			return mav;
		}
		
		try {
			memberService.registMember(member);
			mav.setViewName("redirect:/member/memberList.wow");
			
		} catch(BizDuplicateKeyException ex) {
			logger.error(ex.getMessage(),ex);
			messageVO.setResult(false)
					 .setTitle("글 등록 실패")
					 .setMessage("해당 아이디가 이미 존재합니다.")
					 .setUrl("/member/memberList.wow")
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
	
	@RequestMapping(path = "/memberDelete.wow", params = "memId")
	public String memberDelete(String memId
							 , MemberVO member
							 , ModelMap model) throws Exception {
		logger.debug("MemberVO={}", member);
		ResultMessageVO messageVO = new ResultMessageVO();
		
		try {
			memberService.removeMember(member);
			
			
			messageVO.setResult(true)
					 .setTitle("탈퇴 성공")
					 .setMessage("정상적으로 탈퇴되었습니다.")
					 .setUrl("/member/memberList.wow")
					 .setUrlTitle("목록으로");
			
		} catch (BizNotFoundException e) {
			logger.error(e.getMessage(),e);
			messageVO.setResult(false)
					 .setTitle("삭제 실패")
					 .setMessage("올바르게 접근해주세요.")
					 .setUrl("/member/memberList.wow")
					 .setUrlTitle("목록으로");
			
		} catch(BizNotEffectedException ex) {
			logger.error(ex.getMessage(),ex);
			messageVO.setResult(false)
					 .setTitle("탈퇴에 실패했습니다.")
					 .setMessage("아이디나 비밀번호를 확인해주세요.")
					 .setUrl("/member/memberList.wow")
					 .setUrlTitle("목록으로");
					
		}
		// 속성에 messageVO로 저장
		model.addAttribute("messageVO", messageVO);
		return  "common/message";
	}
	
	@RequestMapping(path = "/memberCheckedDelete.wow", params = "memId")	// memId라는 파라미터가 있어야만 실행
	public String memberCheckedDelete(@RequestParam(name = "memId") String[] memIds, ModelMap model) {
		memberService.checkedMemberDelete(memIds);
		ResultMessageVO messageVO = new ResultMessageVO();
		
		messageVO.setResult(true)
				 .setTitle("회원 탈퇴")
				 .setMessage("회원 탈퇴를 하였습니다.")
				 .setUrl("/member/memberList.wow")
				 .setUrlTitle("목록으로");
		
		model.addAttribute("messageVO", messageVO);
		return "common/message";
	}
	
}
