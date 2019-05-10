package myrpc.server.service;


import myrpc.OrderService;
import myrpc.server.anno.RpcAnnotation;

@RpcAnnotation(value = OrderService.class)
public class OrderServiceImpl1 implements  OrderService {
    @Override
    public String save(String order) {
        return "我是分布式 81服务  订单创建成功！===>"+order;
    }
}