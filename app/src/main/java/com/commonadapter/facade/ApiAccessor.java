package com.commonadapter.facade;


import com.commonadapter.common.util.Constants;
import com.commonadapter.common.util.LoginConfig;
import com.commonadapter.common.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/***
 * http请求类
 *
 * @author aeeiko
 */
public class ApiAccessor {
    private static final int READ_TIMEOUT = 30;
    private static final int CONNECTION_TIMEOUT = 30;
    private static final int WRITE_TIMEOUT = 60;
    private static MediaType Str = MediaType
            .parse("application/x-www-form-urlencoded;charset=UTF-8");
    private static String baseUrl = "";

    /***
     * 获取服务返回数据的方法（同步）
     *
     * @param action
     * @param map
     * @return
     * @throws Exception
     */
    public static String getHttpResponse(String action,
                                         HashMap<String, String> map) throws Exception {
        String params = "";
        String fixParams = "";

        fixParams += "tokenid=" + "zu_58edc4d019020_v1.0";
        fixParams += "&deviceid=" + LoginConfig.getInstance().getDeviceid();
        fixParams += "&plat=android";
        fixParams += "&appver=" + LoginConfig.getInstance().getAppver();//为了请求新版发现接口 写死版本号
        fixParams += "&machine=" + LoginConfig.getInstance().getMachine();
        fixParams += "&osver=" + LoginConfig.getInstance().getOsver();

        if (map != null) {
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                if (params.equals("")) {
                    params += entry.getKey() + "=" + entry.getValue();
                }
                params += "&" + entry.getKey() + "=" + entry.getValue();
            }
        }

        if (StringUtils.isEmpty(params)) {
            params = fixParams;
        } else {
            params = fixParams + "&" + params;
        }

        fixParams = Constants.secret + params;

        String sign = StringUtils.MD5(fixParams);
        sign = StringUtils.reverse1(sign);

        params += "&sign=" + sign;

        OkHttpClient client = createHttpClient();
        RequestBody requestBody = RequestBody.create(Str, params);
        Request.Builder okBuilder = new Request.Builder();
        okBuilder.url(Constants.BASE_URL + action);
        okBuilder.post(requestBody);
      //  okBuilder.cacheControl(CacheControl.FORCE_NETWORK);一直请求网络数据
        Request okRequest = okBuilder.build();
        Response response = client.newCall(okRequest).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        return new String(response.body().bytes(), "utf-8");
    }

    private static OkHttpClient createHttpClient() {
        OkHttpClient client = new OkHttpClient();
       // client.setConnectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
       // client.setReadTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
       // client.setWriteTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        return client;
    }
}
