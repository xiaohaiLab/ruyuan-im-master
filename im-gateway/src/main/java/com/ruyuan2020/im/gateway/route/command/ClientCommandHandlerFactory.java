package com.ruyuan2020.im.gateway.route.command;

import com.ruyuan2020.im.common.core.exception.SystemException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author case
 */
@Component
@RequiredArgsConstructor
public class ClientCommandHandlerFactory {

    private final List<ClientCommandHandler> commandHandlers;

    public ClientCommandHandler getCommandHandler(int type) {
        for (ClientCommandHandler commandHandler : commandHandlers) {
            if (type == commandHandler.getType()) {
                return commandHandler;
            }
        }
        throw new SystemException("找不到命令处理器");
    }
}
