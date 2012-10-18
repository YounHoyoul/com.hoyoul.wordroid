package com.hoyoul.wordroid.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoyoul.wordroid.dto.Folder;
import com.hoyoul.wordroid.dto.User;

@Transactional(readOnly = true,rollbackFor={Exception.class,SQLException.class})
public interface FolderService {

	public List<Folder> listFolder();
	public List<Folder> listFolderByUserRoot(User user);
	public List<Folder> listFolderByParentFolder(Folder folder);
	public Folder getFolder(Integer id);
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int addFolder(Folder folder);	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateFolder(Folder folder);
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteFolder(Integer id);

}
