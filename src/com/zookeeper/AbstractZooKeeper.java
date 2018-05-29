package com.zookeeper;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

import com.alibaba.fastjson.JSON;

public class AbstractZooKeeper implements Watcher {  
    
  
    //缓存时间  
     private static final int SESSION_TIME   = 2000;     
     protected ZooKeeper zooKeeper;  
     protected CountDownLatch countDownLatch=new CountDownLatch(1);  
  
     public void connect(String hosts) throws IOException, InterruptedException{ 
            zooKeeper = new ZooKeeper(hosts,SESSION_TIME,this);     
            countDownLatch.await();
      }     
  
    @Override  
    public void process(WatchedEvent event) {  
        // TODO Auto-generated method stub  
    	System.out.println("回调watcher实例： 路径" + event.getPath() + " 类型：" + event.getType());
        if(event.getState()==KeeperState.SyncConnected){  
        	System.out.println("初始同步已经完成");
        	countDownLatch.countDown();  
        }else if(event.getState()==KeeperState.Disconnected){
        	System.out.println("当前节点连接断开");
        }  
        try {
			//zooKeeper.exists("/zk", this);
        	List<String> list = zooKeeper.getChildren("/zk", this);
        	System.out.println("当前集群客户端组："+JSON.toJSONString(list));
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }  
      
    public void close() throws InterruptedException{     
        zooKeeper.close();     
    }    
}  	