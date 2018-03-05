package com.wfw.controller;

import com.wfw.vo.hello.GreetingResponseVO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by killer9527 on 2018/3/5.
 */
@RestController
@Api(value = "GreetingController", description = "Greeting描述")
public class GreetingController extends BaseController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(path="greeting", method= RequestMethod.GET)
    public GreetingResponseVO greeting(@RequestParam(value="name", defaultValue="World") String name){
        GreetingResponseVO response = new GreetingResponseVO();
        response.setId(counter.incrementAndGet());
        response.setContent(String.format(template, name));
        return response;
    }
}
