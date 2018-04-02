package zstu.domain;

import com.alibaba.fastjson.JSONObject;
import zstu.utils.constants.ErrorCodesEnum;

/**
 * json对象
 *
 * @author Aning
 **/
public class JSONObjectResp extends CommonResponse {
    private JSONObject data;

    public JSONObjectResp(){
        super();
    }
    public JSONObject getData() {
        return data;
    }

    public JSONObjectResp(Integer code, String msg) {
        super(code, msg);
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public JSONObjectResp(Integer code, JSONObject data) {
        super(code);
        if(data==null||data.size()==0){
            this.setCode(ErrorCodesEnum.ERROR_CODE_DATA_NULL.getCode());
        }else{
            this.data = data;
        }
    }

    public JSONObjectResp(Integer code) {
        super(code);
    }

}
