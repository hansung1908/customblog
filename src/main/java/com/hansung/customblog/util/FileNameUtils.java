package com.hansung.customblog.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

// 파일 이름과 관련된 메소드를 모아놓은 유틸리티 클래스
@Component
public class FileNameUtils {

    // 중복 방지를 위한 'uuid_파일명' 형식으로 파일 이름 생성
    public String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID() + "_" + originalFileName;
    }

    // 파일 업로드 경로를 생성
    public String generateFilePath(String uploadPath, String fileName) {
        return uploadPath + File.separator + fileName;
    }

    // 파일 구별을 위해 붙혀둔 uuid를 제거한 원래 이름 추출
    public String extractOriginalName(String fileName) {
        return fileName.substring(fileName.indexOf('_') + 1);
    }

    // url 입력시 한글 깨짐 방지를 위한 파일 이름을 utf-8로 인코딩
    public String encodeFileNameForUrl(String fileName) throws UnsupportedEncodingException {
        return URLEncoder.encode(fileName, StandardCharsets.UTF_8).replace("+", "%20");
    }
}
