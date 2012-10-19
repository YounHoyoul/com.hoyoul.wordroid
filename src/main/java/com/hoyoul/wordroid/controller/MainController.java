package com.hoyoul.wordroid.controller;

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

import com.hoyoul.wordroid.HomeController;
import com.hoyoul.wordroid.dto.User;
import com.hoyoul.wordroid.service.FolderService;
import com.hoyoul.wordroid.service.UserService;
import com.hoyoul.wordroid.service.WordsetService;

@Controller
public class MainController {

	@Autowired
	private UserService userService;

	@Autowired
	private FolderService folderService;
	
	@Autowired
	private WordsetService wordsetService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	//LOGIN CONTROL
	@RequestMapping(value="/main",method=RequestMethod.GET)
	public String main(Model model,
			HttpServletRequest request
		){
		
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		
		if(loginUser == null || loginUser.getId() == 0){
			return "redirect:/";
		}
		
		//model.addAttribute("folderList", folderService.listFolderByUserRoot(loginUser));
		//model.addAttribute("wordsetList", wordsetService.listWordsetByUserRoot(loginUser));
		//model.addAttribute("user", loginUser);
		
		return "index";
	}	
	
	@RequestMapping(value="/main/data",method=RequestMethod.GET)
	public String mainData(Model model,
				HttpServletRequest request
			){
		
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		
		if(loginUser == null){
			return "main/data";
		}
		
		model.addAttribute("folderList", folderService.listFolderByUserRoot(loginUser));
		model.addAttribute("wordsetList", wordsetService.listWordsetByUserRoot(loginUser));
		model.addAttribute("user", loginUser);
		
		return "main/data";
	}
	

}
