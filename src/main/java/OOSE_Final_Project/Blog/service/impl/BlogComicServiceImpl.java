package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.req.blog.BlogComicReq;
import OOSE_Final_Project.Blog.dto.res.blog.BlogComicRes;
import OOSE_Final_Project.Blog.entity.blog.BlogComic;
import OOSE_Final_Project.Blog.enums.EBlogStatus;
import OOSE_Final_Project.Blog.mapper.BlogComicMapper;
import OOSE_Final_Project.Blog.repository.BlogComicRepository;
import OOSE_Final_Project.Blog.service.IBlogComicService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BlogComicServiceImpl implements IBlogComicService {

    @Autowired
    private BlogComicRepository blogComicRepository;

    @Autowired
    private BlogComicMapper blogComicMapper;

    @Override
    public BlogComicRes save(BlogComicReq blogComicReq) {
        BlogComic blogComic = new BlogComic();

        blogComicMapper.updateBlogComicFromDto(blogComicReq, blogComic);
        blogComic.setStatus(EBlogStatus.PENDING);

        blogComic = blogComicRepository.save(blogComic);

        BlogComicRes blogComicRes = new BlogComicRes();

        blogComicMapper.updateBlogComicResponseFromEntity(blogComic, blogComicRes);

        return blogComicRes;
    }

    @Override
    public BlogComicRes findById(Long id) {
        BlogComic blogComic =
                blogComicRepository.findById(id)
                                   .orElseThrow(() -> new IllegalArgumentException("Blog not found"));
        BlogComicRes blogComicRes = new BlogComicRes();
        blogComicMapper.updateBlogComicResponseFromEntity(blogComic, blogComicRes);
        return blogComicRes;
    }

    @Override
    public List<BlogComicRes> findAll() {
        var blogs = blogComicRepository.findAll();
        return blogs.stream()
                    .map(blog -> {

                        BlogComicRes blogComicRes = new BlogComicRes();
                        blogComicMapper.updateBlogComicResponseFromEntity(blog, blogComicRes);
                        return blogComicRes;
                    })
                    .toList();
    }

    @Override
    public void deleteById(Long id) {
        if (!blogComicRepository.existsById(id)) {
            throw new IllegalArgumentException("BlogComic not found with id: " + id);
        }
        blogComicRepository.deleteById(id);
    }

    @Override
    public BlogComicRes update(Long id, BlogComicReq blogComicReq) {
        BlogComic existing = blogComicRepository.findById(id)
                                                .orElseThrow(() -> new IllegalArgumentException(
                                                        "BlogComic not found with id: " + id));
        blogComicMapper.updateBlogComicFromDto(blogComicReq, existing);

        existing = blogComicRepository.save(existing);

        BlogComicRes BlogComicRes = new BlogComicRes();

        blogComicMapper.updateBlogComicResponseFromEntity(existing, BlogComicRes);

        return BlogComicRes;
    }


}
