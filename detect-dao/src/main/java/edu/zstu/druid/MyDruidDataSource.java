package edu.zstu.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Created by liupeizhi on 2017/6/16.
 */

@Component("dataSource")
@Primary
@ConfigurationProperties(prefix = "spring.datasource")
@JsonIgnoreProperties({ "connection", "statData" ,"pooledConnection","statValueAndReset","sqlStatMap","parentLogger","compositeData"})

public class MyDruidDataSource extends DruidDataSource {

}
