package zstu.utils.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * User: Aning
 */
public class PropertyConfig {
    private Logger logger = LoggerFactory.getLogger(PropertyConfig.class);

    private Properties prop = new Properties();// 属性集合对象

    public PropertyConfig(String filePath) {
        logger.info("初始化属性文件："+filePath);
        InputStream fis = null;
        try {
            fis = this.getClass().getClassLoader().getResourceAsStream(filePath);// 属性文件输入流
            prop.load(fis);// 将属性文件流装载到Properties对象中
            logger.info("属性文件内容："+prop.toString());
            fis.close();// 关闭流
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();// 关闭流
                } catch (IOException e) {
                    logger.error(e.getMessage(),e);
                }
            }
        }

    }
    public PropertyConfig(String filePath, Class clazz) {
        logger.info("初始化属性文件："+filePath+" 类路径："+ clazz.toString());
        InputStream fis = null;
        try {
            fis = clazz.getClassLoader().getResourceAsStream(filePath);// 属性文件输入流
            prop.load(fis);// 将属性文件流装载到Properties对象中
            logger.info("属性文件内容："+prop.toString());
            fis.close();// 关闭流
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();// 关闭流
                } catch (IOException e) {
                    logger.error(e.getMessage(),e);
                }
            }
        }

    }

    public Set<Object> getKeys(){
        return prop.keySet();
    }

    public String getProperty(String key) {
        if (prop != null) {
            String value = prop.getProperty(key);
            logger.debug("读取属性："+key+"="+value);
            return value;
        } else {
            return null;
        }
    }
}
