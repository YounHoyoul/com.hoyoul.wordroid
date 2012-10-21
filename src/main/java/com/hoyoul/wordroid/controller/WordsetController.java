package com.hoyoul.wordroid.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hoyoul.wordroid.dto.Folder;
import com.hoyoul.wordroid.dto.Wordset;
import com.hoyoul.wordroid.service.FolderService;
import com.hoyoul.wordroid.service.WordsetService;

@Controller
public class WordsetController {
	
	@Autowired
	private WordsetService wordsetService;
	
	@Autowired
	private FolderService folderService;
	
	@RequestMapping(value="/wordset/list",method=RequestMethod.GET)
	public String listPage(Model model){
		
		model.addAttribute("wordsetList", wordsetService.listWordset());
		
		return "wordset/list";
	}
	
	/*
	@RequestMapping(value="/wordset/detail/{wordsetId}",method=RequestMethod.GET)
	public String detailPage(@PathVariable("wordsetId") Integer wordsetId,Model model){
		
		model.addAttribute("wordset", wordsetService.getWordset(wordsetId));
		
		return "wordset/detail";
	}
	
	@RequestMapping(value="/wordset/updatepage/{wordsetId}",method=RequestMethod.GET)
	public String modifyPage(@PathVariable("wordsetId") Integer wordsetId,Model model){
		
		model.addAttribute("wordset", wordsetService.getWordset(wordsetId));
		
		return "wordset/modify";
	}
	*/
	
	@RequestMapping(value="/wordset/add",method=RequestMethod.POST)
	public String add(@ModelAttribute("wordset") Wordset wordset,Model model,HttpServletRequest request){
		
		Integer folderId = null;
		
		if(request.getParameter("folderId") != null ){
			folderId = new Integer(request.getParameter("folderId"));
			if(folderId != null){
				//folder.setParentFolder(folderService.getFolder(parentId));
				wordset.setParentFolder(folderService.getFolder(folderId));
			}
		}
		
		//wordsetService.addWordset(wordset);
		
		//return "redirect:/wordset/list";
		wordsetService.addWordset(wordset);
		model.addAttribute("data", "OK");
		
		return "jsondata";
	}
	
	@RequestMapping(value="/wordset/update/{wordsetId}",method=RequestMethod.POST)
	public String update(@ModelAttribute("wordset") Wordset wordset,Model model,HttpServletRequest request){
		
		//wordsetService.updateWordset(wordset);
		
		Wordset wordset2 = wordsetService.getWordset(wordset.getId());
		wordset2.setName(wordset.getName());
		wordset2.setDescription(wordset.getDescription());
		wordsetService.updateWordset(wordset2);
		model.addAttribute("data", "OK");
		
		//return "redirect:/wordset/updatepage/"+wordset.getId();
		return "jsondata";
	}
	
	@RequestMapping(value="/wordset/delete/{wordsetId}",method=RequestMethod.GET)
	public String delete(@PathVariable("wordsetId") Integer wordsetId,Model model,HttpServletRequest request){
		
		wordsetService.deleteWordset(wordsetId);
		
		//return "redirect:/wordset/list";
		model.addAttribute("data", "OK");
		return "jsondata";
	}
}
