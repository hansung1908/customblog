package com.hansung.customblog.service;

import com.hansung.customblog.model.Board;
import com.hansung.customblog.model.User;
import com.hansung.customblog.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void save(Board board, User user) {
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional
    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }
}
