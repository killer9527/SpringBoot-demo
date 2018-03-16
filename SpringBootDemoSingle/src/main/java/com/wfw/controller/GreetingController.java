package com.wfw.controller;

import com.wfw.vo.greeting.GreetingRequestVO;
import com.wfw.vo.greeting.GreetingResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by killer9527 on 2018/3/5.
 */
@RestController
@RequestMapping(path = "/greeting")
@Api(value = "GreetingController", description = "GreetingController描述")
public class GreetingController extends BaseController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private Environment env;

    @RequestMapping(path="greetingByGet", method= RequestMethod.GET)
    @ApiOperation(value = "使用Get方法greeting")
    public GreetingResponseVO greetingByGet(@RequestParam(value="name", defaultValue="World") String name){
        GreetingResponseVO response = new GreetingResponseVO();
        response.setId(counter.incrementAndGet());
        response.setContent(String
                .format(template, env.getProperty("username")
                        + ":" + env.getProperty("profile.property")));
        return response;
    }

    @RequestMapping(path = "greetingByPost", method = RequestMethod.POST)
    @ApiOperation(value = "使用Post方法greeting")
    public GreetingResponseVO greetingByPost(@RequestBody GreetingRequestVO request){
        GreetingResponseVO response = new GreetingResponseVO();
        response.setId(counter.incrementAndGet());
        response.setContent(String.format(template, request.getName()));
        return response;
    }
}
