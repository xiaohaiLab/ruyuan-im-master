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
public class RetryCommandExecutor extends AbstractRetryExecutor<Object> {

    private final int COMMAND_MAX_RETRY = PropertiesUtils.getCommandMaxRetry();

    private final TcpClient tcpClient;

    public RetryCommandExecutor(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    private final static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void start() {
        super.running = true;
        executorService.execute(() -> {
            while (super.running) {
                try {
                    // 队列中取出任务
                    DelayTask<Object> task = queue.take();
                    try {
                        // 重新发送
                        tcpClient.sendCommand(task.getCommand(), task.getFailedListener());
                    } catch (Exception e) {
                        // 达到最大重试次数
                        if (task.getCount() == COMMAND_MAX_RETRY) {
                            // 调用失败回调
                            task.getFailedListener().onCommand(task.getCommand());
                            continue;
                        }
                        log.info("发送失败，准备消息重新发送");
                        // 发送失败，更新重试次数
                        task.retry();
                        // 重新放入队列
                        queue.put(task);
                    }
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
            }
        });
    }

    @Override
    public void enqueue(Command command, Object data, CommandListener failedListener) {
        queue.put(new DelayTask<>(command, data, 3000, failedListener));
    }
}
