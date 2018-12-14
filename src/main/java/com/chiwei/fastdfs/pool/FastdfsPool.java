/*
 * Project: phoenix
 * 
 * File Created at 2018年12月13日
 * 
 * Copyright 2016 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
package com.chiwei.fastdfs.pool;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Type FastdfsPool.java
 * @Desc
 * @author chiwei
 * @date 2018年12月13日 下午8:54:35
 * @version
 */
public class FastdfsPool {

	private static Logger logger = LoggerFactory.getLogger(FastdfsPool.class);

	private static GenericObjectPool<TrackerServer> pool;

	private static boolean inited = false;

	/**
	 * 
	 */
	public static void init() {
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnCreate(true);
		poolConfig.setTestOnReturn(true);
		poolConfig.setMaxTotal(16);
		poolConfig.setMaxIdle(4);
		poolConfig.setMinIdle(2);
		poolConfig.setMaxWaitMillis(10L);
		poolConfig.setTestWhileIdle(true);
		poolConfig.setTimeBetweenEvictionRunsMillis(30000L);
		poolConfig.setMinEvictableIdleTimeMillis(600000L);
		while (!inited) {
			try {
				pool = new GenericObjectPool<TrackerServer>(new FastdfsClientFactory("config/fastdfs.properties"),
						poolConfig);
				inited = true;
				logger.info("FAST DFS 连接池初始化成功");
			} catch (Exception e) {
				logger.error("FAST DFS 连接池初始化出错", e);
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public static TrackerServer getResource() {
		try {
			return pool.borrowObject();
		} catch (Exception e) {
			logger.error("连接池中获取track server对象出错", e);
			return null;
		}
	}

	/**
	 * 
	 * @param ts
	 */
	public static void returnResource(TrackerServer ts) {
		pool.returnObject(ts);
	}

	/**
	 * 上传文件，参数为本地文件的完整路径
	 * 
	 * @param fileFullPath
	 * @return
	 */
	public static String upload(String fileFullPath) {
		TrackerServer ts = null;
		try {
			ts = pool.borrowObject();
			StorageClient storageClient = new StorageClient(ts, null);
			String[] str = storageClient.upload_file(fileFullPath, null, null);
			return str[0] + "/" + str[1];
		} catch (Exception e) {
			logger.error("FAST DFS 文件上传异常", e);
			return null;
		} finally {
			returnResource(ts);
		}
	}

	/**
	 * 上传文件，参数为文件输入流
	 * 
	 * @param bb
	 * @param fileExt
	 * @return
	 */
	public static String upload(byte[] bb, String fileExt) {
		TrackerServer ts = null;
		try {
			ts = pool.borrowObject();
			StorageClient storageClient = new StorageClient(ts, null);
			String[] str = storageClient.upload_file(bb, fileExt, null);
			return str[0] + "/" + str[1];
		} catch (Exception e) {
			logger.error("FAST DFS 文件上传异常", e);
			return null;
		} finally {
			returnResource(ts);
		}
	}

	/**
	 * 
	 * @param dfsFileId group1/M00/00/00/rBxIUVwSD4GANuLrAAAhTyIMjcQ308.jpg
	 *                  删除group前面的/
	 * @return 返回0表示成功，其它表示失败
	 */
	public static int delete(String dfsFileId) {
		TrackerServer ts = null;
		try {
			ts = pool.borrowObject();
			StorageClient1 storageClient = new StorageClient1(ts, null);
			return storageClient.delete_file1(dfsFileId);
		} catch (Exception e) {
			logger.error("FAST DFS 文件上传异常", e);
			return -1;
		} finally {
			returnResource(ts);
		}
	}

}

/**
 * Revision history
 * -------------------------------------------------------------------------
 * 
 * Date Author Note
 * -------------------------------------------------------------------------
 * 2018年12月13日 Administrator create
 */