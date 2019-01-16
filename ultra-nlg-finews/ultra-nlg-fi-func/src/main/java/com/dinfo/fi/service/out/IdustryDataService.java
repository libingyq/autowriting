package com.dinfo.fi.service.out;

import com.dinfo.fi.entity.ConceptData;
import com.dinfo.fi.entity.IndustryData;
import com.dinfo.fi.utils.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>Date:2018/10/15</p>
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
@RequestMapping(value = "/industryData")
public interface IdustryDataService {



    @RequestMapping("/getIndustryByCodeAndTimeRange")
    Response<List<IndustryData>> getIndustryByCodeAndTimeRange(@RequestParam(value = "code") String code,
                                                            @RequestParam(value = "startTime") String startTime,
                                                            @RequestParam(value = "endTime") String endTime);


    @RequestMapping("/getIndustryByCodeRangeAndTimeRange")
    Response<List<IndustryData>> getIndustryByCodeRangeAndTimeRange(@RequestParam(value = "codeRange") String codeRange,
                                                                @RequestParam(value = "startTime") String startTime,
                                                                @RequestParam(value = "endTime") String endTime);


}
