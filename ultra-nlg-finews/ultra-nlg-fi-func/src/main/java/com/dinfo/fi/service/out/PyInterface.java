package com.dinfo.fi.service.out;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>Date:2018/9/30</p>
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
@FeignClient("ultra-nlg-fi-sidecar")
public interface PyInterface {

    @RequestMapping(value = "/indeces")
    String updateIndeces();

    @RequestMapping(value = "/tickers")
    String updatetickers();


    @RequestMapping(value = "/DTWCompareDemo/{tip}/{table}/{startTime}/{endTime}/{trend}")
    String DTWCompareDemo(@PathVariable(value = "tip")String tip,
                          @PathVariable(value = "table")String table,
                          @PathVariable(value = "startTime")String startTime,
                          @PathVariable(value = "endTime")String endTime,
                          @PathVariable(value = "trend")String trend);



}
