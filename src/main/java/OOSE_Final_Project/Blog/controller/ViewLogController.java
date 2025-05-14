package OOSE_Final_Project.Blog.controller;

import OOSE_Final_Project.Blog.entity.DailyViewProjection;
import OOSE_Final_Project.Blog.service.impl.ViewLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/views")
@RequiredArgsConstructor
public class ViewLogController {

    private final ViewLogService viewLogService;

    @PostMapping("/log")
    public ResponseEntity<String> logView(
            @RequestParam Long blogId,
            @RequestParam(required = false) Long userId
    ) {
        viewLogService.logView(blogId, userId);
        return ResponseEntity.ok("View logged");
    }

    @GetMapping("/last-5-days")
    public ResponseEntity<List<DailyViewProjection>> getLast5DaysViews() {
        return ResponseEntity.ok(viewLogService.getLast5DaysViews());
    }
}
