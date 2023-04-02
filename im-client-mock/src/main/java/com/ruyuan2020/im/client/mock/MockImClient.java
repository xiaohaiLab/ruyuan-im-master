package com.ruyuan2020.im.client.mock;

import com.ruyuan2020.im.client.constant.ClientConstants;
import com.ruyuan2020.im.client.mock.config.MockPropertiesUtils;
import com.ruyuan2020.im.client.listener.CommandListener;
import com.ruyuan2020.im.client.tcp.TcpClient;
import com.ruyuan2020.im.client.util.IdUtils;
import com.ruyuan2020.im.client.util.TokenHolder;
import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.im.constant.Constants;
import com.ruyuan2020.im.common.im.constant.MessageType;
import com.ruyuan2020.im.common.protobuf.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author case
 */
@Slf4j
public class MockImClient {

    private static final Long DEFAULT_SEND_TO_GROUP_ID = MockPropertiesUtils.getDefaultGroupId();

    private static final String MOCK_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyIqOi9hcGkvKi9hY2NvdW50LyoqIiwiKjovYXBpL2lwbGlzdC8qKiJdLCJyZWFsbSI6ImFjY291bnQiLCJhdXRoX2luZm8iOiJ7XCJuaWNrbmFtZVwiOlwi5Y-v5b-D55qE5bm057qnXCIsXCJpZFwiOjIsXCJzdGF0dXNcIjpcIjAwXCJ9IiwiaWQiOjIsImV4cCI6MTY0NzEwNjYyMSwiYXV0aG9yaXRpZXMiOlsiKjovYXBpLyovYWNjb3VudC8qKiIsIio6L2FwaS9pcGxpc3QvKioiXSwianRpIjoiYzM4NzEyMWEtNzVlYS00OWIyLWFhNTgtMWFjYmIxZGE0MWExIiwiY2xpZW50X2lkIjoiYWNjb3VudCJ9.mW7Rn3MCEfzbxFRaNaNZkvrJxrXFF0odCknz1nApNmtZPVyDDZQpWynSoOtYSnWiabkCfDT-0mClzDcB_Y47OYL02RHyYqXvq2G5IBys_JzLGaLBEKWkJhQSG_3AnLWW8imxTh0f0sM1gnURXBeQ8QofJ_QkU8VcRM0EkeJDyVnB0qyv-08v_RZ20S318sTAvIQSWyH_bGCpiAH1GfTWr8UNmuxyXS-60JrfwbJy3A3ShvTQ34mFGadNbqEeM6nvSOHrMA6mNG7v_CHm0L70e7lEKebdskDud1xvY0DZWAP26IweElWi8Ze-C5yDLGwJBMXXF3nVN-kOKOwVRJExsw";

    public void start() {
        for (long i = 0; i < MockPropertiesUtils.getConnectThreadCount(); i++) {
            new Thread(new MockRunner(i)).start();
        }
    }

    static class MockRunner implements Runnable {
        private final Long threadId;

        MockRunner(Long threadId) {
            this.threadId = threadId;
        }

        @Override
        public void run() {
            for (int i = 0; i < MockPropertiesUtils.getConnectLoopCount(); i++) {
                long userId = (MockPropertiesUtils.getMockNo() * MockPropertiesUtils.getConnectLoopCount() * MockPropertiesUtils.getConnectThreadCount() + threadId * MockPropertiesUtils.getConnectLoopCount() + i) + 1;
                TcpClient tcpClient = new TcpClient();
                MockCommandListener commandListener = new MockCommandListener(tcpClient);
                // 连接网关
                tcpClient.connect(MOCK_TOKEN, userId, commandListener);
            }
        }
    }

    static class MockCommandListener implements CommandListener {

        private static final long MESSAGE_COUNT_END = 1L;

        private final TcpClient tcpClient;

        public MockCommandListener(TcpClient tcpClient) {
            this.tcpClient = tcpClient;
        }

