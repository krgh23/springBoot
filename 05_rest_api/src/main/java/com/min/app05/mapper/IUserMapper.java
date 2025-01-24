package com.min.app05.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.min.app05.model.dto.UserDto;

@Mapper
public interface IUserMapper {
  int insertUser(UserDto userDto);
  int updateUser(UserDto userDto);
  int deleteUser(int userId);
  int selectUserCount();
  List<UserDto> selectUserCount(Map<String, Object> map);
  UserDto selectUserById(int userId);
}
