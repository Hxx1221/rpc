package myrpc.server;

import myrpc.server.Register.IRegisterCenter;
import myrpc.server.Register.ProcessorHandler;
import myrpc.server.anno.RpcAnnotation;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcService {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    private IRegisterCenter iRegisterCenter;
    private String serviceAddress;

    private Map<String, Object> handlerMap = new HashMap<String, Object>();

    public RpcService(IRegisterCenter iRegisterCenter, String serviceAddress) {
        this.iRegisterCenter = iRegisterCenter;
        this.serviceAddress = serviceAddress;
    }


    public void bind(Object... services) {
        for (Object service : services) {
            RpcAnnotation annotation = service.getClass().getAnnotation(RpcAnnotation.class);
            Class<?> value = annotation.value();
            String className = value.getName();
            String version=annotation.version();
            if(version!=null&&!version.equals("")){
                className=className+"-"+version;
            }
            handlerMap.put(className,service);
        }
    }


    public void publisher() throws Exception {
        ServerSocket serverSocket=null;
        String[] split = serviceAddress.split(":");
         serverSocket = new ServerSocket(Integer.valueOf(split[1]));

         for (String interfaceName:handlerMap.keySet()){
             iRegisterCenter.register(interfaceName,serviceAddress);
             System.out.println(interfaceName+"===>"+"注册成功");
         }
         while (true){
             Socket accept = serverSocket.accept();
             executorService.execute(new ProcessorHandler(accept,handlerMap));
         }
    }

}