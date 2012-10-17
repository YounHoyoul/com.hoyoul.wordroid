package com.hoyoul.wordroid.controller;

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
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/user/list",method=RequestMethod.GET)
	public String listPage(Model model){
		
		model.addAttribute("userList", userService.listUser());
		
		return "user/list";
	}
	
	@RequestMapping(value="/user/detail/{userId}",method=RequestMethod.GET)
	public String detailPage(@PathVariable("userId") Integer userId,Model model){
		
		model.addAttribute("user", userService.getUser(userId));
		
		return "user/detail";
	}
	
	@RequestMapping(value="/user/updatepage/{userId}",method=RequestMethod.GET)
	public String modifyPage(@PathVariable("userId") Integer userId,Model model){
		
		model.addAttribute("user", userService.getUser(userId));
		
		return "user/modify";
	}
	
	@RequestMapping(value="/user/add",method=RequestMethod.POST)
	public String add(@ModelAttribute("user") User user,Model model){
		
		userService.addUser(user);
		
		return "redirect:/user/list";
	}
	
	@RequestMapping(value="/user/update/{userId}",method=RequestMethod.POST)
	public String update(@ModelAttribute("user") User user,Model model){
		
		userService.updateUser(user);
		
		return "redirect:/user/updatepage/"+user.getId();
	}
	
	@RequestMapping(value="/user/delete/{userId}",method=RequestMethod.GET)
	public String delete(@PathVariable("userId") Integer userId,Model model){
		
		userService.deleteUser(userId);
		
		return "redirect:/user/list";
	}
}
