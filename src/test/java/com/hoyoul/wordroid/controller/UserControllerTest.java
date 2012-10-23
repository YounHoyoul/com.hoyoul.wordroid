package com.hoyoul.wordroid.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import com.hoyoul.wordroid.controller.UserController;
import com.hoyoul.wordroid.dto.User;
import com.hoyoul.wordroid.service.UserService;

public class UserControllerTest {
	private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private AnnotationMethodHandlerAdapter adapter;

    @InjectMocks
    private static UserController controller;
    
    @Mock
    private static UserService service;
    
    private static ApplicationContext context;
    
    private int userId=0;
    private int userId2=0;
    
	@BeforeClass
	public static void init(){

	}
	
    @Before
    public void setUp() {
        request  = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session  = (MockHttpSession) request.getSession();
        adapter  = new AnnotationMethodHandlerAdapter();
        
		context = new ClassPathXmlApplicationContext("servlet-context.xml");
		
		//SessionFactory session=context.getBean("sessionFactory",SessionFactory.class);
		controller = context.getBean("userController",UserController.class);
		service = context.getBean("userServiceImpl",UserService.class);
		
		userId = service.addUser(new User("First Name","First LoginID","First Password","First Email"));
		//service.addUser(new User("Second Name","Second LoginID","Second Password","Second Email"));
    }
    
    @After
    public void clean(){
    	service.deleteUser(userId);
    	if(userId2 != 0){
    		service.deleteUser(userId2);
    	}
    }
    
    @Test
    public void listPage() throws Exception{
    	
    	request.setRequestURI("/user/list");
        request.setMethod("GET");
        
        ModelAndView mav = adapter.handle(request, response, controller);

        assertThat(mav.getViewName(),is("user/list"));
    }
    
    @Test
    public void list() throws Exception{
    	
    	request.setRequestURI("/user/data");
        request.setMethod("GET");
        
        ModelAndView mav = adapter.handle(request, response, controller);
        
        assertNotNull(mav.getModel().get("data"));
        assertThat(mav.getViewName(),is("jsondata"));
    }    
    
    @Test
    public void listbypage() throws Exception{
    	
    	request.setRequestURI("/user/data");
        request.setMethod("GET");
        request.setParameter("rows", "10");
        request.setParameter("page", "5");
                
        ModelAndView mav = adapter.handle(request, response, controller);
        
        assertNotNull(mav.getModel().get("data"));
        assertThat(mav.getViewName(),is("jsondata"));
    }
    
    @Test
    public void listbyname() throws Exception{
    	
    	request.setRequestURI("/user/data");
        request.setMethod("GET");
        request.setParameter("name", "aa");
                
        ModelAndView mav = adapter.handle(request, response, controller);
        
        assertNotNull(mav.getModel().get("data"));
        assertThat(mav.getViewName(),is("jsondata"));
    }
    
    @Test
    public void add() throws Exception{
    	
    	request.setRequestURI("/user/add");
        request.setMethod("POST");
        request.setParameter("name","Test Name");
        request.setParameter("loginId", "Test Login ID");
        request.setParameter("password", "Test Password");
        
        ModelAndView mav = adapter.handle(request, response, controller);
        
        User actualUser = service.getUser((Integer)mav.getModel().get("userId"));
        
        userId2 = actualUser.getId();
        
        assertEquals("Test Name",actualUser.getName());
        assertEquals("Test Login ID",actualUser.getLoginId());
        assertThat(mav.getViewName(),is("jsondata"));
    }
    
    @Test
    public void update() throws Exception{
    	
    	request.setRequestURI("/user/update/"+userId);
        request.setMethod("POST");
        request.setParameter("id",""+userId);
        request.setParameter("name","Second User[Modify]");
        request.setParameter("loginId", "Second User Description[Modify].");
        request.setParameter("password", "Test Password[Modify]");
        
        ModelAndView mav = adapter.handle(request, response, controller);
        
        User actualUser = service.getUser(userId);
        assertEquals("Second User[Modify]",actualUser.getName());
        assertEquals("Second User Description[Modify].",actualUser.getLoginId());
        assertEquals("Test Password[Modify]",actualUser.getPassword());
        
        assertThat(mav.getViewName(),is("jsondata"));
    }
    
    @Test
    public void delete() throws Exception{
    	
    	request.setRequestURI("/user/delete/"+userId);
        request.setMethod("GET");
        
        ModelAndView mav = adapter.handle(request, response, controller);
        
        User actualUser = service.getUser(userId);
        assertNull(actualUser);
        assertThat(mav.getViewName(),is("jsondata"));
    }
}
