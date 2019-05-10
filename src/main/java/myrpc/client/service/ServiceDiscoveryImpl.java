package myrpc.client.service;

import myrpc.client.loadbalance.LoadBanalce;
import myrpc.client.loadbalance.RandomLoadBanalce;
import myrpc.server.dispose.ZkConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

public class ServiceDiscoveryImpl implements IServiceDiscovery {
    List<String> repos=new ArrayList<String>();
    private String address;

    private CuratorFramework curatorFramework;

    public ServiceDiscoveryImpl(String address) {
        this.address = address;
        curatorFramework= CuratorFrameworkFactory.builder().namespace("service")
                .connectString(address).sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(3000,
                        10)).build();
        curatorFramework.start();
    }

    @Override
    public String discover(String serviceName) throws Exception {
        String path = ZkConfig.ZK_REGISTER_PATH +"/"+ serviceName;
        repos = curatorFramework.getChildren().forPath(path);
        registerWatcher(path);
        LoadBanalce randomLoadBanalce = new RandomLoadBanalce();
        return randomLoadBanalce.selectHost(repos);
    }

    private void registerWatcher(final String path) {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, path, true);
        PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                repos = curatorFramework.getChildren().forPath(path);
            }
        };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
    }
}