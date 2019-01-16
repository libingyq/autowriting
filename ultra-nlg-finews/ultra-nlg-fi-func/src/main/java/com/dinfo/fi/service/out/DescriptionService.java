package com.dinfo.fi.service.out;

import com.dinfo.fi.dto.TemplateAllContent;
import com.dinfo.fi.entity.DescribeInfo;
import com.dinfo.fi.utils.Response;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
@RequestMapping(value = "/describe")
public interface DescriptionService {

    @RequestMapping(value = "/getDescribeByTrend",method = RequestMethod.GET)
    Response<List<DescribeInfo>> getDescribeByTrend(@RequestParam(value = "trend") String trend);

}
