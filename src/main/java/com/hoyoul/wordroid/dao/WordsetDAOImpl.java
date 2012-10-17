package com.hoyoul.wordroid.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hoyoul.wordroid.dto.Word;
import com.hoyoul.wordroid.dto.Wordset;

public class WordsetDAOImpl implements WordsetDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Wordset> listWordset() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();		
		
		@SuppressWarnings("unchecked")
		List<Wordset> list = session.createQuery("from Wordset").list();
		
		session.getTransaction().commit();
		session.close();
		
		return list;
	}	
	
	@Override
	public int addWordset(Wordset wordset) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		int id = ((Integer) session.save(wordset)).intValue();
		
		session.getTransaction().commit();
		session.close();
		
		return id;
	}

	@Override
	public Wordset getWordset(Integer id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Wordset wordset = (Wordset) session.get(Wordset.class, id);
		
		session.getTransaction().commit();
		session.close();
		
		return wordset;
	}

	@Override
	public void updateWordset(Wordset wordset) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.update(wordset);
		
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void deleteWordset(Integer id) {
		Wordset wordset = getWordset(id);
		if(null != wordset){
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			session.delete(wordset);
			
			session.getTransaction().commit();
			session.close();
		}
	}

}
