package com.sunbeam.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sunbeam.daos.UserDao;
//import com.sunbeam.dtos.BlogDTO;
import com.sunbeam.dtos.Credentials;
import com.sunbeam.dtos.DtoEntityConverter;
import com.sunbeam.dtos.UserDTO;
//import com.sunbeam.entities.Blog;
import com.sunbeam.entities.User;
import com.sunbeam.exception.UniqueContraintExeption;

@Transactional
@Service
public class UserServiceImpl {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private DtoEntityConverter converter;
	
	public User findUserFromdbByEmail(String email)
	{
		User user=userDao.findByEmail(email);
		return user;
	}
	
	public User findUserFromdbById(int userId) {
		User user = userDao.findById(userId);
		return user;
	}
	public UserDTO findUserById(int userId) {
		User user = userDao.findById(userId);
		return converter.toUserDto(user);
	}
	public UserDTO findUserByEmail(String email) {
		User user = userDao.findByEmail(email);
		return converter.toUserDto(user);
	}
	
	public UserDTO findUserByEmailAndPassword(Credentials cred) {
		User dbUser = userDao.findByEmail(cred.getEmail());
		String rawPassword = cred.getPassword();
		if(dbUser != null && passwordEncoder.matches(rawPassword, dbUser.getPassword())) {
			UserDTO result = converter.toUserDto(dbUser);
//			result.setPassword("********");
			return result;
		}
		return null;
	}

	public UserDTO saveUser(UserDTO userDto) {
		
			
		User newUser=findUserFromdbByEmail(userDto.getEmail());
		if(newUser != null) {
			if(newUser.getAadhar_no()==null) {
				UserDTO uDto=converter.toUserDto(newUser);
				return  uDto;
			}else {
				
				return null;
			}
		}
	
		String rawPassword = userDto.getPassword();
		String encPassword = passwordEncoder.encode(rawPassword);
		userDto.setPassword(encPassword);
		User user = converter.toUserEntity(userDto);
		try { 
		user = userDao.save(user);
		userDto = converter.toUserDto(user);
		return userDto;
		}catch(DataIntegrityViolationException e) 
		{
//			new UniqueContraintExeption("Aadhar already exists");
		}
	
//		userDto.setPassword("*******");
		return userDto;
		
	}
	
	
	//under testing
	public User saveUserdb(User user) {
		
		User newUser=findUserFromdbByEmail(user.getEmail());
		if(newUser != null) {
			if(newUser.getAadhar_no()==null) {
				
				return newUser;
			}
		}
		String rawPassword = user.getPassword();
		String encPassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		User user1 = userDao.save(user);
//		user1.setPassword("*******");
		return user;
	}
	
	public List<User> findAllUsers() {
		List<User> userList = userDao.findAll();
		return userList;
	}
	public User findByAadharNo(String aadhar_no) {
		
		int user_id=userDao.findIdByaadhar_no(aadhar_no) ;
		User user=userDao.findById(user_id);
		if(user==null) {
			return null;		
		}
		return user;
	}
	
	public void updateUser(String address, long mobile_no,String password,int id) {
		userDao.updateUser(address, mobile_no, password, id);
	}

	
}
