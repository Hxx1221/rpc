package myrpc.client.loadbalance;

import java.util.List;


public interface LoadBanalce {

    String selectHost(List<String> repos);
}


