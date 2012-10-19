package com.hoyoul.wordroid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hoyoul.wordroid.dto.User;
import com.hoyoul.wordroid.service.UserService;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String index(Model model){
		
		model.addAttribute("user", new User());
		
		return "main/login";
	}
	
	@RequestMapping(value="/main/login",method=RequestMethod.POST)
	public String login(@ModelAttribute("user") User user,
			HttpServletRequest request
			){
		
		User queryUser = userService.getUserByLoginId(user.getLoginId());
		
		if(queryUser != null && queryUser.getPassword().equals(user.getPassword())){
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", queryUser);
			return "redirect:/main";
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/main/logout",method=RequestMethod.GET)
	public String login(HttpServletRequest request
			){

		HttpSession session = request.getSession();
		session.setAttribute("loginUser", null);
		
		return "redirect:/";
	}
	
	//JOIN CONTROL
	@RequestMapping(value="/joinpage",method=RequestMethod.GET)
	public String joinPage(Model model){
		
		model.addAttribute("user", new User());
		
		return "main/join";
	}
	
	@RequestMapping(value="/main/join",method=RequestMethod.POST)
	public String join(@ModelAttribute("user") User user,
			HttpServletRequest request){
		
		int id = userService.addUser(user);
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/main/check/{loginId}",method=RequestMethod.GET)
	public String checkLoginId(@PathVariable("loginId") String loginId,
			Model model,HttpServletRequest request){
		
		User user = userService.getUserByLoginId(loginId);
		if(user != null){
			model.addAttribute("result", "occupied already");
		}else{
			model.addAttribute("result", "true");
		}
		
		return "main/check";
	}
}
