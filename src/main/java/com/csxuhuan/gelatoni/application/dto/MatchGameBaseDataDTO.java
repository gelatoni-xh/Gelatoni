package com.csxuhuan.gelatoni.application.dto;

import java.util.List;
import java.util.Map;

public class MatchGameBaseDataDTO {

    private List<String> seasons;

    private List<String> myPlayerNames;

    private List<String> opponentPlayerNames;

    private List<String> myUserNames;

    /** 按赛季分组的比赛日期，key=赛季，value=日期列表（倒序） */
    private Map<String, List<String>> matchDatesBySeason;

    public List<String> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<String> seasons) {
        this.seasons = seasons;
    }

    public List<String> getMyPlayerNames() {
        return myPlayerNames;
    }

    public void setMyPlayerNames(List<String> myPlayerNames) {
        this.myPlayerNames = myPlayerNames;
    }

    public List<String> getOpponentPlayerNames() {
        return opponentPlayerNames;
    }

    public void setOpponentPlayerNames(List<String> opponentPlayerNames) {
        this.opponentPlayerNames = opponentPlayerNames;
    }

    public List<String> getMyUserNames() {
        return myUserNames;
    }

    public void setMyUserNames(List<String> myUserNames) {
        this.myUserNames = myUserNames;
    }

    public Map<String, List<String>> getMatchDatesBySeason() {
        return matchDatesBySeason;
    }

    public void setMatchDatesBySeason(Map<String, List<String>> matchDatesBySeason) {
        this.matchDatesBySeason = matchDatesBySeason;
    }
}
