package util;



import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
   
public class ThreadUtil {
   
    
/* public static ThreadPoolExecutor threadPool= new ThreadPoolExecutor(20, 50, 60, TimeUnit.SECONDS, 
    			  new LinkedBlockingQueue<Runnable>()); */
     public static ExecutorService pool = Executors.newFixedThreadPool(20);   
     //HashMap用于绑定用户ID和对应进程
     //public static HashMap<String, Long> hm=new HashMap<>();
          
     public String CompileJavaInThread(File file,String threadId) {
    	  Callable<String> c=new Task(file.getName(),threadId);    	  
    	  Future f=pool.submit(c);   	     	  
          try {
			return  f.get().toString();
		 } catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "本地编译故障！！！";
  }    
     
       public String isDone(long threadId) {
    	      	   
           ThreadMXBean tmx = ManagementFactory.getThreadMXBean();
           ThreadInfo info = tmx.getThreadInfo(threadId);
           
           if(info.getThreadState()!=Thread.State.TERMINATED) {
        	   
           };
          
       
    	 return null;
     }
     
     
}