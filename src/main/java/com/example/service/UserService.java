package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.dto.LoginDto;
import com.example.dto.UserDto;
import com.example.exception.UserException;
import com.example.model.UserModel;
import com.example.repository.IUserRepo;
import com.example.utility.EmailSenderService;
import com.example.utility.JwtTokenUtil;

@Service
public class UserService implements IUserService {

	@Autowired
	IUserRepo repository;
	@Autowired
	JwtTokenUtil tokenUtil;
	@Autowired
	EmailSenderService emailSenderService;
	@Autowired
	RestTemplate restTemplate;

	@Override
	public String addRecord(UserDto addressDto) throws UserException {
		UserModel book = new UserModel(addressDto);
		repository.save(book);
		String token = tokenUtil.createToken(book.getUserId());
		String userData = "Your Details: \n" + book.getFirstName() + "\n" + book.getAddress() + "\n"
				+ book.getLastNane() + "\n" + book.getDOB() + "\n" + book.getUserId() + "\n" + book.getPassword();
		emailSenderService.sendEmail(book.getEmail_address(), "Added Your Details", userData);
		return token;
	}

	@Override
	public List<UserModel> findAll() {
		return repository.findAll();
	}

	@Override
	public UserModel FindById(Long Id) {
		Optional<UserModel> user = repository.findById(Id);
		if (user.isPresent())
			return user.get();
		else

		{
			throw new UserException("invalid id");
		}
	}

	@Override
	public UserModel FindByUserId(Long id) {
		Optional<UserModel> user = repository.findById(id);
		if (user.isPresent())
			return user.get();
		else {
			return null;
		}
	}

	@Override
	public UserModel getUserByemail(String email) {
		UserModel user = repository.findByEmail(email);
		return user;
	}

	@Override
	public UserModel editByEmail(UserDto userDTO, String email) {
		UserModel editdata = repository.findByEmail(email);
		if (editdata != null) {
			editdata.setFirstName(userDTO.getFirstName());
			editdata.setLastNane(userDTO.getLastName());
			editdata.setEmail_address(userDTO.getEmail_address());
			editdata.setAddress(userDTO.getAddress());
			editdata.setDOB(userDTO.getDOB());
			editdata.setPassword(userDTO.getPassword());
			UserModel user = repository.save(editdata);
			String token = tokenUtil.createToken(editdata.getUserId());
			emailSenderService.sendEmail(editdata.getEmail_address(), "Added Your Details/n",
					"http://localhost:8080/user/retrieve/" + token);
			return user;
		} else {
			throw new UserException("email:" + email + " is not present ");
		}

	}

	@Override
	public UserModel getDataByToken(String token) {
		Long Userid = tokenUtil.decodeToken(token);
		Optional<UserModel> existingData = repository.findById(Userid);
		if (existingData.isPresent()) {
			return existingData.get();
		} else
			throw new UserException("Invalid Token");
	}

	@Override
	public UserModel loginUser(LoginDto loginDTO) {
		Optional<UserModel> userDetails = Optional.ofNullable(repository.findByEmail(loginDTO.getEmail_address()));
		if (userDetails.isPresent()) {
			if (userDetails.get().getPassword().equals(loginDTO.getPassword())) {
				emailSenderService.sendEmail(userDetails.get().getEmail_address(), "Login", "Login Successful!");
				return userDetails.get();
			} else
				emailSenderService.sendEmail(userDetails.get().getEmail_address(), "Login",
						"You have entered Invalid password!");
			throw new UserException("Wrong Password!!!");
		} else
			throw new UserException("Login Failed, Entered wrong email or password!!!");
	}

	@Override
	public String forgotPassword(LoginDto loginDTO) {
		UserModel user = repository.findByEmail(loginDTO.getEmail_address());
		if (user != null) {
			emailSenderService.sendEmail(user.getEmail_address(), "Login", "http://localhost:8081/User/resetPassword");

			return " email link send";
		} else {
			return "Please Enter Valid Email Id And Try Again!";
		}
	}

	@Override
	public String resetPassword(LoginDto loginDTO) {
		Optional<UserModel> userDetails = Optional.ofNullable(repository.findByEmail(loginDTO.getEmail_address()));
		String password = loginDTO.getPassword();
		if (userDetails.isPresent()) {
			userDetails.get().setPassword(password);
			repository.save(userDetails.get());
			return "Password Changed";
		} else
			return "Password is not valid";
	}

	@Override
	public String deleteByid(Long Id, String token) {
		Optional<UserModel> user = repository.findById(Id);
		Long userid = tokenUtil.decodeToken(token);
		Optional<UserModel> userToken = repository.findById(userid);
		if (user.get().getFirstName().equals(userToken.get().getFirstName())) {
			repository.deleteById(Id);
			return user.get() + "User is deleted for this ID";
		} else
			throw new UserException("Data is not match");

	}

	@Override
	public UserModel verifyUser(String token) {
		Long userid = tokenUtil.decodeToken(token);
		Optional<UserModel> user = repository.findById(userid);
		if (user.isPresent()) {
			return user.get();

		} else
			throw new UserException("token is not valid");

	}

}
