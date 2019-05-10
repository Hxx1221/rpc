package myrpc.client;


;

import myrpc.client.handler.RemoteInvocationHandler;
import myrpc.client.service.IServiceDiscovery;

import java.lang.reflect.Proxy;


public class RpcClientProxy {

    private IServiceDiscovery serviceDiscovery;

    public RpcClientProxy(IServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }


    public <T> T clientProxy(final Class<T> interfaceCls,String version){
        //使用到了动态代理。
        return (T)Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class[]{interfaceCls},new RemoteInvocationHandler(serviceDiscovery,version));
    }




}
