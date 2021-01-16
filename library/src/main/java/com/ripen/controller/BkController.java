package com.ripen.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 书籍控制层
 *
 * @author Ripen.Y
 * @version 2021/01/17 0:57
 * @since 2021/01/17
 */
@RestController
@RequestMapping(value = "/book")
@Api(value = "书籍控制层")
public class BkController {
    public final Logger LOGGER = LoggerFactory.getLogger (this.getClass ());



}
