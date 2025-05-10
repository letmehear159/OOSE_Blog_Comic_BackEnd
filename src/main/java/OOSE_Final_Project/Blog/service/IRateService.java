package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.req.RateReq;
import OOSE_Final_Project.Blog.dto.res.RateRes;

import java.util.List;

public interface IRateService {

    RateRes createRate(RateReq rate);

    List<RateRes> getRatesByBlogId(Long blogId);

    RateRes getRateByUserIdAndBlogId(Long userId, Long blogId);

    RateRes updateRate(Long id, long rate);


    void deleteRate(Long id);

    List<RateRes> getAllRates();

    long getRatesCountForBlogId(Long blogId);

    double getRatesForBlogId(Long blogId);

}
