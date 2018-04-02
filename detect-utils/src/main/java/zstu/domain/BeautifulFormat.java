package zstu.domain;

import com.alibaba.fastjson.JSONObject;


public abstract class BeautifulFormat {

    @Override
    public String toString(){
        return JSONObject.toJSONString(this);
    }
    
}
