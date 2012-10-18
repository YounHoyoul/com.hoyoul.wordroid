package com.hoyoul.wordroid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hoyoul.wordroid.dto.Folder;
import com.hoyoul.wordroid.service.FolderService;

@Controller
public class FolderController {
	
	@Autowired
	private FolderService folderService;
	
	@RequestMapping(value="/folder/list",method=RequestMethod.GET)
	public String listPage(Model model){
		
		model.addAttribute("folderList", folderService.listFolder());
		
		return "folder/list";
	}
	
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
	
	@RequestMapping(value="/folder/add",method=RequestMethod.POST)
	public String add(@ModelAttribute("folder") Folder folder,Model model){
		
		folderService.addFolder(folder);
		
		return "redirect:/folder/list";
	}
	
	@RequestMapping(value="/folder/update/{folderId}",method=RequestMethod.POST)
	public String update(@ModelAttribute("folder") Folder folder,Model model){
		
		folderService.updateFolder(folder);
		
		return "redirect:/folder/updatepage/"+folder.getId();
	}
	
	@RequestMapping(value="/folder/delete/{folderId}",method=RequestMethod.GET)
	public String delete(@PathVariable("folderId") Integer folderId,Model model){
		
		folderService.deleteFolder(folderId);
		
		return "redirect:/folder/list";
	}
}
