package com.hansung.customblog.service;

import com.hansung.customblog.dto.request.BoardSaveRequestDto;
import com.hansung.customblog.dto.request.BoardUpdateRequestDto;
import com.hansung.customblog.dto.request.ReplySaveRequestDto;
import com.hansung.customblog.dto.response.BoardDetailResponseDto;
import com.hansung.customblog.dto.response.BoardListResponseDto;
import com.hansung.customblog.dto.response.ReplyResponseDto;
import com.hansung.customblog.model.Board;
import com.hansung.customblog.model.User;
import com.hansung.customblog.repository.BoardRepository;
import com.hansung.customblog.repository.ReplyRepository;
import com.hansung.customblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private FileService fileService;

    @Transactional
    public void save(BoardSaveRequestDto boardSaveRequestDto, MultipartFile file, User user) throws IOException {
        Board newBoard = new Board.Builder()
                .title(boardSaveRequestDto.getTitle())
                .content(boardSaveRequestDto.getContent())
                .count(0)
                .user(user)
                .build();

        boardRepository.save(newBoard);

        if(file != null) {
            fileService.fileUpload(file, newBoard.getId());
        }
    }

    @Transactional(readOnly = true) // 읽기 전용을 설정해 속도 올림
    public Page<BoardListResponseDto> findBoardList(Pageable pageable) {
        return boardRepository.findBoardList(pageable);
    }

    @Transactional(readOnly = true) // 읽기 전용을 설정해 속도 올림
    public Page<BoardListResponseDto> findBoardListByKeyword(String boardKeyword, Pageable pageable) {
        return boardRepository.findBoardListByKeyword(boardKeyword, pageable);
    }

    @Transactional(readOnly = true) // 읽기 전용을 설정해 속도 올림
    public BoardDetailResponseDto findBoardDetail(int id) {
        BoardDetailResponseDto boardDetail = boardRepository.findBoardDetail(id);
        List<ReplyResponseDto> replyList = replyRepository.findReplyListByBoardId(id);
        boardDetail.setReply(replyList);
        return boardDetail;
    }

    @Transactional
    public void delete(int id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, BoardUpdateRequestDto boardUpdateRequestDto) {
        Board tmpBoard = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다."));

        Board updateBoard = tmpBoard.toBuilder()
                .title(boardUpdateRequestDto.getTitle())
                .content(boardUpdateRequestDto.getContent())
                .build();

        boardRepository.save(updateBoard);
    }

    @Transactional
    public void countUp(int id) {
        boardRepository.countUpById(id);
    }

    @Transactional
    public void replySave(ReplySaveRequestDto replySaveRequestDto) {
        replyRepository.replySave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
    }

    @Transactional
    public void replyDelete(int replyId) {
        replyRepository.deleteById(replyId);
    }
}
