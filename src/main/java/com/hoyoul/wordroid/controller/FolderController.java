package com.hoyoul.wordroid.controller;

import java.util.ArrayList;
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
import com.hoyoul.wordroid.dto.Folder;
import com.hoyoul.wordroid.dto.TreeData;
import com.hoyoul.wordroid.dto.User;
import com.hoyoul.wordroid.service.FolderService;

@Controller
public class FolderController {
	
	private static final Logger logger = LoggerFactory.getLogger(FolderController.class);
	
	@Autowired
	private FolderService folderService;
	
	@RequestMapping(value="/folder/list",method=RequestMethod.GET)
	public String listPage(Model model){
		
		model.addAttribute("folderList", folderService.listFolder());
		
		return "folder/list";
	}
	
	@RequestMapping(value="/folder/data",method=RequestMethod.GET)
	public String list(Model model,HttpServletRequest request){
		
		Integer id = 0;
		Folder folder = null;
		if(request.getParameter("id") != null){
			id = Integer.parseInt(request.getParameter("id"));
			folder = folderService.getFolder(id);
		}
		
		List<Folder> list = null;
		
		if(folder == null){
			User user = (User)(request.getSession().getAttribute("loginUser"));
			list = folderService.listFolderByUserRoot(user);
			
			if(list == null || list.size() == 0){
				Folder root = new Folder("Root","User Root Folder");
				root.setFolderUser(user);
				folderService.addFolder(root);
				list = folderService.listFolderByUserRoot(user);
			}
			
		}else{
			list = folderService.listFolderByParentFolder(folder);
		}
		
		List<TreeData> tree = new ArrayList<TreeData>();
		
		for(Folder tmp:list){
			TreeData data = new TreeData();
			data.setId(tmp.getId());
			data.setText(tmp.getName());
			data.setState("closed"); //<->"open"
			data.getAttributes().setType("folder");
			tree.add(data);
		}
		
		logger.info((new Gson()).toJson(tree));
		model.addAttribute("data", (new Gson()).toJson(tree));
		
		return "jsondata";
	}
	
	/*
	@RequestMapping(value="/folder/detail/{folderId}",method=RequestMethod.GET)
	public String detailPage(@PathVariable("folderId") Integer folderId,Model model){
		
		model.addAttribute("folder", folderService.getFolder(folderId));
		
		return "folder/detail";
	}
	
	@RequestMapping(value="/folder/updatepage/{folderId}",method=RequestMethod.GET)
	public String modifyPage(@PathVariable("folderId") Integer folderId,Model model){
		
		model.addAttribute("folder", folderService.getFolder(folderId));
		
		return "folder/modify";
	}
	*/
	
	@RequestMapping(value="/folder/add",method=RequestMethod.POST)
	public String add(@ModelAttribute("folder") Folder folder,Model model,HttpServletRequest request){
		
		Integer parentId = null;
		
		if(request.getParameter("parentid") != null ){
			parentId = new Integer(request.getParameter("parentid"));
			if(parentId != null){
				folder.setParentFolder(folderService.getFolder(parentId));
			}
		}
		
		folderService.addFolder(folder);
		model.addAttribute("data", "OK");
		
		return "jsondata";
	}
	
	@RequestMapping(value="/folder/update/{folderId}",method=RequestMethod.POST)
	public String update(@ModelAttribute("folder") Folder folder,Model model,HttpServletRequest request){
		
		Folder folder2 = folderService.getFolder(folder.getId());
		folder2.setName(folder.getName());
		folder2.setDescription(folder.getDescription());
		folderService.updateFolder(folder2);
		model.addAttribute("data", "OK");
		return "jsondata";
	}
	
	@RequestMapping(value="/folder/delete/{folderId}",method=RequestMethod.GET)
	public String delete(@PathVariable("folderId") Integer folderId,Model model,HttpServletRequest request){
		
		folderService.deleteFolder(folderId);
		model.addAttribute("data", "OK");
		return "jsondata";
	}
}
