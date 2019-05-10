package myrpc.server.Register;

import myrpc.server.dispose.ZkConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class RegisterCenterServiceImpl implements IRegisterCenter{

    private CuratorFramework curatorFramework;
    //初始化zk
    {
         curatorFramework = CuratorFrameworkFactory.builder().namespace("service")
                .connectString(ZkConfig.CONNNECTION_STR)
                .sessionTimeoutMs(1000).
                        retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        curatorFramework.start();
    }


    @Override
    public void register(String serviceName, String adderess) throws Exception {
        String servicePath=ZkConfig.ZK_REGISTER_PATH+"/"+serviceName;
        Stat stat = curatorFramework.checkExists().forPath(servicePath);
        if (stat==null){
            curatorFramework.create().creatingParentsIfNeeded().
                    withMode(CreateMode.PERSISTENT).forPath(servicePath,"0".getBytes());

        }

        String addressPath=servicePath+"/"+adderess;
        String rsNode=curatorFramework.create().withMode(CreateMode.EPHEMERAL).
                forPath(addressPath,"0".getBytes());
        System.out.println("服务注册成功："+rsNode);

    }
}