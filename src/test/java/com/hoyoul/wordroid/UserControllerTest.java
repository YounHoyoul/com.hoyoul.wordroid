package com.hoyoul.wordroid;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

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
		

		service.addUser(new User("First Name","First LoginID","First Password","First Email"));
		service.addUser(new User("Second Name","Second LoginID","Second Password","Second Email"));
    }
    
    @Test
    public void listPage() throws Exception{
    	
    	request.setRequestURI("/user/list");
        request.setMethod("GET");
        
        ModelAndView mav = adapter.handle(request, response, controller);
        
        List<User> list = service.listUser();
        
        assertEquals(2,list.size());
        assertThat(mav.getModelMap().get("userList"),is(List.class));
        assertThat(mav.getViewName(),is("user/list"));
    }
    
    @Test
    public void detailPage() throws Exception{
    	
    	request.setRequestURI("/user/detail/1");
        request.setMethod("GET");
        
        ModelAndView mav = adapter.handle(request, response, controller);
        
        List<User> list = service.listUser();
        
        assertNotNull(mav.getModelMap().get("user"));
        assertThat(mav.getModelMap().get("user"),is(User.class));
        assertThat(mav.getViewName(),is("user/detail"));    
    }    
    
    @Test
    public void add() throws Exception{
    	
    	request.setRequestURI("/user/add");
        request.setMethod("POST");
        request.setParameter("name","Test Name");
        request.setParameter("loginId", "Test Login ID");
        request.setParameter("password", "Test Password");
        
        ModelAndView mav = adapter.handle(request, response, controller);
        
        User actualUser = service.getUser(3);
        assertEquals("Test Name",actualUser.getName());
        assertEquals("Test Login ID",actualUser.getLoginId());
        assertThat(mav.getViewName(),is("redirect:/user/list"));
    }
    
    @Test
    public void updatePage() throws Exception{
    	request.setRequestURI("/user/updatepage/2");
        request.setMethod("GET");
        
        ModelAndView mav = adapter.handle(request, response, controller);
        
        assertNotNull(mav.getModelMap().get("user"));
        assertThat(mav.getModelMap().get("user"),is(User.class));
        assertThat(mav.getViewName(),is("user/modify"));    
    }
    
    @Test
    public void update() throws Exception{
    	
    	request.setRequestURI("/user/update/2");
        request.setMethod("POST");
        request.setParameter("id","2");
        request.setParameter("name","Second User[Modify]");
        request.setParameter("loginId", "Second User Description[Modify].");
        request.setParameter("password", "Test Password[Modify]");
        
        ModelAndView mav = adapter.handle(request, response, controller);
        
        User actualUser = service.getUser(2);
        assertEquals("Second User[Modify]",actualUser.getName());
        assertEquals("Second User Description[Modify].",actualUser.getLoginId());
        assertEquals("Test Password[Modify]",actualUser.getPassword());
        
        assertThat(mav.getViewName(),is("redirect:/user/updatepage/2"));
    }
    
    @Test
    public void delete() throws Exception{
    	
    	request.setRequestURI("/user/delete/2");
        request.setMethod("GET");
        
        ModelAndView mav = adapter.handle(request, response, controller);
        
        User actualUser = service.getUser(2);
        assertNull(actualUser);
        assertThat(mav.getViewName(),is("redirect:/user/list"));
    }
}
