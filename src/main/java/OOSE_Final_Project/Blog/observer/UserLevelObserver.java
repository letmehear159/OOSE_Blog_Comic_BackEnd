package OOSE_Final_Project.Blog.observer;

import OOSE_Final_Project.Blog.enums.ELevelPoint;
import OOSE_Final_Project.Blog.service.IUserService;
import OOSE_Final_Project.Blog.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserLevelObserver implements Observer {

    @Autowired
    IUserService userService;


    @Override
    public void update(Object data) {

        String username = SecurityUtil.getCurrentUserLogin()
                                      .orElse(null);
        if (Objects.equals(username, "anonymousUser")) {
            return;
        }
        userService.updateLevel(username, ELevelPoint.VIEW);
    }

    @Override
    public String getPublisherType() {
        return "BlogViewLevelPublisher";
    }
}

