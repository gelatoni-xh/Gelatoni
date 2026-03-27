package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.application.assembler.RunnerAssembler;
import com.csxuhuan.gelatoni.application.dto.RunnerDTO;
import com.csxuhuan.gelatoni.application.service.RunnerAppService;
import com.csxuhuan.gelatoni.domain.model.entity.Runner;
import com.csxuhuan.gelatoni.domain.query.RunnerPageQuery;
import com.csxuhuan.gelatoni.domain.result.PageResult;
import com.csxuhuan.gelatoni.interfaces.config.AuthCheck;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.PageData;
import com.csxuhuan.gelatoni.interfaces.web.common.PermissionConstants;
import com.csxuhuan.gelatoni.interfaces.web.request.RunnerPageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Runner 控制器
 */
@RestController
@RequestMapping("/api/runners")
public class RunnerController {

    private final RunnerAppService runnerAppService;
    private final RunnerAssembler assembler = new RunnerAssembler();

    public RunnerController(RunnerAppService runnerAppService) {
        this.runnerAppService = runnerAppService;
    }

    @PostMapping(value = "/page",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<PageData<RunnerDTO>> page(@Valid @RequestBody RunnerPageRequest request) {
        RunnerPageQuery query = new RunnerPageQuery(request.getPageNo(), request.getPageSize());

        PageResult<Runner> pageResult = runnerAppService.pageQuery(query);

        PageData<RunnerDTO> pageData = assembler.toPageData(pageResult);

        return BaseResponse.success(pageData);
    }
}
