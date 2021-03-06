package com.lh.common.config.webSocket;

import com.alibaba.fastjson.JSONObject;
import com.lh.modules.remind.entity.RemindMessage;
import com.lh.modules.remind.entity.RemindMessageReceive;
import com.lh.modules.remind.service.RemindMessageReceiveService;
import com.lh.modules.remind.service.RemindMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 功能描述：websocket 操作类
 * <p>版权所有：</p>
 * 未经本人许可，不得以任何方式复制或使用本程序任何部分
 *
 * @Company: 紫色年华
 * @Author: xieyc
 * @Datetime: 2019-10-08
 * @Version: 1.0.0
 */
@Component
@Slf4j
@ServerEndpoint("/websocket/{userId}")/*此注解相当于设置访问URL*/
public class WebSocket {

    @Autowired
    private RemindMessageService remindMessageService;


    /*与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;

    /*用户ID*/
    private String userId="";

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象
    private static CopyOnWriteArraySet<WebSocket> webSockets =new CopyOnWriteArraySet<>();
    private static Map<String,Session> sessionPool = new HashMap<String,Session>();

    /**
     * 连接建立成功
     *      【适当添加业务逻辑：监听用户登录后推送未成功接受的历史消息】
     * @param session
     * @param userId
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value="userId")String userId) {
        try {
            this.session = session;
            webSockets.add(this);
            sessionPool.put(userId, session);
            this.userId=userId;
            log.info("【websocket消息】有新的连接，总数为:"+webSockets.size());
            /*建立连接后主动推送未读消息*/
            // List<RemindMessage> myMessageList = remindMessageService.myNotReceiveMessages(this.userId);
            // if(myMessageList.size()>0){
            //     log.info("新连接有"+myMessageList+"条未读消息,开始推送消息！");
            // }
            // myMessageList.forEach(currMessage->{
            //     sendOneMessage(currMessage.getUserId(),currMessage.getContent());
            // });
        } catch (Exception e) {
            log.error("websocket 链接异常");
        }
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            log.info("【websocket消息】连接断开，总数为:"+webSockets.size());
        } catch (Exception e) {
        }
    }

    /**
     * 收到客户端消息
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端"+userId+"的消息:"+message);
        JSONObject obj = new JSONObject();
        obj.put("type", "heartcheck");   //业务类型
        obj.put("content", "心跳响应");  //消息内容
        session.getAsyncRemote().sendText(obj.toJSONString());
    }

    /**
     * 广播消息
     * @param message
     */
    public void sendAllMessage(String message) {
        log.info("【websocket消息】广播消息:"+message);
        for(WebSocket webSocket : webSockets) {
            try {
                if(webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 单点消息
     * @param userId
     * @param message
     * @return 是否推送成功 true: 成功
     */
    public boolean sendOneMessage(String userId, String message) {
        boolean flag = false;
        Session session = sessionPool.get(userId);
        if (session != null && session.isOpen()) {
            try {
                log.info("【websocket消息】 单点消息:"+message);
                session.getAsyncRemote().sendText(message);
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                return flag;
            }
        }else{
            return false;
        }
    }

    /**
     * 单点消息（多人）
     * @param userIds
     * @param message
     */
    public void sendMoreMessage(String[] userIds, String message) {
        for(String userId:userIds) {
            Session session = sessionPool.get(userId);
            if (session != null&&session.isOpen()) {
                try {
                    log.info("【websocket消息】 单点消息:"+message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
