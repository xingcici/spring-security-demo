package com.cve.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haifeng.pang
 * @since 0.0.1
 */
@RequestMapping("/")
@RestController
public class TestController {

    @GetMapping("/api/public/test/test")
    public String publicApi() {
        return "hello";
    }

    @GetMapping("/api/private/test/test")
    public String privateApi() {
        return "hello";
    }
}
