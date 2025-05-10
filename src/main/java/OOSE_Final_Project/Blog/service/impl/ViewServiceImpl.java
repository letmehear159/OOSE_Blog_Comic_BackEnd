package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.entity.View;
import OOSE_Final_Project.Blog.entity.blog.Blog;
import OOSE_Final_Project.Blog.repository.BlogRepository;
import OOSE_Final_Project.Blog.repository.ViewRepository;
import OOSE_Final_Project.Blog.service.IViewService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
public class ViewServiceImpl implements IViewService {

    @Autowired
    private ViewRepository viewRepository;

    @Autowired
    private BlogRepository blogRepository;


    @Override
    public View incrementViewCount(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                                  .orElseThrow(() -> new IllegalArgumentException("Blog not found with id: " + blogId));

        Optional<View> existingView = viewRepository.findByBlogId(blogId);
        View view;

        if (existingView.isPresent()) {
            view = existingView.get();
            view.setCount(view.getCount() + 1);
        } else {
            view = new View();
            view.setBlog(blog);
            view.setCount(1);
        }

        return viewRepository.save(view);
    }

    @Override
    public List<View> getAllViews() {
        return viewRepository.findAll();
    }

    @Override
    public Optional<View> getViewById(Long id) {
        return viewRepository.findById(id);
    }

    @Override
    public View getViewByBlogId(Long blogId) {
        return viewRepository.findByBlogId(blogId)
                             .orElse(null);

    }

    @Override
    public void deleteView(Long id) {
        if (!viewRepository.existsById(id)) {
            throw new IllegalArgumentException("View not found with id: " + id);
        }
        viewRepository.deleteById(id);
    }
}
