package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.DailyBlogStatsDTO;
import OOSE_Final_Project.Blog.dto.ResultPaginationDTO;
import OOSE_Final_Project.Blog.dto.res.blog.BlogCharacterRes;
import OOSE_Final_Project.Blog.dto.res.blog.BlogRes;
import OOSE_Final_Project.Blog.entity.blog.Blog;
import OOSE_Final_Project.Blog.entity.blog.BlogComic;
import OOSE_Final_Project.Blog.entity.blog.BlogInsight;
import OOSE_Final_Project.Blog.enums.EBlogStatus;
import OOSE_Final_Project.Blog.mapper.BlogMapper;
import OOSE_Final_Project.Blog.repository.*;
import OOSE_Final_Project.Blog.service.IBlogService;
import OOSE_Final_Project.Blog.specification.BlogComicSpecification;
import OOSE_Final_Project.Blog.specification.BlogInsightSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReactionCommentRepository reactionCommentRepository;

    @Autowired
    private ReactionBlogRepository reactionBlogRepository;


    @Autowired
    private ViewLogRepository viewLogRepository;


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

    public long getTodayBlogCount() {
        // Lấy thời gian hiện tại
        LocalDateTime startOfDay = LocalDate.now()
                                            .atStartOfDay(); // 00:00 hôm nay
        LocalDateTime endOfDay = LocalDateTime.now();              // thời điểm hiện tại

        // Lọc các blog có createdAt nằm trong hôm nay
        return blogRepository.findAll()
                             .stream()
                             .filter(blog -> blog.getCreatedAt() != null)
                             .filter(blog -> !blog.getCreatedAt()
                                                  .isBefore(startOfDay)
                                     && !blog.getCreatedAt()
                                             .isAfter(endOfDay))
                             .count();
    }

    @Override
    public long getAllBlogsCount() {
        return blogRepository.findAll()
                             .size();
    }

    @Override
    public ResultPaginationDTO getBlogsWithPageable(Pageable pageable) {
        var pageBlog = blogRepository.findAll(pageable);
        List<BlogRes> blogResponses = pageBlog.getContent()
                                              .stream()
                                              .map(
                                                      blog -> {
                                                          BlogRes blogRes = new BlogRes();
                                                          blogMapper.updateToBlogResponseFromEntity(blog, blogRes);
                                                          return blogRes;
                                                      }
                                              )
                                              .toList();
        return toResultPaginationDTO(pageBlog, blogResponses);
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

    public List<DailyBlogStatsDTO> getLast5DaysStats() {
        LocalDate today = LocalDate.now();

        // Tạo danh sách 5 ngày gần nhất
        List<LocalDate> last5Days = IntStream.rangeClosed(0, 4)
                                             .mapToObj(today::minusDays)
                                             .sorted() // đảm bảo theo thứ tự tăng dần
                                             .collect(Collectors.toList());

        List<DailyBlogStatsDTO> result = new ArrayList<>();

        for (LocalDate date : last5Days) {
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.atTime(LocalTime.MAX);

            long blogCount = blogRepository.countByCreatedAtBetween(start, end);
            long rateCount = rateRepository.countByCreatedAtBetween(start, end);
            long commentCount = commentRepository.countByCreatedAtBetween(start, end);
            long reactionCommentCount = reactionCommentRepository.countByCreatedAtBetween(start, end); // nếu có
            long reactionBlogCount = reactionBlogRepository.countByCreatedAtBetween(start, end); // nếu có
            var reactionCount = reactionBlogCount + reactionCommentCount;
            long views = viewLogRepository.countByCreatedAtBetween(start, end); // nếu có
            // Reaction entity

            result.add(new DailyBlogStatsDTO(date, blogCount, rateCount, commentCount, reactionCount, views));
        }

        return result;
    }

    @Override
    public void deleteBlogById(Long id) {
        blogRepository.deleteById(id);
        return;
    }
}
