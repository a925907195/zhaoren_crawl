package com.bj58.zhaoren.crawl.schedule.onetask;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class OnceTask implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		 if(event.getApplicationContext().getParent() == null){//root application context 没有parent，他就是老大.
	           //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
			 System.out.println("时间[" + new java.util.Date().toLocaleString()
                     + "]-----> 此任务只执行一次！");
			 System.out.println("-----only run one time -----------------");
	      }
	}

}
