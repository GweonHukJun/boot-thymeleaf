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

import com.mysql.cj.Session;

import org.springframework.web.bind.annotation.PathVariable;

import idu.cs.domain.User;
import idu.cs.entity.UserEntity;
import idu.cs.exception.ResourceNotFoundException;
import idu.cs.repository.UserRepository;
import idu.cs.service.UserService;

@Controller //spring Framework에게 이 클래스로 부터 작성된 객체는 controller 역할을 함을 알려줌
//spring 이 이 클래스로 부터 Bean  객체를 생성해서 등록할 수 있음
public class QuestionController {
	
	@Autowired UserService userService;
	
	@GetMapping("/")
	public String home(Model model) {
		return "index";
	}

	@GetMapping("/user-login-form")
	public String getLoginForm(Model model) {
		return "login";
	}

	@PostMapping("/login")
	public String loginUser(@Valid UserEntity user, HttpSession session) {
		User sessionUser = userService.getUserByUserId(user.getUserid());
		
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
	
	@GetMapping("/users")
	public String getAllUser(Model model, HttpSession session) {
		model.addAttribute("users", userService.getUsers());
		return "userlist";
	}
	
	@GetMapping("/user-register-form")
	public String getForm(Model model) {
		return "register";
	}

	@PostMapping("/users")
	public String createUser(@Valid User user, Model model) {
		userService.saveUser(user);
		return "redirect:/users"; //get 방식으로 url로 재지정한다
	}
	
	@GetMapping("/user-update-form")
	public String getUpdateForm(Model model, HttpSession session) {
		// 서비스를 통해 히파지터리로 부터 정보를 가져와야 하나 세션에 저장해두었으므로 정보를 활용하자
		User user = (User) session.getAttribute("user");
		/*
		User sessionUser = userService.getUserById(user.getId());
		model.addAttribute("user", sessionUser);
		*/
		model.addAttribute("user", user);
		return "info";
	}
	
	/*

	
	@GetMapping("/users/{id}")
	public String getUserById(@PathVariable(value = "id") Long userId, Model model) throws ResourceNotFoundException {
		UserEntity user = userRepo.findById(userId).get(); // -> new ResourceNotFoundException("User not found for this id ::
														// " + userId));
		model.addAttribute("user", user);
		return "user";
		// return ResponseEntity.ok().body(user);
	}

	@GetMapping("/users/fn")
	public String getUserByName(@Param(value = "name") String name, Model model) throws ResourceNotFoundException {
		List<UserEntity> users = userRepo.findByName(name); // -> new ResourceNotFoundException("User not found for this id ::
														// " + userId));
		model.addAttribute("users", users);
		return "userlist";
		// return ResponseEntity.ok().body(user);
	}
*/
	@PutMapping("/users/{id}") // @patchMapping
	public String updateUser(@PathVariable(value = "id") Long id, @Valid User user, Model model, HttpSession session) {
		/*
		 * updateUser 객체는 입력 폼 내용ㅇ : id 값이 없음,
		 */
		user.setId(userService.getUserById(id).getId());
		userService.updateUser(user);
		session.setAttribute("user", user);
		return "redirect:/users";
	}
/*
	@DeleteMapping("/users/{id}")
	public String deleteUser(@PathVariable(value = "id") Long userId, Model model) {
		UserEntity user = userRepo.findById(userId).get();
		userRepo.delete(user);
		model.addAttribute("name", user.getName());
		return "user-deleted";
	}
*/
	
	/*  내가 했던 update
	@PostMapping("/user-update")
	public String updateUser(@Valid UserEntity user, Model model, HttpSession session) {
		
		UserEntity sessionUser = userRepo.findByUserid(user.getUserid());
		
		sessionUser.setName(user.getName());
		sessionUser.setCompany(user.getCompany());
		sessionUser.setUserid(user.getUserid());
		sessionUser.setUserpw(user.getUserpw());
		userRepo.save(sessionUser);
		
		session.setAttribute("user", sessionUser);
		model.addAttribute("users", userRepo.findAll());
		return "userlist";
	}
	*/
}
