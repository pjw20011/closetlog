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

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("upload") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        try {
            String uuid = UUID.randomUUID().toString();
            String originalFilename = file.getOriginalFilename();
            String filename = uuid + "_" + originalFilename;

            File folder = new File(uploadPath);
            if (!folder.exists()) {
                folder.mkdirs(); // ✅ uploads 폴더 없으면 생성
            }

            File saveFile = new File(uploadPath, filename);
            file.transferTo(saveFile);

            result.put("uploaded", true);
            result.put("url", "/uploads/" + filename); // ✅ 경로 주의

        } catch (Exception e) {
            log.error("파일 업로드 실패", e);
            result.put("uploaded", false);
        }
        return result;
    }
}
