package com.ruyuan2020.im.common.im.domain;

import cn.hutool.json.JSONObject;
import com.ruyuan2020.im.common.protobuf.Command;
import lombok.Getter;
import lombok.Setter;

/**
 * @author case
 */
@Getter
@Setter
public class JsonCommand {

    private Long userId;

    private Integer client;

    private int type;

    private JSONObject body;

    public static JsonCommand convert(Command command) {
        JsonCommand jsonCommand = new JsonCommand();
        jsonCommand.setType(command.getType());
        jsonCommand.setUserId(command.getUserId());
        jsonCommand.setClient(command.getClient());
        return jsonCommand;
    }
}
