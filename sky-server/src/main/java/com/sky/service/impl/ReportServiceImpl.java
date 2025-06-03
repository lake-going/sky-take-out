package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @param
 * @return
 */

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public TurnoverReportVO queryByData(LocalDate begin, LocalDate end) {
        // 构造datalish
        List<LocalDate> localDateList = new ArrayList<LocalDate>();
        // 循环插入
        while (begin.isAfter(end)){
            localDateList.add(begin);
            begin.plusDays(1);
        }

        // 循环查询营业额
        List<Integer> turnoverList = new ArrayList<Integer>();
        localDateList.forEach(localDate -> {
            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("status", Orders.COMPLETED);
            objectObjectHashMap.put("begin", LocalDateTime.of(localDate, LocalTime.MIN));
            objectObjectHashMap.put("end",LocalDateTime.of(localDate,LocalTime.MAX));
            Integer turnover = orderMapper.queryByData(objectObjectHashMap);
            turnoverList.add(turnover);
        });

        // 构造vo
        TurnoverReportVO turnoverReportVO = new TurnoverReportVO();
        turnoverReportVO.setTurnoverList(turnoverList.toString());
        turnoverReportVO.setDateList(localDateList.toString());

        return turnoverReportVO;
    }
}
