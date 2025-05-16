package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.ResultPaginationDTO;
import OOSE_Final_Project.Blog.dto.req.blog.BlogInsightReq;
import OOSE_Final_Project.Blog.dto.res.blog.BlogInsightRes;
import OOSE_Final_Project.Blog.entity.blog.BlogInsight;
import OOSE_Final_Project.Blog.enums.EBlogStatus;
import OOSE_Final_Project.Blog.enums.EBlogType;
import OOSE_Final_Project.Blog.mapper.BlogInsightMapper;
import OOSE_Final_Project.Blog.repository.BlogInsightRepository;
import OOSE_Final_Project.Blog.service.IBlogInsightService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Transactional(rollbackOn = Exception.class)
@Service
public class BlogInsightServiceImpl implements IBlogInsightService {

    @Autowired
    BlogInsightRepository blogInsightRepository;

    @Autowired
    BlogInsightMapper blogInsightMapper;

    @Autowired
    ImageUploadService imageUploadService;


    @Override
    public BlogInsightRes save(BlogInsightReq BlogInsightReq, MultipartFile thumbnail) throws IOException {
        BlogInsight blogInsight = new BlogInsight();

        blogInsightMapper.updateBlogInsightFromDto(BlogInsightReq, blogInsight);
        blogInsight.setStatus(EBlogStatus.PENDING);
        //        FileUtil.storeFile(thumbnail);
        String thumbnailName = imageUploadService.uploadImage(thumbnail);

        blogInsight.setThumbnail(thumbnailName);

        blogInsight.setType(EBlogType.INSIGHT);

        blogInsight = blogInsightRepository.save(blogInsight);

        BlogInsightRes blogInsightRes = new BlogInsightRes();

        blogInsightMapper.updateBlogInsightResponseFromEntity(blogInsight, blogInsightRes);

        return blogInsightRes;
    }

    @Override
    public BlogInsightRes findById(Long id) {
        BlogInsight blogInsight =
                blogInsightRepository.findById(id)
                                     .orElseThrow(() -> new IllegalArgumentException("Blog not found"));
        BlogInsightRes blogInsightRes = new BlogInsightRes();
        blogInsightMapper.updateBlogInsightResponseFromEntity(blogInsight, blogInsightRes);
        return blogInsightRes;
    }

    @Override
    public List<BlogInsightRes> findAll() {
        var blogs = blogInsightRepository.findAll();
        return blogs.stream()
                    .map(blog -> {

                        BlogInsightRes blogInsightRes = new BlogInsightRes();
                        blogInsightMapper.updateBlogInsightResponseFromEntity(blog, blogInsightRes);
                        return blogInsightRes;
                    })
                    .toList();
    }

    @Override
    public void deleteById(Long id) {
        if (!blogInsightRepository.existsById(id)) {
            throw new IllegalArgumentException("BlogInsight not found with id: " + id);
        }
        blogInsightRepository.deleteById(id);
    }

    @Override
    public BlogInsightRes update(Long id, BlogInsightReq updatedBlogInsightReq, MultipartFile thumbnail)
            throws IOException {
        BlogInsight existing = blogInsightRepository.findById(id)
                                                    .orElseThrow(() -> new IllegalArgumentException(
                                                            "Blog insight not found with id: " + id));
        blogInsightMapper.updateBlogInsightFromDto(updatedBlogInsightReq, existing);

        if (thumbnail != null) {
//            FileUtil.storeFile(thumbnail);
            var fileName = imageUploadService.uploadImage(thumbnail);
            existing.setThumbnail(fileName);
        }

        existing = blogInsightRepository.save(existing);


        BlogInsightRes blogInsightRes = new BlogInsightRes();

        blogInsightMapper.updateBlogInsightResponseFromEntity(existing, blogInsightRes);

        return blogInsightRes;
    }

    @Override
    public List<BlogInsightRes> findByCharacterId(Long characterId) {
        var blogs = blogInsightRepository.findByBlogCharacterId(characterId);
        return blogs.stream()
                    .map(blog -> {

                        BlogInsightRes blogInsightRes = new BlogInsightRes();
                        blogInsightMapper.updateBlogInsightResponseFromEntity(blog, blogInsightRes);
                        return blogInsightRes;
                    })
                    .toList();
    }

    @Override
    public List<BlogInsightRes> findByComicId(Long comicId) {
        var blogs = blogInsightRepository.findByComicId((comicId));
        return blogs.stream()
                    .map(blog -> {
                        BlogInsightRes blogInsightRes = new BlogInsightRes();
                        blogInsightMapper.updateBlogInsightResponseFromEntity(blog, blogInsightRes);
                        return blogInsightRes;
                    })
                    .toList();
    }

    @Override
    public ResultPaginationDTO findAll(Pageable pageable) {
        Page<BlogInsight> blogPage = blogInsightRepository.findAll(pageable);
        var blogInsightResList = blogPage.getContent()
                                         .stream()
                                         .map(b -> {
                                             BlogInsightRes blogInsightRes = new BlogInsightRes();
                                             blogInsightMapper.updateBlogInsightResponseFromEntity(
                                                     b, blogInsightRes);
                                             return blogInsightRes;
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
                                  .result(blogInsightResList)
                                  .build();

    }
}
