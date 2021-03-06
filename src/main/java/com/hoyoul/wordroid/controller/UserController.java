package com.hoyoul.wordroid.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.hoyoul.wordroid.HomeController;
import com.hoyoul.wordroid.dto.ReturnData;
import com.hoyoul.wordroid.dto.User;
import com.hoyoul.wordroid.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value="/user/list",method=RequestMethod.GET)
	public String listPage(Model model,HttpServletRequest request){
		
		return "user/list";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/user/data",method=RequestMethod.GET)
	public String list(Model model,HttpServletRequest request){

		int page = 1;
		int rows = 10;
		String name = "";
		
		if(request.getParameter("page") != null && !"".equals(request.getParameter("page"))){
			page = Integer.parseInt(request.getParameter("page"));
		}
		if(request.getParameter("rows") != null && !"".equals(request.getParameter("rows"))){
			rows = Integer.parseInt(request.getParameter("rows"));
		}
		if(request.getParameter("name") != null && !"".equals(request.getParameter("name"))){
			name = request.getParameter("name");
		}
		
		ReturnData returnData = new ReturnData();
		
		if("".equals(name)){
			returnData.setTotal(userService.listUserCount());
			returnData.setRows(userService.listUserByPage(page,rows));
		}else{
			returnData.setTotal(userService.listUserCountByName(name));
			returnData.setRows(userService.listUserNameByPage(name,page,rows));
		}
		
		for(User user : (List<User>)returnData.getRows()){
			user.setFolders(null);
		}
		
		String jsonData = (new Gson()).toJson(returnData);
		logger.info(jsonData);
		model.addAttribute("data",jsonData);
		
		return "jsondata";
	}
	
	@RequestMapping(value="/user/add",method=RequestMethod.POST)
	public String add(@ModelAttribute("user") User user,Model model,HttpServletRequest request){
		
		int id = userService.addUser(user);
		
		model.addAttribute("userId",id);
		model.addAttribute("data", "{}");
		
		return "jsondata";
	}
	
	@RequestMapping(value="/user/update/{userId}",method=RequestMethod.POST)
	public String update(@PathVariable("userId") Integer userId,
			@ModelAttribute("user") User user,Model model,HttpServletRequest request){
		
		user.setId(userId);
		userService.updateUser(user);
		
		model.addAttribute("userId",userId);
		model.addAttribute("data", "{}");
		
		return "jsondata";
	}
	
	@RequestMapping(value="/user/delete/{userId}",method=RequestMethod.GET)
	public String delete(@PathVariable("userId") Integer userId,Model model,HttpServletRequest request){
		
		userService.deleteUser(userId);
		
		model.addAttribute("data", "{}");
		
		return "jsondata";
	}
}
