package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.req.BlogCharacterReq;
import OOSE_Final_Project.Blog.entity.blog.BlogCharacter;

import java.util.List;
import java.util.Optional;

public interface IBlogCharacterService {
     BlogCharacter save(BlogCharacterReq blogCharacterRequest);

    Optional<BlogCharacter> findById(Long id);

    List<BlogCharacter> findAll();

    void deleteById(Long id);


    BlogCharacter update(Long id, BlogCharacter updatedBlogCharacter);

}
