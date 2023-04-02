package com.ruyuan2020.im.client.retry;

import com.ruyuan2020.im.client.listener.CommandListener;
import com.ruyuan2020.im.common.protobuf.Command;

/**
 * @author case
 */
interface RetryExecutor<T> {

    void start();

    void stop();

    void enqueue(Command command, T data, CommandListener failedListener);
}
