package com.hansung.customblog.controller;

import com.hansung.customblog.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/file/{id}")
    public void fileDownload(@PathVariable int id, HttpServletResponse response) throws IOException {
        fileService.fileDownload(id, response);
    }
}
