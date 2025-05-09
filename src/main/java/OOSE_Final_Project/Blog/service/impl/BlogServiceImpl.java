package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.ResultPaginationDTO;
import OOSE_Final_Project.Blog.dto.res.blog.BlogRes;
import OOSE_Final_Project.Blog.entity.blog.Blog;
import OOSE_Final_Project.Blog.mapper.BlogMapper;
import OOSE_Final_Project.Blog.repository.BlogRepository;
import OOSE_Final_Project.Blog.service.IBlogService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class BlogServiceImpl implements IBlogService {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    BlogMapper blogMapper;

    @Override
    public ResultPaginationDTO getBlogWithKeywords(Specification<Blog> specs, Pageable pageable) {

        Page<Blog> blogPage;
        if (specs == null) {
            blogPage = blogRepository.findAll(pageable);

        } else {
            blogPage = blogRepository.findAll(specs, pageable);
        }

        var blogResponses = blogPage.getContent()
                                    .stream()
                                    .map(
                                            blog -> {
                                                BlogRes blogRes = new BlogRes();
                                                blogMapper.updateToBlogResponseFromEntity(blog, blogRes);
                                                return blogRes;

                                            }
                                    )
                                    .toList();
        var meta = ResultPaginationDTO.Meta.builder()
                                           .pages(blogPage.getTotalPages())
                                           .total(blogPage.getTotalElements())
                                           .pageSize(blogPage.getSize())
                                           .page(blogPage.getNumber() + 1)
                                           .build();
        return ResultPaginationDTO.builder()
                                  .meta(meta)
                                  .result(blogResponses)
                                  .build();
    }

    @Override
    public List<BlogRes> getAllBlogs() {
        var blogs = blogRepository.findAll();
        return blogs.stream()
                    .map(
                            blog -> {
                                BlogRes blogRes = new BlogRes();
                                blogMapper.updateToBlogResponseFromEntity(blog, blogRes);
                                return blogRes;

                            }
                    )
                    .toList();
    }
}
