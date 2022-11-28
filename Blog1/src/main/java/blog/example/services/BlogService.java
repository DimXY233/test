package blog.example.services;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import blog.example.models.dao.BlogDao;
import blog.example.models.entity.BlogEntity;

@Service
public class BlogService {
	@Autowired
	BlogDao blogDao;

	//内容を保存
	public void insert(String blogTitle,String fileName,int account_id,int userId,int fav) {
		blogDao.save(new BlogEntity(blogTitle,fileName,account_id,userId,fav));
	}
	//ブログ一覧
	public List<BlogEntity> selectByUserId(int userId){
		return blogDao.findByUserId(userId);
	}

	//ブログ詳細
	public BlogEntity selectByBlogId(int blogId){
		return blogDao.findByBlogId(blogId);
	}
	//内容を更新
	public void update(String blogTitle,String fileName,int account_id,int userId,int fav) {
		blogDao.save(new BlogEntity(blogTitle,fileName,account_id,userId,fav));
		 
		
	}

	//ユーザーブログ一覧
	public List<BlogEntity> selectByAll(){
		return blogDao.findAll();
	}

	//削除
	public List<BlogEntity>deleteBlog(int blogId){
		return blogDao.deleteByBlogId(blogId);
	}
}
