package OOSE_Final_Project.Blog.observer;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObserverRegistry {

    @Autowired
    private BlogPublisher blogPublisher;

    @Autowired
    private BlogObserver blogObserver;

    @PostConstruct
    public void registerObserver() {
        blogPublisher.subscribe(blogObserver);
    }
}
