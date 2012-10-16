package com.hoyoul.wordroid.service;

import java.util.List;
import com.hoyoul.wordroid.dto.Word;

public interface WordService {

	public List<Word> listWord();
	public void addWord(Word word);
	public Word getWord(Integer id);
	public void updateWord(Word word);
	public void removeWord(Integer id);

}
