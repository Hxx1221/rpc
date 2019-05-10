package myrpc.server.service;


import myrpc.OrderService;
import myrpc.server.anno.RpcAnnotation;

import java.util.Random;

@RpcAnnotation(value = OrderService.class)
public class OrderServiceImpl implements  OrderService {
    @Override
    public String save(String order) {
        return "我是分布式 80服务  订单创建成功！===>"+"=="+order+ new Random().nextInt();
    }
}