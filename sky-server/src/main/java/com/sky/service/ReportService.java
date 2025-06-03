package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {
    TurnoverReportVO queryByData(LocalDate begin, LocalDate end);

    UserReportVO queryUserByData(LocalDate begin, LocalDate end);

    OrderReportVO queryOrderByData(LocalDate begin, LocalDate end);

    SalesTop10ReportVO queryTop10ByData(LocalDate begin, LocalDate end);
}
