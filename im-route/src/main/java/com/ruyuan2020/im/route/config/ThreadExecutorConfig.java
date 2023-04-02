package com.ruyuan2020.im.route.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author case
 */
@Configuration
public class ThreadExecutorConfig {

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new MonitorThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(200);
        //设置最大线程数
        executor.setMaxPoolSize(800);
        //设置队列容量
        executor.setQueueCapacity(50);
        //设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("command-handler");
        //设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //执行初始化
        executor.initialize();
        return executor;
    }

    @Slf4j
    static class MonitorThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

        private void printThreadPool(String prefix) {
            ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

            log.debug("{}{},count:[{}], completed:[{}], active:[{}], queueSize:[{}]",
                    this.getThreadNamePrefix(),
                    prefix,
                    threadPoolExecutor.getTaskCount(),
                    threadPoolExecutor.getCompletedTaskCount(),
                    threadPoolExecutor.getActiveCount(),
                    threadPoolExecutor.getQueue().size());
        }

        @Override
        public void execute(Runnable task) {
            printThreadPool(" do execute");
            super.execute(task);
        }

        @Override
        public void execute(Runnable task, long startTimeout) {
            printThreadPool(" do execute");
            super.execute(task, startTimeout);
        }

        @Override
        public Future<?> submit(Runnable task) {
            printThreadPool(" do submit");
            return super.submit(task);
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            printThreadPool(" do submit");
            return super.submit(task);
        }

        @Override
        public ListenableFuture<?> submitListenable(Runnable task) {
            printThreadPool(" do submitListenable");
            return super.submitListenable(task);
        }

        @Override
        public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
            printThreadPool(" do submitListenable");
            return super.submitListenable(task);
        }
    }
}
