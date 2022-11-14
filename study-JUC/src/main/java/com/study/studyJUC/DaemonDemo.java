package com.study.studyJUC;

/**
 * 守护线程
 */
public class DaemonDemo {
	public static void main(String[] args) throws InterruptedException {
		MyDeamoThread thread = new MyDeamoThread();

		// 设置为守护线程，当main线程结束时才退出
		thread.setDaemon(true);
		thread.start();
		
		for (int i = 0; i < 10; i++) {
			System.out.println("main start to loading...");
			Thread.sleep(1000);
		}
		
		
	}

}

class MyDeamoThread  extends Thread{
	@Override
	public void run() {
	
		for(;;) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("xx - xx method ");
		}
	}
}
