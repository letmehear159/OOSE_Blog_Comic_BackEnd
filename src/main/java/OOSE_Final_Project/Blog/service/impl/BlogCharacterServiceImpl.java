package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.req.BlogCharacterReq;
import OOSE_Final_Project.Blog.entity.blog.BlogCharacter;
import OOSE_Final_Project.Blog.mapper.BlogCharacterMapper;
import OOSE_Final_Project.Blog.repository.*;
import OOSE_Final_Project.Blog.service.IBlogCharacterService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BlogCharacterServiceImpl implements IBlogCharacterService {

    @Autowired
    private BlogCharacterRepository blogCharacterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogCharacterMapper blogCharacterMapper;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BlogComicRepository blogComicRepository;

    @Override
    public BlogCharacter save(BlogCharacterReq blogCharacterRequest) {
        BlogCharacter blogCharacter = new BlogCharacter();

        blogCharacterMapper.updateBlogCharacterFromDto(blogCharacterRequest, blogCharacter);

        return blogCharacterRepository.save(blogCharacter);
    }

    @Override
    public Optional<BlogCharacter> findById(Long id) {
        return blogCharacterRepository.findById(id);
    }

    @Override
    public List<BlogCharacter> findAll() {
        return blogCharacterRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        if (!blogCharacterRepository.existsById(id)) {
            throw new IllegalArgumentException("BlogCharacter not found with id: " + id);
        }
        blogCharacterRepository.deleteById(id);
    }

    @Override
    public BlogCharacter update(Long id, BlogCharacter updatedBlogCharacter) {
        BlogCharacter existing = blogCharacterRepository.findById(id)
                                                        .orElseThrow(() -> new IllegalArgumentException(
                                                                "BlogCharacter not found with id: " + id));

        return blogCharacterRepository.save(existing);
    }


}
