package com.hoyoul.wordroid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hoyoul.wordroid.dto.Wordset;
import com.hoyoul.wordroid.service.WordsetService;

@Controller
public class WordsetController {
	
	@Autowired
	private WordsetService wordsetService;
	
	@RequestMapping(value="/wordset/list",method=RequestMethod.GET)
	public String listPage(Model model){
		
		model.addAttribute("wordsetList", wordsetService.listWordset());
		
		return "wordset/list";
	}
	
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
	
	@RequestMapping(value="/wordset/add",method=RequestMethod.POST)
	public String add(@ModelAttribute("wordset") Wordset wordset,Model model){
		
		wordsetService.addWordset(wordset);
		
		return "redirect:/wordset/list";
	}
	
	@RequestMapping(value="/wordset/update/{wordsetId}",method=RequestMethod.POST)
	public String update(@ModelAttribute("wordset") Wordset wordset,Model model){
		
		wordsetService.updateWordset(wordset);
		
		return "redirect:/wordset/updatepage/"+wordset.getId();
	}
	
	@RequestMapping(value="/wordset/delete/{wordsetId}",method=RequestMethod.GET)
	public String delete(@PathVariable("wordsetId") Integer wordsetId,Model model){
		
		wordsetService.deleteWordset(wordsetId);
		
		return "redirect:/wordset/list";
	}
}
