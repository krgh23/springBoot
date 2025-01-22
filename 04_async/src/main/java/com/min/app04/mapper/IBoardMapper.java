package com.min.app04.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.min.app04.dto.BoardDto;

@Mapper
public interface IBoardMapper {
  // 페이징
  int selectBoardCount();
  List<BoardDto> selectBoardList(Map<String, Object> param);
  // 상세
  BoardDto selectBoardById(int boardId);
  // 삽입 수정 삭제
  int insertBoard(BoardDto boardDot);
  int updateBoard(BoardDto boardDot);
  int deleteBoard(int boardId);
  
}
