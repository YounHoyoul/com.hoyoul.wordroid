package com.hoyoul.wordroid.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoyoul.wordroid.dto.Folder;
import com.hoyoul.wordroid.dto.User;
import com.hoyoul.wordroid.dto.Wordset;

@Transactional(readOnly = true,rollbackFor={Exception.class,SQLException.class})
public interface WordsetService {

	public List<Wordset> listWordset();
	public Wordset getWordset(Integer id);
	public List<Wordset> listWordsetByUserRoot(User user);
	public List<Wordset> listWordsetByFolder(Folder folder);
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int addWordset(Wordset wordset);	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateWordset(Wordset wordset);
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteWordset(Integer id);
}
