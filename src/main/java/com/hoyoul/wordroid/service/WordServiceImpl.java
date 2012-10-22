package com.hoyoul.wordroid.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoyoul.wordroid.dao.WordDAO;
import com.hoyoul.wordroid.dto.Word;
import com.hoyoul.wordroid.dto.Wordset;

@Service
public class WordServiceImpl implements WordService {
	
	@Autowired
	private WordDAO wordDAO;
	
	@Override
	public List<Word> listWord() {
		return wordDAO.listWord();
	}

	@Override
	public int addWord(Word word) {
		return wordDAO.addWord(word);
	}

	@Override
	public Word getWord(Integer id) {
		return wordDAO.getWord(id);
	}

	@Override
	public void updateWord(Word word) {
		wordDAO.updateWord(word);
	}

	@Override
	public void deleteWord(Integer id) {
		wordDAO.deleteWord(id);
	}

	@Override
	public Long listWordCount(Wordset wordset) {
		return wordDAO.listWordCount(wordset);
	}

	@Override
	public Long listWordCount(Wordset wordset,String word) {
		return wordDAO.listWordCount(wordset,word);
	}

	@Override
	public List<Word> listWordByPage(Wordset wordset,int page, int rows) {
		return wordDAO.listWordByPage(wordset,page,rows);
	}

	@Override
	public List<Word> listWordByPage(Wordset wordset,String word, int page, int rows) {
		return wordDAO.listWordByPage(wordset,word,page,rows);
	}

}
