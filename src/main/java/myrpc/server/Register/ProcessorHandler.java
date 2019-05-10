package myrpc.server.Register;


import myrpc.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

public class ProcessorHandler implements Runnable{

    private java.net.Socket socket=null;

    private Map<String ,Object> handlerMap=null;

    public ProcessorHandler(Socket socket, Map<String, Object> handlerMap) {
        this.socket = socket;
        this.handlerMap = handlerMap;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream=null;
        try {
            objectInputStream=new ObjectInputStream(socket.getInputStream());
            RpcRequest request=(RpcRequest) objectInputStream.readObject();
            Object result=invoke(request); //通过反射去调用本地的方法
            ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());

            outputStream.writeObject(result);
            outputStream.flush();
            outputStream.close();
            outputStream.close();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(objectInputStream!=null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }






    private Object invoke(RpcRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        //以下均为反射操作，目的是通过反射调用服务
        Object[] args=request.getParameters();
        Class<?>[] types=new Class[args.length];
        for(int i=0;i<args.length;i++){
            types[i]=args[i].getClass();
        }
        String serviceName=request.getClassName();
        String version=request.getVersion();
        if(version!=null&&!version.equals("")){
            serviceName=serviceName+"-"+version;
        }
        //从handlerMap中，根据客户端请求的地址，去拿到响应的服务，通过反射发起调用
        Object service=handlerMap.get(serviceName);
        Method method=service.getClass().getMethod(request.getMethodName(),types);
        return method.invoke(service,args);
    }


}