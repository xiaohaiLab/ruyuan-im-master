package com.ruyuan2020.im.client.retry;

import com.ruyuan2020.im.client.listener.CommandListener;
import com.ruyuan2020.im.common.protobuf.Command;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author case
 */
@Getter
public class DelayTask<T> implements Delayed {

    private final CommandListener failedListener;

    private final Command command;

    private final T data;

    private final long expireTime;

    private int count = 0;

    public DelayTask(Command command, T data, long expireTime, CommandListener failedListener) {
        this.command = command;
        this.data = data;
        this.expireTime = System.currentTimeMillis() + expireTime;
        this.failedListener = failedListener;
    }

    @Override
    public long getDelay(@NotNull TimeUnit unit) {
        return expireTime - System.currentTimeMillis();
    }

    @Override
    public int compareTo(@NotNull Delayed o) {
        return (int) (getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    public void retry() {
        count++;
    }
}
