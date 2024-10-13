package com.hansung.customblog.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.hansung.customblog.repository.BoardRepository;
import com.hansung.customblog.repository.ReplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class BoardServiceTest {

    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private ReplyRepository replyRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindBoardDetail_NotFound() {
        // 아이디를 못찾는 상황
        int boardId = 999;
        when(boardRepository.findBoardDetail(boardId)).thenReturn(Optional.empty());

        // 메소드 실행 및 예외 검증
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            boardService.findBoardDetail(boardId);
        });

        // 예외 메세지 검증
        assertEquals("글 찾기 실패: 아이디를 찾을 수 없습니다.", exception.getMessage());
    }
}
