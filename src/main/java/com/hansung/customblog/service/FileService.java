package com.hansung.customblog.service;

import com.hansung.customblog.model.File;
import com.hansung.customblog.repository.BoardRepository;
import com.hansung.customblog.repository.FileRepository;
import com.hansung.customblog.util.FileNameUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Value("${upload.path}")
    private String uploadPath; // yml에 설정한 업로드 경로

    @Autowired
    private FileNameUtils fileNameUtils;

    @Transactional
    public void fileUpload(MultipartFile file, int boardId) throws IOException {
        String fileName = fileNameUtils.generateUniqueFileName(file.getOriginalFilename());
        String filePath = fileNameUtils.generateFilePath(uploadPath, fileName);

        java.io.File uploadFile = new java.io.File(uploadPath, fileName); // 해당 경로에 파일 복사를 위한 File 형식 지정
        byte[] fileData = file.getBytes(); // 해당 파일 데이터

        FileCopyUtils.copy(fileData, uploadFile); // 파일 업로드

        File newFile = new File.Builder() // 파일 정보 저장
                .fileName(fileName)
                .filePath(filePath)
                .fileSize((int) file.getSize())
                .fileContentType(file.getContentType())
                .board(boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.")))
                .build();

        fileRepository.save(newFile);
    }

    @Transactional
    public String findFileName(int id) {
        File fileDetail = fileRepository.findByBoardId(id).orElseGet(() -> new File.Builder().build());

        // 원래 파일명으로 수정
        if (fileDetail.getFileName() != null) {
            String originalFileName = fileDetail.getFileName();
            return fileNameUtils.extractOriginalName(originalFileName);
        }

        return null;
    }

    @Transactional
    public void fileDownload(int id, HttpServletResponse response) throws IOException {
        File fileDetail = fileRepository.findByBoardId(id).orElseThrow(() -> new IllegalArgumentException("파일 찾기 실패: 파일을 찾을 수 없습니다."));

        if(fileDetail.getFileName() != null) {
            String fileName = fileDetail.getFileName();
            String filePath = fileDetail.getFilePath();

            // 다운로드를 위한 응답 헤더 세팅
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            String originalFileName = fileNameUtils.extractOriginalName(fileName); // 원래 파일명
            String encodedFileName = fileNameUtils.encodeFileNameForUrl(originalFileName); // 인코딩한 파일명
            // Content-Disposition은 첨부 파일을 다루는 방식을 지정
            // attachment는 다운로드를 지시, inline은 브라우저에서 직접 열도록 지시
            // filename은 파일 이름 지정, filename*은 파일 이름의 인코딩 방식을 설정
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"; filename*=UTF-8''" + encodedFileName);

            // 파일 다운로드
            java.io.File file = new java.io.File(filePath); // 파일 경로 설정
            FileInputStream fis = new FileInputStream(file); // 경로에 있는 파일 입력
            ServletOutputStream sos = response.getOutputStream(); // http 응답을 통해 클라이언트에서 파일을 보내기 위한 출력
            FileCopyUtils.copy(fis, sos); // 파일 복사하여 출력 스트림에 전송

            fis.close();
            sos.close();
        }
    }

    @Transactional
    public void fileDelete(int id) {
        File fileDetail = fileRepository.findByBoardId(id).orElseGet(() -> new File.Builder().build());

        if(fileDetail.getFileName() != null) { // 파일이 있으면 동작, 없으면 생략
            String filePath = fileDetail.getFilePath();

            // 파일 삭제
            java.io.File file = new java.io.File(filePath);
            file.delete();

            // 파일 정보 삭제
            fileRepository.deleteByBoardId(id);
        }
    }
}
