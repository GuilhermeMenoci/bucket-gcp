package com.bucket.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bucket.model.DownloadResponseVO;
import com.bucket.service.ArquivoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/downloads")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DonwloadController {

	private final ArquivoService arquivoService;
	
	@GetMapping()
	@ResponseBody
	public ResponseEntity<Resource> getFile() {
		DownloadResponseVO download = arquivoService.download();
		
		HttpHeaders headersOut = new HttpHeaders();
		headersOut.setContentType(MediaType.valueOf("application/pdf"));
		ContentDisposition contentDisposition = ContentDisposition
				.builder(MediaType.APPLICATION_PDF.getType())
				.filename(download.getNome())
				.build();
		headersOut.setContentDisposition(contentDisposition);
		
		return ResponseEntity.ok().headers(headersOut).body(download.getArquivo());
	}
	
}
