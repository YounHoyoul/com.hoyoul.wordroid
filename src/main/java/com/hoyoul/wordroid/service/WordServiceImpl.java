package com.hoyoul.wordroid.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoyoul.wordroid.dao.WordDAO;
import com.hoyoul.wordroid.dto.Word;

@Service
public class WordServiceImpl implements WordService {
	
	@Autowired
	private WordDAO wordDAO;
	
	public List<Word> listWord() {
		return wordDAO.listWord();
	}

	public int addWord(Word word) {
		return wordDAO.addWord(word);
	}

	public Word getWord(Integer id) {
		return wordDAO.getWord(id);
	}

	public void updateWord(Word word) {
		wordDAO.updateWord(word);
	}

	public void deleteWord(Integer id) {
		wordDAO.deleteWord(id);
	}

}
