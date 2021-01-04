package com.study.member.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.study.code.service.ICommonCodeService;
import com.study.code.vo.CodeVO;
import com.study.common.valid.JoinStep1;
import com.study.common.valid.JoinStep2;
import com.study.common.valid.JoinStep3;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotFoundException;
import com.study.member.service.IMemberService;
import com.study.member.vo.MemberJoinVO;
import com.study.member.vo.MemberSearchVO;
import com.study.member.vo.MemberVO;

@Controller
@RequestMapping("/join")
@SessionAttributes(names = "memberJoin")	// @ModelAttribute의 name과 맞아야 함
public class MemberJoinController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IMemberService memberService ;
	
	@Autowired
	private ICommonCodeService codeService ;

	private List<MemberVO> list;
	
	// @ModelAttribute
	// 공통(반복)적으로 사용되는 모델객체를 저장할 때 사용 
	// 해당 컨트롤러의 모든 요청 전에 호출되어 저장됩니다.
	// * 만약 동일한 이름의 모델이 존재한다면 처리하지 않습니다.
	@ModelAttribute("memberJoin")
	public MemberJoinVO getInitJoinVO() {
		MemberJoinVO joinVO = new MemberJoinVO();
		return joinVO;
	}
	
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
	
	@GetMapping(value = {"/join","/step1"})
	public String step1(@ModelAttribute("memberJoin") MemberJoinVO joinVO) throws Exception {
		logger.debug("step1 = {}", joinVO);
		
		return "join/step1";
	}
	
	@PostMapping(value = "/step2")
	public String step2(@ModelAttribute("memberJoin") @Validated(JoinStep1.class) MemberJoinVO joinVO, BindingResult errors) throws Exception {
		logger.debug("step2 = {}", joinVO);
		if(errors.hasErrors()) {
			logger.info("step2 검증실패 {}", errors);
			return "join/step1";
		}
		return "join/step2";
	}
	
	@PostMapping(value = "/step3")
	public String step3(@ModelAttribute("memberJoin") @Validated(JoinStep2.class) MemberJoinVO joinVO, BindingResult errors, MemberSearchVO searchVO) throws Exception {
		logger.debug("step3 = {}", joinVO);
		System.out.println(list);
		
		if(errors.hasErrors()) {
			logger.info("step3 검증실패 {}", errors);
			return "join/step2";
		}
		try {
			// 해당 아이디가 사용중인 경우
			MemberVO vo = memberService.getMember(joinVO.getMemId());
			errors.rejectValue("memId", "errors.required", "해당 아이디는 사용중입니다.");
			return "join/step2";
			
		} catch (BizNotFoundException e) {
			return "join/step3";
		}
	}
	
	@PostMapping(value = "/regist")
																// JoinStep1~3 전부 검증해야 함
	public String regist(@ModelAttribute("memberJoin") @Validated({JoinStep1.class, JoinStep2.class, JoinStep3.class}) MemberJoinVO joinVO, BindingResult errors
						, SessionStatus status, ModelMap model) throws Exception {
		logger.debug("regist = {}", joinVO);
		if(errors.hasErrors()) {
			logger.info("regist 검증실패 {}", errors);
			return "join/step3";
		}
		
		// try-catch 하기
		ResultMessageVO message = new ResultMessageVO();
		memberService.registMember(joinVO);
		status.setComplete(); 			// 현재 세션에 있는 모델정보 제거 후 이동(서버 자원적인 측면)
		message.setResult(true)
			   .setTitle("가입 완료")
			   .setMessage("회원가입을 축하합니다!")
			   .setUrl("/login/login.wow")
			   .setUrlTitle("로그인");
		model.addAttribute("resultMessage", message);
		
		return "join/regist";
	}
	
	@GetMapping(value = "/cancel")
	public String cancel(SessionStatus status) throws Exception {
		status.setComplete(); 			// 현재 세션에 있는 모델정보 제거 후 이동(서버 자원적인 측면)
		return "redirect:/";
	}
}
