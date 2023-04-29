package com.hunfeng.money.service.impl;



import com.alibaba.druid.sql.visitor.functions.Char;
import com.hunfeng.money.common.ConnUtil;
import com.hunfeng.money.common.DemoException;
import com.hunfeng.money.common.TokenHolder;
import com.hunfeng.money.entity.Bill;
import com.hunfeng.money.service.VoiceService;
import com.hunfeng.money.utils.Base64Util;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class VoiceServiceImpl implements VoiceService {

    private final boolean METHOD_RAW = true; // 默认以json方式上传音频文件

    //  填写网页上申请的appkey 如 $apiKey="g8eBUMSokVB1BHGmgxxxxxx"
    private final String APP_KEY = "yItpIXIKtKwmQcZKEqoqcBHO";

    // 填写网页上申请的APP SECRET 如 $SECRET_KEY="94dc99566550d87f8fa8ece112xxxxx"
    private final String SECRET_KEY = "HZkGp11fOs6d0x9IOUfe7UXcegIrHsY6";

    // 需要识别的文件
    private String FILENAME = ".pcm";

    // 文件格式, 支持pcm/wav/amr 格式，极速版额外支持m4a 格式
    private final String FORMAT = FILENAME.substring(FILENAME.length() - 3).toLowerCase();


    private String CUID = "1234567JAVA";

    // 采样率固定值
    private final int RATE = 16000;

    private String URL;

    private int DEV_PID;

    //private int LM_ID;//测试自训练平台需要打开此注释

    private String SCOPE;

    //  普通版 参数
    {
        URL = "http://vop.baidu.com/server_api"; // 可以改为https
        //  1537 表示识别普通话，使用输入法模型。 其它语种参见文档
        DEV_PID = 1537;
        SCOPE = "audio_voice_assistant_get";
    }

    public Map<String, Object> translate(InputStream is) throws DemoException, IOException {
        TokenHolder holder = new TokenHolder(APP_KEY, SECRET_KEY, SCOPE);
        //调用百度智能云接口，获取可以访问语音识别接口的识别标志：token
        holder.resfresh();
        String token = holder.getToken();
        String result = null;
        if (METHOD_RAW) {   //判断语音文件上传格式,调用语音识别接口
            result = runRawPostMethod(token, is);
        } else {
            result = runJsonPostMethod(token,is);
        }
        System.out.println("json格式的结果：" + result);
        JSONObject json = new JSONObject(result);
        if (!json.has("result")) {
            // 返回没有access_token字段
            throw new DemoException("result not obtained, " + result);
        }
        JSONArray res = json.getJSONArray("result");
        String str = res.getString(0);
        //添加预设定关键字，以便识别收入或支出类型
        HashMap<String, Integer> map = new HashMap<>();
        map.put("花费", -1);
        map.put("花了", -1);
        map.put("收入", 1);
        map.put("进账", 1);
        String details = null;
        Double money = null;
        Map<String, Object> resMap = new HashMap<>();
        //对每个关键字进行遍历，判断是字符中包含哪个关键字
        for (String s : map.keySet()){
            if (str.indexOf(s) != -1){
                //若找到关键字，将字符串以关键字分成两部分，
                //前一部分为详情，后一部分为金额
                int i = str.indexOf(s);
                details = str.substring(0, i);
                money = getDoubleValue(str, i + 2) * map.get(s);
                resMap.put("money", money);
                resMap.put("details", details);
                break;
            }
        }
        resMap.put("result", str);
        System.out.println("解析后的result:"+str);
        return resMap;
    }

    private Map<Character, Integer> wordMap = new HashMap<Character, Integer>(){{
        put('一', 1);
        put('二', 2);
        put('三', 3);
        put('四', 4);
        put('五', 5);
        put('六', 6);
        put('七', 7);
        put('八', 8);
        put('九', 9);
        put('十', 10);
    }};

    private double getDoubleValue(String str, int startIdx) {
        double d = 0;
        if(str!=null && str.length()!=0)
        {
            StringBuffer bf = new StringBuffer();

            char[] chars = str.toCharArray();
            for(int i=startIdx;i<chars.length;i++)
            {
                char c = chars[i];
                if(Character.isDigit(c))
                {
                    bf.append(c);
                } else if (wordMap.containsKey(c)){
                    bf.append(wordMap.get(c));
                    break;
                } else if(c=='.') {
                    if(bf.length()==0) continue;
                    else if(bf.indexOf(".")!=-1) break;
                    else bf.append(c);
                } else {
                    if(bf.length()!=0) break;
                }
            }
            try {
                d = Double.parseDouble(bf.toString());
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return d;
    }

    public String runJsonPostMethod(String token, InputStream is) throws DemoException, IOException {

        byte[] content = getFileContent(is);
        String speech = base64Encode(content);

        JSONObject params = new JSONObject();
        params.put("dev_pid", DEV_PID);
        //params.put("lm_id",LM_ID);//测试自训练平台需要打开注释
        params.put("format", FORMAT);
        params.put("rate", RATE);
        params.put("token", token);
        params.put("cuid", CUID);
        params.put("channel", "1");
        params.put("len", content.length);
        params.put("speech", speech);

        // System.out.println(params.toString());
        HttpURLConnection conn = (HttpURLConnection) new URL(URL).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setDoOutput(true);
        conn.getOutputStream().write(params.toString().getBytes());
        conn.getOutputStream().close();
        String result = ConnUtil.getResponseString(conn);


        params.put("speech", "base64Encode(getFileContent(FILENAME))");
        System.out.println("url is : " + URL);
        System.out.println("params is :" + params.toString());


        return result;
    }

    private String runRawPostMethod(String token, InputStream is) throws IOException, DemoException {
        String url2 = URL + "?cuid=" + ConnUtil.urlEncode(CUID) + "&dev_pid=" + DEV_PID + "&token=" + token;
        //测试自训练平台需要打开以下信息
        //String url2 = URL + "?cuid=" + ConnUtil.urlEncode(CUID) + "&dev_pid=" + DEV_PID + "&lm_id="+ LM_ID + "&token=" + token;
        String contentTypeStr = "audio/" + FORMAT + "; rate=" + RATE;
        //System.out.println(url2);
        byte[] content = getFileContent(is);
        HttpURLConnection conn = (HttpURLConnection) new URL(url2).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Content-Type", contentTypeStr);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.getOutputStream().write(content);
        conn.getOutputStream().close();
        System.out.println("url is " + url2);
        System.out.println("header is  " + "Content-Type :" + contentTypeStr);
        String result = ConnUtil.getResponseString(conn);
        return result;
    }

    private byte[] getFileContent(InputStream is) throws DemoException, IOException {
        if (is == null) {
            System.err.println("文件不存在或者不可读");
            throw new DemoException("file cannot read");
        }
        try {
            return ConnUtil.getInputStreamContent(is);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private String base64Encode(byte[] content) {
        /**
         Base64.Encoder encoder = Base64.getEncoder(); // JDK 1.8  推荐方法
         String str = encoder.encodeToString(content);
         **/

        char[] chars = Base64Util.encode(content); // 1.7 及以下，不推荐，请自行跟换相关库
        String str = new String(chars);

        return str;
    }
}
