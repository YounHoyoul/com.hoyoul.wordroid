package com.hoyoul.wordroid.dao;

import java.util.List;

import com.hoyoul.wordroid.dto.Wordset;

public interface WordsetDAO {
	public int addWordset(Wordset wordset);
	public List<Wordset> listWordset();
	public Wordset getWordset(Integer id);
	public void updateWordset(Wordset wordset);
	public void deleteWordset(Integer id);
}
