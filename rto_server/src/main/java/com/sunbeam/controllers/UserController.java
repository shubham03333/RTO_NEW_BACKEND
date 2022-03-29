package com.sunbeam.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import com.sunbeam.payload.Responsef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sunbeam.daos.DatabaseFileRepository;
import com.sunbeam.daos.UserDao;
import com.sunbeam.dtos.Credentials;
import com.sunbeam.dtos.DtoEntityConverter;
import com.sunbeam.dtos.Response;
import com.sunbeam.dtos.UserDTO;
import com.sunbeam.entities.DatabaseFile;
import com.sunbeam.entities.User;
import com.sunbeam.services.DatabaseFileService;
import com.sunbeam.services.UserServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user/")
public class UserController {
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private UserDao userDao;
	

//	@Autowired
//	private PasswordEncoder passwordEncoder;
	

	
	@Autowired
	    private DatabaseFileService fileStorageService;
	
	@Autowired
	DtoEntityConverter converter;
	
	DatabaseFile fileName;
	 @Autowired
	 DatabaseFileRepository databaseFileRepository;

	 	User user;
		
		DatabaseFile databaseFile;

	 @PostMapping("/uploadFile")
	    public Responsef uploadFile(@RequestParam("file") MultipartFile file) {
	    	 fileName = fileStorageService.storeFile(file);
	    

	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/downloadFile/")
	                .path(fileName.getFileName())
	                .toUriString();

	        return new Responsef(fileName.getFileName(), fileDownloadUri,
	                file.getContentType(), file.getSize());
	    }

	
	@PostMapping("/signin")
	public ResponseEntity<?> signIn(@Valid @RequestBody Credentials cred) {
		UserDTO userDto = userService.findUserByEmailAndPassword(cred);
		if(userDto == null)
			return Response.error("user not found");
		return Response.success(userDto);
	}
	

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody UserDTO userDto) {

		UserDTO result = userService.saveUser(userDto);
		System.out.println(result.getPassword());
		return Response.success(result);
	}

	@PostMapping("/register")
	public ResponseEntity<?> signUp1(@RequestBody UserDTO userDto) {
		
		if(fileName==null)
			return Response.error("please upload photo");
			
		userDto.setPhoto_id(this.fileName.getId());
		fileName=null;
		
//		User userAadhar=userService.findByAadharNo(userDto.getAadhar_no());
//		System.out.println(userAadhar);
//		if(userAadhar==null) {
//			return Response.error("User already exists");
//		}
		if(userDto.getAadhar_no().length()!=12)
		{
//			databaseFileRepository.deletePhoto(this.fileName.getId());
			return Response.error("Enter valid Aadhar Number");
		}
		UserDTO result = userService.saveUser(userDto);
		if(result==null)
		{	
//			databaseFileRepository.deletePhoto(this.fileName.getId());
			
			return Response.error("Email already exists try to enter different Email");
		}
		else if(result.getAadhar_no()==null) {
			User user = userService.findUserFromdbById(result.getId());
//			UserDTO user0 = userService.findUserById(result.getId());
			User user1 =converter.toUserEntity(userDto);
			
			
			System.out.println(user1);
			user1.setPassword(userDto.getPassword());
			System.out.println(user1.getPassword());
			this.updateUserv1(user.getId(), user1);
		}
		return Response.success(result);
	}

	
	
	@GetMapping("/search")
	public ResponseEntity<?> findUser() {
		List<User> result = new ArrayList<>();
			result = userService.findAllUsers();
		return Response.success(result);
	}
	
	// get user by id rest api
//		@GetMapping("/{id}")
//		public ResponseEntity<User> getUserById(@PathVariable int id) {
//			User user = userService.findUserFromdbById(id);
//			user.setCount(userDao.userCount());
//					if(user==null) {
//						return (ResponseEntity<User>) Response.error("User not exist with id :"+id);
//					}
////					.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
//			return ResponseEntity.ok(user);
//		}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
		UserDTO user = userService.findUserById(id);
		user.setCount(userDao.userCount());
				if(user==null) {
					return (ResponseEntity<UserDTO>) Response.error("User not exist with id :"+id);
				}
//				.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
		return ResponseEntity.ok(user);
	}
	
		
		@PutMapping("/update/{id}")
		public ResponseEntity<User> updateUserv1(@PathVariable int id, @RequestBody User UserDetails){
			User user = userService.findUserFromdbById(id);
			if(user==null) {
				return (ResponseEntity<User>) Response.error("User not exist with id :"+id);
			}
//					.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
			
			user.setName(UserDetails.getName());
			user.setDob(UserDetails.getDob());
			user.setAddress(UserDetails.getAddress());
			user.setBlood_group(UserDetails.getBlood_group());
			user.setEmail(UserDetails.getEmail());
			user.setMobile_no(UserDetails.getMobile_no());
			user.setPassword(UserDetails.getPassword());
//			user.setPassword(passwordEncoder.encode(UserDetails.getPassword()));
			user.setAadhar_no(UserDetails.getAadhar_no());
			user.setGender(UserDetails.getGender());
			user.setRole(UserDetails.getRole());
			user.setPhoto_id(UserDetails.getPhoto_id());
			
			
			
			User updatedUser = userService.saveUserdb(user);
			return ResponseEntity.ok(updatedUser);
		}
		
		
		@PutMapping("/{id}")
		public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User UserDetails){
			User user = userService.findUserFromdbById(id);
			if(user==null) {
				return (ResponseEntity<User>) Response.error("User not exist with id :"+id);
			}
//					.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
			

			user.setAddress(UserDetails.getAddress());
			user.setMobile_no(UserDetails.getMobile_no());
			user.setPassword(UserDetails.getPassword());
//			user.setPassword(passwordEncoder.encode(UserDetails.getPassword()));
			
			userService.updateUser(user.getAddress(),user.getMobile_no(),user.getPassword(),user.getId());
			return ResponseEntity.ok(user);
		}
		
		
		// delete User rest api
		@DeleteMapping("/{id}")
		public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable int id){
			User user = userService.findUserFromdbById(id);
			if(user==null) {
				return (ResponseEntity<Map<String, Boolean>>) Response.error("User not exist with id :"+id);
			}
//					.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
			
			userDao.delete(user);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return ResponseEntity.ok(response);
		}
		

	
}
