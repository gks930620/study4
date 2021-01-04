package com.study.login.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.common.util.CookieUtils;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.login.service.ILoginService;
import com.study.login.vo.UserVO;

@Controller
@RequestMapping("/login")
public class LoginController {

//	private ILoginService loginService = new LoginServiceImpl();
	@Autowired
	private ILoginService loginService;
	
	// Common Logging : private final Log logger = LogFactory.getLog(getClass());
	// SLF4J
	private final Logger logger = LoggerFactory.getLogger(getClass());	// 앞으로 controller에는 계속 들어가야
	
	@RequestMapping(path = "/login.wow", method = RequestMethod.GET)
	public String loginGet() throws Exception {
		return "login/login";
	}

	@RequestMapping(path = "/login.wow", method = RequestMethod.POST)
	public String loginPost(UserVO vo
						  , HttpSession session
						  , @RequestParam(name = "rememberMe", required = false) String remember
						  , ModelMap model
						  , HttpServletResponse resp) throws Exception {
		
		// 여러 값을 콤마로 구분해서 처리
//		logger.debug("UserVO=", vo, ", remember=", remember);
		// 또는 {}를 사용하는 것도 가능
		logger.debug("UserVO={}, remember={}", vo,  remember);
		try {
			UserVO userVO = loginService.loginCheck(vo);

			if ("Y".equals(remember)) {
				Cookie cookie = CookieUtils.createCookie("SAVE_ID", vo.getUserId(), "/", 60 * 60 * 24 * 31);
				resp.addCookie(cookie);
			} else {
				Cookie cookie = CookieUtils.createCookie("SAVE_ID", "", "/", 0);
			}

			// 현재 사용자 정보(UserVO)를 세션에 저장
			logger.debug("세션에 정보 저장={}", userVO);
			session.setAttribute("USER_INFO", userVO);

			return "redirect:/";
		} catch (BizNotFoundException | BizPasswordNotMatchedException e) {
			logger.error(e.getMessage(),e);
			ResultMessageVO message = new ResultMessageVO();
			message.setResult(false)
				   .setTitle("로그인 실패")
				   .setMessage("회원이 존재하지 않거나 비밀번호가 틀립니다.");
			model.addAttribute("messageVO", message);
			return "common/message";
		}
	}

	@RequestMapping(path = "/logout.wow")
	public String logout(HttpSession session) throws Exception {
		UserVO user = (UserVO) session.getAttribute("USER_INFO");
		loginService.logout(null);
		logger.debug("로그아웃={}", user);
		session.invalidate();
		return "redirect:/";
	}
}
