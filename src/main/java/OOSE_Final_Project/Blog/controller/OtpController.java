package OOSE_Final_Project.Blog.controller;

import OOSE_Final_Project.Blog.dto.res.ApiResponse;
import OOSE_Final_Project.Blog.facade.UserOTPFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/otp")
public class OtpController {


    @Autowired
    UserOTPFacade userOTPFacade;

    @GetMapping("")
    public ApiResponse<Boolean> generateNewOTP(@RequestParam("userId") String userId) {
        userOTPFacade.generateOTP(Long.valueOf(userId));
        return new ApiResponse<>(HttpStatus.CREATED, "Generate new OTP", Boolean.TRUE, null);
    }

    @PostMapping("")
    public ApiResponse<Boolean> verifyOTP(@RequestBody String otp, @RequestParam("userId") String userId) {
        var result = userOTPFacade.verifyOTP(Long.valueOf(userId), otp);
        return new ApiResponse<>(HttpStatus.OK, "Verify OTP", result, null);
    }


}
