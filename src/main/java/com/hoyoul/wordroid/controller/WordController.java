package com.hoyoul.wordroid.controller;

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

import com.hoyoul.wordroid.dto.Word;
import com.hoyoul.wordroid.service.WordService;

@Controller
public class WordController {
	
	private static final Logger logger = LoggerFactory.getLogger(WordController.class);
	
	@Autowired
	private WordService wordService;
	
	@RequestMapping(value = "/study", method = RequestMethod.GET)
	public String study() {
		
		return "study";
	}

	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public String data(Model model,HttpServletRequest request) {
		
		model.addAttribute("wordList",wordService.listWord());
		
		return "data";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("word") Word word,
			Model model,HttpServletRequest request) {
		
		wordService.addWord(word);
		
		return "result";
	}
	
	@RequestMapping(value = "/update/{wordId}", method = RequestMethod.POST)
	public String update(@PathVariable("wordId") Integer wordId,
			Model model,HttpServletRequest request) {
			
		Word word = wordService.getWord(wordId);
		word.setWord(request.getParameter("word"));
		word.setMean(request.getParameter("mean"));
		
		wordService.updateWord(word);
		
		return "result";
	}
	
	@RequestMapping(value = "/update/box/{wordId}", method = RequestMethod.POST)
	public String updateBox(@PathVariable("wordId") Integer wordId,
			Model model,HttpServletRequest request) {
		
		Word word = wordService.getWord(wordId);
		word.setBox(Integer.parseInt(request.getParameter("box")));
		
		wordService.updateWord(word);
		
		return "result";
	}
	
	@RequestMapping(value = "/delete/{wordId}", method = RequestMethod.GET)
	public String delete(@PathVariable("wordId") Integer wordId,
			Model model,HttpServletRequest request) {
		
		wordService.deleteWord(wordId);
		
		return "result";
	}	
}
