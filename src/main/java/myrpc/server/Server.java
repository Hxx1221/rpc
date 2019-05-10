package myrpc.server;

import myrpc.server.Register.IRegisterCenter;
import myrpc.server.Register.RegisterCenterServiceImpl;
import myrpc.OrderService;
import myrpc.server.service.OrderServiceImpl;
import myrpc.server.service.OrderServiceImpl1;

public class Server {

    public static void main(String[] args) throws Exception {
        OrderService service = new OrderServiceImpl();
        OrderService service1 = new OrderServiceImpl1();
        IRegisterCenter registerCenterService = new RegisterCenterServiceImpl();
        RpcService rpcService = new RpcService(registerCenterService, "127.0.0.1:8081");
        rpcService.bind(service);
        rpcService.publisher();
        System.in.read();
    }
}