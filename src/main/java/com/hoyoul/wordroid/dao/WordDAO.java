package com.hoyoul.wordroid.dao;

import java.util.List;

import com.hoyoul.wordroid.dto.Word;

public interface WordDAO {
	
	public List<Word> listWord();
	public int addWord(Word word);
	public Word getWord(Integer id);
	public void updateWord(Word word);
	public void deleteWord(Integer id);
	
}
