package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.application.dto.RunnerDTO;
import com.csxuhuan.gelatoni.application.service.RunnerAppService;
import com.csxuhuan.gelatoni.interfaces.web.request.RunnerPageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Runner 控制器
 */
@RestController
@RequestMapping("/api/runners")
public class RunnerController {

    private final RunnerAppService runnerAppService;

    public RunnerController(RunnerAppService runnerAppService) {
        this.runnerAppService = runnerAppService;
    }

    @GetMapping("/page")
    public Map<String, Object> getRunnerPage(@Valid RunnerPageRequest request) {
        List<RunnerDTO> runners = runnerAppService.getRunnerPage(request.getPageNum(), request.getPageSize());
        long total = runnerAppService.count();

        Map<String, Object> result = new HashMap<>();
        result.put("data", runners);
        result.put("total", total);
        result.put("pageNum", request.getPageNum());
        result.put("pageSize", request.getPageSize());
        return result;
    }
}
