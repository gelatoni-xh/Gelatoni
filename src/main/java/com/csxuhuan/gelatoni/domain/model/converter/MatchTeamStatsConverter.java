package com.csxuhuan.gelatoni.domain.model.converter;

import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.entity.MatchTeamStats;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.MatchTeamStatsDO;

/**
 * MatchTeamStats 领域对象转换器
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
public class MatchTeamStatsConverter {

    /**
     * 私有构造函数，工具类不允许实例化
     */
    private MatchTeamStatsConverter() {
    }

    /**
     * 将数据库对象转换为领域实体
     *
     * <p>转换过程中会过滤掉数据库特有的字段（如 isDeleted）。
     *
     * @param statsDO 数据库对象
     * @return 领域实体，如果输入为 null 则返回 null
     */
    public static MatchTeamStats toDomain(MatchTeamStatsDO statsDO) {
        if (statsDO == null) {
            return null;
        }

        return new MatchTeamStats(
                statsDO.getId(),
                statsDO.getMatchId(),
                statsDO.getTeamType(),
                statsDO.getScore(),
                statsDO.getFgAttempt(),
                statsDO.getFgMade(),
                statsDO.getThreeAttempt(),
                statsDO.getThreeMade(),
                statsDO.getAssist(),
                statsDO.getRebound(),
                statsDO.getOffRebound(),
                statsDO.getDefRebound(),
                statsDO.getSteal(),
                statsDO.getBlock(),
                statsDO.getDunk(),
                statsDO.getPaintScore(),
                statsDO.getSecondChanceScore(),
                statsDO.getTurnoverToScore(),
                statsDO.getMaxLead(),
                statsDO.getCreator(),
                statsDO.getModifier(),
                statsDO.getCreateTime(),
                statsDO.getModifiedTime()
        );
    }

    /**
     * 将领域实体转换为数据库对象
     *
     * <p>转换过程中会设置数据库特有的字段（如 isDeleted）。
     *
     * @param stats 领域实体
     * @return 数据库对象，如果输入为 null 则返回 null
     */
    public static MatchTeamStatsDO toDO(MatchTeamStats stats) {
        if (stats == null) {
            return null;
        }
        MatchTeamStatsDO statsDO = new MatchTeamStatsDO();
        statsDO.setId(stats.getId());
        statsDO.setMatchId(stats.getMatchId());
        statsDO.setTeamType(stats.getTeamType());
        statsDO.setScore(stats.getScore());
        statsDO.setFgAttempt(stats.getFgAttempt());
        statsDO.setFgMade(stats.getFgMade());
        statsDO.setThreeAttempt(stats.getThreeAttempt());
        statsDO.setThreeMade(stats.getThreeMade());
        statsDO.setAssist(stats.getAssist());
        statsDO.setRebound(stats.getRebound());
        statsDO.setOffRebound(stats.getOffRebound());
        statsDO.setDefRebound(stats.getDefRebound());
        statsDO.setSteal(stats.getSteal());
        statsDO.setBlock(stats.getBlock());
        statsDO.setDunk(stats.getDunk());
        statsDO.setPaintScore(stats.getPaintScore());
        statsDO.setSecondChanceScore(stats.getSecondChanceScore());
        statsDO.setTurnoverToScore(stats.getTurnoverToScore());
        statsDO.setMaxLead(stats.getMaxLead());
        statsDO.setCreator(stats.getCreator());
        statsDO.setModifier(stats.getModifier());
        statsDO.setCreateTime(stats.getCreateTime());
        statsDO.setModifiedTime(stats.getModifiedTime());
        statsDO.setIsDeleted(DeletedEnum.NOT_DELETED.getValue());
        return statsDO;
    }
}