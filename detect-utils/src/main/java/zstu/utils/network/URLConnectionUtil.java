package zstu.utils.network;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class URLConnectionUtil {
    private final static Logger logger = LoggerFactory.getLogger(URLConnectionUtil.class);

    private static final String SERVLET_POST = "POST";
    private static final String SERVLET_GET = "GET";
    private static final String SERVLET_DELETE = "DELETE";
    private static final String SERVLET_PUT = "PUT";



    /**
     * 对给定字符进行 URL 编码GB2312.
     *
     * @param src String
     * @return String
     */
    public static String urlEncode(String src) {
        return urlEncode(src, "GB2312");
    }

    /**
     * 对给定字符进行 URL 解码GB2312
     *
     * @param value 解码前的字符串
     * @return 解码后的字符串
     */
    public static String urlDecode(String value) {
        return urlDecode(value, "GB2312");
    }

    /**
     * 对给定字符进行 URL 编码.
     *
     * @param src   String
     * @param coder 字符编码格式（GB2312/GBK）
     * @return String
     */
    public static String urlEncode(String src, String coder) {
        try {
            src = URLEncoder.encode(src, coder);

            return src;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return src;
    }

    /**
     * 对给定字符进行 URL 解码
     *
     * @param value 解码前的字符串
     * @param coder 字符编码格式（GB2312/GBK）
     * @return 解码后的字符串
     */
    public static String urlDecode(String value, String coder) {
        try {
            return java.net.URLDecoder.decode(value, coder);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return value;
    }



    public static JSONObject requestJSONObject(String url, Map headers, String params, String method) {

        return JSONObject.parseObject(request(url,headers,params,method));
    }

    public static JSONArray requestJSONArray(String url, Map headers, String params, String method) {
        return JSONArray.parseArray(request(url,headers,params,method));
    }


    public static String request(String url, Map headers, String params, String method) {
        logger.info("Start 请求URL:" + url + " headers:" + headers + " params:" + params + " method:" + method);
        String result = null;
        try {
            if (SERVLET_GET.equalsIgnoreCase(method)) {
                result = doGet(url, headers, null);
            }
            if (SERVLET_POST.equalsIgnoreCase(method)) {
                result = doPost(url, headers, params);
            }
            if (SERVLET_DELETE.equalsIgnoreCase(method)) {
                result = doDelete(url, headers);
            }
            if (SERVLET_PUT.equalsIgnoreCase(method)) {
                result = doPut(url, headers, params);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("End 请求URL:" + url + " headers:" + headers + " params:" + params + " method:" + method + " result:" + result);
        return result;

    }



    private static String prepareParam(Map<String, Object> paramMap) {
        StringBuffer sb = new StringBuffer();
        if (paramMap == null || paramMap.isEmpty()) {
            return "";
        } else {
            for (String key : paramMap.keySet()) {
                String value = paramMap.get(key).toString();
                if (sb.length() < 1) {
                    sb.append(key).append("=").append(value);
                } else {
                    sb.append("&").append(key).append("=").append(value);
                }
            }
            return sb.toString();
        }
    }

    public static String doPost(String urlStr, Map<String, String> headers, Map<String, Object> params) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
        if (headers != null) {
            for (String key : headers.keySet()) {
                conn.setRequestProperty(key, headers.get(key));
            }
        }

        StringBuilder sb = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                try {
                    sb.append(entry.getKey())
                            .append("=")
                            .append(URLEncoder.encode(String.valueOf(entry.getValue()), "utf-8"))
                            .append("&");
                } catch (UnsupportedEncodingException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            // 删掉最后一个 & 字符
            sb.deleteCharAt(sb.length() - 1);
        }
        OutputStream os = conn.getOutputStream();
        os.write(sb.toString().getBytes("utf-8"));
        os.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        String result = "";
        while ((line = br.readLine()) != null) {
            result += "" + line;
        }
        br.close();
        return result;
    }
    public static JSONObject doPostJSON(String urlStr, Map<String, String> headers, Map<String, Object> params) throws Exception{
        return JSONObject.parseObject(doPost(urlStr,headers,params));
    }

    public static String doPost(String urlStr, Map<String, String> headers, String params) throws Exception {
        logger.info("执行请求："+urlStr+" headers:"+headers+" params:"+params);
        long start = System.currentTimeMillis();
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(SERVLET_POST);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(3000);
        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        if (headers != null) {
            for (String key : headers.keySet()) {
                conn.setRequestProperty(key, headers.get(key));
            }
        }
        OutputStream os = conn.getOutputStream();
        os.write(params.toString().getBytes("utf-8"));
        os.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        String result = "";
        while ((line = br.readLine()) != null) {
            result += "" + line;
        }
        //haier返回token
        String accessToken = conn.getHeaderField("accessToken");
        if (accessToken != null && result.endsWith("}")){
            result = result.substring(0,result.length() - 1) + ",\"accessToken\":\"" + accessToken + "\"}";
        }
        br.close();
        logger.info("请求结果："+result+" 耗时："+(System.currentTimeMillis()-start)+"ms");
        return result;

    }
    public static JSONObject doPostJSON(String urlStr, Map<String, String> headers, String params) throws Exception{
        return JSONObject.parseObject(doPost(urlStr,headers,params));
    }

    public static String doGet(String urlStr, Map<String, String> headers, Map<String, Object> paramMap) throws Exception {
        String paramStr = prepareParam(paramMap);
        if (paramStr == null || paramStr.trim().length() < 1) {

        } else {
            urlStr += "?" + paramStr;
        }
        System.out.println(urlStr);
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(SERVLET_GET);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(3000);
        conn.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
        if (headers != null) {
            for (String key : headers.keySet()) {
                conn.setRequestProperty(key, headers.get(key));
            }
        }

        conn.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        String result = "";
        while ((line = br.readLine()) != null) {
            result += "" + line;
        }
        br.close();
        return result;
    }
    public static JSONObject doGetJSON(String urlStr, Map<String, String> headers, Map<String, Object> paramMap) throws Exception {
        return JSONObject.parseObject(doGet(urlStr,headers,paramMap));
    }


    public static String doPut(String urlStr, Map<String, String> headers, Map<String, Object> paramMap) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(SERVLET_PUT);
        String paramStr = prepareParam(paramMap);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(3000);
        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        if (headers != null) {
            for (String key : headers.keySet()) {
                conn.setRequestProperty(key, headers.get(key));
            }
        }
        OutputStream os = conn.getOutputStream();
        os.write(paramStr.toString().getBytes("utf-8"));
        os.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        String result = "";
        while ((line = br.readLine()) != null) {
            result += "" + line;
        }
        br.close();
        return result;

    }
    public static JSONObject doPutJSON(String urlStr, Map<String, String> headers, Map<String, Object> paramMap) throws Exception {
        return JSONObject.parseObject(doPut(urlStr,headers,paramMap));
    }

    public static String doPut(String urlStr, Map<String, String> headers, String params) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(SERVLET_PUT);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(3000);
        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        if (headers != null) {
            for (String key : headers.keySet()) {
                conn.setRequestProperty(key, headers.get(key));
            }
        }
        OutputStream os = conn.getOutputStream();
        os.write(params.toString().getBytes("utf-8"));
        os.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        String result = "";
        while ((line = br.readLine()) != null) {
            result += "" + line;
        }

        br.close();
        return result;

    }

    public static JSONObject doPutJSON(String urlStr, Map<String, String> headers, String params) throws Exception {
        return JSONObject.parseObject(doPut(urlStr,headers,params));
    }



    public static String doDelete(String urlStr, Map<String, String> headers) throws Exception {

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod(SERVLET_DELETE);
        conn.setConnectTimeout(3000);
        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        if (headers != null) {
            for (String key : headers.keySet()) {
                conn.setRequestProperty(key, headers.get(key));
            }
        }
        //屏蔽掉的代码是错误的，java.net.ProtocolException: HTTP method DELETE doesn't support output
/*		OutputStream os = conn.getOutputStream();
        os.write(paramStr.toString().getBytes("utf-8"));
		os.close();  */

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        String result = "";
        while ((line = br.readLine()) != null) {
            result += "" + line;
        }
        br.close();
        return result;
    }
    public static JSONObject doDeleteJSON(String urlStr, Map<String, String> headers) throws Exception{
        return JSONObject.parseObject(doDelete(urlStr,headers));
    }





    /**
     * 表单格式提交数据
     * @param url 请求url
     * @param headers 头部数据
     * @param params 参数
     * @param method 暂时只提供POST方式
     * @param contantType  Form格式提交数据
     * @return
     */
    public static String request(String url, Map headers, Map params, String method, String contantType) {
        logger.info("Start 请求URL:" + url + " headers:" + headers + " params:" + params + " method:" + method + " contantType:" + contantType);
        String result = "";
        try {
            if (SERVLET_POST.equalsIgnoreCase(method)) {
                result = doPost(url, headers, params);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("End 请求URL:" + url + " headers:" + headers + " params:" + params + " method:" + method + " result:" + result);
        return result;
    }


    public static String sendPost(String urlStr, Map<String, String> headers, String params, Integer timeOut) throws Exception {
        logger.info("HttpRequest:{} headers:{} params:{}", new Object[]{urlStr, headers, params});
        long start = System.currentTimeMillis();
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(3000);
        if (timeOut != null){
            conn.setReadTimeout(timeOut);
        }
        conn.setRequestProperty("Content-type", "application/json");
        if (headers != null) {
            for (String key : headers.keySet()) {
                conn.setRequestProperty(key, headers.get(key));
            }
        }

        OutputStream os = conn.getOutputStream();
        os.write(params.toString().getBytes("utf-8"));
        os.close();

        if (conn.getResponseCode() == 400){
            conn.disconnect();
            return null;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line;
        String result;
        for(result = ""; (line = br.readLine()) != null; result = result + "" + line) {
            ;
        }
        br.close();
        conn.disconnect();
        logger.info("HttpRequest:{} headers:{} params:{} resp:{} cost:{}ms", new Object[]{urlStr, headers, params, result.length() > 1000 ? result.substring(0, 1000) : result, System.currentTimeMillis() - start});
        return result;
    }

	/**
	 *  执行POST请求，返回String
	 */
	public static String sendPost(String urlStr, Map<String, String> headers, String params) throws Exception {
        return sendPost(urlStr, headers, params, 30000);
	}


    public static void main(String[] args) throws Exception {


	    //select u.uid,u.username,u.telephone,u.email,u.nickname,uep.ext_prop_val,ud.device_id,ud.device_name from user u,user_ext_prop uep,user_device ud where platform_id=25 and u.uid=uep.uid and u.uid=ud.uid;


        Files.lines(Paths.get("D:\\data.txt"), StandardCharsets.UTF_8).forEach(line->{
            String[] keys = line.split("\\t");
            String did = keys[0];
            String username = keys[1];
            String urlStr = "http://api.machtalk.net:10086/v1.0/device/"+did+"/datapoints";
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("APIKey","0de921259c1ab617422f580da89e7c9c");
            System.out.println("家庭成员："+username);
            try {
                JSONObject jsonObject =  doPostJSON(urlStr,headers,"{\"pageSize\":50000}");
                if(jsonObject.containsKey("data")){
                    JSONArray data = jsonObject.getJSONArray("data");
                    for(int i=0;i<data.size();i++){
                        JSONObject d = data.getJSONObject(i);
                        int device_value_id = d.getInteger("device_value_id");
//                        if(device_value_id == 8){
//                            //心电：
//                            JSONArray j1 = d.getJSONArray("data");
//                            if(j1!=null&&j1.size()>0){
//                                for(int j=0;j<j1.size();j++){
//                                    JSONObject xindian = j1.getJSONObject(j);
//                                    String key = xindian.getString("key");
//                                    String fileId = xindian.getString("value");
//
////                                    System.out.println("心电数据文件ID\t"+key+"\t"+fileId);
//                                }
//                            }
//                        }
                        if(device_value_id == 12){
                            //心率：

                            JSONArray j1 = d.getJSONArray("data");
                            if(j1!=null&&j1.size()>0){
                                for(int j=0;j<j1.size();j++){
                                    JSONObject xindian = j1.getJSONObject(j);
                                    String key = xindian.getString("key");

                                    JSONObject value = JSONObject.parseObject(xindian.getString("value"));


                                    String average_hr = value.getString("average_hr");
                                    System.out.println(did+"\t"+key+"\t"+average_hr);
                                }
                            }

                        }
//                        if(device_value_id == 11){
//                            //心电异常：
//                            JSONArray j1 = d.getJSONArray("data");
//                            if(j1!=null&&j1.size()>0){
//                                for(int j=0;j<j1.size();j++){
//                                    JSONObject xindian = j1.getJSONObject(j);
//                                    String key = xindian.getString("key");
//
//                                    JSONArray value = JSONObject.parseArray(xindian.getString("value"));
//                                    if(value!=null&&value.size()>0){
//                                        for(int k=0;k<value.size();k++)
//                                        {
//                                            JSONObject jsonObject1 = value.getJSONObject(k);
//                                            String type = jsonObject1.getString("type");
//                                            String name = jsonObject1.getString("name");
//                                            String position = jsonObject1.getString("position");
//                                            String time = jsonObject1.getString("time");
//                                            System.out.println("心电异常=时间\t"+key+"\t疾病类型\t"+type+"\t疾病名字\t"+name+"\t疾病位置\t"+position+"\t疾病时间\t"+time);
//                                        }
//                                    }
//
//
//
//                                }
//                            }
//                        }
//                        if(device_value_id == 15){
//                            //血压：
//                            JSONArray j1 = d.getJSONArray("data");
//                            if(j1!=null&&j1.size()>0){
//                                for(int j=0;j<j1.size();j++){
//                                    JSONObject xindian = j1.getJSONObject(j);
//                                    String key = xindian.getString("key");
//
//                                    JSONObject value = JSONObject.parseObject(xindian.getString("value"));
//
//
//                                    String date = value.getString("date");
//                                    String time = value.getString("time");
//                                    String medicine_state = value.getString("medicine_state");
//                                    String hypotension_value = value.getString("hypotension_value");
//                                    String hypertension_value = value.getString("hypertension_value");
//                                    System.out.println("血压=时间\t"+key+"\t测量日期\t"+date+"\t测量时间段\t"+time+"\t服药状态\t"+medicine_state+"\t低血压值\t"+hypotension_value+"\t高血压值\t"+hypertension_value);
//                                }
//                            }
//                        }
//                        if(device_value_id == 16){
//                            //血糖：
//                            JSONArray j1 = d.getJSONArray("data");
//                            if(j1!=null&&j1.size()>0){
//                                for(int j=0;j<j1.size();j++){
//                                    JSONObject xindian = j1.getJSONObject(j);
//                                    String key = xindian.getString("key");
//
//                                    JSONObject value = JSONObject.parseObject(xindian.getString("value"));
//
//
//                                    String date = value.getString("date");
//                                    String time = value.getString("time");
//                                    String station = value.getString("station");
//                                    String blood_value = value.getString("blood_value");
//                                    System.out.println("血压=时间\t"+key+"\t测量日期\t"+date+"\t测量时间段\t"+time+"\t餐饮状态\t"+station+"\t血糖值\t"+blood_value);
//                                }
//                            }
//                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        });

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", "张三");
        map.put("password", "88888");
//		URLConnectionUtil.doGet(urlStr, map);
//		URLConnectionUtil.doPost(urlStr, map);
//		URLConnectionUtil.doPut(urlStr, map);
//        URLConnectionUtil.doDelete(urlStr,null, map);

    }

//    心电：
//    device_value_id=8 device_type_id=4
//    字段名称 ecgFilePath
//
//    心率：
//    device_value_id=12 device_type_id=4
//    字段名称：average_hr（平均心率）
//
//    心电异常：
//    device_value_id=11 device_type_id=4
//    字段名称：
//    type(疾病类型)
//    name(疾病名字)
//    position(疾病位置)
//    time（疾病时间）
//
//    血压：
//    device_value_id=15 device_type_id=4
//    字段名称：
//    date(测量日期)
//    time(测量时间段)
//    medicine_state(服药状态，0 未服用，1 已服用)
//    hypotension_value(低血压值）
//            hypertension_value(高血压值）
//
//
//            血糖：
//            device_value_id=16 device_type_id=4
//            字段名称：
//            date(测量日期)
//    time(测量时间段)
//    medicine_state(服药状态，0 未服用，1 已服用)
//    hypotension_value(低血压值）
//            hypertension_value(高血压值）


}
