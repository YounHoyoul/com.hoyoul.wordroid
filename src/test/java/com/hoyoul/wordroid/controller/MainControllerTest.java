package com.hoyoul.wordroid.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.servlet.http.HttpSession;

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

import com.hoyoul.wordroid.controller.MainController;
import com.hoyoul.wordroid.dto.User;
import com.hoyoul.wordroid.service.UserService;

public class MainControllerTest {
	private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private AnnotationMethodHandlerAdapter adapter;

    @InjectMocks
    private static MainController controller;
    
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
		controller = context.getBean("mainController",MainController.class);
		service = context.getBean("userServiceImpl",UserService.class);
		
    }
    
    @Test
    public void indexWithoutSession() throws Exception{
    	request.setRequestURI("/main");
    	request.setMethod("GET");
    	
    	ModelAndView mav = adapter.handle(request, response, controller);
    	
    	assertThat(mav.getViewName(),is("redirect:/"));
    }
    
    @Test
    public void indexWithSession() throws Exception{
    	request.setRequestURI("/main");
    	request.setMethod("GET");
    	
    	HttpSession session = request.getSession();
    	User user = new User("Name","LoginID","Password","Email");
    	user.setId(1004);
    	session.setAttribute("loginUser", user);
    	
    	ModelAndView mav = adapter.handle(request, response, controller);
    	
    	assertThat(mav.getViewName(),is("index"));
    }
    
    @Test
    public void mainDataWithoutSession() throws Exception{
    	request.setRequestURI("/main/data");
    	request.setMethod("GET");
   	
    	ModelAndView mav = adapter.handle(request, response, controller);
    	
    	assertThat(mav.getViewName(),is("main/data"));
    }
    
    @Test
    public void mainDataWithSession() throws Exception{
    	request.setRequestURI("/main/data");
    	request.setMethod("GET");
    	
    	HttpSession session = request.getSession();
    	User user = new User("Name","LoginID","Password","Email");
    	user.setId(1);
    	
    	ModelAndView mav = adapter.handle(request, response, controller);
    	
    	assertThat(mav.getViewName(),is("main/data"));
    }
}
