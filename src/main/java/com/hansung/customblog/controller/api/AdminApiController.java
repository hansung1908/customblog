package com.hansung.customblog.controller.api;

import com.hansung.customblog.dto.request.NoticeSaveRequestDto;
import com.hansung.customblog.dto.response.ResponseDto;
import com.hansung.customblog.service.BoardService;
import com.hansung.customblog.service.FileService;
import com.hansung.customblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminApiController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/api/admin/board/{boardId}")
    public ResponseDto<String> deleteById(@PathVariable int boardId) {
        fileService.fileDelete(boardId); // 외래키 참조로 파일 먼저 삭제
        boardService.delete(boardId);
        return new ResponseDto<String>(HttpStatus.OK.value(), "게시글 삭제가 완료되었습니다.");
    }

    @DeleteMapping("/api/admin/board/{boardId}/reply/{replyId}")
    public ResponseDto<String> replyDelete(@PathVariable int replyId) {
        boardService.replyDelete(replyId);
        return new ResponseDto<String>(HttpStatus.OK.value(), "댓글 삭제가 완료되었습니다.");
    }

    @DeleteMapping("api/admin/user/{userId}")
    public ResponseDto<String> userDeleteById(@PathVariable int userId) {
        userService.delete(userId);
        return new ResponseDto<String>(HttpStatus.OK.value(), "유저 삭제가 완료되었습니다.");
    }

    @PostMapping("api/admin/notice")
    public ResponseDto<String> noticeSave(@RequestBody NoticeSaveRequestDto noticeSaveRequestDto) {
        System.out.println(noticeSaveRequestDto.getTitle());
        System.out.println(noticeSaveRequestDto.getContent());
        return new ResponseDto<String>(HttpStatus.OK.value(), "공지사항 저장이 완료되었습니다.");
    }
}
