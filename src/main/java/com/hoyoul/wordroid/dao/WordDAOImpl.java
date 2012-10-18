package com.hoyoul.wordroid.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hoyoul.wordroid.dto.Word;

@Repository
public class WordDAOImpl implements WordDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@SuppressWarnings("unchecked")
	
	public List<Word> listWord() {
		return sessionFactory.getCurrentSession().createQuery("from Word").list();
	}

	@Override
	public int addWord(Word word) {
		return ((Integer) sessionFactory.getCurrentSession().save(word)).intValue();
	}

	@Override
	public Word getWord(Integer id) {
		return (Word) sessionFactory.getCurrentSession().get(Word.class, id);
	}

	@Override
	public void updateWord(Word word) {
		sessionFactory.getCurrentSession().update(word);
	}

	@Override
	public void deleteWord(Integer id) {
		Word word = getWord(id);
		if(null != word){
			sessionFactory.getCurrentSession().delete(word);
		}
	}

}
