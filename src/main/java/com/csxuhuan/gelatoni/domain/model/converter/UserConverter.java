package com.csxuhuan.gelatoni.domain.model.converter;

import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.entity.User;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.UserDO;

/**
 * User 领域对象转换器
 *
 * <p>负责领域层与基础设施层之间的对象转换：
 * <ul>
 *     <li>DO → Domain：将数据库对象转换为领域实体</li>
 *     <li>Domain → DO：将领域实体转换为数据库对象</li>
 * </ul>
 *
 * <p>设计目的：
 * <ul>
 *     <li>防止基础设施层的实现细节（如 MyBatis 注解、数据库字段名）渗透到领域层</li>
 *     <li>领域层保持纯净，只关注业务逻辑</li>
 *     <li>便于更换持久化框架（如从 MyBatis 换成 JPA）</li>
 * </ul>
 *
 * @author csxuhuan
 */
public class UserConverter {

    /**
     * 私有构造函数，工具类不允许实例化
     */
    private UserConverter() {
    }

    /**
     * 将数据库对象转换为领域实体
     *
     * <p>转换过程中会过滤掉数据库特有的字段（如 isDeleted）。
     *
     * @param userDO 数据库对象
     * @return 领域实体，如果输入为 null 则返回 null
     */
    public static User toDomain(UserDO userDO) {
        if (userDO == null) {
            return null;
        }

        return new User(
                userDO.getId(),
                userDO.getUsername(),
                userDO.getPasswordHash(),
                userDO.getNickname(),
                userDO.getStatus(),
                userDO.getCreator(),
                userDO.getModifier(),
                userDO.getCreateTime(),
                userDO.getModifiedTime()
        );
    }

    /**
     * 将领域实体转换为数据库对象
     *
     * <p>转换时会自动设置 isDeleted 为未删除状态。
     *
     * @param user 领域实体
     * @return 数据库对象，如果输入为 null 则返回 null
     */
    public static UserDO toDO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDO(
                user.getId(),
                user.getUsername(),
                user.getPasswordHash(),
                user.getNickname(),
                user.getStatus(),
                user.getCreator(),
                user.getModifier(),
                user.getCreateTime(),
                user.getModifiedTime(),
                DeletedEnum.NOT_DELETED.getValue()
        );
    }
}
