package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.res.blog.BlogRes;
import OOSE_Final_Project.Blog.repository.BlogRepository;
import OOSE_Final_Project.Blog.service.IBlogService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class BlogServiceImpl implements IBlogService {
    @Autowired
    BlogRepository blogRepository;

    @Override
    public List<BlogRes> getAllBlogs() {
        var blogs = blogRepository.findAll();
//        return blogs.stream().map(blog->{
//
//        })
        return null;
    }

    @Override
    public BlogRes getBlogById(long id) {
        return null;
    }
}
