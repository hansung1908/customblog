package com.hansung.customblog.controller.api;

import com.hansung.customblog.config.auth.PrincipalDetail;
import com.hansung.customblog.dto.request.BoardSaveRequestDto;
import com.hansung.customblog.dto.request.BoardUpdateRequestDto;
import com.hansung.customblog.dto.request.ReplySaveRequestDto;
import com.hansung.customblog.dto.response.ResponseDto;
import com.hansung.customblog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<String> save(@RequestBody BoardSaveRequestDto boardSaveRequestDto, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        boardService.save(boardSaveRequestDto, principalDetail.getUser());
        return new ResponseDto<String>(HttpStatus.OK.value(), "게시글 저장이 완료되었습니다.");
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<String> deleteById(@PathVariable int id) {
        boardService.delete(id);
        return new ResponseDto<String>(HttpStatus.OK.value(), "게시글 삭제가 완료되었습니다.");
    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<String> update(@PathVariable int id, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {
        boardService.update(id, boardUpdateRequestDto);
        return new ResponseDto<String>(HttpStatus.OK.value(), "게시글 수정이 완료되었습니다.");
    }

    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<String> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto) {
        boardService.replySave(replySaveRequestDto);
        return new ResponseDto<String>(HttpStatus.OK.value(), "댓글 쓰기가 완료되었습니다.");
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<String> replyDelete(@PathVariable int replyId) {
        boardService.replyDelete(replyId);
        return new ResponseDto<String>(HttpStatus.OK.value(), "댓글 삭제가 완료되었습니다.");
    }
}
