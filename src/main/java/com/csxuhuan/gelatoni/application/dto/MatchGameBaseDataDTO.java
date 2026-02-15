package com.csxuhuan.gelatoni.application.dto;

import java.util.List;

public class MatchGameBaseDataDTO {

    private List<String> seasons;

    private List<String> myPlayerNames;

    private List<String> opponentPlayerNames;

    private List<String> myUserNames;

    private List<String> matchDates;

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

    public List<String> getMatchDates() {
        return matchDates;
    }

    public void setMatchDates(List<String> matchDates) {
        this.matchDates = matchDates;
    }
}
