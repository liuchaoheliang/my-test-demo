package com.sychronized;

import java.util.concurrent.CountDownLatch;

public class SychronizedDemo {
	
	private static Integer mInt = 0  ;
	
	private static Object lock = new Object() ;
	
//	private static CountDownLatch countDownLatch=new CountDownLatch(2);
	
	public synchronized int addNum(){
//		synchronized(mInt){
			mInt= mInt +1 ;
			return mInt;
//		}
	}

	
	public static void main(String[] args) throws InterruptedException {
		SychronizedDemo d = new SychronizedDemo();
		SychronizedDemo d1 = new SychronizedDemo();
		
		new Thread(){
			@Override
			public void run() {
				synchronized(d){
					for(int i = 0 ; i< 100 ;i++ ){
						System.out.println("线程"+this.getName()+"执行运算结果："+d.addNum());
					}
					d.notify();
				}
			}
			
		}.start(); 
		
//		new Thread(){
//			@Override
//			public void run() {
//				synchronized(d){
//					try {
//						System.out.println("等待线程0计算完成");
//						d.wait();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					for(int i = 0 ; i< 100 ;i++ ){
//						System.out.println("线程"+this.getName()+"执行运算结果："+d1.addNum());
//					}
//					
//				}
//			}
//		}.start(); 

		synchronized (d) {
			System.out.println("等待线程0计算完成");
			d.wait();
			new Thread(){
				@Override
				public void run() {
					synchronized(d){
						for(int i = 0 ; i< 100 ;i++ ){
							System.out.println("线程"+this.getName()+"执行运算结果："+d1.addNum());
						}
						d.notify();
					}
				}
			}.start();
			System.out.println("等待线程1计算完成");
			d.wait();
			System.out.println("最终结果 mInt:"+mInt);
		}
	
		
	}
	
}
