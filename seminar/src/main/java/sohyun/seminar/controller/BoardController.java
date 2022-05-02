package sohyun.seminar.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sohyun.seminar.board.dto.BoardDto;
import sohyun.seminar.board.service.BoardService;

import java.util.List;


//http요청이 진입하는 지점이며,사용자에게 서버에서 처리된 데이터를 View와 함께 응답하게 해줍니다.
//제목, 작성자, 내용을 작성하면 입력한 데이터가 데이터베이스에 저장되어야 합니다.
// 글쓰기 페이지에서 ‘글쓰기’ 버튼을 누르면, Post 방식으로 /post에 요청이 온다.
@Controller
public class BoardController {
    private BoardService boardService;


    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String list(Model model) {
        List<BoardDto> boardDtoList = boardService.getBoardList();
        model.addAttribute("postList", boardDtoList);
        return "board/list.html";
    }

    @GetMapping("/post")
    public String post() {
        return "board/post.html";
    }

    @PostMapping("/post")
    public String write(BoardDto boardDto) {
        boardService.savePost(boardDto);
        return "redirect:/";
    }


    //각 게시글을 클릭하면, /post/{id}으로 Get 요청을 합니다. (만약 1번 글을 클릭하면 /post/1로 접속됩니다.)
    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id,Model model){
        BoardDto boardDto = boardService.getPost(id);
        model.addAttribute("post",boardDto);
        return "board/detail.html";
    }


    @DeleteMapping("/post/{id}")
    public String deletePost(@PathVariable("id") Long id) {
        boardService.deletePost(id);
        return "redirect:/";
    }

}