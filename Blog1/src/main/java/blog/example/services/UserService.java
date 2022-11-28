package blog.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.example.config.WebSecurityConfig;
import blog.example.models.Account;
import blog.example.models.dao.UserDao;
import blog.example.models.entity.UserEntity;
import blog.example.repositories.AccountRepository;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	  //ユーザの情報を保存する
	public boolean createAccount(String userName,String password) {
		//UserServiceから渡されるユーザ情報（ユーザ名、パスワード）を条件にDB検索で検索する
		List<UserEntity> userList = userDao.findByUserNameAndPassword(userName, password);

		if (userList.isEmpty()) {
			userDao.save(new UserEntity(userName, password));
			return true;
		} else {
			return false;
		}
	}
	
	public boolean validateAccount(String username, String password) { 
		UserEntity account = userDao.findByUserName(username);
		if (account == null || !account.getPassword().equals(password)) {
			return false;
		} else {
			return true;
		}
	}
	

    //ユーザの一覧を取得する
	public List<UserEntity> getAllAccounts() {
		return userDao.findAll();
	}

	public UserEntity selectById(String userName) {
		return userDao.findByUserName(userName);
	}

}