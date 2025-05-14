package OOSE_Final_Project.Blog.observer;


import OOSE_Final_Project.Blog.dto.res.blog.BlogRes;
import OOSE_Final_Project.Blog.repository.BlogRepository;
import OOSE_Final_Project.Blog.service.IViewService;
import OOSE_Final_Project.Blog.service.impl.ViewLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlogViewObserver implements Observer {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    IViewService viewService;

    @Autowired
    ViewLogService viewLogService;

    @Override
    public void update(Object data) {

        BlogRes blogRes = (BlogRes) data;


        viewLogService.logView(blogRes.getId(), null);
    }

    @Override
    public String getPublisherType() {
        return "BlogViewLevelPublisher";
    }
}
