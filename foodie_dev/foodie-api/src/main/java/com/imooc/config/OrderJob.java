package com.imooc.config;

import com.imooc.service.OrderService;
import com.imooc.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderJob {
//    @Autowired
//    private OrderService orderService;
//    //cron.qqe2.com
//     @Scheduled(cron = "0 0 0 L * ? *")
//     public void autoCloseOrder(){
//      orderService.closeOrder();
//     }
}
