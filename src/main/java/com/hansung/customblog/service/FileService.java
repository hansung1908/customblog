package com.hansung.customblog.service;

import com.hansung.customblog.model.File;
import com.hansung.customblog.repository.BoardRepository;
import com.hansung.customblog.repository.FileRepository;
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
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Value("${upload.path}")
    private String uploadPath; // yml에 설정한 업로드 경로

    @Transactional
    public void fileUploadAndSave(MultipartFile file, int boardId) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename(); // 중복 방지를 위한 'uuid_파일명' 형식 지정
        String filePath = uploadPath + java.io.File.separator + fileName; // 파일 업로드를 위한 경로 지정
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
    public File fileDetail(int id) {
        File fileDetail = fileRepository.findByBoardId(id).orElseGet(() -> new File.Builder().build());
        return fileDetail;
    }

    @Transactional
    public void fileDownload(int id, HttpServletResponse response) throws IOException {
        File fileDetail = fileRepository.findByBoardId(id).orElseGet(() -> new File.Builder().build());

        if(fileDetail.getFileName() != null) {
            File exFile = fileRepository.findByBoardId(id).orElseGet(() -> new File.Builder().build());

            String fileName = fileDetail.getFileName();
            System.out.println();
            String filePath = fileDetail.getFilePath();

            // 다운로드를 위한 응답 헤더 세팅
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            // 파일 다운로드
            java.io.File file = new java.io.File(filePath);
            FileInputStream fis = new FileInputStream(file); // 파일 입력
            ServletOutputStream sos = response.getOutputStream(); // 파일 출력
            FileCopyUtils.copy(fis, sos);

            fis.close();
            sos.close();
        }
    }
}
