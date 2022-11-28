package blog.example.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import blog.example.services.AccountService;
import blog.example.services.UserService;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/hello","/blog","/bloguserdetail/**","/blogcategorylist/**","/register","/css/**", "/js/**","/blog-image/**","/images/**","/img").permitAll()
		.antMatchers("/*.svg","/*.png","/*.js","/*.css").permitAll() // 
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.defaultSuccessUrl("/blogall")
		.usernameParameter("username") //リクエストパラメータのname属性を明示
		.passwordParameter("password")
		.permitAll()
		.and()
		.logout()
		.logoutSuccessUrl("/login")
		.permitAll();
	}

	public static UserDetailsManager manager = null;
	@Autowired
	private UserService userService;

	@Override
	@Bean
	public UserDetailsService userDetailsService() {
		List<UserDetails> users = userService.getAllAccounts().stream().map(
				account -> User.withDefaultPasswordEncoder()
				.username(account.getUserName())
				.password(account.getPassword())
				.roles("USER")
				.build()
				).toList();

		manager = new InMemoryUserDetailsManager(users);
		manager.createUser(User.withDefaultPasswordEncoder()
				.username("Alice")
				.password("ABC12345")
				.roles("USER")
				.build());

		return manager;
	}

	public static void addUser(String usernmail, String password) {
		manager.createUser(User.withDefaultPasswordEncoder()
				.username(usernmail)
				.password(password)
				.roles("USER")
				.build());
	}
}