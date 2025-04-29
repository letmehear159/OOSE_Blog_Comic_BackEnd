package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.req.blog.BlogCharacterReq;
import OOSE_Final_Project.Blog.dto.res.blog.BlogCharacterRes;

import java.util.List;

public interface IBlogCharacterService {

    BlogCharacterRes save(BlogCharacterReq blogCharacterRequest);

    BlogCharacterRes findById(Long id);

    List<BlogCharacterRes> findAll();

    void deleteById(Long id);


    BlogCharacterRes update(Long id, BlogCharacterReq updatedBlogCharacter);

}
