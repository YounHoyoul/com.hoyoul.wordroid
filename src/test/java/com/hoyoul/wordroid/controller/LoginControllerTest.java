package com.hoyoul.wordroid.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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

import com.hoyoul.wordroid.LoginController;
import com.hoyoul.wordroid.controller.UserController;
import com.hoyoul.wordroid.dto.User;
import com.hoyoul.wordroid.service.UserService;

public class LoginControllerTest {
	private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private AnnotationMethodHandlerAdapter adapter;

    @InjectMocks
    private static LoginController controller;
    
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
		controller = context.getBean("loginController",LoginController.class);
		service = context.getBean("userServiceImpl",UserService.class);
		
    }
    
    @Test
    public void index() throws Exception{
    	request.setRequestURI("/");
        request.setMethod("GET");
    	
    	ModelAndView mav = adapter.handle(request, response, controller);
    	
    	assertThat(mav.getViewName(),is("main/login"));
    }
    
    @Test
    public void loginSuccess() throws Exception{
    	request.setRequestURI("/main/login");
        request.setMethod("POST");
    	request.setParameter("loginId", "guest");
    	request.setParameter("password", "guest");
    	
    	ModelAndView mav = adapter.handle(request, response, controller);
    	
    	assertThat(mav.getViewName(),is("redirect:/main"));
    	
    }
    
    @Test
    public void loginFail() throws Exception{
    	request.setRequestURI("/main/login");
        request.setMethod("POST");
    	request.setParameter("loginId", "guest");
    	request.setParameter("password", "guest~");
    	
    	ModelAndView mav = adapter.handle(request, response, controller);
    	
    	assertThat(mav.getViewName(),is("redirect:/"));
    }
    
    @Test
    public void logout() throws Exception{
    	request.setRequestURI("/main/logout");
        request.setMethod("GET");
    	
    	ModelAndView mav = adapter.handle(request, response, controller);
    	
    	assertThat(mav.getViewName(),is("redirect:/"));
    }
    
    @Test
    public void checkLoginIdAlready() throws Exception{
    	request.setRequestURI("/main/check/guest");
        request.setMethod("GET");
    	
    	ModelAndView mav = adapter.handle(request, response, controller);
    	
    	assertEquals(mav.getModel().get("result"),"occupied already");
    	
    	assertThat(mav.getViewName(),is("main/check"));
    }
    
    @Test
    public void checkLoginIdNew() throws Exception{
    	request.setRequestURI("/main/check/guest000000000");
        request.setMethod("GET");
    	
    	ModelAndView mav = adapter.handle(request, response, controller);
    	
    	assertEquals(mav.getModel().get("result"),"true");
    	
    	assertThat(mav.getViewName(),is("main/check"));
    }
    
    @Test
    public void joinPage() throws Exception{
    	request.setRequestURI("/main/joinpage");
    	request.setMethod("GET");
    	
    	ModelAndView mav = adapter.handle(request, response, controller);
    	
    	assertThat(mav.getViewName(),is("main/join"));
    }
    
    @Test
    public void join() throws Exception{
    	request.setRequestURI("/main/join");
    	request.setMethod("POST");
    	request.setParameter("name", "Test Name");
    	request.setParameter("password", "Test Password");
    	request.setParameter("loginId", "Test LoginId");
    	request.setParameter("email", "test@email.com");
    	
    	ModelAndView mav = adapter.handle(request, response, controller);
    	
    	assertNotNull(mav.getModel().get("userId"));
    	
    	User actualUser = service.getUser((Integer)mav.getModel().get("userId"));
    	
    	assertEquals("Test Name",actualUser.getName());
    	assertEquals("Test Password",actualUser.getPassword());
    	assertEquals("Test LoginId",actualUser.getLoginId());
    	assertEquals("test@email.com",actualUser.getEmail());
    	
    	assertThat(mav.getViewName(),is("redirect:/"));
    }
    
}
