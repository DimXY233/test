package blog.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import blog.example.services.UserService;

@Controller
public class LoginController {
	@Autowired
	private UserService accountService;

	
	@GetMapping("/login")
	public ModelAndView getLoginPage(ModelAndView mav) {
		mav.addObject("error", false);
		mav.setViewName("login.html");
		return mav;
	}
	
	@PostMapping("/login")
	public ModelAndView login(@RequestParam String username,
			@RequestParam String password, ModelAndView mav) {
		if (accountService.createAccount(username, password)) {
			mav.addObject("name", username);
			mav.setViewName("blog.html");
		} else {
			mav.addObject("error", true);
			mav.setViewName("login.html");
		}
		return mav;
	}
}