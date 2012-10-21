package com.hoyoul.wordroid.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hoyoul.wordroid.dto.Folder;
import com.hoyoul.wordroid.dto.User;
import com.hoyoul.wordroid.dto.Wordset;

@Repository
public class WordsetDAOImpl implements WordsetDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Wordset> listWordset() {
		return sessionFactory.getCurrentSession().createQuery("from Wordset").list();
	}	
	
	@Override
	public int addWordset(Wordset wordset) {
		return (Integer) sessionFactory.getCurrentSession().save(wordset);
	}

	@Override
	public Wordset getWordset(Integer id) {
		return (Wordset) sessionFactory.getCurrentSession().get(Wordset.class, id);
	}

	@Override
	public void updateWordset(Wordset wordset) {
		sessionFactory.getCurrentSession().update(wordset);
	}

	@Override
	public void deleteWordset(Integer id) {
		Wordset wordset = getWordset(id);
		if(null != wordset){
			sessionFactory.getCurrentSession().delete(wordset);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Wordset> listWordsetByUserRoot(User user) {
		if(user == null) return null;
		
		String hql = "from Wordset e where e.user = :user and e.folder = null";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("user", user);
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Wordset> listWordsetByFolder(Folder folder) {
		if(folder == null) return null;
		String hql = "from Wordset e where e.parentFolder = :parentFolder";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("parentFolder", folder);
		
		return query.list();
	}

}
