package com.csxuhuan.gelatoni.application.assembler;

import com.csxuhuan.gelatoni.application.dto.RunnerDTO;
import com.csxuhuan.gelatoni.domain.model.entity.Runner;

import java.util.List;
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

    public List<RunnerDTO> toDTOList(List<Runner> runners) {
        return runners.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
