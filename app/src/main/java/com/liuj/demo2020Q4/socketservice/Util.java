package com.liuj.demo2020Q4.socketservice;

import org.cybergarage.http.HTTP;
import org.cybergarage.util.Debug;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author liuj
 * @time 2020/12/30
 */
public class Util {
    /**
     * 双重循环获取终端中所有网络接口的所有IP地址，然后返回第一个遇到的非本地回环的IPv4地址
     *
     * @return 当前手机ip
     */
    public static String getIpAddressString() {
        try {
            // 获得所有网络接口
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface.getNetworkInterfaces(); enNetI
                    .hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                // 获得该接口下所有IP地址
                for (Enumeration<InetAddress> enumIpAddress = netI.getInetAddresses(); enumIpAddress
                        .hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddress.nextElement();
                    // 判断是否是IPV4和是否是非本地回环IP
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    static String readLine(BufferedInputStream in) {
        ByteArrayOutputStream lineBuf = new ByteArrayOutputStream();
        byte readBuf[] = new byte[1];

        try {
            int readLen = in.read(readBuf);
            while (0 < readLen) {
                if (readBuf[0] == HTTP.LF)
                    break;
                if (readBuf[0] != HTTP.CR)
                    lineBuf.write(readBuf[0]);
                readLen = in.read(readBuf);
            }
        } catch (InterruptedIOException e) {
            //Ignoring warning because it's a way to break the HTTP connecttion
            //TODO Create a new level of Logging and log the event
        } catch (IOException e) {
            Debug.warning(e);
        }
        return lineBuf.toString();
    }

}
