package OOSE_Final_Project.Blog.controller;

import OOSE_Final_Project.Blog.dto.req.ReportReq;
import OOSE_Final_Project.Blog.dto.res.ApiResponse;
import OOSE_Final_Project.Blog.dto.res.ReportRes;
import OOSE_Final_Project.Blog.observer.ReportPublisher;
import OOSE_Final_Project.Blog.service.strategy.report.IReportService;
import OOSE_Final_Project.Blog.service.strategy.report.ReportStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/reports")
public class ReportController {

    @Autowired
    ReportStrategyFactory reportStrategyFactory;

    @Autowired
    ReportPublisher reportPublisher;

    @PostMapping("")
    public ApiResponse<ReportRes> createReport(@RequestBody ReportReq req) {
        IReportService reportService = reportStrategyFactory.getStrategy(req.getType());
        ReportRes res = reportService.createReport(req);
        return (new ApiResponse<>(HttpStatus.OK, "Report created", res, null));
    }

    @GetMapping("")
    public ApiResponse<List<ReportRes>> getAllReports(
            @RequestParam("type") String type
    ) {
        IReportService reportService = reportStrategyFactory.getStrategy(String.valueOf(type));
        List<ReportRes> res = reportService.getAllReports();
        return (new ApiResponse<>(HttpStatus.OK, "All " + type + " report", res, null));
    }

    @GetMapping("/unhandled")
    public ApiResponse<List<ReportRes>> getUnhandledReports(
            @RequestParam("type") String type
    ) {
        IReportService reportService = reportStrategyFactory.getStrategy(String.valueOf(type));
        List<ReportRes> res = reportService.getUnHandledReports();

        return (new ApiResponse<>(HttpStatus.OK, "All unhandled " + type + " report", res, null));
    }

    @GetMapping("/unread")
    public ApiResponse<List<ReportRes>> getUnReadReports(
            @RequestParam("type") String type
    ) {
        IReportService reportService = reportStrategyFactory.getStrategy(String.valueOf(type));
        List<ReportRes> res = reportService.getUnReadReports();
        return (new ApiResponse<>(HttpStatus.OK, "All unread " + type + " report", res, null));
    }

    @PatchMapping("/read/{reportId}")
    public ApiResponse<ReportRes> markAsRead(@PathVariable long reportId, @RequestParam String type) {
        IReportService reportService = reportStrategyFactory.getStrategy(String.valueOf(type));
        var result = reportService.markAsRead(reportId);
        if (Objects.equals(type, "Comment")) {
            reportPublisher.notifyObservers(result);

        }
        return (new ApiResponse<>(HttpStatus.OK, "Report marked as read", result, null));
    }

    @PatchMapping("/handle/{reportId}")
    public ApiResponse<ReportRes> markAsHandle(@PathVariable long reportId, @RequestParam String type) {
        IReportService reportService = reportStrategyFactory.getStrategy(String.valueOf(type));
        var result = reportService.markAsHandled(reportId);
        if (Objects.equals(type, "Comment")) {
            reportPublisher.notifyObservers(result);

        }
        return (new ApiResponse<>(HttpStatus.OK, "Report marked as handle", result, null));
    }


    @DeleteMapping("")
    public ApiResponse<Boolean> deleteReport(
            @RequestParam("type") String type,
            @RequestParam("id") Long id) {
        IReportService reportService = reportStrategyFactory.getStrategy(type);
        reportService.deleteReport(id);
        return (new ApiResponse<>(HttpStatus.OK, "Report deleted", Boolean.TRUE, null));
    }
}
