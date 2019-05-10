package myrpc.client.handler;

import myrpc.RpcRequest;
import myrpc.client.TCPTransport;
import myrpc.client.service.IServiceDiscovery;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHandler implements InvocationHandler {
    private IServiceDiscovery iServiceDiscovery;
    private String version;
    public RemoteInvocationHandler(IServiceDiscovery iServiceDiscovery,String version) {
        this.version=version;
        this.iServiceDiscovery = iServiceDiscovery;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameters(args);
        request.setVersion(version);
        String discover = iServiceDiscovery.discover(request.getClassName());
        TCPTransport tcpTransport = new TCPTransport(discover);
        return tcpTransport.send(request);
    }
}