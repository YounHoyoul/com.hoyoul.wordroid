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

import com.hoyoul.wordroid.controller.FolderController;
import com.hoyoul.wordroid.dto.Folder;
import com.hoyoul.wordroid.service.FolderService;

public class FolderControllerTest {
	private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private AnnotationMethodHandlerAdapter adapter;

    @InjectMocks
    private static FolderController controller;
    
    @Mock
    private static FolderService service;
    
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
		controller = context.getBean("folderController",FolderController.class);
		service = context.getBean("folderServiceImpl",FolderService.class);
		

		service.addFolder(new Folder("First Name","First Description"));
		service.addFolder(new Folder("Second Name","Second Description"));
    }
    
    @Test
    public void listPage() throws Exception{
    	
    	request.setRequestURI("/folder/list");
        request.setMethod("GET");
        
        ModelAndView mav = adapter.handle(request, response, controller);
        
        List<Folder> list = service.listFolder();
        
        assertEquals(2,list.size());
        assertThat(mav.getModelMap().get("folderList"),is(List.class));
        assertThat(mav.getViewName(),is("folder/list"));
    }
    
    @Test
    public void detailPage() throws Exception{
    	
    	request.setRequestURI("/folder/detail/1");
        request.setMethod("GET");
        
        ModelAndView mav = adapter.handle(request, response, controller);
        
        List<Folder> list = service.listFolder();
        
        assertNotNull(mav.getModelMap().get("folder"));
        assertThat(mav.getModelMap().get("folder"),is(Folder.class));
        assertEquals("First Name",((Folder)mav.getModelMap().get("folder")).getName());
        assertEquals("First Description",((Folder)mav.getModelMap().get("folder")).getDescription());
        assertThat(mav.getViewName(),is("folder/detail"));    
    }    
    
    @Test
    public void add() throws Exception{
    	
    	request.setRequestURI("/folder/add");
        request.setMethod("POST");
        request.setParameter("name","Test Name");
        request.setParameter("description", "Test Description");
        
        ModelAndView mav = adapter.handle(request, response, controller);
        
        Folder actualFolder = service.getFolder(3);
        assertEquals("Test Name",actualFolder.getName());
        assertEquals("Test Description",actualFolder.getDescription());
        assertThat(mav.getViewName(),is("redirect:/folder/list"));
    }
    
    @Test
    public void updatePage() throws Exception{
    	request.setRequestURI("/folder/updatepage/1");
        request.setMethod("GET");
        
        ModelAndView mav = adapter.handle(request, response, controller);
        
        assertNotNull(mav.getModelMap().get("folder"));
        assertThat(mav.getModelMap().get("folder"),is(Folder.class));
        assertEquals("First Name",((Folder)mav.getModelMap().get("folder")).getName());
        assertEquals("First Description",((Folder)mav.getModelMap().get("folder")).getDescription());        
        assertThat(mav.getViewName(),is("folder/modify"));    
    }
    
    @Test
    public void update() throws Exception{
    	
    	request.setRequestURI("/folder/update/2");
        request.setMethod("POST");
        request.setParameter("id","2");
        request.setParameter("name","Second Folder[Modify]");
        request.setParameter("description", "Second Description[Modify].");
        
        ModelAndView mav = adapter.handle(request, response, controller);
        
        Folder actualFolder = service.getFolder(2);
        assertEquals("Second Folder[Modify]",actualFolder.getName());
        assertEquals("Second Description[Modify].",actualFolder.getDescription());
        
        assertThat(mav.getViewName(),is("redirect:/folder/updatepage/2"));
    }
    
    @Test
    public void delete() throws Exception{
    	
    	request.setRequestURI("/folder/delete/2");
        request.setMethod("GET");
        
        ModelAndView mav = adapter.handle(request, response, controller);
        
        Folder actualFolder = service.getFolder(2);
        assertNull(actualFolder);
        assertThat(mav.getViewName(),is("redirect:/folder/list"));
    }
}
