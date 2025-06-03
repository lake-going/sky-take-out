package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserLoginMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public OrderReportVO queryOrderByData(LocalDate begin, LocalDate end) {
        // 构造datalish
        List<LocalDate> localDateList = new ArrayList<LocalDate>();
        // 循环插入
        while (begin.isAfter(end)) {
            localDateList.add(begin);
            begin.plusDays(1);
        }

        // 循环查询订单数
        Integer totalOrderCount = 0;
        Integer validOrderCount = 0;
        List<Integer> orderCountList = new ArrayList<Integer>();
        List<Integer> validOrderCountList = new ArrayList<Integer>();
        localDateList.forEach(localDate -> {
            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("begin", LocalDateTime.of(localDate, LocalTime.MIN));
            objectObjectHashMap.put("end", LocalDateTime.of(localDate, LocalTime.MAX));
            objectObjectHashMap.put("status", Orders.COMPLETED);

            // 查询有效订单
            Integer userValidNumber = orderMapper.queryOrderByData(objectObjectHashMap);
            validOrderCountList.add(userValidNumber);

            // 查询所有订单
            objectObjectHashMap.put("status", null);
            Integer userTotalNumber = orderMapper.queryOrderByData(objectObjectHashMap);
            orderCountList.add(userTotalNumber);
        });

        // 查询整个时间段内的所有订单
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("begin", LocalDateTime.of(begin, LocalTime.MIN));
        objectObjectHashMap.put("end", LocalDateTime.of(end, LocalTime.MAX));
        Integer totalOrderCount1 = orderMapper.queryOrderByData(objectObjectHashMap);


        // 查询整个时间段内的所有有效订单
        objectObjectHashMap.put("status", Orders.COMPLETED);
        Integer validOrderCount1 = orderMapper.queryOrderByData(objectObjectHashMap);


        // 计算有效订单的比例
        Double orderCompletionRate = null;
        if (totalOrderCount1 != 0) {
            orderCompletionRate = (double) (validOrderCount1 / totalOrderCount1) * 100;
        }


        // 构造vo
        return OrderReportVO.builder()
                .validOrderCount(validOrderCount1)
                .totalOrderCount(totalOrderCount1)
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .orderCompletionRate(orderCompletionRate)
                .dateList(StringUtils.join(localDateList, ","))
                .build();
    }

    @Override
    public SalesTop10ReportVO queryTop10ByData(LocalDate begin, LocalDate end) {
        // 构造datalish
        List<LocalDate> localDateList = new ArrayList<LocalDate>();
        // 循环插入
        while (begin.isAfter(end)){
            localDateList.add(begin);
            begin.plusDays(1);
        }

        // 循环查询套餐销量
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("begin", LocalDateTime.of(begin, LocalTime.MIN));
        objectObjectHashMap.put("end",LocalDateTime.of(end,LocalTime.MAX));
        objectObjectHashMap.put("status",Orders.COMPLETED);

        // 查询前十套餐销量
        List orderNameList = new ArrayList();
        List orderNumberList = new ArrayList();
        List<Map> listTop = orderMapper.queryTop10ByData(objectObjectHashMap);
        for (Map m:listTop){
            String name = (String) m.get("name");
            orderNameList.add(name);
            String number = (String) m.get("sumNum");
            orderNumberList.add(number);
        }

        // 构造vo
        return SalesTop10ReportVO.builder()
                .nameList(StringUtils.join(orderNameList, ","))
                .numberList(StringUtils.join(orderNumberList, ","))
                .build();
    }
}
