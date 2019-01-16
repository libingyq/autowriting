package com.dinfo.fi.service;

import com.dinfo.fi.dto.TemplateAllContent;
import com.dinfo.fi.entity.SemiTemplate;
import com.dinfo.fi.entity.Template;
import com.dinfo.fi.utils.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Date:2018/10/22</p>
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
@FeignClient(value = "ultra-nlg-fi-resource")
@RequestMapping(value = "/semiTemplate")
public interface SemiTemplateService {

    @RequestMapping(value = "/getAllSemiTemplate",method = RequestMethod.GET)
    Response<List<SemiTemplate>> getAllTemplate();

    @RequestMapping(value = "/getTemplateById",method = RequestMethod.GET)
    Response<SemiTemplate> getTemplateById(
            @RequestParam(value = "id") Integer id);
}
