package com.hoyoul.wordroid.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hoyoul.wordroid.dto.Folder;
import com.hoyoul.wordroid.dto.User;

@Repository
public class FolderDAOImpl implements FolderDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Folder> listFolder() {
		return sessionFactory.getCurrentSession().createQuery("from Folder").list();
	}	
	
	@Override
	public int addFolder(Folder folder) {
		return (Integer) sessionFactory.getCurrentSession().save(folder);
	}

	@Override
	public Folder getFolder(Integer id) {
		return (Folder) sessionFactory.getCurrentSession().get(Folder.class, id);
	}

	@Override
	public void updateFolder(Folder folder) {
		sessionFactory.getCurrentSession().update(folder);
	}

	@Override
	public void deleteFolder(Integer id) {
		Folder folder = getFolder(id);
		if(null != folder){
			sessionFactory.getCurrentSession().delete(folder);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Folder> listFolderByUserRoot(User user) {
		if(user == null) return null;
		
		String hql="from Folder e where e.folderUser = :folderUser and e.parentFolder = null";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("folderUser", user);
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Folder> listFolderByParentFolder(Folder folder) {
		if(folder == null) return null;
		
		String hql="from Folder e where e.parentFolder = :parentFolder";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("parentFolder", folder);
		return query.list();
	}

}
