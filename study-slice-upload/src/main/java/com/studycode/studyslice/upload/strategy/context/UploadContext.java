package com.studycode.studyslice.upload.strategy.context;


import com.studycode.studyslice.common.util.SpringContextHolder;
import com.studycode.studyslice.upload.strategy.SliceUploadStrategy;
import com.studycode.studyslice.upload.strategy.annotation.UploadMode;
import com.studycode.studyslice.upload.strategy.enu.UploadModeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.reflections.Reflections;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public enum UploadContext {
    INSTANCE;

    private static final String PACKAGE_NAME = "com.studycode.studyslice.upload.strategy.impl";

    private Map<UploadModeEnum, Class<SliceUploadStrategy>> uploadStrategyMap = new ConcurrentHashMap<>();


    public void init() {
        Reflections reflections = new Reflections(PACKAGE_NAME);
        Set<Class<?>> clzSet = reflections.getTypesAnnotatedWith(UploadMode.class);
        if (CollectionUtils.isNotEmpty(clzSet)) {
            for (Class<?> clz : clzSet) {
                UploadMode uploadMode = clz.getAnnotation(UploadMode.class);
                uploadStrategyMap.put(uploadMode.mode(), (Class<SliceUploadStrategy>) clz);
            }
        }
    }

    public SliceUploadStrategy getInstance(UploadModeEnum mode) {
        return this.getStrategyByType(mode);

    }


    private SliceUploadStrategy getStrategyByType(UploadModeEnum mode) {
        Class<SliceUploadStrategy> clz = uploadStrategyMap.get(mode);
        Assert.notNull(clz, "mode:" + mode + "can not found class,please checked");
        return SpringContextHolder.getBean(clz);
    }

}
