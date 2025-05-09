package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.ResultPaginationDTO;
import OOSE_Final_Project.Blog.dto.req.blog.BlogComicReq;
import OOSE_Final_Project.Blog.dto.res.blog.BlogComicRes;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IBlogComicService {

    BlogComicRes save(BlogComicReq blogComicReq, MultipartFile thumbnail) throws IOException;

    BlogComicRes findById(Long id);

    List<BlogComicRes> findAll();

    void deleteById(Long id);


    BlogComicRes update(Long id, BlogComicReq updatedBlogComicReq);

    ResultPaginationDTO findAll(Pageable pageable);

}
