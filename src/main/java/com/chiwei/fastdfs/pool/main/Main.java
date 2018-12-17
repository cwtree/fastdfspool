package com.chiwei.fastdfs.pool.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chiwei.fastdfs.pool.FastdfsPool;
import com.chiwei.fastdfs.pool.common.LogBackConfigLoader;
import com.chiwei.fastdfs.pool.common.SystemConfig;


/**
 * @Type Main.java
 * @Desc 
 * @author Administrator
 * @date 2018年12月14日 上午11:19:18
 * @version 
 */
public class Main {

	private static Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			LogBackConfigLoader.load("config/logback.xml");
		} catch (Exception e) {
			logger.error("日志加载错误", e);
		}
		SystemConfig.init();
		FastdfsPool.init();
		String res = FastdfsPool.upload("e:\\1.png");
		System.out.println(res);
	}

}


/**
 * Revision history
 * -------------------------------------------------------------------------
 * 
 * Date Author Note
 * -------------------------------------------------------------------------
 * 2018年12月14日 Administrator create
 */
