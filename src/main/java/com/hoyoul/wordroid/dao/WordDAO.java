package com.hoyoul.wordroid.dao;

import java.util.List;

import com.hoyoul.wordroid.dto.Word;
import com.hoyoul.wordroid.dto.Wordset;

public interface WordDAO {
	
	public List<Word> listWord();
	public int addWord(Word word);
	public Word getWord(Integer id);
	public void updateWord(Word word);
	public void deleteWord(Integer id);
	public Long listWordCount(Wordset wordset);
	public Long listWordCount(Wordset wordset,String word);
	public List<Word> listWordByPage(Wordset wordset,int page, int rows);
	public List<Word> listWordByPage(Wordset wordset,String word, int page, int rows);
}
