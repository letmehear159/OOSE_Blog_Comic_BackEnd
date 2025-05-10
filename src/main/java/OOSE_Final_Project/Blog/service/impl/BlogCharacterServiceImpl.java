package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.ResultPaginationDTO;
import OOSE_Final_Project.Blog.dto.req.blog.BlogCharacterReq;
import OOSE_Final_Project.Blog.dto.res.blog.BlogCharacterRes;
import OOSE_Final_Project.Blog.entity.Character;
import OOSE_Final_Project.Blog.entity.blog.BlogCharacter;
import OOSE_Final_Project.Blog.enums.EBlogStatus;
import OOSE_Final_Project.Blog.enums.EBlogType;
import OOSE_Final_Project.Blog.mapper.BlogCharacterMapper;
import OOSE_Final_Project.Blog.repository.BlogCharacterRepository;
import OOSE_Final_Project.Blog.repository.CharacterRepository;
import OOSE_Final_Project.Blog.service.IBlogCharacterService;
import OOSE_Final_Project.Blog.util.FileUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Transactional(rollbackOn = Exception.class)
@Service
public class BlogCharacterServiceImpl implements IBlogCharacterService {

    @Autowired
    private BlogCharacterRepository blogCharacterRepository;

    @Autowired
    private BlogCharacterMapper blogCharacterMapper;

    @Autowired
    private CharacterRepository characterRepository;


    @Override
    public BlogCharacterRes save(BlogCharacterReq blogCharacterRequest, MultipartFile thumbnail) throws IOException {
        BlogCharacter blogCharacter = new BlogCharacter();

        blogCharacterMapper.updateBlogCharacterFromDto(blogCharacterRequest, blogCharacter);

        blogCharacter.setStatus(EBlogStatus.PENDING);

        Character character = blogCharacter.getCharacter();


        character = characterRepository.save(character);

        blogCharacter.setCharacter(character);
        blogCharacter.setType(EBlogType.CHARACTER);

        String thumbnailName = FileUtil.storeFile(thumbnail);

        blogCharacter.setThumbnail(thumbnailName);

        blogCharacter = blogCharacterRepository.save(blogCharacter);

        BlogCharacterRes blogCharacterRes = new BlogCharacterRes();

        blogCharacterMapper.updateBlogCharacterResponseFromEntityWithoutDetail(blogCharacter, blogCharacterRes);

        return blogCharacterRes;
    }

    @Override
    public BlogCharacterRes findById(Long id) {
        BlogCharacter blogCharacter =
                blogCharacterRepository.findById(id)
                                       .orElseThrow(() -> new IllegalArgumentException("Blog not found"));
        BlogCharacterRes blogCharacterRes = new BlogCharacterRes();
        blogCharacterMapper.updateBlogCharacterResponseFromEntity(blogCharacter, blogCharacterRes);
        return blogCharacterRes;
    }

    @Override
    public List<BlogCharacterRes> findAll() {
        var blogs = blogCharacterRepository.findAll();
        return blogs.stream()
                    .map(blog -> {

                        BlogCharacterRes blogCharacterRes = new BlogCharacterRes();
                        blogCharacterMapper.updateBlogCharacterResponseFromEntityWithoutDetail(blog, blogCharacterRes);
                        return blogCharacterRes;
                    })
                    .toList();
    }

    @Override
    public void deleteById(Long id) {
        if (!blogCharacterRepository.existsById(id)) {
            throw new IllegalArgumentException("BlogCharacter not found with id: " + id);
        }
        blogCharacterRepository.deleteById(id);
    }

    @Override
    public BlogCharacterRes update(Long id, BlogCharacterReq updatedBlogCharacter, MultipartFile thumbnail)
            throws IOException {
        BlogCharacter existing = blogCharacterRepository.findById(id)
                                                        .orElseThrow(() -> new IllegalArgumentException(
                                                                "BlogCharacter not found with id: " + id));
        blogCharacterMapper.updateBlogCharacterFromDto(updatedBlogCharacter, existing);

        var character = characterRepository.save(existing.getCharacter());

        if (thumbnail != null) {
            FileUtil.deleteFile(existing.getThumbnail());

            var fileName = FileUtil.storeFile(thumbnail);
            existing.setThumbnail(fileName);
        }


        existing.setCharacter(character);

        existing = blogCharacterRepository.save(existing);

        BlogCharacterRes blogCharacterRes = new BlogCharacterRes();

        blogCharacterMapper.updateBlogCharacterResponseFromEntityWithoutDetail(existing, blogCharacterRes);

        return blogCharacterRes;
    }

    @Override
    public List<BlogCharacterRes> getRelatedCharacters(Long comicId) {
        var blogs = blogCharacterRepository.findByComicId(comicId);
        return blogs.stream()
                    .map(blog -> {

                        BlogCharacterRes blogCharacterRes = new BlogCharacterRes();
                        blogCharacterMapper.updateBlogCharacterResponseFromEntityWithoutDetail(blog, blogCharacterRes);
                        return blogCharacterRes;
                    })
                    .toList();
    }


    @Override
    public ResultPaginationDTO findAll(Pageable pageable) {
        Page<BlogCharacter> blogPage = blogCharacterRepository.findAll(pageable);
        var blogCharacterRes = blogPage.getContent()
                                       .stream()
                                       .map(b -> {
                                           BlogCharacterRes blogCharacterRes1 = new BlogCharacterRes();
                                           blogCharacterMapper.updateBlogCharacterResponseFromEntityWithoutDetail(
                                                   b, blogCharacterRes1);
                                           return blogCharacterRes1;
                                       })
                                       .toList();

        var meta = ResultPaginationDTO.Meta.builder()
                                           .pages(blogPage.getTotalPages())
                                           .total(blogPage.getTotalElements())
                                           .pageSize(blogPage.getSize())
                                           .page(blogPage.getNumber() + 1)
                                           .build();
        return ResultPaginationDTO.builder()
                                  .meta(meta)
                                  .result(blogCharacterRes)
                                  .build();

    }


}
