package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.ResultPaginationDTO;
import OOSE_Final_Project.Blog.dto.res.blog.BlogCharacterRes;
import OOSE_Final_Project.Blog.dto.res.blog.BlogRes;
import OOSE_Final_Project.Blog.entity.blog.Blog;
import OOSE_Final_Project.Blog.entity.blog.BlogComic;
import OOSE_Final_Project.Blog.entity.blog.BlogInsight;
import OOSE_Final_Project.Blog.enums.EBlogStatus;
import OOSE_Final_Project.Blog.mapper.BlogMapper;
import OOSE_Final_Project.Blog.repository.BlogComicRepository;
import OOSE_Final_Project.Blog.repository.BlogInsightRepository;
import OOSE_Final_Project.Blog.repository.BlogRepository;
import OOSE_Final_Project.Blog.service.IBlogService;
import OOSE_Final_Project.Blog.specification.BlogComicSpecification;
import OOSE_Final_Project.Blog.specification.BlogInsightSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = Exception.class)
public class BlogServiceImpl implements IBlogService {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    BlogComicRepository blogComicRepository;

    @Autowired
    BlogInsightRepository blogInsightRepository;


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
        return toResultPaginationDTO(blogPage, blogResponses);


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

    @Override
    public ResultPaginationDTO getBlogsWithFilterAndPageable(
            List<Long> categoryIds, List<Long> tagIds, Pageable pageable) {
        Specification<BlogComic> comicSpec;
        Specification<BlogInsight> insightSpec;
        List<BlogComic> comics;
        List<BlogInsight> insights;

        if (tagIds.isEmpty() && categoryIds.isEmpty()) {
            comics = blogComicRepository.findAll();
            insights = blogInsightRepository.findAll();
        } else {
            if (tagIds.isEmpty()) {
                comicSpec = BlogComicSpecification.hasAllCategories(categoryIds);
                insightSpec = BlogInsightSpecification.hasAllCategories(categoryIds);
            } else if (categoryIds.isEmpty()) {
                comicSpec = BlogComicSpecification.hasAllTags(tagIds);
                insightSpec = BlogInsightSpecification.hasAllTags(tagIds);
            } else {
                comicSpec = BlogComicSpecification.hasAllCategoriesAndTags(categoryIds, tagIds);
                insightSpec = BlogInsightSpecification.hasAllCategoriesAndTags(categoryIds, tagIds);
            }

            comics = blogComicRepository.findAll(comicSpec);
            insights = blogInsightRepository.findAll(insightSpec);
        }
        List<Blog> allBlogs = new ArrayList<>();
        allBlogs.addAll(comics);
        // Hoặc findAll nếu không filter
        allBlogs.addAll(insights);

        // Map sang DTO
        List<BlogRes> blogRes = allBlogs.stream()
                                        .map(b -> {
                                            BlogRes res = new BlogRes();
                                            blogMapper.updateToBlogResponseFromEntity(b, res);
                                            return res;
                                        })
                                        .collect(Collectors.toList());

        // Shuffle và phân trang thủ công
        Collections.shuffle(blogRes);

        int pageNumber = pageable.getPageNumber();      // 0-based
        int pageSize = pageable.getPageSize();          // Số item mỗi trang

        int start = pageNumber * pageSize;
        int end = Math.min(start + pageSize, allBlogs.size());

        List<BlogRes> pagedBlogs;
        if (start >= allBlogs.size()) {
            // Trường hợp số trang yêu cầu vượt quá tổng số dữ liệu
            pagedBlogs = Collections.emptyList();
        } else {
            pagedBlogs = blogRes.subList(start, end);
        }

        ResultPaginationDTO.Meta meta = ResultPaginationDTO.Meta.builder()
                                                                .pages((int) Math.ceil(
                                                                        (double) allBlogs.size() / pageSize))
                                                                .total((long) allBlogs.size())
                                                                .pageSize(pageSize)
                                                                .page(pageNumber +
                                                                              1) // Nếu bạn muốn trả về 1-based page
                                                                .build();

        return ResultPaginationDTO.builder()
                                  .meta(meta)
                                  .result(pagedBlogs)
                                  .build();
    }

    @Override
    public BlogRes updateBlogStatus(Long id, EBlogStatus status) {
        var blog = blogRepository.findById(id)
                                 .orElseThrow(
                                         () -> new IllegalArgumentException("Blog not found with id: " + id));
        blog.setStatus(status);
        blog = blogRepository.save(blog);
        BlogCharacterRes blogCharacterRes = new BlogCharacterRes();
        blogMapper.updateToBlogResponseFromEntity(blog, blogCharacterRes);
        return blogCharacterRes;
    }

    @Override
    public BlogRes getBlogById(Long id) {
        var blog = blogRepository.findById(id)
                                 .orElseThrow(() -> new IllegalArgumentException("Blog not found with id: " + id));

        BlogRes blogRes = new BlogRes();
        blogMapper.updateToBlogResponseFromEntity(blog, blogRes);
        return blogRes;
    }

    ResultPaginationDTO toResultPaginationDTO(Page<Blog> blogPage, List<BlogRes> blogResponses) {
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
}
