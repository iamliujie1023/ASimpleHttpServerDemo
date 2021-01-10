package com.liuj.demo2020Q4.httpserver2;


import android.text.TextUtils;

import com.liuj.demo2020Q4.LogUtils;
import com.liuj.demo2020Q4.main.MainAct;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author liuj
 * @time 2020/12/31
 */
public class RealNanoHttpServer extends NanoHTTPD {

    private final String webRoot;

    public RealNanoHttpServer(String webRoot, int port) {
        super(port);
        this.webRoot = webRoot;
    }

    @Override
    public Response serve(IHTTPSession session) {
        Method method = session.getMethod();
        if(TextUtils.equals("/api/file/upload",session.getUri())){
            Map map = null;
            if (Method.POST.equals(method)) {
                map = doPost(session);
            }
            LogUtils.INSTANCE.i("response");
            if (map == null || map.size() == 0) {
                return super.serve(session);
            }
            return defaultResponse(session, map);
        }
        return super.serve(session);
    }

    private Response defaultResponse(IHTTPSession session, Map<String, String> map) {
        JSONObject jsonResponse = new JSONObject();
        try {
            jsonResponse.put("code", 200);
            JSONObject jsonData = new JSONObject();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                jsonData.put(entry.getKey(), entry.getValue());
            }
            jsonResponse.put("data", jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return NanoHTTPD.newFixedLengthResponse(jsonResponse.toString());
    }

    /**
     * POST方法先进行文件存储
     *
     * @param session
     */
    private Map doPost(IHTTPSession session) {
        Map<String, String> params = session.getParms();
        Map<String, String> files = new HashMap<String, String>();
        Map<String, String> savePaths = new HashMap<>();
        try {
            session.parseBody(files);
            System.out.println(files.size());
            for (Map.Entry<String, String> entry : files.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
                String srcFilePath = entry.getValue();
                String destFilePath = this.webRoot + "/simpleHttpServer_" + System.currentTimeMillis();
                FileUtils.copyFile(srcFilePath, destFilePath, false);
                savePaths.put(entry.getKey(), destFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseException e) {
            e.printStackTrace();
        }
        return savePaths;
    }

}
