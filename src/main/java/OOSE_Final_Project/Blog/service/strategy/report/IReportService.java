package OOSE_Final_Project.Blog.service.strategy.report;

import OOSE_Final_Project.Blog.dto.req.ReportReq;
import OOSE_Final_Project.Blog.dto.res.ReportRes;

import java.util.List;

public interface IReportService {

    ReportRes createReport(ReportReq reportReq);

    List<ReportRes> getAllReports();

    ReportRes markAsRead(long id);

    ReportRes markAsHandled(long id);

    void deleteReport(long id);

     List<ReportRes> getUnHandledReports();


}
