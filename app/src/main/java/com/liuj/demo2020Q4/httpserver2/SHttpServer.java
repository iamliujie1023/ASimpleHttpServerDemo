package com.liuj.demo2020Q4.httpserver2;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * @author liuj
 * @time 2020/12/31
 */
public class SHttpServer extends NanoHTTPD {

    private final String webRoot;

    public SHttpServer(String webRoot, int port){
        super(port);
        this.webRoot = webRoot;
    }

    @Override
    public Response serve(IHTTPSession session){
        Method method = session.getMethod();
        if(Method.POST.equals(method)){
            doPost(session);
        }
        return defaultResponse(session);
    }

    /**
     * 返回文件上传的静态页面
     * @param session
     * @return
     */
    private Response defaultResponse(IHTTPSession session){
        return NanoHTTPD.newFixedLengthResponse(DefaultHtml.HTML_STRING);
    }

    /**
     * POST方法先进行文件存储
     * @param session
     */
    private void doPost(IHTTPSession session){
        Map<String, String> params = session.getParms();
        Map<String, String> files = new HashMap<String, String>();
        try {
            session.parseBody(files);
            System.out.println(files.size());
            for(Map.Entry<String, String> entry : files.entrySet()){
                System.out.println(entry.getKey() + ":" + entry.getValue());
                String srcFilePath = entry.getValue();
                String destFilePath = this.webRoot + "/" + params.get(entry.getKey());
                FileExtraUtils.copyFile(srcFilePath, destFilePath, false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseException e) {
            e.printStackTrace();
        }
    }

}
