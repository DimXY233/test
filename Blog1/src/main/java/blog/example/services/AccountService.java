package blog.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.example.config.WebSecurityConfig;
import blog.example.models.Account;
import blog.example.repositories.AccountRepository;

@Service
public class AccountService {
	@Autowired
	private AccountRepository repository;
	
	public boolean validateAccount(String username, String password) { 
		Account account = repository.findByUsername(username);
		if (account == null || !account.getPassword().equals(password)) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean createAccount(String username, String password) {
		if (repository.findByUsername(username) == null) {
			repository.save(new Account(username, password));
			WebSecurityConfig.addUser(username, password);
			return true;
		} else {
			return false;
		}
	}
	//ユーザの一覧を取得する
		public List<Account> getAllAccounts() {
			return repository.findAll();
		}
}
