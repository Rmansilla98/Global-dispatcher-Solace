package com.newcron.globaldispatch.controller;

import com.newcron.globaldispatch.model.DispatchRequest;
import com.newcron.globaldispatch.model.DispatchResult;
import com.newcron.globaldispatch.service.GlobalDispatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalDispatchController {

    private final GlobalDispatchService globalDispatchService;

    public GlobalDispatchController(
            GlobalDispatchService globalDispatchService) {

        this.globalDispatchService = globalDispatchService;
    }

    @GetMapping("/")
    public String home() {
        return globalDispatchService.getMessage();
    }

    @PostMapping("/dispatch")
    public DispatchResult dispatch(
            @RequestBody DispatchRequest request) {

        return globalDispatchService.validateDispatch(request);
    }
}