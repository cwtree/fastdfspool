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


import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Type FastdfsClientFactory.java
 * @Desc
 * @author Administrator
 * @date 2018年12月13日 下午8:42:16
 * @version
 */
public class FastdfsClientFactory extends BasePooledObjectFactory<TrackerServer> {

	private static Logger logger = LoggerFactory.getLogger(FastdfsClientFactory.class);

	public FastdfsClientFactory(String propertiesFile) {
		try {
			ClientGlobal.initByProperties(propertiesFile);
		} catch (Exception e) {
			logger.error("FAST DFS 初始化配置异常", e);
		}
	}

	@Override
	public TrackerServer create() throws Exception {
		// TODO Auto-generated method stub
		// 让服务端对象获得连接
		return new TrackerClient().getConnection();
	}

	@Override
	public PooledObject<TrackerServer> wrap(TrackerServer obj) {
		// TODO Auto-generated method stub
		return new DefaultPooledObject<>(obj);
	}

	@Override
	public void destroyObject(PooledObject<TrackerServer> p) throws Exception {
		// TODO Auto-generated method stub
		if (p == null) {
			return;
		}
		TrackerServer ts = p.getObject();
		if (ts != null) {
			ts.close();
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