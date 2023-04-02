package com.ruyuan2020.im.c2c.service.impl;

import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.common.hbase.HBaseTemplate;
import com.ruyuan2020.im.common.im.constant.Constants;
import com.ruyuan2020.im.common.im.domain.MessageJsonPush;
import com.ruyuan2020.im.common.im.domain.MessageSendJsonRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author case
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MessageSendServiceImpl extends AbstractMessageSendService {

    private static final Integer MESSAGE_FETCH_THRESHOLD = 10;

    private static final int FACTOR = 128;

    private final HBaseTemplate hbaseTemplate;

    @Override
    public void save(List<MessageSendJsonRequest> requests, Long chatId) {
        List<Put> puts = new ArrayList<>(requests.size());
        for (MessageSendJsonRequest request : requests) {
            log.info("发送消息:{}", request.toJsonStr());
            String rowKey = getRowKey(request.getChatId(), request.getMessageId());
            Get get = new Get(Bytes.toBytes(rowKey));
            boolean exists = hbaseTemplate.exists("c2c_message", get);
            if (!exists) {
                Put put = new Put(Bytes.toBytes(rowKey));
                put.addColumn(
                        Bytes.toBytes("message_body"),
                        Bytes.toBytes("chatId"),
                        Bytes.toBytes(request.getChatId())
                );
                put.addColumn(
                        Bytes.toBytes("message_body"),
                        Bytes.toBytes("fromId"),
                        Bytes.toBytes(request.getFromId())
                );
                put.addColumn(
                        Bytes.toBytes("message_body"),
                        Bytes.toBytes("toId"),
                        Bytes.toBytes(request.getToId())
                );
                put.addColumn(
                        Bytes.toBytes("message_body"),
                        Bytes.toBytes("messageId"),
                        Bytes.toBytes(request.getMessageId())
                );
                put.addColumn(
                        Bytes.toBytes("message_body"),
                        Bytes.toBytes("messageType"),
                        Bytes.toBytes(request.getMessageType())
                );
                put.addColumn(
                        Bytes.toBytes("message_body"),
                        Bytes.toBytes("content"),
                        Bytes.toBytes(request.getContent())
                );
                put.addColumn(
                        Bytes.toBytes("message_body"),
                        Bytes.toBytes("sequence"),
                        Bytes.toBytes(request.getSequence())
                );
                put.addColumn(
                        Bytes.toBytes("message_body"),
                        Bytes.toBytes("timestamp"),
                        Bytes.toBytes(request.getTimestamp())
                );
                puts.add(put);
            }
        }
        if (CollectionUtils.isNotEmpty(puts)) {
            this.hbaseTemplate.save("c2c_message", puts);
        }
        Map<Long, List<MessageSendJsonRequest>> groupByToId = requests.stream().collect(Collectors.groupingBy(MessageSendJsonRequest::getToId));
        for (Map.Entry<Long, List<MessageSendJsonRequest>> entry : groupByToId.entrySet()) {
            if (entry.getValue().size() > MESSAGE_FETCH_THRESHOLD) {
                sendMessageFetch(chatId, entry.getKey());
                for (MessageSendJsonRequest request : entry.getValue()) {
                    sendMessageSendResponse(request);
                }
            } else {
                for (MessageSendJsonRequest request : entry.getValue()) {
                    sendMessageSendResponse(request);
                    sendMessagePush(request);
                }
            }
        }
    }

    @Override
    public BasePage<MessageJsonPush> fetch(Long chatId, Long startMessageId, Long stopMessageId, int size) {
        Scan scan = new Scan();
        if (Objects.nonNull(startMessageId)) {
            String startRowKey = getRowKey(chatId, startMessageId);
            scan.withStartRow(Bytes.toBytes(startRowKey));
        }
        String stopRowKey = getRowKey(chatId, stopMessageId);
        scan.withStopRow(Bytes.toBytes(stopRowKey)).setLimit(size);
        List<MessageJsonPush> messageDTOS = hbaseTemplate.list("c2c_message", scan, (result) -> {
            MessageJsonPush push = new MessageJsonPush();
            push.setChatId(Bytes.toLong(result.getValue(Bytes.toBytes("message_body"), Bytes.toBytes("chatId"))));
            push.setChatType(Constants.CHAT_TYPE_C2C);
            push.setFromId(Bytes.toLong(result.getValue(Bytes.toBytes("message_body"), Bytes.toBytes("fromId"))));
            push.setToId(Bytes.toLong(result.getValue(Bytes.toBytes("message_body"), Bytes.toBytes("toId"))));
            push.setMessageId(Bytes.toLong(result.getValue(Bytes.toBytes("message_body"), Bytes.toBytes("messageId"))));
            push.setMessageType(Bytes.toInt(result.getValue(Bytes.toBytes("message_body"), Bytes.toBytes("messageType"))));
            push.setContent(Bytes.toString(result.getValue(Bytes.toBytes("message_body"), Bytes.toBytes("content"))));
            push.setSequence(Bytes.toLong(result.getValue(Bytes.toBytes("message_body"), Bytes.toBytes("sequence"))));
            push.setTimestamp(Bytes.toLong(result.getValue(Bytes.toBytes("message_body"), Bytes.toBytes("timestamp"))));
            return push;
        });
        return new BasePage<>(messageDTOS);
    }

    private String getRowKey(Long toId, Long messageId) {
        Long tempMessageId = messageId;
        if (Objects.isNull(tempMessageId)) {
            tempMessageId = 0L;
        }
        // 逆序消息id，做排序用，最新的消息-先查找
        return getRowKey(toId)
                + StringUtils.leftPad(String.valueOf(Long.MAX_VALUE - tempMessageId), 19, '0');
    }

    private String getRowKey(Long toId) {
        int hash = (int) (toId % FACTOR);
        // 3位会话id的hash+群id+逆序消息id
        // hash，是为了在hbase中分区存储，相当于mysql的分表
        // 群id，rowKey做群消息查询
        return StringUtils.leftPad(String.valueOf(hash), 3, '0') + "|"
                + StringUtils.leftPad(String.valueOf(toId), 19, '0') + "|";
    }
}
