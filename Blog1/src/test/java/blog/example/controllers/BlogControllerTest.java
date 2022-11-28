package blog.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import blog.example.services.BlogService;
import blog.example.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class BlogControllerTest {
	
	@MockBean
	private UserService userService;
	@MockBean
	BlogService blogService;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	
}
