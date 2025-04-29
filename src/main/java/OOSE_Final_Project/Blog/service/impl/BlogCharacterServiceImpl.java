package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.req.blog.BlogCharacterReq;
import OOSE_Final_Project.Blog.dto.res.blog.BlogCharacterRes;
import OOSE_Final_Project.Blog.entity.blog.BlogCharacter;
import OOSE_Final_Project.Blog.enums.EBlogStatus;
import OOSE_Final_Project.Blog.mapper.BlogCharacterMapper;
import OOSE_Final_Project.Blog.repository.BlogCharacterRepository;
import OOSE_Final_Project.Blog.service.IBlogCharacterService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BlogCharacterServiceImpl implements IBlogCharacterService {

    @Autowired
    private BlogCharacterRepository blogCharacterRepository;

    @Autowired
    private BlogCharacterMapper blogCharacterMapper;

    @Override
    public BlogCharacterRes save(BlogCharacterReq blogCharacterRequest) {
        BlogCharacter blogCharacter = new BlogCharacter();

        blogCharacterMapper.updateBlogCharacterFromDto(blogCharacterRequest, blogCharacter);

        blogCharacter.setStatus(EBlogStatus.PENDING);

        blogCharacter = blogCharacterRepository.save(blogCharacter);

        BlogCharacterRes blogCharacterRes = new BlogCharacterRes();

        blogCharacterMapper.updateBlogCharacterResponseFromEntity(blogCharacter, blogCharacterRes);

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
                        blogCharacterMapper.updateBlogCharacterResponseFromEntity(blog, blogCharacterRes);
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
    public BlogCharacterRes update(Long id, BlogCharacterReq updatedBlogCharacter) {
        BlogCharacter existing = blogCharacterRepository.findById(id)
                                                        .orElseThrow(() -> new IllegalArgumentException(
                                                                "BlogCharacter not found with id: " + id));
        blogCharacterMapper.updateBlogCharacterFromDto(updatedBlogCharacter, existing);

        existing = blogCharacterRepository.save(existing);

        BlogCharacterRes blogCharacterRes = new BlogCharacterRes();

        blogCharacterMapper.updateBlogCharacterResponseFromEntity(existing, blogCharacterRes);

        return blogCharacterRes;
    }


}
