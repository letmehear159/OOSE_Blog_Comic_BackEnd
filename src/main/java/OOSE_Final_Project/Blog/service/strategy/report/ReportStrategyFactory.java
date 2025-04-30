package OOSE_Final_Project.Blog.service.strategy.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReportStrategyFactory {

    private final Map<String, IReportService> strategies;

    @Autowired
    public ReportStrategyFactory(
            @Qualifier("reportBlogServiceImpl") IReportService blog,
            @Qualifier("reportCommentServiceImpl") IReportService comment
    ) {
        strategies = new HashMap<>();
        strategies.put("Blog", blog);
        strategies.put("Comment", comment);
    }

    public IReportService getStrategy(String type) {
        return strategies.get(type);
    }
}
