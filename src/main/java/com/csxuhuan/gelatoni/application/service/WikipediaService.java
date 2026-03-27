package com.csxuhuan.gelatoni.application.service;

import java.util.Map;

/**
 * Wikipedia 数据获取服务
 */
public interface WikipediaService {

    /**
     * 从日文 Wikipedia 获取选手数据
     *
     * @param name 选手名字
     * @return 包含 title 和 content 的 Map，如果未找到返回 null
     */
    Map<String, String> fetchRunnerData(String name);
}
