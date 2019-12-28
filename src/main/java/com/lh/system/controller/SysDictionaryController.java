package com.lh.system.controller;

import com.lh.system.service.SysDictionaryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 功能描述：系统字典
 *
 *  <p>版权所有：</p>
 *  未经本人许可，不得以任何方式复制或使用本程序任何部分
 *
 * @Company: 紫色年华
 * @Author xieyc
 * @Date 2019-09-20
 * @Version: 1.0.0
 *
 */
@RestController
@RequestMapping("/sysDictionary/sys-dictionary")
@Slf4j
@Api(tags = "系统字典")
public class SysDictionaryController {

    private final SysDictionaryService service;

    @Autowired
    public SysDictionaryController(SysDictionaryService service) {
        this.service = service;
    }
}
