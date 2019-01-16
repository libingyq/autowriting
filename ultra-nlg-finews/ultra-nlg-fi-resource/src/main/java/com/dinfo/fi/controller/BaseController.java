package com.dinfo.fi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>Date:2018/10/8</p>
 * <p>Module:</p>
 * <p>Description: </p>
 * <p>Remark: </p>
 *
 * @author wuxiangbo
 * @version 1.0
 *          <p>------------------------------------------------------------</p>
 *          <p> Change history</p>
 *          <p> Serial number: date:modified person: modification reason:</p>
 */
@Controller
@RequestMapping("/")
public class BaseController {

    /**
     * 将初始页定在swagger上
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String basePage(){
        return "redirect:swagger-ui.html";
    }

}
