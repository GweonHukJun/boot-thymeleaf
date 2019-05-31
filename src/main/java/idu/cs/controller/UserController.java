package idu.cs.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import idu.cs.domain.User;
import idu.cs.exception.ResourceNotFoundException;
import idu.cs.repository.UserRepository;

@Controller //spring Framework에게 이 클래스로 부터 작성된 객체는 controller 역할을 함을 알려줌
//spring 이 이 클래스로 부터 Bean  객체를 생성해서 등록할 수 있음
public class UserController {
	@Autowired
	UserRepository userRepo; // Dependency Injection

	@GetMapping("/")
	public String home(Model model) {
		return "index";
	}

	@GetMapping("/user-login-form")
	public String getLoginForm(Model model) {
		return "login";
	}

	@PostMapping("/login")
	public String loginUser(@Valid User user, HttpSession session) {
		User sessionUser = userRepo.findByUserid(user.getUserid());
		if(sessionUser == null) {
			System.out.println("id 오루");
			return "redirect:/user-login-form";
		}
		if(sessionUser.getUserpw().equals(user.getUserpw())) {
			session.setAttribute("user", sessionUser);
		}
		else {
			System.out.println("password 오루");
			return "redirect:/user-login-form";
		}
		//userRepo.save(user);

		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logoutUser(HttpSession session) {
		session.removeAttribute("user");
		// session.invalidate();
		//모든 세션을 삭제한다.
		return "redirect:/";
	}
	

	@GetMapping("/update")
	public String updatePage(HttpSession session) {
		
		return "update";
	}
	
	@GetMapping("/user-register-form")
	public String getForm(Model model) {
		return "register";
	}

	@GetMapping("/users")
	public String getAllUser(Model model) {
		model.addAttribute("users", userRepo.findAll());
		return "userlist";
	}

	@PostMapping("/users")
	public String createUser(@Valid User user, Model model) {
		
	
		if(userRepo.save(user) != null) {
		System.out.println("Database  성공");
		}
		else {
			System.out.println("Database 실패 ");
		}
		model.addAttribute("users", userRepo.findAll());
		return "redirect:/users";
	}

	@PostMapping("/update")
	public String updateUser(@Valid User user, Model model, HttpSession session) {
		
		User sessionUser = userRepo.findByUserid(user.getUserid());
		
		sessionUser.setName(user.getName());
		sessionUser.setCompany(user.getCompany());
		sessionUser.setUserid(user.getUserid());
		sessionUser.setUserpw(user.getUserpw());
		userRepo.save(sessionUser);
		
		session.setAttribute("user", sessionUser);
		model.addAttribute("users", userRepo.findAll());
		return "userlist";
	}
	
	@GetMapping("/users/{id}")
	public String getUserById(@PathVariable(value = "id") Long userId, Model model) throws ResourceNotFoundException {
		User user = userRepo.findById(userId).get(); // -> new ResourceNotFoundException("User not found for this id ::
														// " + userId));
		model.addAttribute("user", user);
		return "user";
		// return ResponseEntity.ok().body(user);
	}

	@GetMapping("/users/fn")
	public String getUserByName(@Param(value = "name") String name, Model model) throws ResourceNotFoundException {
		List<User> users = userRepo.findByName(name); // -> new ResourceNotFoundException("User not found for this id ::
														// " + userId));
		model.addAttribute("users", users);
		return "userlist";
		// return ResponseEntity.ok().body(user);
	}

	@PutMapping("/users/{id}") // @patchMapping
	public String updateUser(@PathVariable(value = "id") Long userId, @Valid User userDetails, Model model) {
		User user = userRepo.findById(userId).get(); // user 는 DB로 부터 읽어온 객체
		user.setName(userDetails.getName()); // userDetails는 전송한 객체
		user.setCompany(userDetails.getCompany());
		userRepo.save(user);
		model.addAttribute("users", userRepo.findAll());
		return "redirect:/users";
	}

	@DeleteMapping("/users/{id}")
	public String deleteUser(@PathVariable(value = "id") Long userId, Model model) {
		User user = userRepo.findById(userId).get();
		userRepo.delete(user);
		model.addAttribute("name", user.getName());
		return "user-deleted";
	}

}
