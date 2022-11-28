package blog.example.controllers;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;

import blog.example.config.WebSecurityConfig;

import blog.example.models.entity.BlogEntity;
import blog.example.services.BlogService;
import blog.example.services.UserService;
import blog.example.models.entity.UserEntity;
import lombok.NonNull;

@Controller
public class BlogController {
//
	@Autowired
	private UserService userService;
	
	@Autowired
	BlogService blogService;

	
	@GetMapping("/newblog")
	public String newblog(){
		return "newblog.html";
	}
	
	@PostMapping("/newblogs")
	public String newblog(@RequestParam String blogTitle,@RequestParam("blogImage") MultipartFile blogImage,@RequestParam String newblog,Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userEmail = auth.getName();
		UserEntity user = userService.selectById(userEmail);
		int userId = user.getUserId();
		
		String fileName = blogImage.getOriginalFilename();
		String blogfileName= blogTitle;
		
		try {
			File blogFile = new File("./src/main/resources/static/blog-image/"+fileName);
			byte[] bytes = blogImage.getBytes();
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(blogFile));
			out.write(bytes);
			out.close();
			
			File blogtext = new File("./src/main/resources/static/blog-text/"+blogfileName+"-BY-"+userId+".txt");
			FileWriter fw = new FileWriter(blogtext);
			fw.write(newblog);
			fw.close();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		blogService.insert(blogTitle, fileName, userId, userId,0);
		return "redirect:/blogall";
		}

	
	@GetMapping("/blogdetail/{blogId}")
	public String blogdetail(@PathVariable int blogId,Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userEmail = auth.getName();
		UserEntity user = userService.selectById(userEmail);
		//ユーザーのテーブルの中から、ユーザー名を取得
		String userName = user.getUserName();
		//ユーザーのテーブルの中から、ユーザーIDを取得
		int userId = user.getUserId();
		
		BlogEntity blogs = blogService.selectByBlogId(blogId);
		String title =blogs.getBlogTitle();
		
		File blogtext = new File("./src/main/resources/static/blog-text/"+title+"-BY-"+userId+".txt");
		List<String> strList = new ArrayList<>();
		try (FileReader fileReader = new FileReader(blogtext);
	            BufferedReader reader = new BufferedReader(fileReader); 
	         ){
			String str;
			while ((str = reader.readLine()) != null) {
				 strList.add(str);
	         }
			fileReader.close();
			reader.close();
		}catch (IOException e) {
			System.out.println(e);
		}
		model.addAttribute("userId",userId);
		model.addAttribute("blogs",blogs);	
		model.addAttribute("blogList",strList);
		 
		return "blog_view.html";
	}


	@GetMapping("/updateblog/{blogId}")
	public String updateblog(@PathVariable int blogId,Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userEmail = auth.getName();
		UserEntity user = userService.selectById(userEmail);
		//ユーザーのテーブルの中から、ユーザー名を取得
		String userName = user.getUserName();
		//ユーザーのテーブルの中から、ユーザーIDを取得
		int userId = user.getUserId();
		
		BlogEntity blogs = blogService.selectByBlogId(blogId);
		String title =blogs.getBlogTitle();
		
		File blogtext = new File("./src/main/resources/static/blog-text/"+title+"-BY-"+userId+".txt");
		List<String> strList = new ArrayList<>();
		try (FileReader fileReader = new FileReader(blogtext);
	            BufferedReader reader = new BufferedReader(fileReader); 
	         ){
			String str;
			while ((str = reader.readLine()) != null) {
				 strList.add(str);
	         }
			fileReader.close();
			reader.close();
		}catch (IOException e) {
			System.out.println(e);
		}
		String result = String.join("\n", strList);
		
		model.addAttribute("userId",userId);
		model.addAttribute("blogs",blogs);	
		model.addAttribute("blogList",result);
		 
		return "updateblog.html";
	}

	@PostMapping("/blogupdate")
	public String updateData(@RequestParam int blogId,@RequestParam String blogTitle,@RequestParam("blogImage") MultipartFile blogImage,@RequestParam String newblog,Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userEmail = auth.getName();
		UserEntity user = userService.selectById(userEmail);
		int userId = user.getUserId();
		
		String fileName = blogImage.getOriginalFilename();
		String blogfileName= blogTitle;
		
		try {
			File blogFile = new File("./src/main/resources/static/blog-image/"+fileName);
			byte[] bytes = blogImage.getBytes();
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(blogFile));
			out.write(bytes);
			out.close();
			
			File blogtext = new File("./src/main/resources/static/blog-text/"+blogfileName+"-BY-"+userId+".txt");
			FileWriter fw = new FileWriter(blogtext);
			fw.write(newblog);
			fw.close();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		blogService.update(blogTitle, fileName, userId, userId,0);
		blogService.deleteBlog(blogId);
		return "redirect:/blogall";
	}
	
	
	//ブログの内容を削除
	@PostMapping("/delete")
	public String blogDelete(@RequestParam int blogId,Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userEmail = auth.getName();
		UserEntity user = userService.selectById(userEmail);
		int userId = user.getUserId();
		BlogEntity blogs = blogService.selectByBlogId(blogId);
		String title =blogs.getBlogTitle();
		
		File blogtext = new File("./src/main/resources/static/blog-text/"+title+"-BY-"+userId+".txt");
		
		if (blogtext.exists()) {
			blogtext.delete();
		}else{
			System.out.println("file error");
			}
		
		blogService.deleteBlog(blogId);
		return "redirect:/blogall";
	}

	
	//ブログ画面の表示（ログイン操作無し）
		@GetMapping("/blog")
		public String getBlogUserPage(Model model) {
			return "blog.html";
		}
		
		
		@GetMapping("/blogall")
		public String getLoginPage(Model model) {
	
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			//ログインした人のメールアドレスを取得
			String userEmail = auth.getName();
			//ユーザーのテーブルの中から、ユーザーのEmailで検索をかけて該当するユーザーのID情報を引っ張り出す。
			UserEntity user = userService.selectById(userEmail);
			//ユーザーのテーブルの中からログインしているユーザーの名前の取得
			String userName = user.getUserName();

			//ユーザーのテーブルの中からログインしているユーザーのIDを取得
			int userId = user.getUserId();

			//ブログテーブルの中からユーザーIDを使って、そのユーザーが書いたブログ記事のみを取得する
			List<BlogEntity>blogList = blogService.selectByUserId(userId);

			//html側にListに格納した情報を渡す
			model.addAttribute("username",userName);
			model.addAttribute("blogList",blogList);
			
			return "blog.html";
		}

		

	
}
