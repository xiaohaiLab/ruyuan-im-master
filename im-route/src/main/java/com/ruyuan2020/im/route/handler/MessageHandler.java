package com.ruyuan2020.im.route.handler;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.im.constant.Constants;
import com.ruyuan2020.im.common.im.domain.MessageJsonFetch;
import com.ruyuan2020.im.common.im.domain.MessageJsonPush;
import com.ruyuan2020.im.common.im.domain.MessageSendJsonResponse;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.common.protobuf.MessageFetch;
import com.ruyuan2020.im.common.protobuf.MessagePush;
import com.ruyuan2020.im.common.protobuf.MessageSendResponse;
import com.ruyuan2020.im.route.gateway.GatewayRegistry;
import com.ruyuan2020.im.route.domain.Session;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author case
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageHandler {

    private final RedisTemplate<String, Session> redisTemplate;

    private final GatewayRegistry registry;

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            5, 20, 60, TimeUnit.SECONDS,
            new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 处理发送消息响应
     */
    @KafkaListener(topics = "#{'topic-message-send-response-'.concat('${spring.profiles.active}')}")
    public void handleMessageSendResponse(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
        // 对要推送的用户分组
        Map<Long, List<MessageSendJsonResponse>> groupByFromId = records.stream().map(record -> JSONObject.parseObject(record.value(), MessageSendJsonResponse.class))
                .collect(Collectors.groupingBy(MessageSendJsonResponse::getFromId));
        for (Map.Entry<Long, List<MessageSendJsonResponse>> entry : groupByFromId.entrySet()) {

            // 分组后可以只取一次session
            Long userId = entry.getKey();
            Map<Object, Object> clients = redisTemplate.boundHashOps(Constants.REDIS_SESSION_KEY + "::" + userId).entries();
            if (CollUtil.isNotEmpty(clients)) {
                // 遍历client
                for (Object object : clients.values()) {
                    // 获取到session
                    Session session = (Session) object;
                    ChannelHandlerContext ctx = registry.getChannel(session.getGatewayId());
                    if (Objects.nonNull(ctx)) {
                        // 如果有ctx，发送消息发送响应信息
                        List<MessageSendJsonResponse> responses = entry.getValue();
                        for (MessageSendJsonResponse jsonResponse : responses) {
                            MessageSendResponse response = MessageSendResponse.newBuilder()
                                    .setMessageId(jsonResponse.getMessageId())
                                    .setChatId(jsonResponse.getChatId())
                                    .setChatType(jsonResponse.getChatType())
                                    .setFromId(jsonResponse.getFromId())
                                    .setToId(jsonResponse.getToId())
                                    .setMessageId(jsonResponse.getMessageId())
                                    .setSequence(jsonResponse.getSequence())
                                    .setTimestamp(jsonResponse.getTimestamp())
                                    .build();

                            Command command = Command.newBuilder()
                                    .setUserId(session.getUserId())
                                    .setClient(session.getClient())
                                    .setType(CommandType.COMMAND_MESSAGE_SEND)
                                    .setBody(response.toByteString())
                                    .build();
                            ctx.writeAndFlush(command);
                        }
                    }
                }
            }

        }
        ack.acknowledge();
    }

    /**
     * 处理消息推送
     */
    @KafkaListener(topics = "#{'topic-message-push-'.concat('${spring.profiles.active}')}", properties = {
            "max.poll.interval.ms:600000",
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG + "=1000"
    })
    public void handleMessagePush(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
        // 对要推送的用户分组
        Map<Long, List<MessageJsonPush>> groupByToId = records.stream().map(record -> JSONObject.parseObject(record.value(), MessageJsonPush.class))
                .collect(Collectors.groupingBy(MessageJsonPush::getToId));
        for (Map.Entry<Long, List<MessageJsonPush>> entry : groupByToId.entrySet()) {
            executor.execute(() -> {
                // 分组后可以只取一次session
                Long userId = entry.getKey();
                Map<Object, Object> clients = redisTemplate.boundHashOps(Constants.REDIS_SESSION_KEY + "::" + userId).entries();
                if (CollUtil.isNotEmpty(clients)) {
                    // 遍历client
                    for (Object object : clients.values()) {
                        // 获取到session
                        Session session = (Session) object;
                        ChannelHandlerContext ctx = registry.getChannel(session.getGatewayId());
                        if (Objects.nonNull(ctx)) {
                            // 如果有ctx，发送消息发送响应信息
                            List<MessageJsonPush> pushList = entry.getValue();
                            for (MessageJsonPush jsonPush : pushList) {
                                MessagePush response = MessagePush.newBuilder()
                                        .setChatType(jsonPush.getChatType())
                                        .setFromId(jsonPush.getFromId())
                                        .setChatId(jsonPush.getChatId())
                                        .setMessageId(jsonPush.getMessageId())
                                        .setMessageType(jsonPush.getMessageType())
                                        .setContent(jsonPush.getContent())
                                        .setSequence(jsonPush.getSequence())
                                        .setTimestamp(jsonPush.getTimestamp())
                                        .build();

                                Command command = Command.newBuilder()
                                        .setUserId(session.getUserId())
                                        .setClient(session.getClient())
                                        .setType(CommandType.COMMAND_MESSAGE_PUSH)
                                        .setBody(response.toByteString())
                                        .build();
                                log.info("接收消息[chatType:{},messageId:{},fromId:{},toId:{},sequence:{},content:{}]",
                                        jsonPush.getChatType(), jsonPush.getMessageId(), jsonPush.getFromId(), jsonPush.getToId(), jsonPush.getSequence(), jsonPush.getContent());
                                ctx.executor().execute(() -> ctx.writeAndFlush(command));
                            }
                        }
                    }
                }
            });
        }
        ack.acknowledge();
    }

    @KafkaListener(topics = "#{'topic-message-fetch-'.concat('${spring.profiles.active}')}")
    public void handleMessageFetch(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
        Map<Long, List<MessageJsonFetch>> groupByToId = records.stream().map(record -> JSONObject.parseObject(record.value(), MessageJsonFetch.class))
                .collect(Collectors.groupingBy(MessageJsonFetch::getToId));
        for (Map.Entry<Long, List<MessageJsonFetch>> entry : groupByToId.entrySet()) {
            // 分组后可以只取一次session
            Long userId = entry.getKey();
            Map<Object, Object> clients = redisTemplate.boundHashOps(Constants.REDIS_SESSION_KEY + "::" + userId).entries();
            if (CollUtil.isNotEmpty(clients)) {
                // 遍历client
                for (Object object : clients.values()) {
                    // 获取到session
                    Session session = (Session) object;
                    ChannelHandlerContext ctx = registry.getChannel(session.getGatewayId());
                    if (Objects.nonNull(ctx)) {
                        List<MessageJsonFetch> fetchList = entry.getValue();
                        for (MessageJsonFetch jsonFetch : fetchList) {
                            MessageFetch response = MessageFetch.newBuilder()
                                    .setChatType(jsonFetch.getChatType())
                                    .setChatId(jsonFetch.getChatId())
                                    .setToId(jsonFetch.getToId())
                                    .build();
                            Command command = Command.newBuilder()
                                    .setUserId(session.getUserId())
                                    .setClient(session.getClient())
                                    .setType(CommandType.COMMAND_MESSAGE_FETCH)
                                    .setBody(response.toByteString())
                                    .build();
                            ctx.writeAndFlush(command);
                        }
                    }
                }
            }
        }
        ack.acknowledge();
    }
}
