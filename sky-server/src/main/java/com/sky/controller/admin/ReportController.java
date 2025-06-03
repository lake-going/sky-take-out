package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @param
 * @return
 */

@Api(tags = "统计数据")
@Slf4j
@RestController
@RequestMapping("/admin/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/turnoverStatistics")
    public Result turnoverStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("统计{}至{}之间内的数据",begin,end);

        TurnoverReportVO turnoverReportVO = reportService.queryByData(begin,end);

        return Result.success(turnoverReportVO);
    }

    @GetMapping("/userStatistics")
    public Result<UserReportVO> userStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("统计{}至{}之间内的新增用户数据",begin,end);

        UserReportVO userReportVO = reportService.queryUserByData(begin,end);

        return Result.success(userReportVO);
    }
}
