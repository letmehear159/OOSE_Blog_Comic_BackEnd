package OOSE_Final_Project.Blog.facade;

import OOSE_Final_Project.Blog.dto.req.blog.BlogCharacterReq;
import OOSE_Final_Project.Blog.dto.res.blog.BlogCharacterRes;
import OOSE_Final_Project.Blog.observer.BlogPublisher;
import OOSE_Final_Project.Blog.service.IBlogCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class BlogFacade {

    @Autowired
    BlogPublisher blogPublisher;

    @Autowired
    IBlogCharacterService blogCharacterService;

    public BlogCharacterRes saveBlogCharacter(BlogCharacterReq blogCharacterReq, MultipartFile thumbnail)
            throws IOException {

        var result = blogCharacterService.save(blogCharacterReq, thumbnail);

        blogPublisher.notifyObservers(result);
        return result;
    }
    
}
