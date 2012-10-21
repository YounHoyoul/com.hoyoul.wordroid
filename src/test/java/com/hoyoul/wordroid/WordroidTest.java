package com.hoyoul.wordroid;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hoyoul.wordroid.dto.Folder;
import com.hoyoul.wordroid.dto.User;
import com.hoyoul.wordroid.dto.Wordset;
import com.hoyoul.wordroid.service.FolderService;
import com.hoyoul.wordroid.service.UserService;
import com.hoyoul.wordroid.service.WordsetService;

public class WordroidTest {

	private static ApplicationContext context;

	@Before
	public void setUp() {
		context = new ClassPathXmlApplicationContext("servlet-context.xml");
	}

	@Test
	public void testUserNFolder() {

		UserService userService = context.getBean("userServiceImpl",
				UserService.class);
		FolderService folderService = context.getBean("folderServiceImpl",
				FolderService.class);
		
		// Add First User
		User user = new User("Hoyoul Youn","yhy0215","1234^^^^","yhy0215@gmail.com");
		// Add Folders at the first User.
		Folder folder1 = new Folder("FirstFolder","First Folder Description");
		Folder folder2 = new Folder("SecondFolder","Second Folder Description");
		Folder folder3 = new Folder("ThirdFolder","Third Folder Description");
		
		folder3.setParentFolder(folder1);
		
		// Save the first User & Folders.
		folder1.setFolderUser(user);
		folder2.setFolderUser(user);
		folder3.setFolderUser(user);
		
		userService.addUser(user);
		folderService.addFolder(folder1);
		folderService.addFolder(folder2);
		folderService.addFolder(folder3);
		
		// Get the first User.
		User queryUser = userService.getUser(1);
		
		// Check the information.
		Folder queryFolder = folderService.getFolder(1);
		assertEquals("FirstFolder",queryFolder.getName());
		assertEquals(3,queryUser.getFolders().size());
		
		List<Folder> list = folderService.listFolderByUserRoot(queryUser);
		assertEquals(2,list.size());
		assertEquals("FirstFolder",list.get(0).getName());
		assertEquals("First Folder Description",list.get(0).getDescription());
		assertEquals("SecondFolder",list.get(1).getName());
		assertEquals("Second Folder Description",list.get(1).getDescription());
		
		List<Folder> list2 = folderService.listFolderByParentFolder(folder1);
		assertEquals(1,list2.size());
		assertEquals("ThirdFolder",list2.get(0).getName());
		assertEquals("Third Folder Description",list2.get(0).getDescription());

	}
	
	@Test
	public void testFolderNWordset(){
		UserService userService = context.getBean("userServiceImpl",
				UserService.class);
		FolderService folderService = context.getBean("folderServiceImpl",
				FolderService.class);
		WordsetService wordsetService = context.getBean("wordsetServiceImpl",
				WordsetService.class);
		
		User user = new User("Hoyoul Youn","yhy0215","1234^^^^","yhy0215@gmail.com");
		userService.addUser(user);
		
		
		Folder folder1 = new Folder("FirstFolder","First Folder Description");
		Folder folder2 = new Folder("SecondFolder","Second Folder Description");
		Folder folder3 = new Folder("ThirdFolder","Third Folder Description");
		
		folder3.setParentFolder(folder1);
		
		folderService.addFolder(folder1);
		folderService.addFolder(folder2);
		folderService.addFolder(folder3);
		
		Wordset wordset1 = new Wordset("FirsetWordset","First Wordset Description");
		Wordset wordset2 = new Wordset("SecondWordset","Second Wordset Description");
		Wordset wordset3 = new Wordset("ThirdWordset","Third Wordset Description");
		Wordset wordset4 = new Wordset("FourchWordset","Fourth Wordset Description");
		
		//wordset1.setUser(user);
		//wordset2.setUser(user);
		//wordset3.setUser(user);
		//wordset4.setUser(user);
		
		//wordset3.setFolder(folder1);
		//wordset4.setFolder(folder1);
		
		wordsetService.addWordset(wordset1);
		wordsetService.addWordset(wordset2);
		wordsetService.addWordset(wordset3);
		wordsetService.addWordset(wordset4);
		
		List<Wordset> wordsetList = wordsetService.listWordsetByUserRoot(user);
		assertEquals(2,wordsetList.size());
		assertEquals("FirsetWordset",wordsetList.get(0).getName());
		assertEquals("First Wordset Description",wordsetList.get(0).getDescription());
		assertEquals("SecondWordset",wordsetList.get(1).getName());
		assertEquals("Second Wordset Description",wordsetList.get(1).getDescription());
		
		wordsetList = wordsetService.listWordsetByFolder(folder1);
		assertEquals(2,wordsetList.size());
		assertEquals("ThirdWordset",wordsetList.get(0).getName());
		assertEquals("Third Wordset Description",wordsetList.get(0).getDescription());
		assertEquals("FourchWordset",wordsetList.get(1).getName());
		assertEquals("Fourth Wordset Description",wordsetList.get(1).getDescription());
	}
	
	@Test
	public void testWordsetNWord(){
		
	}
}
