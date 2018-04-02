package zstu.domain;


import zstu.utils.constants.ErrorCodesEnum;

public class CommonResponse extends BeautifulFormat {
    private Integer code;
    private String msg;
    public CommonResponse(){
        this(0);
    }
    public CommonResponse(Integer code){
        this.code = code;
    }

    public CommonResponse(Integer code, String msg){
             this.code = code;
             this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {

        if(code!=0) {
            if(msg!=null) {
                return msg;
            }

            for (ErrorCodesEnum errorCodesEnum: ErrorCodesEnum.values()){
                if (errorCodesEnum.getCode() == code.intValue()){
                    return errorCodesEnum.getMsg();
                }
            }
        }

        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
