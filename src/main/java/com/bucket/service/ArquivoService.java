package com.bucket.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bucket.model.DownloadResponseVO;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ArquivoService {

	@Value("${gcp.project-id}")
	private String projectId;
	
	@Value("${gcp.bucket-upload}")
	private String bucket;
	
	public void upload(MultipartFile file) {
		
		String idArquivo = UUID.randomUUID().toString();
		LocalDate now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toLocalDate();
		String caminho = String.format("%s/%s/%s/%s", now.getYear(), now.getMonth().getValue(), now.getDayOfMonth(), idArquivo);
		
	    Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
	    BlobId blobId = BlobId.of(bucket, caminho);
	    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

	    Storage.BlobWriteOption precondition;
	    if (storage.get(bucket, caminho) == null) {
	      precondition = Storage.BlobWriteOption.doesNotExist();
	    } else {
	      precondition = Storage.BlobWriteOption.generationMatch(storage.get(bucket, caminho).getGeneration());
	    }
	    
	    try {
			storage.createFrom(blobInfo, file.getInputStream(), precondition);
		} catch (IOException e) {
			throw new RuntimeException("Não foi possível anexar o arquivo ", e);
		}

	}
	
	public DownloadResponseVO download() {

		Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
		BlobId blobId = BlobId.of(bucket, "2023/8/5/1a84f9bd-248e-44ba-b8a2-253c400f233f");
		Blob retorno = storage.get(blobId);
		
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		retorno.downloadTo(b, Blob.BlobSourceOption.generationMatch());
		byte[] res = b.toByteArray();
		Resource r = new ByteArrayResource(res);
		
		return DownloadResponseVO.builder()
				.nome("Teste")
				.arquivo(r)
				.build();
	}
	
	
}
