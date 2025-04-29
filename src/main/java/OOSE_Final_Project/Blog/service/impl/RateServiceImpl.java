package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.req.RateReq;
import OOSE_Final_Project.Blog.entity.Rate;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.entity.blog.Blog;
import OOSE_Final_Project.Blog.repository.BlogRepository;
import OOSE_Final_Project.Blog.repository.RateRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import OOSE_Final_Project.Blog.service.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RateServiceImpl implements IRateService {


    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Rate createRate(RateReq rateReq) {

        Rate rate = new Rate();

        User user = userRepository.findById(rateReq.getUserId())
                                  .orElseThrow(() -> new RuntimeException("User Not Found"));
        Blog blog = blogRepository.findById(rateReq.getBlogId())
                                  .orElseThrow(() -> new RuntimeException("Blog Not Found"));
        rate.setUser(user);
        rate.setBlog(blog);
        rate.setRateStar(rateReq.getRating());

        // Kiểm tra xem user đã đánh giá blog này chưa
        Optional<Rate> existingRate = rateRepository.findByUserIdAndBlogId(
                rate.getUser()
                    .getId(), rate.getBlog()
                                  .getId());
        if (existingRate.isPresent()) {
            throw new IllegalArgumentException("User " + rate.getUser()
                                                             .getId() +
                                                       " has already rated blog " + rate.getBlog()
                                                                                        .getId());
        }

        // Kiểm tra giá trị rateStar hợp lệ (giả sử từ 1 đến 5)
        if (rate.getRateStar() < 1 || rate.getRateStar() > 5) {
            throw new IllegalArgumentException("Rate star must be between 1 and 5");
        }

        return rateRepository.save(rate);
    }

    @Override
    public List<Rate> getAllRates() {
        return rateRepository.findAll();
    }

    @Override
    public Optional<Rate> getRateById(Long id) {
        return rateRepository.findById(id);
    }

    @Override
    public List<Rate> getRatesByBlogId(Long blogId) {
        return rateRepository.findByBlogId(blogId);
    }

    @Override
    public List<Rate> getRatesByUserId(Long userId) {
        return rateRepository.findByUserId(userId);
    }

    @Override
    public Optional<Rate> getRateByUserIdAndBlogId(Long userId, Long blogId) {
        return rateRepository.findByUserIdAndBlogId(userId, blogId);
    }

    @Override
    public Rate updateRate(Long id, long rateStar) {
        Rate rate = rateRepository.findById(id)
                                  .orElseThrow(() -> new IllegalArgumentException("Rate not found with id: " + id));

        // Kiểm tra giá trị rateStar hợp lệ
        if (rateStar < 1 || rateStar > 5) {
            throw new IllegalArgumentException("Rate star must be between 1 and 5");
        }

        rate.setRateStar(rateStar);
        // Không cho phép thay đổi blog hoặc sender để đảm bảo tính nhất quán
        return rateRepository.save(rate);
    }

    @Override
    public void deleteRate(Long id) {
        if (!rateRepository.existsById(id)) {
            throw new IllegalArgumentException("Rate not found with id: " + id);
        }
        rateRepository.deleteById(id);
    }
}
