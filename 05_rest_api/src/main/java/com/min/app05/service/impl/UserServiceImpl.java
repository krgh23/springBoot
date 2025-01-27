package com.min.app05.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.min.app05.mapper.IUserMapper;
import com.min.app05.model.dto.UpdateUserDto;
import com.min.app05.model.dto.InsertUserDto;
import com.min.app05.model.exception.UserNotFoundException;
import com.min.app05.service.IUserService;
import com.min.app05.util.PageUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

  private final IUserMapper userMapper;
  private final PageUtil pageUtil;
  
  @Override
  public InsertUserDto registUser(InsertUserDto insertUserDto) {
    userMapper.insertUser(insertUserDto);
    return insertUserDto;
  }
  
  @Override
  public UpdateUserDto modifyUser(UpdateUserDto updateUserDto) throws Exception {
    int updatedCount = userMapper.updateUser(updateUserDto);
    if(updatedCount == 0)
      throw new UserNotFoundException("해당 회원 번호를 가진 사용자 정보를 찾을 수 없어 수정하지 않았습니다.");
    return updateUserDto;
  }
  
  @Override
  public void removeUser(int userId) throws Exception {
    int deletedCount = userMapper.deleteUser(userId);
    if(deletedCount == 0)
      throw new UserNotFoundException("해당 회원 번호를 가진 사용자 정보를 찾을 수 없어 삭제하지 않았습니다.");
  }

  @Override
  public List<InsertUserDto> getUsers(HttpServletRequest request) {
    Optional<String> optPage = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(optPage.orElse("1"));
    Optional<String> optDisplay = Optional.ofNullable(request.getParameter("display"));
    int display = Integer.parseInt(optDisplay.orElse("20"));
    int count = userMapper.selectUserCount();
    pageUtil.setPaging(page, display, count);
    Optional<String> optSort = Optional.ofNullable(request.getParameter("sort"));
    String sort = optSort.orElse("DESC");
    return userMapper.selectUserList(Map.of("sort", sort
                                          , "offset", pageUtil.getOffset()
                                          , "display", display));
  }
  
  @Override
  public InsertUserDto getUserById(int userId) throws Exception {
    InsertUserDto foundUser = userMapper.selectUserById(userId);
    if(foundUser == null)
      throw new UserNotFoundException("해당 회원 번호를 가진 사용자 정보를 조회할 수 없습니다.");
    return foundUser;
  }
  
}
