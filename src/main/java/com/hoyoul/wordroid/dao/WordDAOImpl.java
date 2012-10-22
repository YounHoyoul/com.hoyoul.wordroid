package com.hoyoul.wordroid.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hoyoul.wordroid.dto.Word;
import com.hoyoul.wordroid.dto.Wordset;

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

	@Override
	public Long listWordCount(Wordset wordset) {
		Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from Word e where e.wordset = :wordset");
		query.setParameter("wordset", wordset);
		Long count = (Long) query.uniqueResult();
		return count;
	}

	@Override
	public Long listWordCount(Wordset wordset,String word) {
		Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from Word e where e.wordset = :wordset and e.word like :word");
		query.setParameter("wordset", wordset);
		query.setParameter("word", "%"+word+"%");
		Long count = (Long) query.uniqueResult();
		return count;
	}

	@Override
	public List<Word> listWordByPage(Wordset wordset,int page, int rows) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Word e where e.wordset = :wordset");
		query.setParameter("wordset", wordset);
		query.setFirstResult((page-1)*rows);
		query.setMaxResults(rows);
		return query.list();
	}

	@Override
	public List<Word> listWordByPage(Wordset wordset,String word, int page, int rows) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Word e where e.wordset = :wordset and e.word like :word");
		query.setParameter("wordset", wordset);
		query.setParameter("word", "%"+word+"%");
		query.setFirstResult((page-1)*rows);
		query.setMaxResults(rows);
		return query.list();
	}

}
