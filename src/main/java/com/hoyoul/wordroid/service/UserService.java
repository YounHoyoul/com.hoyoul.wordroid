package com.hoyoul.wordroid.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonElement;
import com.hoyoul.wordroid.dto.User;

@Transactional(readOnly = true,rollbackFor={Exception.class,SQLException.class})
public interface UserService {

	public List<User> listUser();
	public User getUser(Integer id);
	public User getUserByLoginId(String loginId);
	public List<User> listUserByPage(int page, int rows);
	public Long listUserCount();
	public Long listUserCountByName(String name);
	public List<User> listUserNameByPage(String name, int page, int rows);
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int addUser(User user);	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateUser(User user);
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteUser(Integer id);

}
