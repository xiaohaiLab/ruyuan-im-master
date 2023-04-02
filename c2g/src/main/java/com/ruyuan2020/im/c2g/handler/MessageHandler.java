package com.ruyuan2020.im.c2g.handler;

import com.alibaba.fastjson.JSONObject;
import com.ruyuan2020.im.c2g.service.MessageSendService;
import com.ruyuan2020.im.common.im.domain.MessageAckJsonRequest;
import com.ruyuan2020.im.common.im.domain.MessageSendJsonRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author case
 */
@Component
public class MessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);

    @Autowired
    private MessageSendService messageSendService;

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            5, 20, 10, TimeUnit.SECONDS,
            new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

    @KafkaListener(topics = "#{'topic-c2g-message-send-'.concat('${spring.profiles.active}')}", properties = {
            "max.poll.interval.ms:600000",
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG + "=1000"
    })
    public void handleMessageSend(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
        // 批量消费kafka消息
        Map<Long, List<MessageSendJsonRequest>> grouped = records.stream()
                .map(record -> JSONObject.parseObject(record.value(), MessageSendJsonRequest.class))
                .collect(Collectors.groupingBy(MessageSendJsonRequest::getChatId));
        for (Map.Entry<Long, List<MessageSendJsonRequest>> entry : grouped.entrySet()) {
            // 把消息放入线程池中执行
            executor.execute(() -> messageSendService.save(entry.getValue(), entry.getKey()));
        }
        // 提交ack
        // 提交ack后，如果线程池中的消息没有处理完，服务器宕机了，不会给客户端回复消息的ack
        // 客户端长时间没有收到ack会重新发送消息，保证消息不丢失
        ack.acknowledge();
    }

    @KafkaListener(topics = "#{'topic-c2g-message-ack-'.concat('${spring.profiles.active}')}", properties = {
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG + "=50"
    })
    public void handleMessagePushResponse(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
        for (ConsumerRecord<String, String> record : records) {
//            LOGGER.info("消息ack:{}", record.value());
            MessageAckJsonRequest messageAckJsonRequest = JSONObject.parseObject(record.value(), MessageAckJsonRequest.class);
            messageSendService.updateAck(messageAckJsonRequest.getChatId(), messageAckJsonRequest.getMemberId(), messageAckJsonRequest.getClientId(), messageAckJsonRequest.getMessageId());
        }
        ack.acknowledge();
    }
}
