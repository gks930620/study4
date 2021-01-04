package com.study.common.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.study.attach.vo.AttachVO;

@Component
public class StudyAttachUtils {
	
	@Value("#{app['file.upload.path']}")
	private String uploadPath;
	
	private String uploadServerPath;
	
	@Autowired
	private ServletContext context;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("YYMMDD");
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/** MultipartFile에서 VO 설정 및 업로드 파일 처리 후 리턴, 없는 경우 null */
	public AttachVO getAttachByMultipart(MultipartFile multipart, String category, String path) throws IOException {
		
		if (!multipart.isEmpty()) {
			// 실제 저장할 파일명
			// 상품 : 상품 코드기반으로 이름 정하기
			// 사용자가 올린 파일명 기반 (동일한 이름 여부 확인)
			// 날짜 기반으로, 랜덤하게
			// "java어려워.dmp" -> 200918_java어려워.dmp
//			String fileName = UUID.randomUUID().toString();
			String originalName = multipart.getOriginalFilename();
			
			String fileName = sdf.format(new Date()) + "_" + originalName;
			uploadServerPath = context.getRealPath("/upload");
			
			AttachVO vo = new AttachVO();
			
//			vo.setAtchOriginalName(multipart.getOriginalFilename());
			vo.setAtchOriginalName(originalName);
			vo.setAtchFileSize(multipart.getSize());
			vo.setAtchContentType(multipart.getContentType());
			vo.setAtchFileName(fileName);
			vo.setAtchCategory(category);
			vo.setAtchPath(path);
			vo.setAtchFancySize(StudyFileUtil.fincySize(multipart.getSize()));
			
			String filePath = uploadPath + File.separatorChar + path;
			String fileServerPath = uploadServerPath + File.separatorChar + path;
			
			// 어디에 저장했는지 확인용
			logger.debug("filePath = {}, fileName = {}", filePath, fileName);
			logger.debug("uploadServerPath = {}", uploadServerPath);
			
			// multipart.transferTo(new File(filePath, fileName));
			// 일부러 두 번 저장 한 것. 하나는 워크스페이스에, 하나는 서버에
			FileUtils.copyInputStreamToFile(multipart.getInputStream(), new File(filePath, fileName));
			FileUtils.copyInputStreamToFile(multipart.getInputStream(), new File(fileServerPath, fileName));
			return vo;
		} else {
			return null;
		}
	}

	/** 다중 MultipartFile에서 VO 설정 및 업로드 파일 처리 후 List 리턴 */
	public List<AttachVO> getAttachListByMultiparts(MultipartFile[] multipartFiles, String category, String path)
			throws IOException {
		
		List<AttachVO> atchList = new ArrayList<AttachVO>();
		
		for (int i = 0; i < multipartFiles.length; i++) {
			MultipartFile multipart = multipartFiles[i];
			AttachVO vo = this.getAttachByMultipart(multipart, category, path);
			if (vo != null) {
				atchList.add(vo);
			}
		}
		return atchList;
	}
}
