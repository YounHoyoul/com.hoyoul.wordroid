package com.hoyoul.wordroid.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoyoul.wordroid.dto.Word;

@Transactional(readOnly = true,rollbackFor={Exception.class,SQLException.class})
public interface WordService {

	public List<Word> listWord();
	public Word getWord(Integer id);
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int addWord(Word word);
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateWord(Word word);
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteWord(Integer id);

}
