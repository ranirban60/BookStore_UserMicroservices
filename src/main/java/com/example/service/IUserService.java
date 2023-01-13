package com.example.service;

import java.util.List;

import com.example.dto.LoginDto;
import com.example.dto.UserDto;
import com.example.model.UserModel;

public interface IUserService {

	String addRecord(UserDto addressDto);

	List<UserModel> findAll();

	UserModel FindById(Long id);

	UserModel getUserByemail(String email);

	UserModel editByEmail(UserDto userDTO, String email_address);

	UserModel getDataByToken(String token);

	UserModel loginUser(LoginDto loginDTO);

	String forgotPassword(LoginDto loginDTO);

	String resetPassword(LoginDto loginDTO);

	String deleteByid(Long id, String token);

	UserModel verifyUser(String token);

	UserModel FindByUserId(Long id);
}
