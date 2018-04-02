package zstu.utils.common;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class BaiduAPIConverter {
    /**
     * @param from 源坐标体系，0：GPS,2:谷歌
     * @param to   目标坐标体系 4：百度
     * @param x    精度
     * @param y    维度
     * @throws IOException
     */
    public static Map<String, String> convertLocation(int from, int to, String x, String y) throws IOException {
//        try {
        URL url = new URL("http://api.map.baidu.com/ag/coord/convert?from=0&to=4&x=" + x + "&y=" + y);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
        // remember to clean up
        out.flush();
        out.close();
        // 一旦发送成功，用以下方法就可以得到服务器的回应：
        String sCurrentLine, sTotalString;
        sCurrentLine = sTotalString = "";
        InputStream lUrlStream;
        lUrlStream = connection.getInputStream();
        BufferedReader lReader = new BufferedReader(new InputStreamReader(lUrlStream));
        while ((sCurrentLine = lReader.readLine()) != null) {
            if (!"".equals(sCurrentLine)) {
                sTotalString += sCurrentLine;
            }
        }
        sTotalString = sTotalString.substring(1, sTotalString.length() - 1);
        String[] results = sTotalString.split("\\,");

        Map<String, String> jsonObject = new HashMap<String, String>();
        if (results.length == 3) {
            if ("0".equals(results[0].split("\\:")[1])) {
                String mapX = results[1].split("\\:")[1];
                String mapY = results[2].split("\\:")[1];
                mapX = mapX.substring(1, mapX.length() - 1);
                mapY = mapY.substring(1, mapY.length() - 1);
                mapX = new String(Base64.decode(mapX));
                mapY = new String(Base64.decode(mapY));
                System.out.println("\t" + mapX + "\t" + mapY);
                jsonObject.put("lng", mapX);
                jsonObject.put("lat", mapY);
            }
        }
//            sleep(10000);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        return jsonObject;
    }

    /**
     * 接口参见 http://developer.baidu.com/map/changeposition.htm
     *
     * @param coords 格式：经度,纬度;经度,纬度…   限制：最多支持100个
     * @param from   源坐标类型 取值为如下：
     *               1：GPS设备获取的角度坐标;
     *               2：GPS获取的米制坐标、sogou地图所用坐标;
     *               3：google地图、soso地图、aliyun地图、mapabc地图和amap地图所用坐标
     *               4：3中列表地图坐标对应的米制坐标
     *               5：百度地图采用的经纬度坐标
     *               6：百度地图采用的米制坐标
     *               7：mapbar地图坐标;
     *               8：51地图坐标
     * @param to     目的坐标类型  有两种可供选择：5、6。
     *               5：bd09ll(百度经纬度坐标),
     *               6：bd09mc(百度米制经纬度坐标);
     * @return {"status":0,"result":[{"x":114.23075601241,"y":29.579081629834},{"x":114.23075520112,"y":29.579081890341}]}
     * @throws IOException
     */
    public static JSONObject convertCoords(String coords, int from, int to) throws IOException {
        JSONObject result = null;
        String ak = "53120e259360043c0e350676c02eba60";
        String urlStr = String.format("http://api.map.baidu.com/geoconv/v1/?coords=%s&from=%d&to=%d&ak=%s", coords, from, to, ak);
        HttpURLConnection connection = null;
        BufferedInputStream inputStream = null;
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
            out.flush();
            out.close();
            inputStream = new BufferedInputStream(connection.getInputStream());
            int n = -1;
            byte[] buffer = new byte[1024];
            StringBuilder sb = new StringBuilder();
            while ((n = inputStream.read(buffer, 0, buffer.length)) != -1) {
                String line = new String(buffer, 0, n, "UTF-8");
                sb.append(line);
            }
            result = JSONObject.parseObject(sb.toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return result;
    }


    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        System.out.println(convertCoords("114.21892734521,29.575429778924", 3, 5));
//        testPost("116.59470133", "35.4378085");
//        System.out.println("ok");

//        HttpRequestProxy hrp = new HttpRequestProxy();
//        String url="http://api.machtalk.net:10086/";
//        url+="v1.0/device/" +
//                "6757fe9defd34dfb989e65c4612b69c3/1/1/datapoints/add";
//
//        Map<String,String> headers = new HashMap<String, String>();
//        headers.put("apikey","5ac5c823b3914ca8a71c866e33904775");
//
//
//        while(true){
//        try {
//            Map<String,String> data = new HashMap<String, String>();
//            JSONObject params = new JSONObject();
//            Random random = new Random(100);
//            params.put("value",random.nextDouble()*100);
//
//            data.put("params",params.toJSONString());
//            String resp = hrp.doRequest(url,data,headers,"UTF-8","POST");
//            System.out.println(resp);
//            Thread.sleep(500);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        }
//       System.out.println(convertLocation(0,0,"116.31813755","39.98115526666666"));


//        System.out.println(convertGPS(3958.869316));
        String a = "tcp        0      0 :::7777                     :::*                        LISTEN      26841/java";
        String[] b = a.split("\\s+");
        String arg = b[b.length - 1];

        System.out.println(arg.split("\\/")[0]);


    }


    public static Double convertGPS(Double a) {
        Double b = a / 100;
        int c = b.intValue(); //度
        Double d = new Double(a - c * 100);
        double h = d / 60;
        return c + h;
    }
}