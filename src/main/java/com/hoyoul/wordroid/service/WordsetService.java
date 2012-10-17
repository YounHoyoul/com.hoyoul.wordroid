package com.hoyoul.wordroid.service;

import java.util.List;

import com.hoyoul.wordroid.dto.Wordset;

public interface WordsetService {

	public int addWordset(Wordset wordset);
	public List<Wordset> listWordset();
	public Wordset getWordset(Integer id);
	public void updateWordset(Wordset wordset);
	public void deleteWordset(Integer id);

}
