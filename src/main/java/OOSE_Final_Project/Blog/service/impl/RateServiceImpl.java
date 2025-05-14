package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.req.RateReq;
import OOSE_Final_Project.Blog.dto.res.RateRes;
import OOSE_Final_Project.Blog.entity.Rate;
import OOSE_Final_Project.Blog.mapper.RateMapper;
import OOSE_Final_Project.Blog.repository.RateRepository;
import OOSE_Final_Project.Blog.service.IRateService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class RateServiceImpl implements IRateService {


    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private RateMapper rateMapper;

    @Override
    public RateRes createRate(RateReq rateReq) {

        Rate rate = new Rate();

        rateMapper.updateRateFromDto(rateReq, rate);

        if (rateRepository.findByUserIdAndBlogId(rateReq.getUserId(), rateReq.getBlogId())
                          .isPresent()) {
            throw new IllegalArgumentException("Rate already exists");
        }

        // Kiểm tra giá trị rateStar hợp lệ (giả sử từ 1 đến 5)
        if (rate.getRateStar() < 1 || rate.getRateStar() > 5) {
            throw new IllegalArgumentException("Rate star must be between 1 and 5");
        }


        rate = rateRepository.save(rate);
        RateRes res = new RateRes();
        rateMapper.updateRateResponseFromEntity(rate, res);
        return res;
    }


    @Override
    public List<RateRes> getRatesByBlogId(Long blogId) {
        var rates = rateRepository.findByBlogId(blogId);
        return rates.stream()
                    .map(
                            rate -> {
                                RateRes res = new RateRes();
                                rateMapper.updateRateResponseFromEntity(rate, res);
                                return res;
                            }

                    )
                    .toList();
    }

    @Override
    public RateRes getRateByUserIdAndBlogId(Long userId, Long blogId) {
        var rate = rateRepository.findByUserIdAndBlogId(userId, blogId)
                                 .orElseThrow(() -> new RuntimeException("Rate not found"));
        RateRes res = new RateRes();
        rateMapper.updateRateResponseFromEntity(rate, res);
        return res;
    }

    @Override
    public RateRes updateRate(Long id, long rateStar) {
        Rate rate = rateRepository.findById(id)
                                  .orElseThrow(() -> new IllegalArgumentException("Rate not found with id: " + id));

        // Kiểm tra giá trị rateStar hợp lệ
        if (rateStar < 1 || rateStar > 5) {
            throw new IllegalArgumentException("Rate star must be between 1 and 5");
        }

        rate.setRateStar(rateStar);
        // Không cho phép thay đổi blog hoặc sender để đảm bảo tính nhất quán
        rate = rateRepository.save(rate);
        RateRes res = new RateRes();
        rateMapper.updateRateResponseFromEntity(rate, res);
        return res;
    }

    @Override
    public void deleteRate(Long id) {
        if (!rateRepository.existsById(id)) {
            throw new IllegalArgumentException("Rate not found with id: " + id);
        }
        rateRepository.deleteById(id);
    }

    @Override
    public List<RateRes> getAllRates() {
        var rates = rateRepository.findAll();
        return rates.stream()
                    .map(
                            rate -> {
                                RateRes res = new RateRes();
                                rateMapper.updateRateResponseFromEntity(rate, res);
                                return res;
                            }

                    )
                    .toList();
    }

    @Override
    public long getRatesCountForBlogId(Long blogId) {
        var result = rateRepository.findByBlogId(blogId);
        return result == null ? 0 : result.size();
    }

    @Override
    public double getRatesForBlogId(Long blogId) {
        var result = rateRepository.findByBlogId(blogId);
        double total = 0;
        if (!result.isEmpty()) {
            for (Rate rate : result) {
                total += rate.getRateStar();
            }
        }

        return result.isEmpty() ? 0 : total / result.size();
    }

    @Override
    public long getAllRatesCount() {
        return rateRepository.count();
    }


}
