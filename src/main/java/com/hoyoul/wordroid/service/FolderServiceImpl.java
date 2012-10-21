package com.hoyoul.wordroid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoyoul.wordroid.dao.WordDAO;
import com.hoyoul.wordroid.dao.FolderDAO;
import com.hoyoul.wordroid.dto.Folder;
import com.hoyoul.wordroid.dto.User;
import com.hoyoul.wordroid.dto.Wordset;

@Service
public class FolderServiceImpl implements FolderService {

	@Autowired
	private FolderDAO folderDAO;

	@Autowired
	private WordDAO wordDAO;
	
	@Override
	public List<Folder> listFolder() {
		return folderDAO.listFolder();
	}
	
	@Override
	public int addFolder(Folder folder) {
		return folderDAO.addFolder(folder);
	}

	@Override
	public Folder getFolder(Integer id) {
		return folderDAO.getFolder(id);
	}

	@Override
	public void updateFolder(Folder folder) {
		folderDAO.updateFolder(folder);
	}

	@Override
	public void deleteFolder(Integer id) {
		folderDAO.deleteFolder(id);
	}

	@Override
	public List<Folder> listFolderByUserRoot(User user) {
		//return folderDAO.listFolderByUserRoot(user);
		List<Folder> folderList = folderDAO.listFolderByUserRoot(user);
		
		for(Folder tmp:folderList){
			List<Folder> sublist = tmp.getChildrenFolder();
			for(Folder subtmp:sublist){
				subtmp.getId();
			}
			
			/*
			List<Wordset> wordsets = tmp.getWordsets();
			for(Wordset set:wordsets){
				set.getId();
			}
			*/
		}
		
		return folderList;
	}

	@Override
	public List<Folder> listFolderByParentFolder(Folder folder) {
		
		List<Folder> folderList = folderDAO.listFolderByParentFolder(folder);
		
		for(Folder tmp:folderList){
			List<Folder> sublist = tmp.getChildrenFolder();
			for(Folder subtmp:sublist){
				subtmp.getId();
			}
			/*
			List<Wordset> wordsets = tmp.getWordsets();
			for(Wordset set:wordsets){
				set.getId();
			}
			*/
		}
		
		return folderList;
	}

}
