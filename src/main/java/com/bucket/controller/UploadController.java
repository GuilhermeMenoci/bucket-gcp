package com.bucket.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bucket.service.ArquivoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/uploads")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UploadController {

	private final ArquivoService arquivoService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping()
	public void uploadFile(@RequestHeader Map<String, String> headers, @RequestParam("file") MultipartFile file) {
		arquivoService.upload(file);
	}
	
}
