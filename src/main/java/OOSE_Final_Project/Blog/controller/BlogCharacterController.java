package OOSE_Final_Project.Blog.controller;

import OOSE_Final_Project.Blog.dto.BlogCharacterReq;
import OOSE_Final_Project.Blog.entity.blog.BlogCharacter;
import OOSE_Final_Project.Blog.service.IBlogCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/blog-character")
@RestController
public class BlogCharacterController {

    @Autowired
    IBlogCharacterService blogCharacterService;
    @PostMapping("")
    public BlogCharacter save(@RequestBody BlogCharacterReq blogCharacterRequest) {
        return blogCharacterService.save(blogCharacterRequest);
    }

}
