// ImageUploadController.java
package com.jw.fashionreview.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Controller
public class ImageUploadController {

    private String uploadPath = "C:/Users/kisyj/Desktop/fashionReview/fashionreview/uploads";

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<?> upload(@RequestParam("upload") MultipartFile file) {
        try {
            log.info("업로드 경로: {}", uploadPath);  // 경로 확인용

            File dir = new File(uploadPath);
            if (!dir.exists()) dir.mkdirs();

            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            originalFileName = originalFileName.replaceAll("[^a-zA-Z0-9._-]", "_");
            String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

            File destination = new File(uploadPath, uniqueFileName);
            file.transferTo(destination);

            Map<String, Object> response = new HashMap<>();
            response.put("url", "/uploads/" + uniqueFileName);  // key 변경
            return ResponseEntity.ok(response);


        } catch (Exception e) {
            log.error("파일 업로드 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Cannot upload file: " + file.getOriginalFilename()));
        }
    }
}
