package com.hoyoul.wordroid.dao;

import java.util.List;

import com.google.gson.JsonElement;
import com.hoyoul.wordroid.dto.User;

public interface UserDAO {
	public int addUser(User user);
	public List<User> listUser();
	public User getUser(Integer id);
	public void updateUser(User user);
	public void deleteUser(Integer id);
	public User getUserByLoginId(String loginId);
	public List<User> listUserByPage(int page, int rows);
	public Long listUserCount();
	public Long listUserCountByName(String name);
	public List<User> listUserNameByPage(String name, int page, int rows);
}
