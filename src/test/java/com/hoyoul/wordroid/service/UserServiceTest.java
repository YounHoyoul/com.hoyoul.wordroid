package com.hoyoul.wordroid.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.hoyoul.wordroid.dto.User;

public class UserServiceTest {
	private static ApplicationContext context;
	
	@Mock
    private static UserService service;
	
	private int userId;
	
    @Before
    public void setUp() {
        
		context = new ClassPathXmlApplicationContext("servlet-context.xml");
		service = context.getBean("userServiceImpl",UserService.class);
		
		userId = service.addUser(new User("First Name","First LoginID","First Password","First Email"));
		//service.addUser(new User("Second Name","Second LoginID","Second Password","Second Email"));
    }
    
    @After
    public void clean(){
    	service.deleteUser(userId);
    	if(userId != 0){
    		service.deleteUser(userId);
    	}
    }
    
    @Test
    public void listUser(){
    	List<User> list = service.listUser();
    	assertTrue(list.size() > 0);
    }
    
    @Test
    public void getUser(){
    	User user = service.getUser(userId);
    	assertThat(user.getName(),is("First Name"));
    	assertThat(user.getLoginId(),is("First LoginID"));
    	assertThat(user.getPassword(),is("First Password"));
    	assertThat(user.getEmail(),is("First Email"));
    }
    
    @Test
    public void getUserByLoginId(){
    	User user = service.getUserByLoginId("First LoginID");
    	assertThat(user.getName(),is("First Name"));
    	assertThat(user.getLoginId(),is("First LoginID"));
    	assertThat(user.getPassword(),is("First Password"));
    	assertThat(user.getEmail(),is("First Email"));
    }
    
    @Test
    public void listUserByPage(){
    	List<User> list = service.listUserByPage(1,10);
    	assertTrue(list.size() > 0);
    	assertTrue(list.size() <= 10);
    }
}
