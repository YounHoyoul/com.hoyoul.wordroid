package com.hoyoul.wordroid.dao;

import java.util.List;

import com.hoyoul.wordroid.dto.Folder;
import com.hoyoul.wordroid.dto.User;

public interface FolderDAO {
	public int addFolder(Folder folder);
	public List<Folder> listFolder();
	public Folder getFolder(Integer id);
	public void updateFolder(Folder folder);
	public void deleteFolder(Integer id);
	public List<Folder> listFolderByUserRoot(User user);
	public List<Folder> listFolderByParentFolder(Folder folder);
	
}
