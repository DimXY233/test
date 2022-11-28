package blog.example.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.example.models.entity.UserEntity;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer> {
	List<UserEntity> findByUserNameAndPassword(String username, String password);
	UserEntity findByUserName(String username);
	UserEntity save(UserEntity userEntity);
}
