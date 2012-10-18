package com.hoyoul.wordroid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoyoul.wordroid.dao.WordDAO;
import com.hoyoul.wordroid.dao.WordsetDAO;
import com.hoyoul.wordroid.dto.Folder;
import com.hoyoul.wordroid.dto.User;
import com.hoyoul.wordroid.dto.Word;
import com.hoyoul.wordroid.dto.Wordset;

@Service
public class WordsetServiceImpl implements WordsetService {

	@Autowired
	private WordsetDAO wordsetDAO;

	@Autowired
	private WordDAO wordDAO;
	
	@Override
	public List<Wordset> listWordset() {
		return wordsetDAO.listWordset();
	}
	
	@Override
	public int addWordset(Wordset wordset) {
		return wordsetDAO.addWordset(wordset);
	}

	@Override
	public Wordset getWordset(Integer id) {
		return wordsetDAO.getWordset(id);
	}

	@Override
	public void updateWordset(Wordset wordset) {
		wordsetDAO.updateWordset(wordset);
		List<Word> list = wordset.getWords();
		if(list != null){
			for(Word word:list){
				word.setWordset(wordset);
				if(word.getId() == null || word.getId() == 0){
					wordDAO.addWord(word);
				}else{
					wordDAO.updateWord(word);
				}
			}
		}
	}

	@Override
	public void deleteWordset(Integer id) {
		wordsetDAO.deleteWordset(id);
	}

	@Override
	public List<Wordset> listWordsetByUserRoot(User user) {
		return wordsetDAO.listWordsetByUserRoot(user);
	}

	@Override
	public List<Wordset> listWordsetByFolder(Folder folder) {
		return wordsetDAO.listWordsetByFolder(folder);
	}

}
