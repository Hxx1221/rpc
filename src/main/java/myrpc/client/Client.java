package myrpc.client;


import myrpc.OrderService;
import myrpc.client.dispose.ZkConfig;
import myrpc.client.service.IServiceDiscovery;
import myrpc.client.service.ServiceDiscoveryImpl;

public class Client {

    public static void main(String[] args) throws InterruptedException {
        IServiceDiscovery serviceDiscovery=new
                ServiceDiscoveryImpl(ZkConfig.CONNNECTION_STR);

      RpcClientProxy rpcClientProxy=new RpcClientProxy(serviceDiscovery);

        for(int i=0;i<10000;i++) {
            OrderService hello = rpcClientProxy.clientProxy(OrderService.class, null);
            System.out.println(hello.save("123"));
        }
    }
}