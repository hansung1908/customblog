package com.hansung.customblog.controller;

import com.hansung.customblog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private LogService logService;

    @Autowired
    private ReportService reportService;

    @Value("${log.path}")
    private String logPath;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model,
                            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            @RequestParam(name = "noticeKeyword", required = false, defaultValue = "") String noticeKeyword) {
        if(noticeKeyword.equals("")) { // 키워드가 없으면 모든 게시물 반환
            model.addAttribute("noticeList", noticeService.noticeList(pageable));
        } else { // 키워드가 포함된 게시물 반환
            model.addAttribute("noticeList", noticeService.getNoticeListByKeyword(noticeKeyword, pageable));
            model.addAttribute("noticeKeyword", noticeKeyword); // 키워드 값을 유지하여 페이징 처리하기 위한 keyword 값 설정
        }

        model.addAttribute("logs", logService.readLatestLog(logPath));
        model.addAttribute("reportList", reportService.reportList());

        return "admin/dashboard";
    }

    @GetMapping("/admin/boards")
    public String boardList(Model model,
                        @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                        @RequestParam(name = "boardKeyword", required = false, defaultValue = "") String boardKeyword) {
        if(boardKeyword.equals("")) { // 키워드가 없으면 모든 게시물 반환
            model.addAttribute("boardList", boardService.findBoardList(pageable));
        } else { // 키워드가 포함된 게시물 반환
            model.addAttribute("boardList", boardService.findBoardListByKeyword(boardKeyword, pageable));
            model.addAttribute("boardKeyword", boardKeyword); // 키워드 값을 유지하여 페이징 처리하기 위한 keyword 값 설정
        }

        return "admin/boards";
    }

    @GetMapping("/admin/board/{id}")
    public String boardDetail(@PathVariable int id, Model model) {
        model.addAttribute("boardDetail", boardService.findBoardDetail(id));
        model.addAttribute("fileName", fileService.findFileName(id));
        return "admin/boardDetail";
    }

    @GetMapping("/admin/users")
    public String userList(Model model,
                           @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                           @RequestParam(name = "userKeyword", required = false, defaultValue = "") String userKeyword) {
        if(userKeyword.equals("")) { // 키워드가 없으면 모든 게시물 반환
            model.addAttribute("userList", userService.getUserList(pageable));
        } else { // 키워드가 포함된 게시물 반환
            model.addAttribute("userList", userService.getUserListByKeyword(userKeyword, pageable));
            model.addAttribute("userKeyword", userKeyword); // 키워드 값을 유지하여 페이징 처리하기 위한 keyword 값 설정
        }

        return "admin/users";
    }

    @GetMapping("/admin/noticeSaveForm")
    public String noticeSaveForm() {
        return "admin/noticeSaveForm";
    }
}
