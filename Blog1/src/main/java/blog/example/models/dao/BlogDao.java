package blog.example.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import blog.example.models.entity.BlogEntity;

public interface BlogDao extends JpaRepository<BlogEntity, Integer> { 
	//ブログの内容を保存
	BlogEntity save(BlogEntity blogEntity);
	//ブログテーブルのuser_idとアカウントテーブルのuserIdを使ってテーブルを結合させてuserIdで検索をかけてデータを取得
	@Query(value="SELECT blog.blog_id,blog.account_id,blog.title,blog.blog_image,blog.message,blog.user_id,blog.fav From blog INNER JOIN account ON blog.user_id = account.id WHERE blog.user_id=?1",nativeQuery = true)
	List<BlogEntity>findByUserId(int userId);
	
	//blogIdを使用してDBに検索をかける
	BlogEntity findByBlogId(int blogId);
	
	//ブログテーブルのすべての情報を取得
	List<BlogEntity>findAll();
	
//	//カテゴリー名を使用して、DBに検索をかける
//	List<BlogEntity>findByCategoryName(String categoryName);
	
	//blogIdを取得して該当するブログ情報を削除する
	@Transactional
	List<BlogEntity> deleteByBlogId(int blogId);
}