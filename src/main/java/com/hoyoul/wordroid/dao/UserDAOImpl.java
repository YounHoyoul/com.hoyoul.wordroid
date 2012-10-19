package com.hoyoul.wordroid.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hoyoul.wordroid.dto.User;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listUser() {
		return sessionFactory.getCurrentSession().createQuery("from User").list();
	}	
	
	@Override
	public int addUser(User user) {
		return (Integer) sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public User getUser(Integer id) {
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}

	@Override
	public void updateUser(User user) {
		sessionFactory.getCurrentSession().update(user);
	}

	@Override
	public void deleteUser(Integer id) {
		User user = getUser(id);
		if(null != user){
			sessionFactory.getCurrentSession().delete(user);
		}
	}

	@Override
	public User getUserByLoginId(String loginId) {
		
		if(loginId == null) return null;
		
		String hql = "from User e where e.loginId = :loginId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("loginId", loginId);
		
		@SuppressWarnings("unchecked")
		List<User> list = query.list();
		
		if(list.size() == 0){
			return null;
		}
	
		return list.get(0);
	}

}