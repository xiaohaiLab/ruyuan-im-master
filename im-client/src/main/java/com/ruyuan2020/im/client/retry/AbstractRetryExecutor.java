package com.ruyuan2020.im.client.retry;

import com.ruyuan2020.im.client.listener.CommandListener;
import com.ruyuan2020.im.client.retry.DelayTask;
import com.ruyuan2020.im.client.retry.RetryExecutor;
import com.ruyuan2020.im.common.protobuf.Command;

import java.util.concurrent.DelayQueue;

/**
 * @author case
 */
abstract class AbstractRetryExecutor<T> implements RetryExecutor<T> {

    /**
     * 延迟队列
     */
    protected final DelayQueue<DelayTask<T>> queue = new DelayQueue<>();

    protected volatile boolean running;

    public abstract void start();

    public void stop() {
        running = false;
    }

    public abstract void enqueue(Command command, T data, CommandListener failedListener);
}
