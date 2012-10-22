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
import com.hoyoul.wordroid.dto.ReturnData;
import com.hoyoul.wordroid.dto.User;
import com.hoyoul.wordroid.dto.Word;
import com.hoyoul.wordroid.dto.Wordset;
import com.hoyoul.wordroid.service.WordService;
import com.hoyoul.wordroid.service.WordsetService;

@Controller
public class WordController {
	
	private static final Logger logger = LoggerFactory.getLogger(WordController.class);
	
	@Autowired
	private WordService wordService;
	
	@Autowired
	private WordsetService wordsetService;
	
	@RequestMapping(value = "/study", method = RequestMethod.GET)
	public String study() {
		
		return "study";
	}

	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public String data(Model model,HttpServletRequest request) {
		
		model.addAttribute("wordList",wordService.listWord());
		
		return "data";
	}
	
	@RequestMapping(value = "/word/data/{wordsetid}", method = RequestMethod.GET)
	public String list(@PathVariable("wordsetid") Integer wordsetid,Model model,HttpServletRequest request) {
		
		int page = 1;
		int rows = 10;
		String word = "";
		
		if(request.getParameter("page") != null && !"".equals(request.getParameter("page"))){
			page = Integer.parseInt(request.getParameter("page"));
		}
		if(request.getParameter("rows") != null && !"".equals(request.getParameter("rows"))){
			rows = Integer.parseInt(request.getParameter("rows"));
		}
		if(request.getParameter("word") != null && !"".equals(request.getParameter("word"))){
			word = request.getParameter("word");
		}
		
		Wordset wordset = wordsetService.getWordset(wordsetid);
		
		ReturnData returnData = new ReturnData();
		
		if("".equals(word)){
			returnData.setTotal(wordService.listWordCount(wordset));
			returnData.setRows(wordService.listWordByPage(wordset,page,rows));
		}else{
			returnData.setTotal(wordService.listWordCount(wordset,word));
			returnData.setRows(wordService.listWordByPage(wordset,word,page,rows));
		}
		
		for(Word tmp : (List<Word>)returnData.getRows()){
			tmp.setWordset(null);
		}
		
		String jsonData = (new Gson()).toJson(returnData);
		logger.info(jsonData);
		model.addAttribute("data",jsonData);
		
		return "jsondata";
	}	
	
	@RequestMapping(value = "/word/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("word") Word word,
			Model model,HttpServletRequest request) {
		
		word.setWordset(wordsetService.getWordset(Integer.parseInt(request.getParameter("wordsetid"))));
		
		int id = wordService.addWord(word);
		
		model.addAttribute("userId",id);
		model.addAttribute("data", "{}");
		
		return "jsondata";
	}
	
	@RequestMapping(value = "/word/update/{wordId}", method = RequestMethod.POST)
	public String update(@PathVariable("wordId") Integer wordId,
			Model model,HttpServletRequest request) {
			
		Word word = wordService.getWord(wordId);
		word.setWord(request.getParameter("word"));
		word.setMean(request.getParameter("mean"));
		
		wordService.updateWord(word);
				
		model.addAttribute("data", "{}");
		
		return "jsondata";
	}
	
	@RequestMapping(value = "/word/update/box/{wordId}", method = RequestMethod.POST)
	public String updateBox(@PathVariable("wordId") Integer wordId,
			Model model,HttpServletRequest request) {
		
		Word word = wordService.getWord(wordId);
		word.setBox(Integer.parseInt(request.getParameter("box")));
		
		wordService.updateWord(word);
		
		return "result";
	}
	
	@RequestMapping(value = "/word/delete/{wordId}", method = RequestMethod.GET)
	public String delete(@PathVariable("wordId") Integer wordId,
			Model model,HttpServletRequest request) {
		
		wordService.deleteWord(wordId);
		
		model.addAttribute("data", "{}");
		
		return "jsondata";
	}	
}
