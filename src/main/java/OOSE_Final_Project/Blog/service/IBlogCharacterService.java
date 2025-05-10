package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.ResultPaginationDTO;
import OOSE_Final_Project.Blog.dto.req.blog.BlogCharacterReq;
import OOSE_Final_Project.Blog.dto.res.blog.BlogCharacterRes;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IBlogCharacterService {

    BlogCharacterRes save(BlogCharacterReq blogCharacterRequest, MultipartFile thumbnail) throws IOException;

    BlogCharacterRes findById(Long id);

    List<BlogCharacterRes> findAll();

    void deleteById(Long id);

    BlogCharacterRes update(Long id, BlogCharacterReq updatedBlogCharacter, MultipartFile thumbnail) throws IOException;

    List<BlogCharacterRes> getRelatedCharacters(Long comicId);


    ResultPaginationDTO findAll(Pageable pageable);

}
