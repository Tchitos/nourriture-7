package com.android.nurriture.util;

/**
 * Created by Administrator on 2015/12/28.
 */
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public abstract class HttpUtil extends AsyncTask<Void, Void, String> {

    String controller = null;
    Map<String, String> parm = null;
    HttpMethod method = null;
    List<String> path_list = new ArrayList<String>();
    public static HttpClient client = new DefaultHttpClient();
    private static final int TIME_OUT = 10 * 1000;

    public HttpUtil(String controller, HttpMethod method) {
        this.controller = controller;
        this.method = method;
    }

    public HttpUtil(String controller, HttpMethod method,
                    Map<String, String> parm) {
        this.controller = controller;
        this.parm = parm;
        this.method = method;
    }

    public HttpUtil(String controller, HttpMethod method, List<String> path_list) {
        this.controller = controller;
        this.method = method;
        this.path_list = path_list;
    }

    @Override
    protected String doInBackground(Void... arg0) {
        String result = null;
        switch (method) {
            case POST:
                result = connectPost();
                break;
            case GET:
                result = connectGet();
                break;
            default:
                break;
        }
        return result;
    }

    protected String connectPost()
    {
        String url = Config.SERVER_URL + controller;
        HttpPost post = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        for (String key : parm.keySet()) {
            params.add(new BasicNameValuePair(key, parm.get(key)));
        }

        try {
            post.setEntity(new UrlEncodedFormEntity(params, Config.CHARSET));
            HttpResponse response = client.execute(post);

            String value = EntityUtils.toString(response.getEntity(),
                    Config.CHARSET);
            JSONObject json = new JSONObject();
            try{
                json.put("statusCode", response.getStatusLine().getStatusCode());
                json.put("value", value);
            }catch(Exception e){

            }
            return json.toString();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String connectGet()
    {
        String url = Config.SERVER_URL + controller;
        StringBuffer paramsStr = new StringBuffer();
        for (String key : parm.keySet()) {
            paramsStr.append(key).append("=").append(parm.get(key)).append("&");
        }
        url = url + "?" + paramsStr.toString();
        System.out.println("url:" + url);
        HttpPost post = new HttpPost(url);

        try {
            HttpResponse response = client.execute(post);

            String value = EntityUtils.toString(response.getEntity(),
                    Config.CHARSET);

            return value;

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

/*    protected String Upload_Picture() {
        String url = Config.SERVER_URL + controller;
        for (String path : path_list) {
            File file = new File(path);

            if (file != null) {
                String request = upload(file, url);
            }

        }

        FileUtils.deleteDir();
        return "success";
    }*/

    protected String upload(File file, String RequestURL) {
        int res=0;
        String result = null;
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型

        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", Config.CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="+ BOUNDARY);

            if (file != null) {
                /**
                 * 当文件不为空时执行上传
                 */
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名
                 */

                sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
                        + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="
                        + Config.CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                res = conn.getResponseCode();
                if (res == 200) {
                    Log.e("", "request success");
                    InputStream input = conn.getInputStream();
                    StringBuffer sb1 = new StringBuffer();
                    int ss;
                    while ((ss = input.read()) != -1) {
                        sb1.append((char) ss);
                    }
                    result = sb1.toString();
                    Log.e("", "result : " + result);
                } else {
                    Log.e("", "request error");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    protected String connectPathVariable_Post() {
        String url = Config.SERVER_URL + controller;
        if (parm != null) {
            for (String key : parm.keySet()) {
                url = url + parm.get(key) + "/";
            }
        }

        HttpPost post = new HttpPost(url);
        try {
            HttpResponse response = client.execute(post);

            String value = EntityUtils.toString(response.getEntity(),
                    Config.CHARSET);

            return value;

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String connectPathVariable_Get() {
        String url = Config.SERVER_URL + controller;
        if (parm != null) {
            for (String key : parm.keySet()) {
                url = url + parm.get(key) + "/";
            }
        }
        System.out.println("?????url:" + url);

        HttpGet post = new HttpGet(url);
        try {
            HttpResponse response = client.execute(post);

            String value = EntityUtils.toString(response.getEntity(),
                    Config.CHARSET);

            return value;

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String connectRequestParm_Post() {
        String url = Config.SERVER_URL + controller;
        HttpPost post = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        for (String key : parm.keySet()) {
            params.add(new BasicNameValuePair(key, parm.get(key)));
        }

        try {
            post.setEntity(new UrlEncodedFormEntity(params, Config.CHARSET));
            HttpResponse response = client.execute(post);

            String value = EntityUtils.toString(response.getEntity(),
                    Config.CHARSET);
            return value;

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String connectRequestParm_Get() {
        String url = Config.SERVER_URL + controller;
        StringBuffer paramsStr = new StringBuffer();
        for (String key : parm.keySet()) {
            paramsStr.append(key).append("=").append(parm.get(key)).append("&");
        }
        url = url + "?" + paramsStr.toString();
        System.out.println("url:" + url);
        HttpPost post = new HttpPost(url);

        try {
            HttpResponse response = client.execute(post);

            String value = EntityUtils.toString(response.getEntity(),
                    Config.CHARSET);

            return value;

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        getResult(result);
    }

    protected abstract void getResult(String result);
}

