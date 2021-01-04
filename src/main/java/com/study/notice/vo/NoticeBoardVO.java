package com.study.notice.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("serial")
public class NoticeBoardVO implements Serializable {

	// valid 걸기
	
	private int noNo;                  /* 글번호 */
	private String noTopYn;            /* 상단공지 */
	private String noTitle;            /* 글제목 */
	private String noContents;         /* 글내용 */
	private int noHit;                 /* 조회수 */
	private String noRegDate;          /* 등록일자 */
	private String noModDate;          /* 수정일자 */
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}  
	
	public int getNoNo() {
		return noNo;
	}
	public void setNoNo(int noNo) {
		this.noNo = noNo;
	}
	public String getNoTopYn() {
		return noTopYn;
	}
	public void setNoTopYn(String noTopYn) {
		this.noTopYn = noTopYn;
	}
	public String getNoTitle() {
		return noTitle;
	}
	public void setNoTitle(String noTitle) {
		this.noTitle = noTitle;
	}
	public String getNoContents() {
		return noContents;
	}
	public void setNoContents(String noContents) {
		this.noContents = noContents;
	}
	public int getNoHit() {
		return noHit;
	}
	public void setNoHit(int noHit) {
		this.noHit = noHit;
	}
	public String getNoRegDate() {
		return noRegDate;
	}
	public void setNoRegDate(String noRegDate) {
		this.noRegDate = noRegDate;
	}
	public String getNoModDate() {
		return noModDate;
	}
	public void setNoModDate(String noModDate) {
		this.noModDate = noModDate;
	}
	
	
}
