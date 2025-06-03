package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.entity.Orders;
import com.sky.entity.User;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserLoginMapper;
import com.sky.service.ReportService;
import com.sky.service.UserService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
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
    @Autowired
    private UserLoginMapper UserLoginMapper;
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
            turnover = turnover == null ? 0 : turnover;
            turnoverList.add(turnover);
        });

        // 构造vo
        TurnoverReportVO turnoverReportVO = new TurnoverReportVO();
        turnoverReportVO.setTurnoverList(StringUtils.join(turnoverList,","));
        turnoverReportVO.setDateList(StringUtils.join(localDateList,","));

        return turnoverReportVO;
    }

    @Override
    public UserReportVO queryUserByData(LocalDate begin, LocalDate end) {
        // 构造datalish
        List<LocalDate> localDateList = new ArrayList<LocalDate>();
        // 循环插入
        while (begin.isAfter(end)){
            localDateList.add(begin);
            begin.plusDays(1);
        }

        // 循环查询新增用户数
        List<Integer> userNumberList = new ArrayList<Integer>();
        List<Integer> userTotalNumberList = new ArrayList<Integer>();
        localDateList.forEach(localDate -> {
            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("begin", LocalDateTime.of(localDate, LocalTime.MIN));
            objectObjectHashMap.put("end",LocalDateTime.of(localDate,LocalTime.MAX));

            // 查询新用户
            Integer userNumber = UserLoginMapper.queryUserByData(objectObjectHashMap);
            userNumber = userNumber == null ? 0 : userNumber;
            userNumberList.add(userNumber);

            // 查询所有用户
            LocalDateTime endTime = LocalDateTime.of(localDate,LocalTime.MAX);
            Integer userTotalNumber = UserLoginMapper.queryTotalUserByData(endTime);
            userTotalNumber = userTotalNumber == null ? 0 : userTotalNumber;
            userTotalNumberList.add(userTotalNumber);
        });

        // 构造vo
        UserReportVO userReportVO = new UserReportVO();
        userReportVO.builder()
                .newUserList(StringUtils.join(userNumberList,","))
                .dateList(StringUtils.join(localDateList,","))
                .totalUserList(StringUtils.join(userTotalNumberList,","))
                .build();

        return userReportVO;
    }
}
