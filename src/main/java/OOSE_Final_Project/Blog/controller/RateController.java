package OOSE_Final_Project.Blog.controller;

import OOSE_Final_Project.Blog.dto.req.RateReq;
import OOSE_Final_Project.Blog.dto.res.ApiResponse;
import OOSE_Final_Project.Blog.dto.res.RateRes;
import OOSE_Final_Project.Blog.service.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rates")
public class RateController {

    @Autowired
    IRateService rateService;

    @PostMapping
    public ApiResponse<RateRes> createRate(@RequestBody RateReq rateReq) {
        RateRes created = rateService.createRate(rateReq);
        return new ApiResponse<>(HttpStatus.CREATED, "Rate created successfully", created, null);
    }

    @GetMapping("/blog/{blogId}")
    public ApiResponse<List<RateRes>> getRatesByBlogId(@PathVariable Long blogId) {
        List<RateRes> rates = rateService.getRatesByBlogId(blogId);
        return new ApiResponse<>(HttpStatus.OK, "Rates retrieved successfully", rates, null);
    }

    @GetMapping("/user/{userId}/blog/{blogId}")
    public ApiResponse<RateRes> getRateByUserIdAndBlogId(@PathVariable Long userId, @PathVariable Long blogId) {
        RateRes rate = rateService.getRateByUserIdAndBlogId(userId, blogId);
        return new ApiResponse<>(HttpStatus.OK, "Rate retrieved successfully", rate, null);
    }

    @PutMapping("/{id}")
    public ApiResponse<RateRes> updateRate(@PathVariable Long id, @RequestParam long rate) {
        RateRes updated = rateService.updateRate(id, rate);
        return new ApiResponse<>(HttpStatus.OK, "Rate updated successfully", updated, null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteRate(@PathVariable Long id) {
        rateService.deleteRate(id);
        return new ApiResponse<>(HttpStatus.NO_CONTENT, "Rate deleted successfully", Boolean.TRUE, null);
    }

    @GetMapping("")
    public ApiResponse<List<RateRes>> getAllRates() {
        List<RateRes> rates = rateService.getAllRates();
        return new ApiResponse<>(HttpStatus.OK, "Rates retrieved successfully", rates, null);
    }

    @GetMapping("/count-all")
    public ApiResponse<Long> getAllRatesCount() {
        var rates = rateService.getAllRatesCount();
        return new ApiResponse<>(HttpStatus.OK, "Rate count retrieved successfully", rates, null);
    }

}
