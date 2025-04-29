package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.req.RateReq;
import OOSE_Final_Project.Blog.entity.Rate;

import java.util.List;
import java.util.Optional;

public interface IRateService {

    Rate createRate(RateReq rate);

    List<Rate> getAllRates();

    Optional<Rate> getRateById(Long id);

    List<Rate> getRatesByBlogId(Long blogId);

    List<Rate> getRatesByUserId(Long userId);

    Optional<Rate> getRateByUserIdAndBlogId(Long userId, Long blogId);

    Rate updateRate(Long id, long rate);


    void deleteRate(Long id);
}
