package com.pvsoul.datacollection;

import com.pvsoul.datacollection.service.IotRouterService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Slf4j
public class DeviceConnector implements Runnable {

    private Socket socket;

    private InputStream inputStream;

    private OutputStream outputStream;

    private IotRouterService iotRouterService;

    public DeviceConnector(Socket socket, IotRouterService iotRouterService) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
        this.iotRouterService = iotRouterService;
    }

    @Override
    public void run() {
        //接收客户端数据
        StringBuilder sb = onMessage();
        //处理逻辑：xmlStringToEsb为处理结果
        //返回给客户端
        if (sb.length() == 0) {
            sb.append("received null data from " + socket.getInetAddress().getHostAddress());
        } else {
            sb.append(" received from " + socket.getInetAddress().getHostAddress());
        }
        iotRouterService.saveData(sb.toString());
        sendMessage(sb.toString());
        //socket.close();
    }

    private StringBuilder onMessage() {
        byte[] bytes = new byte[1024];
        int len;
        try {
            StringBuilder sb = new StringBuilder();
            while (inputStream.available() > 0) {
                len = inputStream.read(bytes);
                // 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len, "UTF-8"));
            }
            //此处，需要关闭服务器的输出流，但不能使用inputStream.close().
            //socket.shutdownInput();
            return sb;
        } catch (IOException e) {
            log.error("系统错误：", e);
        }
        return null;
    }

    private void sendMessage(String message) {
        try {
            //向客户端返回数据
            //首先需要计算得知消息的长度
            byte[] sendBytes = message.getBytes("UTF-8");
            //然后将消息的长度优先发送出去
            outputStream.write(sendBytes.length >> 8);
            outputStream.write(sendBytes.length);
            //然后将消息再次发送出去
            outputStream.write(sendBytes);
            outputStream.flush();
            //outputStream.close();
        } catch (IOException e) {
            log.error("发送数据错误：", e);
        }
    }
}
