package OOSE_Final_Project.Blog.observer;


import OOSE_Final_Project.Blog.dto.res.blog.BlogRes;
import OOSE_Final_Project.Blog.repository.BlogRepository;
import OOSE_Final_Project.Blog.service.IViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlogViewObserver implements Observer {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    IViewService viewService;

    @Override
    public void update(Object data) {

        BlogRes blogRes = (BlogRes) data;

        var blog = blogRepository.findById(blogRes.getId())
                                 .orElse(null);
        if (blog == null) {
            return;
        }
        var view = viewService.getViewByBlogId(blogRes.getId());
        if (view != null) {
            viewService.incrementViewCount(blogRes.getId());
        }
    }

    @Override
    public String getPublisherType() {
        return "BlogViewLevelPublisher";
    }
}
