package com.kh.Final3.restcontroller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.Final3.dao.AttachDao;
import com.kh.Final3.dto.AttachDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/download")
public class FileDownloadController
{
	@Autowired
	private AttachDao attachDao;

	@GetMapping("/{attachNo}")
	public ResponseEntity<ByteArrayResource> download(
			@PathVariable int attachNo) throws IOException {
		//imgNo로 imgDto 하나 조회
		AttachDto attachDto = attachDao.find(attachNo);

		//imgDto가 없으면(null) 404처리
		if(attachDto == null) {
			return ResponseEntity.notFound().build();
		}

		//실제 파일 불러오기
		File dir = new File(System.getProperty("user.home"), "upload");
		File target = new File(dir, String.valueOf(attachDto.getAttachNo()));

		byte[] data = FileUtils.readFileToByteArray(target);
		ByteArrayResource resource = new ByteArrayResource(data);

		log.debug("array = {}", resource);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8.name())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.contentLength(attachDto.getAttachSize())
				.header(HttpHeaders.CONTENT_DISPOSITION, 
					ContentDisposition.attachment()
						.filename(attachDto.getAttachName(), StandardCharsets.UTF_8)
						.build().toString()
				)
				.body(resource);
	}
}