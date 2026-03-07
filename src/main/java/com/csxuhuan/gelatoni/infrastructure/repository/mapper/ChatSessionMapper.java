package com.csxuhuan.gelatoni.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.ChatSessionDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * chat_session 表 MyBatis-Plus Mapper
 */
@Mapper
public interface ChatSessionMapper extends BaseMapper<ChatSessionDO> {
}
