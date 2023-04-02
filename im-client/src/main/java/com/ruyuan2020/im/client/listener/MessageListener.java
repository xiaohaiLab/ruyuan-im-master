package com.ruyuan2020.im.client.listener;

import com.ruyuan2020.im.common.im.domain.MessageJsonPush;

/**
 * @author case
 */
public interface MessageListener {

    void onMessage(MessageJsonPush message);
}
