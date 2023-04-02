package com.ruyuan2020.im.client.util;

import com.netease.nim.camellia.id.gen.sdk.CamelliaIdGenSdkConfig;
import com.netease.nim.camellia.id.gen.sdk.CamelliaSegmentIdGenSdk;
import com.ruyuan2020.im.client.config.PropertiesUtils;

/**
 * @author case
 */
public class IdUtils {

    private static final String ID_SERVER_URL = PropertiesUtils.getIdServerUrl();

    private static final int ID_CACHE_COUNT = PropertiesUtils.getIdCacheCount();

    private static final CamelliaSegmentIdGenSdk idGenSdk;

    static {
        CamelliaIdGenSdkConfig config = new CamelliaIdGenSdkConfig();
        config.setUrl(ID_SERVER_URL);
        config.setMaxRetry(2);//重试次数
        config.getSegmentIdGenSdkConfig().setCacheEnable(true);//表示sdk是否缓存id
        config.getSegmentIdGenSdkConfig().setStep(ID_CACHE_COUNT);//sdk缓存的id数
        idGenSdk = new CamelliaSegmentIdGenSdk(config);
    }

    public static Long getId(String tag) {
        return idGenSdk.genId(tag);
    }
}
