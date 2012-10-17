package com.hoyoul.wordroid.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hoyoul.wordroid.dto.Word;

@Repository
public class WordDAOImpl implements WordDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	public List<Word> listWord() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();		
		
		@SuppressWarnings("unchecked")
		List<Word> list = session.createQuery("from Word").list();
		
		session.getTransaction().commit();
		session.close();
		
		return list;
	}

	public int addWord(Word word) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		int id = ((Integer) session.save(word)).intValue();
		
		session.getTransaction().commit();
		session.close();
		
		return id;
	}

	public Word getWord(Integer id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Word word = (Word) session.get(Word.class, id);
		
		session.getTransaction().commit();
		session.close();
		
		return word;
	}

	public void updateWord(Word word) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.update(word);
		
		session.getTransaction().commit();
		session.close();
	}

	public void deleteWord(Integer id) {
		Word word = getWord(id);
		if(null != word){
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			session.delete(word);
			
			session.getTransaction().commit();
			session.close();
		}
	}

}
