package com.chiwei.fastdfs.pool.common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * ClassName: WlanConfig <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2018年1月31日 下午4:50:03 <br/>
 * 
 * @author chiwei
 * @version
 * @since JDK 1.6
 */
public class SystemConfig {

    private static Properties prop = new Properties();

    /**
     * init system config file read
     */
    public static void init() {
        try {
            InputStream in = new FileInputStream("config/config.properties");
            prop.load(in);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 
     * @param key
     * @return
     */
    public static String get(String key) {
        String str = prop.getProperty(key);
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(str.trim())) {
            return "";
        }
        return str.trim();
    }

}
