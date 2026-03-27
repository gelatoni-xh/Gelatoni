package com.csxuhuan.gelatoni.application.assembler;

import com.csxuhuan.gelatoni.application.dto.RunnerDTO;
import com.csxuhuan.gelatoni.domain.model.entity.Runner;
import com.csxuhuan.gelatoni.domain.result.PageResult;
import com.csxuhuan.gelatoni.interfaces.web.common.PageData;

import java.util.stream.Collectors;

/**
 * RunnerAssembler
 */
public class RunnerAssembler {

    public RunnerDTO toDTO(Runner runner) {
        if (runner == null) {
            return null;
        }
        RunnerDTO dto = new RunnerDTO();
        dto.setId(runner.getId());
        dto.setNameJa(runner.getNameJa());
        dto.setNameZh(runner.getNameZh());
        dto.setNameKana(runner.getNameKana());
        dto.setNameRomaji(runner.getNameRomaji());
        dto.setBirthYear(runner.getBirthYear());
        dto.setCreatedAt(runner.getCreatedAt());
        dto.setUpdatedAt(runner.getUpdatedAt());
        return dto;
    }

    public PageData<RunnerDTO> toPageData(PageResult<Runner> pageResult) {
        return new PageData<>(
                pageResult.getRecords().stream().map(this::toDTO).collect(Collectors.toList()),
                pageResult.getTotal(),
                pageResult.getPageNo(),
                pageResult.getPageSize()
        );
    }
}
