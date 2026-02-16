package com.csxuhuan.gelatoni.domain.model.converter;

import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.entity.MatchGame;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.MatchGameDO;

/**
 * MatchGame 领域对象转换器
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
 * @author Gelatoni
 */
public class MatchGameConverter {

    /**
     * 私有构造函数，工具类不允许实例化
     */
    private MatchGameConverter() {
    }

    /**
     * 将数据库对象转换为领域实体
     *
     * <p>转换过程中会过滤掉数据库特有的字段（如 isDeleted）。
     *
     * @param gameDO 数据库对象
     * @return 领域实体，如果输入为 null 则返回 null
     */
    public static MatchGame toDomain(MatchGameDO gameDO) {
        if (gameDO == null) {
            return null;
        }

        return new MatchGame(
                gameDO.getId(),
                gameDO.getSeason(),
                gameDO.getMatchTime(),
                gameDO.getIsRobot(),
                gameDO.getMyScore(),
                gameDO.getOppScore(),
                gameDO.getResult(),
                gameDO.getRemark(),
                gameDO.getCreator(),
                gameDO.getModifier(),
                gameDO.getCreateTime(),
                gameDO.getModifiedTime()
        );
    }

    /**
     * 将领域实体转换为数据库对象
     *
     * <p>转换过程中会设置数据库特有的字段（如 isDeleted）。
     *
     * @param game 领域实体
     * @return 数据库对象，如果输入为 null 则返回 null
     */
    public static MatchGameDO toDO(MatchGame game) {
        if (game == null) {
            return null;
        }
        MatchGameDO gameDO = new MatchGameDO();
        gameDO.setId(game.getId());
        gameDO.setSeason(game.getSeason());
        gameDO.setMatchTime(game.getMatchTime());
        gameDO.setIsRobot(game.getIsRobot());
        gameDO.setMyScore(game.getMyScore());
        gameDO.setOppScore(game.getOppScore());
        gameDO.setResult(game.getResult());
        gameDO.setRemark(game.getRemark());
        gameDO.setCreator(game.getCreator());
        gameDO.setModifier(game.getModifier());
        gameDO.setCreateTime(game.getCreateTime());
        gameDO.setModifiedTime(game.getModifiedTime());
        gameDO.setIsDeleted(DeletedEnum.NOT_DELETED.getValue());
        return gameDO;
    }
}