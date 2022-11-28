package blog.example.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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

import blog.example.services.AccountService;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTest {
	@MockBean
	private AccountService accountService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	public void prepareData() {
		when(accountService.createAccount(any(), any())).thenReturn(true);
		when(accountService.createAccount(eq("Alice"), any())).thenReturn(false);
		when(accountService.validateAccount(any(), any())).thenReturn(false);
		when(accountService.validateAccount("Alice", "ABC12345")).thenReturn(true);
	}
	
	@Test
	public void testGetRegisterPage_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.get("/register");
		
		mockMvc.perform(request)
				.andExpect(view().name("register.html"))
				.andExpect(model().attribute("error", false));
				//.andExpect(model().attributeDoesNotExist("error"));
	}
	
	@Test
	public void testRegister_NewUsername_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/register")
				.param("username", "Bob")
				.param("password", "Bob54321")
				.with(csrf());
		
		
		mockMvc.perform(request)
				.andExpect(view().name("login.html"))
				.andExpect(model().attribute("error", false));
				//.andExpect(model().attributeDoesNotExist("error"));
	}
	

	@Test
	public void testLogin_ExistingUsername_Fail() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/register")
				.param("username", "Alice")
				.param("password", "ABC12345")
				.with(csrf());
		
		mockMvc.perform(request)
				.andExpect(view().name("register.html"))
				.andExpect(model().attribute("error", true));
	}
	
	
}
