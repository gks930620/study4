package com.study.member.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.ScriptAssert;

import com.study.common.valid.JoinStep1;
import com.study.common.valid.JoinStep2;

@SuppressWarnings("serial")
// 전역에러
@ScriptAssert(lang = "javascript", script = "_.memPass == _.memPassConfirm", alias = "_"
			, groups = JoinStep2.class, message = "입력한 비밀번호가 일치하지 않습니다.")
public class MemberJoinVO extends MemberVO {
	
	@NotBlank(message = "이용약관 동의는 필수입니다.", groups = JoinStep1.class)
	@Pattern(regexp = "Y", message = "이용약관 동의는 필수입니다.", groups = JoinStep1.class)
	private String agreeYn ;  
	
	@NotBlank(message = "개인정보 수집 및 이용에 대한 동의는 필수입니다.", groups = JoinStep1.class)
	@Pattern(regexp = "Y", message = "개인정보 수집 및 이용에 대한 동의는 필수입니다.", groups = JoinStep1.class)
	private String privacyYn;
	
	private String eventYn;
	
	private String memPassConfirm;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	} 
	
	public String getAgreeYn() {
		return agreeYn;
	}
	public void setAgreeYn(String agreeYn) {
		this.agreeYn = agreeYn;
	}
	public String getPrivacyYn() {
		return privacyYn;
	}
	public void setPrivacyYn(String privacyYn) {
		this.privacyYn = privacyYn;
	}
	public String getEventYn() {
		return eventYn;
	}
	public void setEventYn(String eventYn) {
		this.eventYn = eventYn;
	}

	public String getMemPassConfirm() {
		return memPassConfirm;
	}

	public void setMemPassConfirm(String memPassConfirm) {
		this.memPassConfirm = memPassConfirm;
	}
}
