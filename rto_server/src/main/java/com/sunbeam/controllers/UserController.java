package com.sunbeam.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import com.sunbeam.payload.Responsef;
import com.sunbeam.services.DatabaseFileService;
import com.sunbeam.services.EmailSenderServiceImpl;
import com.sunbeam.services.UserServiceImpl;
import com.sunbeam.util.JwtUtil;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user/")
public class UserController {
	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private DatabaseFileService fileStorageService;

	@Autowired
	DtoEntityConverter converter;

	DatabaseFile fileName;

	@Autowired
	DatabaseFileRepository databaseFileRepository;

	User user;

	DatabaseFile databaseFile;

	@Autowired
	private EmailSenderServiceImpl emailSenderService;

	private int randomNumber;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/authenticate")
	public String generateToken(@RequestBody Credentials credentials) throws Exception {

		try {

			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
			
		} catch (Exception e) {
			throw new Exception("Invalid username or password");
		}
	

		return jwtUtil.generateToken(credentials.getEmail());

	}

	@PostMapping("/uploadFile")
	public Responsef uploadFile(@RequestParam("file") MultipartFile file) {

		fileName = fileStorageService.storeFile(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName.getFileName()).toUriString();

		return new Responsef(fileName.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
	}

	@PostMapping("/signin")
	public ResponseEntity<?> signIn(@Valid @RequestBody Credentials cred) {
		UserDTO userDto = userService.findUserByEmailAndPassword(cred);
		if (userDto == null)
			return Response.error("user not found");
	
		System.out.println("Status " + userDto.getStatus1());
		return Response.success(userDto);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@Valid @RequestBody UserDTO userDto) {

		try {
			Credentials cred = new Credentials();
			cred.setEmail(userDto.getEmail());
			cred.setPassword(userDto.getPassword());

			UserDTO users = userService.findUserByEmailAndPassword(cred);

			UserDTO result = userService.saveUser(userDto);

			System.out.println(result.getPassword());

			return Response.success(result);

		} catch (Exception e) {

			return Response.error("Enter valid email id or user id already registered");
		}

	}

	@PostMapping("/register")
	public ResponseEntity<?> signUp1(@RequestBody UserDTO userDto) {

		if (fileName == null)
			return Response.error("please upload photo");

		userDto.setPhoto_id(this.fileName.getId());

		fileName = null;

//		User userAadhar=userService.findByAadharNo(userDto.getAadhar_no());
//		System.out.println(userAadhar);
//		if(userAadhar==null) {
//			return Response.error("User already exists");
//		}
		if (userDto.getAadhar_no().length() != 12) {
//			databaseFileRepository.deletePhoto(this.fileName.getId());
			return Response.error("Enter valid Aadhar Number");
		}
		UserDTO result = userService.saveUser(userDto);
		if (result == null) {
//			databaseFileRepository.deletePhoto(this.fileName.getId());

			return Response.error("Email already exists try to enter different Email");

		} else if (result.getAadhar_no() == null) {
			User user = userService.findUserFromdbById(result.getId());
//			UserDTO user0 = userService.findUserById(result.getId());
			User user1 = converter.toUserEntity(userDto);

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
		if (user == null) {
			return (ResponseEntity<UserDTO>) Response.error("User not exist with id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
		return ResponseEntity.ok(user);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<User> updateUserv1(@PathVariable int id, @RequestBody User UserDetails) {
		User user = userService.findUserFromdbById(id);
		if (user == null) {
			return (ResponseEntity<User>) Response.error("User not exist with id :" + id);
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
	public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User UserDetails) {
		User user = userService.findUserFromdbById(id);
		if (user == null) {
			return (ResponseEntity<User>) Response.error("User not exist with id :" + id);
		}
//					.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));

		user.setAddress(UserDetails.getAddress());
		user.setMobile_no(UserDetails.getMobile_no());
		user.setPassword(UserDetails.getPassword());
		user.setEmail(UserDetails.getEmail());
		user.setStatus(UserDetails.getStatus());
		user.setNotification(UserDetails.getNotification());
//			user.setPassword(passwordEncoder.encode(UserDetails.getPassword()));

//		userService.updateUser(user.getAddress(), user.getMobile_no(), user.getPassword(), user.getId());
		userService.updateUser(user.getAddress(), user.getMobile_no(), user.getPassword(), user.getStatus(),user.getNotification(),
				user.getId());
		return ResponseEntity.ok(user);
	}

	@PutMapping("/updateStatus/{id}")
	public ResponseEntity<User> updateUserStatus(@PathVariable int id, @RequestBody User UserDetails) {
		User user = userService.findUserFromdbById(id);
		if (user == null) {
			return (ResponseEntity<User>) Response.error("User not exist with id :" + id);
		}

		user.setStatus(UserDetails.getStatus());
//			user.setPassword(passwordEncoder.encode(UserDetails.getPassword()));

		userDao.updateUserStatus(user.getStatus(), user.getId());

		return ResponseEntity.ok(user);
	}

//	@PutMapping("/updateNotification/{id}")
//	public ResponseEntity<User> updateUserNotification(@PathVariable int id, @RequestBody User UserDetails) {
//		User user = userService.findUserFromdbById(id);
//		if (user == null) {
//			return (ResponseEntity<User>) Response.error("User not exist with id :" + id);
//		}
//
//		user.setNotification(UserDetails.getNotification());
////			user.setPassword(passwordEncoder.encode(UserDetails.getPassword()));
//
//		userDao.updateUserStatus(user.getStatus(), user.getId());
//
//		return ResponseEntity.ok(user);
//	}

	// delete User rest api
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable int id) {
		User user = userService.findUserFromdbById(id);
		if (user == null) {
			return (ResponseEntity<Map<String, Boolean>>) Response.error("User not exist with id :" + id);
		}
//					.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));

		userDao.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/forgotPasswordinit")
	public ResponseEntity<?> forgotPassword(@RequestBody Credentials cred) throws MessagingException {

		User user = userService.findUserFromdbByEmail(cred.getEmail());

		Random random = new Random();

		randomNumber = random.nextInt(10000);

		System.out.println("random number: " + randomNumber);

		if (user == null)
			return Response.error("user not found");

		emailSenderService
				.sendSimpleEmail(
						user.getEmail(), "Dear " + user.getName() + ",\n\n" + "Your OTP for password Reset is [ "
								+ randomNumber + " ] .\n" + "\n" + "Warm Regards,\n" + "RTO Info Group,\n",
						"Password reset request");

		return Response.success(user);
	}

	@PostMapping("/forgotPasswordprocess")
	public ResponseEntity<?> forgotPasswordprocessing(@RequestBody Credentials cred) throws MessagingException {

		User user = userService.findUserFromdbByEmail(cred.getEmail());

		if (cred.getOtp() == randomNumber) {

			String rawPassword = cred.getPassword();

			String encPassword = passwordEncoder.encode(rawPassword);

			user.setPassword(encPassword);

//			  System.out.println("enc paasword "+encPassword);

			userDao.updateUserPassword(encPassword, user.getId());

			if (user == null)
				return Response.error("user not found");

			emailSenderService.sendSimpleEmail(user.getEmail(),
					"Dear " + user.getName() + ",\n\n"
							+ "Your password for rto management website is successfully changed.\n" + "\n"
							+ "Warm Regards,\n" + "RTO Info Group,\n",
					"Your password have been reset ");

			randomNumber = 0;

			return Response.success(user);

		}

		return Response.error("Please enter valid otp!!!!");

//		System.out.println("random number: "+randomNumber);

	}

}
