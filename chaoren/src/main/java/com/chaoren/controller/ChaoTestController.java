package com.chaoren.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with Date:2018/10/2 Time:1:37
 * author:LiChao
 */
@Api(description = "项目通路测试")
@RestController
@RequestMapping("/test")
public class ChaoTestController {

    private static Logger logger = LoggerFactory.getLogger(ChaoTestController.class);

    @RequestMapping(value = "/StringToSame", method = RequestMethod.POST)
    public String toSameString(@RequestParam(value = "SameString", required = false) String similarity){
        logger.info(similarity);
        return similarity;
    }






}