        @Override
        @SneakyThrows
        public void onCommand(Command command) {
            if (command.getType() == CommandType.COMMAND_ONLINE) {
                Result result = Result.parseFrom(command.getBody());
                if (result.getSuccess()) {
                    for (int j = 0; j < MESSAGE_COUNT_END; j++) {
                        sendMessage(Constants.CHAT_TYPE_C2G, MessageType.MESSAGE_TYPE_TEXT, "hello" + j, DEFAULT_SEND_TO_GROUP_ID, DEFAULT_SEND_TO_GROUP_ID);
                    }
                } else {
                    log.info("认证失败");
                    // 处理认证失败，退出到登录画面，客户端重新登录
                }
            } else if (command.getType() == CommandType.COMMAND_MESSAGE_SEND) {
                respondSendMessage(command);
            } else if (command.getType() == CommandType.COMMAND_MESSAGE_PUSH) {
                respondPushMessage(command);
            } else if (command.getType() == CommandType.COMMAND_MESSAGE_FETCH) {
                fetchMessage(command);
            }
        }

        /**
         * 发送消息
         */
        private void sendMessage(int chatType, int messageType, String content, Long toId, Long chatId) {
            TokenHolder.TokenInfo tokenInfo = TokenHolder.getTokenInfo(tcpClient);
            MessageSendRequest.Builder builder = MessageSendRequest.newBuilder();
            long messageId = IdUtils.getId("message");
            builder.setChatType(chatType)
                    .setMessageId(messageId)
                    .setFromId(tokenInfo.getUserId())
                    .setMessageType(messageType)
                    .setToId(toId)
                    .setChatId(chatId)
                    .setContent(content);
            if (chatType == Constants.CHAT_TYPE_C2C) {
                builder.setSequence(messageId);
            }
            MessageSendRequest request = builder.build();
            Command command = Command.newBuilder()
                    .setType(CommandType.COMMAND_MESSAGE_SEND)
                    .setClient(ClientConstants.CLIENT_ID)
                    .setUserId(tokenInfo.getUserId())
                    .setBody(request.toByteString())
                    .build();
            tcpClient.sendCommand(command, failedCommand -> log.info("消息[messageId:{},chatType:{},messageType:{},content:{}]发送失败", request.getMessageId(), request.getChatType(), request.getMessageType(), request.getContent()));
        }

        /**
         * 服务端保存消息后的响应
         */
        @SneakyThrows
        private void respondSendMessage(Command command) {
            MessageSendResponse response = MessageSendResponse.parseFrom(command.getBody());
            log.info("消息发送成功[messageId:{},fromId:{}]", response.getMessageId(), response.getFromId());
        }

        /**
         * 接收到推送消息，回复ack
         */
        @SneakyThrows
        private void respondPushMessage(Command command) {
            TokenHolder.TokenInfo tokenInfo = TokenHolder.getTokenInfo(tcpClient);
            MessagePush push = MessagePush.parseFrom(command.getBody());
            log.info("接收消息[chatType:{},messageId:{},fromId:{},sequence:{},content:{}]", push.getChatType(), push.getMessageId(), push.getFromId(), push.getSequence(), push.getContent());
            MessageAckRequest request = MessageAckRequest.newBuilder()
                    .setChatType(push.getChatType())
                    .setMemberId(tokenInfo.getUserId())
                    .setChatId(push.getChatId())
                    .setMessageId(push.getMessageId())
                    .build();
            Command newCommand = Command.newBuilder()
                    .setType(CommandType.COMMAND_MESSAGE_ACK)
                    .setClient(ClientConstants.CLIENT_ID)
                    .setUserId(tokenInfo.getUserId())
                    .setBody(request.toByteString())
                    .build();
            tcpClient.sendCommand(newCommand, failedCommand -> log.info("ack命令发送失败[chatType:{},memberId:{},chatId:{},messageId:{}]", request.getChatType(), request.getMemberId(), request.getChatId(), request.getMessageId()));
        }

        @SneakyThrows
        private void fetchMessage(Command command) {
            MessageFetch fetch = MessageFetch.parseFrom(command.getBody());
            log.info("拉取消息[chatId:{}]", fetch.getChatId());
        }
    }
}
