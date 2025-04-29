package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.req.blog.BlogComicReq;
import OOSE_Final_Project.Blog.dto.res.blog.BlogComicRes;

import java.util.List;

public interface IBlogComicService {

    BlogComicRes save(BlogComicReq blogComicReq);

    BlogComicRes findById(Long id);

    List<BlogComicRes> findAll();

    void deleteById(Long id);


    BlogComicRes update(Long id, BlogComicReq updatedBlogComicReq);

}
