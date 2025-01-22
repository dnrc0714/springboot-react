package com.ccbb.demo.controller.common;

import com.ccbb.demo.service.common.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cmm")
@RequiredArgsConstructor
public class CommonController {
    private final CommonService commonService;

    @PostMapping(value = "/getCmmCode")
    public ResponseEntity<?> cmmCode(@RequestParam(name="sysCode") String code) {
        return ResponseEntity.ok(commonService.getCmmCode(code));
    }
}
