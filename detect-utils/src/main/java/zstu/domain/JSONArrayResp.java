package zstu.domain;

import com.alibaba.fastjson.JSONArray;

/**
 * 返回JSON格式数组，形如{"code":0, "data":[]}
 *
 * @author Aning
 **/
public class JSONArrayResp extends CommonResponse {
    private JSONArray data;
    private Long total;

    public Long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }

    public JSONArrayResp() {
        super(0);
    }
    public JSONArrayResp(Integer code, JSONArray data) {
        super(code);
        if(data != null&&data.size()>0){
            this.data = data;
            this.total = (long)data.size();
        }else{
            this.total = (long)0;
            this.data = new JSONArray();
        }
    }

    public JSONArrayResp(Integer code){
        super(code);
    }

    public JSONArrayResp(Integer code, String msg) {
        super(code, msg);
    }
}
