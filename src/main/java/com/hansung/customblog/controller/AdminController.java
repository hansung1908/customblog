package com.hansung.customblog.controller;

import com.hansung.customblog.model.User;
import com.hansung.customblog.service.BoardService;
import com.hansung.customblog.service.FileService;
import com.hansung.customblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/boards")
    public String boardList(Model model,
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

    @GetMapping("/admin/board/{id}")
    public String boardDetail(@PathVariable int id, Model model) {
        model.addAttribute("boardDetail", boardService.boardDetail(id));
        model.addAttribute("fileName", fileService.findFileName(id));
        return "admin/boardDetail";
    }

    @GetMapping("/admin/users")
    public String userList(Model model,
                           @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("userList", userService.userList(pageable));
        return "admin/users";
    }
}
