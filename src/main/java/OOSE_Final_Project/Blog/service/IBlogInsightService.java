package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.req.blog.BlogInsightReq;
import OOSE_Final_Project.Blog.dto.res.blog.BlogInsightRes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IBlogInsightService {

    BlogInsightRes save(BlogInsightReq BlogInsightReq, MultipartFile thumbnail) throws IOException;

    BlogInsightRes findById(Long id);

    List<BlogInsightRes> findAll();

    void deleteById(Long id);


    BlogInsightRes update(Long id, BlogInsightReq updatedBlogInsightReq,MultipartFile thumbnail) throws IOException;

    List<BlogInsightRes> findByCharacterId(Long characterId);
}
