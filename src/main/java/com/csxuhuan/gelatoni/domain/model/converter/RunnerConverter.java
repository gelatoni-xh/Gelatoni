package com.csxuhuan.gelatoni.domain.model.converter;

import com.csxuhuan.gelatoni.domain.model.entity.Runner;
import com.csxuhuan.gelatoni.infrastructure.repository.ekiden.entity.RunnerDO;

/**
 * RunnerConverter
 */
public class RunnerConverter {

    public static Runner toDomain(RunnerDO runnerDO) {
        if (runnerDO == null) {
            return null;
        }
        Runner runner = new Runner();
        runner.setId(runnerDO.getId());
        runner.setNameJa(runnerDO.getNameJa());
        runner.setNameZh(runnerDO.getNameZh());
        runner.setNameKana(runnerDO.getNameKana());
        runner.setNameRomaji(runnerDO.getNameRomaji());
        runner.setBirthYear(runnerDO.getBirthYear());
        runner.setCreatedAt(runnerDO.getCreatedAt());
        runner.setUpdatedAt(runnerDO.getUpdatedAt());
        return runner;
    }

    public static RunnerDO toDO(Runner runner) {
        if (runner == null) {
            return null;
        }
        RunnerDO runnerDO = new RunnerDO();
        runnerDO.setId(runner.getId());
        runnerDO.setNameJa(runner.getNameJa());
        runnerDO.setNameZh(runner.getNameZh());
        runnerDO.setNameKana(runner.getNameKana());
        runnerDO.setNameRomaji(runner.getNameRomaji());
        runnerDO.setBirthYear(runner.getBirthYear());
        runnerDO.setCreatedAt(runner.getCreatedAt());
        runnerDO.setUpdatedAt(runner.getUpdatedAt());
        return runnerDO;
    }
}
