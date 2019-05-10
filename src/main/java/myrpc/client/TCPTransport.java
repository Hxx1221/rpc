package myrpc.client;

import myrpc.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPTransport {
    private String serviceAddress;

    public TCPTransport(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    private Socket newSocket() {
        System.out.println("创建一个新的连接");
        Socket socket;
        try {
            String[] arrs = serviceAddress.split(":");
            socket = new Socket(arrs[0], Integer.parseInt(arrs[1]));
            return socket;
        } catch (Exception e) {
            throw new RuntimeException("连接建立失败");
        }
    }

    public Object send(RpcRequest rpcRequest) throws IOException, ClassNotFoundException {
        Socket socket = null;
        socket = newSocket();
        //拿到流
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        objectOutputStream.writeObject(rpcRequest);

        objectOutputStream.flush();
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Object o = objectInputStream.readObject();
        objectInputStream.close();
        objectOutputStream.close();
        return o;

    }

}