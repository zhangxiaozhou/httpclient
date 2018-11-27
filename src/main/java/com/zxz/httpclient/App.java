package com.zxz.httpclient;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ClientProtocolException, IOException
    {  
    	Test work = new Test();
    	Thread t1 = new Thread(work);
    	Thread t2 = new Thread(work);
    	Thread t3 = new Thread(work);
    	Thread t4 = new Thread(work);
        
    	t1.start();
    	t2.start();
    	t3.start();
    	t4.start();
    }
}

class Test implements Runnable{

	Lock lock = new ReentrantLock();
	
	int i = 1;
	
	String targetUrl = "http://localhost:9008/order/payment/";
	
	public void run() {
		 
		while(i<=10000){
        	try {
        		CloseableHttpClient client = HttpClients.createDefault();
            	HttpGet get = new HttpGet(targetUrl + i);
				CloseableHttpResponse res = client.execute(get);
				
				System.out.println(i + "---" + res); 
				
				client.close();
		
				lock.lock();
				
				i++;
        	} catch (ClientProtocolException e) { 
				e.printStackTrace();
			} catch (IOException e) { 
				e.printStackTrace();
			}finally {
				lock.unlock();
			}
        }
	}
	
	
}


















 
