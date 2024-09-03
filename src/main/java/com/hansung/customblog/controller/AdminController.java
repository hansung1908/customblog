package com.hansung.customblog.controller;

import com.hansung.customblog.dto.response.ResponseDto;
import com.hansung.customblog.service.BoardService;
import com.hansung.customblog.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private FileService fileService;

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard"; // templates/admin/dashboard.html
    }

    @GetMapping("/admin/boards")
    public String adminBoardList(Model model,
                        @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                        @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {
        if(keyword.equals("")) { // 키워드가 없으면 모든 게시물 반환
            model.addAttribute("boardList", boardService.boardList(pageable));
        } else { // 키워드가 포함된 게시물 반환
            model.addAttribute("boardList", boardService.boardListByKeyword(keyword, pageable));
            model.addAttribute("keyword", keyword); // 키워드 값을 유지하여 페이징 처리하기 위한 keyword 값 설정
        }

        return "admin/boards";
    }

    @GetMapping("/admin/boards/{id}")
    public String adminBoardDetail(@PathVariable int id, Model model) {
        model.addAttribute("boardDetail", boardService.boardDetail(id));
        model.addAttribute("fileName", fileService.findFileName(id));
        return "admin/boardDetail";
    }

    @DeleteMapping("/admin/board/{id}")
    public ResponseDto<String> deleteById(@PathVariable int id) {
        fileService.fileDelete(id); // 외래키 참조로 파일 먼저 삭제
        boardService.delete(id);
        return new ResponseDto<String>(HttpStatus.OK.value(), "게시글 삭제가 완료되었습니다.");
    }

    @DeleteMapping("/admin/board/{boardId}/reply/{replyId}")
    public ResponseDto<String> replyDelete(@PathVariable int replyId) {
        boardService.replyDelete(replyId);
        return new ResponseDto<String>(HttpStatus.OK.value(), "댓글 삭제가 완료되었습니다.");
    }
}
