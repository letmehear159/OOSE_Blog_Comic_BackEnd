package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.res.blog.BlogRes;

import java.util.List;

public interface IBlogService {

    List<BlogRes> getAllBlogs();

    BlogRes getBlogById(long id);



}
