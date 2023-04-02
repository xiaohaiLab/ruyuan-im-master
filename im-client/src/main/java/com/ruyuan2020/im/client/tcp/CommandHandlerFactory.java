package com.ruyuan2020.im.client.tcp;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author case
 */
public class CommandHandlerFactory {

    protected static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            5, 100, 60, TimeUnit.SECONDS,
            new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

    private static final List<CommandHandler> commandHandlers = new ArrayList<>();

    private static class Singleton {
        private static final CommandHandlerFactory instance = new CommandHandlerFactory();

        static {
            commandHandlers.add(new OnlineCommandHandler(executor));
            commandHandlers.add(new HeartbeatCommandHandler(executor));
            commandHandlers.add(new MessageSendCommandHandler(executor));
        }
    }

    public static CommandHandlerFactory getInstance() {
        return Singleton.instance;
    }

    public CommandHandler getCommandHandler(int type) {
        for (CommandHandler commandHandler : commandHandlers) {
            if (type == commandHandler.getType()) {
                return commandHandler;
            }
        }
        return null;
    }
}
