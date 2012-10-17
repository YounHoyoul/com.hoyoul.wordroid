package com.hoyoul.wordroid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoyoul.wordroid.dao.WordDAO;
import com.hoyoul.wordroid.dao.UserDAO;
import com.hoyoul.wordroid.dto.Word;
import com.hoyoul.wordroid.dto.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private WordDAO wordDAO;
	
	@Override
	public List<User> listUser() {
		return userDAO.listUser();
	}
	
	@Override
	public int addUser(User user) {
		return userDAO.addUser(user);
	}

	@Override
	public User getUser(Integer id) {
		return userDAO.getUser(id);
	}

	@Override
	public void updateUser(User user) {
		userDAO.updateUser(user);
	}

	@Override
	public void deleteUser(Integer id) {
		userDAO.deleteUser(id);
	}

}
