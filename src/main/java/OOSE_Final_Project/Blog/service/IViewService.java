package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.entity.View;

import java.util.List;
import java.util.Optional;

public interface IViewService {
    View incrementViewCount(Long blogId);

    List<View> getAllViews();

    Optional<View> getViewById(Long id);

    View getViewByBlogId(Long blogId);

    void deleteView(Long id);
}
