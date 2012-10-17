package com.hoyoul.wordroid.dao;

import java.util.List;

import com.hoyoul.wordroid.dto.User;

public interface UserDAO {
	public int addUser(User user);
	public List<User> listUser();
	public User getUser(Integer id);
	public void updateUser(User user);
	public void deleteUser(Integer id);
}
