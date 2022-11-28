package blog.example.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import blog.example.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
	@MockBean
	private UserService accountService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void preapreData() {
		when(accountService.validateAccount(any(), any())).thenReturn(false);
		when(accountService.validateAccount("Alice", "ABC123456")).thenReturn(true);
		when(accountService.validateAccount(any(), any())).thenReturn(false);
		when(accountService.validateAccount("Alice", "ABC12345")).thenReturn(true);
	}

	@Test
	public void testGetLoginPage_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.get("/login");

		mockMvc.perform(request)
		.andExpect(view().name("login.html"));
		//.andExpect(model().attributeDoesNotExist("error"));
	}

	@Test
	public void testLogin_CorrectInfo_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/login")
				.param("username", "Alice")
				.param("password", "ABC12345")
				.with(csrf());

		mockMvc.perform(request)
		//.andExpect(view().name("blog.html"))
		.andExpect(redirectedUrl("/blogall"));
		//.andExpect(model().attribute("name", "Alice"));
	}

	@Test
	public void testLogin_IncorrectInfo_Fail() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/login")
				.param("username", "Bob")
				.param("password", "Bob54321")
				.with(csrf());

		mockMvc.perform(request)
		.andExpect(redirectedUrl("/login?error"));
		//.andExpect(model().attribute("error", true));
	}




}

