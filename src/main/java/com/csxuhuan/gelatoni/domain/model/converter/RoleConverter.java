package com.csxuhuan.gelatoni.domain.model.converter;

import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.entity.Role;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.RoleDO;

/**
 * Role 领域对象转换器
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
public class RoleConverter {

    /**
     * 私有构造函数，工具类不允许实例化
     */
    private RoleConverter() {
    }

    /**
     * 将数据库对象转换为领域实体
     *
     * <p>转换过程中会过滤掉数据库特有的字段（如 isDeleted）。
     *
     * @param roleDO 数据库对象
     * @return 领域实体，如果输入为 null 则返回 null
     */
    public static Role toDomain(RoleDO roleDO) {
        if (roleDO == null) {
            return null;
        }

        return new Role(
                roleDO.getId(),
                roleDO.getRoleCode(),
                roleDO.getRoleName(),
                roleDO.getStatus(),
                roleDO.getCreator(),
                roleDO.getModifier(),
                roleDO.getCreateTime(),
                roleDO.getModifiedTime()
        );
    }

    /**
     * 将领域实体转换为数据库对象
     *
     * <p>转换时会自动设置 isDeleted 为未删除状态。
     *
     * @param role 领域实体
     * @return 数据库对象，如果输入为 null 则返回 null
     */
    public static RoleDO toDO(Role role) {
        if (role == null) {
            return null;
        }
        return new RoleDO(
                role.getId(),
                role.getRoleCode(),
                role.getRoleName(),
                role.getStatus(),
                role.getCreator(),
                role.getModifier(),
                role.getCreateTime(),
                role.getModifiedTime(),
                DeletedEnum.NOT_DELETED.getValue()
        );
    }
}
