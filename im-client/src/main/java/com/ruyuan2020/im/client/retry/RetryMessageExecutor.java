package com.ruyuan2020.im.client.retry;

import com.ruyuan2020.im.client.config.PropertiesUtils;
import com.ruyuan2020.im.client.listener.CommandListener;
import com.ruyuan2020.im.client.tcp.TcpClient;
import com.ruyuan2020.im.common.protobuf.Command;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 命令重发执行器
 *
 * @author case
 */
@Slf4j
public class RetryMessageExecutor extends AbstractRetryExecutor<Long> {

    private final static int waitTime = PropertiesUtils.getMessageAckWaitTime();

    private final TcpClient tcpClient;

    public RetryMessageExecutor(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    private final static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void start() {
        super.running = true;
        executorService.execute(() -> {
            while (super.running) {
                try {
                    // 队列中取出任务
                    DelayTask<Long> task = queue.take();
                    // 检查服务端是否返回ack,如果还在等待ack，重新发送消息
                    if (tcpClient.waitAck(task.getData())) {
                        // 重新发送
                        tcpClient.sendCommand(task.getCommand(), task.getFailedListener());
                    }
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
            }
        });
    }

    @Override
    public void enqueue(Command command, Long messageId, CommandListener failedListener) {
        queue.put(new DelayTask<>(command, messageId, waitTime, failedListener));
    }
}
