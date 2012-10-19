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
	
	@RequestMapping(value="/main",method=RequestMethod.GET)
	public String main(Model model,
			HttpServletRequest request
		){
		
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		
		if(loginUser == null || loginUser.getId() == 0){
			return "redirect:/";
		}
		
		model.addAttribute("folderList", folderService.listFolderByUserRoot(loginUser));
		model.addAttribute("wordsetList", wordsetService.listWordsetByUserRoot(loginUser));
		model.addAttribute("user", loginUser);
		
		return "index";
	}	
	
	@RequestMapping(value="/main/data",method=RequestMethod.GET)
	public String mainData(Model model,
				HttpServletRequest request
			){
		
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		
		model.addAttribute("folderList", folderService.listFolderByUserRoot(loginUser));
		model.addAttribute("wordsetList", wordsetService.listWordsetByUserRoot(loginUser));
		model.addAttribute("user", loginUser);
		
		return "main/data";
	}
	
	//JOIN CONTROL
	@RequestMapping(value="/joinpage",method=RequestMethod.GET)
	public String joinPage(Model model){
		
		model.addAttribute("user", new User());
		
		return "main/join";
	}
	
	@RequestMapping(value="/main/join",method=RequestMethod.POST)
	public String join(@ModelAttribute("user") User user){
		
		int id = userService.addUser(user);
		
		return "main/login";
	}
	
	@RequestMapping(value="/main/check/{loginId}",method=RequestMethod.GET)
	public String checkLoginId(@PathVariable("loginId") String loginId,
			Model model){
		
		User user = userService.getUserByLoginId(loginId);
		if(user != null){
			model.addAttribute("result", "occupied already");
		}else{
			model.addAttribute("result", "");
		}
		
		return "main/check";
	}
}
