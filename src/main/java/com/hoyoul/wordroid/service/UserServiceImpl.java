package com.hoyoul.wordroid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.hoyoul.wordroid.dao.WordDAO;
import com.hoyoul.wordroid.dao.UserDAO;
import com.hoyoul.wordroid.dto.Folder;
import com.hoyoul.wordroid.dto.User;
import com.hoyoul.wordroid.dto.Wordset;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private WordDAO wordDAO;
	
	@Override
	public List<User> listUser() {
		
		List<User> list = userDAO.listUser();
		
		for(User user:list){
			List<Folder> folders = user.getFolders();
			
			for(Folder folder : folders){
				folder.getId();
			}
		}
		
		return list;
	}
	
	@Override
	public List<User> listUserByPage(int page, int rows) {
		
		List<User> list = userDAO.listUserByPage(page,rows);
		
		/*
		for(User user:list){
			List<Folder> folders = user.getFolders();
			
			for(Folder folder : folders){
				folder.getId();
			}
		}
		*/
		
		return list;
	}
	
	@Override
	public Long listUserCountByName(String name) {
		return userDAO.listUserCountByName(name);
	}

	@Override
	public List<User> listUserNameByPage(String name, int page, int rows) {
		
		List<User> list = userDAO.listUserNameByPage(name,page,rows);
		
		/*
		for(User user:list){
			List<Folder> folders = user.getFolders();
			
			for(Folder folder : folders){
				folder.getId();
			}
		}
		*/
		
		return list;
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

	@Override
	public User getUserByLoginId(String loginId) {
		return userDAO.getUserByLoginId(loginId);
	}

	@Override
	public Long listUserCount() {
		return userDAO.listUserCount();
	}

}
