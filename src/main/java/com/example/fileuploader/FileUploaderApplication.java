package com.example.fileuploader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kong.unirest.MultipartBody;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@RestController
@Slf4j
public class FileUploaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileUploaderApplication.class, args);
	}

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@PostMapping(value = "/upload")
	public ResponseEntity<String> postMethodName(@RequestPart("file") MultipartFile file) throws IOException {

		MultipartBody multipartBody = Unirest
				// .post("https://webhook.site/a0d15618-3fa0-4bea-a746-f2770c55003c")
				.post("http://localhost:3050/save")
				.field("file", new ByteArrayInputStream(file.getBytes()), "yolo.jpeg");

		var response = multipartBody.asString();

		log.info("response --> {}", response.getBody());

		return ResponseEntity.ok().body("ok");
	}

	@PostMapping(value = "/save")
	public ResponseEntity<String> save(@RequestPart("file") MultipartFile file)
			throws IllegalStateException, IOException {
		log.info("image saved {}", file);

		file.transferTo(FileSystems.getDefault().getPath("yolo.jpg").toAbsolutePath());
		log.info("saved --> {} ", FileSystems.getDefault().getPath("yolo.jpg").toAbsolutePath().toString());

		return ResponseEntity.ok().body("ok");

	}

}
