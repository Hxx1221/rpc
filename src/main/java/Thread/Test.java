package Thread;

import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:He_xixiang
 * @Title: ${enclosing_method}
 * @Description: ${todo}(这里用一句话描述这个方法的作用)
 * @return ${return_type}    返回类型
 * @throws
 */
public class Test {
    private static List<String> paths = new ArrayList<String>();

    public static void main(String[] args) throws Exception {
        ls("/kafka-test");
        Thread.sleep(10000);
        for (String path :
                paths) {
            System.out.println(path);
        }

    }

    public static void ls(String path) throws Exception {
        paths.add(path);
        System.out.println(path);
        ZooKeeper zk = new ZooKeeper("106.12.12.39:2181", 5000, null);
        List<String> list = zk.getChildren(path, null);
        //判断是否有子节点
        if (list.isEmpty() || list == null) {
            return;
        }
        for (String s : list) {
            //判断是否为根目录
            if (path.equals("/")) {
                ls(path + s);
            } else {
                ls(path + "/" + s);
            }
        }
    }


}
