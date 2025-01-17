package com.min.app02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.min.app02.dto.BoardDto;
import com.min.app02.service.IBoardService;
import com.min.app02.util.PageUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {

  private final IBoardService boardService;
  private final PageUtil pageUtil;

  
  @GetMapping("/list.do")
  public ModelAndView list() throws Exception {
    pageUtil.setPaging(1, 20, 1000);
    ModelAndView mav = new ModelAndView();
    mav.setViewName("list");
    mav.addObject("boardList", boardService.getBoardList());
    mav.addObject("paging", pageUtil.getPaging("/list.do", "sort", "DESC", "query", "검색어"));
    return mav;
  }
  
  @GetMapping(value = "detail.do")
  public String detail(/* @RequestParam 생략가능*/ int boardId, Model model) {
    model.addAttribute("board", boardService.getBoardById(boardId));
    return "board/detail";
  }
  
  @PostMapping(value = "regist.do")
  public String regist(BoardDto boardDto, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("msg", boardService.registBoard(boardDto));
    return "redirect:/board/list.do";
  }
  
  @PostMapping(value = "modify.do")
  public String modify(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("msg", boardService.modifyBoard(request));
    return "redirect:/board/detail.do";
  }
  
  
  @GetMapping("/remove.do")
  public String remove(int boardId, RedirectAttributes redirectAttrs) {
    redirectAttrs.addFlashAttribute("msg", boardService.removeBoard(boardId));
    return "redirect:/list.do";
  }
  
  
  
  
  
  
  
  
  
}
