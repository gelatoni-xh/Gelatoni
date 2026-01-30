package com.csxuhuan.gelatoni.domain.service;

import com.csxuhuan.gelatoni.domain.model.entity.MatchGame;
import com.csxuhuan.gelatoni.domain.model.entity.MatchTeamStats;
import com.csxuhuan.gelatoni.domain.model.entity.MatchPlayerStats;
import com.csxuhuan.gelatoni.domain.query.MatchGameCreateQuery;
import com.csxuhuan.gelatoni.domain.query.MatchGameUpdateQuery;
import com.csxuhuan.gelatoni.domain.query.MatchGamePageQuery;

import java.util.List;

/**
 * 比赛领域服务接口
 *
 * <p>处理比赛相关的业务逻辑。
 *
 * @author Gelatoni
 */
public interface MatchGameDomainService {

    /**
     * 创建比赛
     *
     * <p>支持两种创建方式：
     * <ul>
     *     <li>只保存比赛基础信息</li>
     *     <li>一次性保存整场比赛全部数据（比赛 + 队伍统计 + 所有球员数据）</li>
     * </ul>
     *
     * @param query 创建查询对象
     * @return 创建的比赛ID
     */
    Long createMatchGame(MatchGameCreateQuery query);

    /**
     * 更新比赛
     *
     * <p>支持两种更新方式：
     * <ul>
     *     <li>只更新比赛基础信息</li>
     *     <li>一次性更新整场比赛全部数据（比赛 + 队伍统计 + 所有球员数据）</li>
     * </ul>
     *
     * @param query 更新查询对象
     * @return 更新成功标志
     */
    boolean updateMatchGame(MatchGameUpdateQuery query);

    /**
     * 删除比赛
     *
     * <p>删除比赛记录，同时删除该比赛对应的队伍统计数据和球员数据。
     *
     * @param id 比赛ID
     * @return 删除成功标志
     */
    boolean deleteMatchGame(Long id);

    /**
     * 根据ID获取比赛
     *
     * @param id 比赛ID
     * @return 比赛实体
     */
    MatchGame getMatchGameById(Long id);

    /**
     * 分页查询比赛列表
     *
     * @param query 分页查询对象
     * @return 比赛列表
     */
    List<MatchGame> getMatchGamePage(MatchGamePageQuery query);

    /**
     * 获取比赛总数
     *
     * @return 比赛总数
     */
    int getMatchGameCount();

    /**
     * 获取比赛详情
     *
     * @param id 比赛ID
     * @return 包含比赛、队伍统计和球员统计的完整数据
     */
    MatchGameDetailData getMatchGameDetail(Long id);

    /**
     * 比赛详情数据内部类
     */
    class MatchGameDetailData {
        private final MatchGame matchGame;
        private final List<MatchTeamStats> myTeamStats;
        private final List<MatchTeamStats> opponentTeamStats;
        private final List<MatchPlayerStats> myPlayerStats;
        private final List<MatchPlayerStats> opponentPlayerStats;

        public MatchGameDetailData(MatchGame matchGame, List<MatchTeamStats> myTeamStats,
                                   List<MatchTeamStats> opponentTeamStats, List<MatchPlayerStats> myPlayerStats,
                                   List<MatchPlayerStats> opponentPlayerStats) {
            this.matchGame = matchGame;
            this.myTeamStats = myTeamStats;
            this.opponentTeamStats = opponentTeamStats;
            this.myPlayerStats = myPlayerStats;
            this.opponentPlayerStats = opponentPlayerStats;
        }

        public MatchGame getMatchGame() {
            return matchGame;
        }

        public List<MatchTeamStats> getMyTeamStats() {
            return myTeamStats;
        }

        public List<MatchTeamStats> getOpponentTeamStats() {
            return opponentTeamStats;
        }

        public List<MatchPlayerStats> getMyPlayerStats() {
            return myPlayerStats;
        }

        public List<MatchPlayerStats> getOpponentPlayerStats() {
            return opponentPlayerStats;
        }
    }
}