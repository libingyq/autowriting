package com.dinfo.fi.service.out;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

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

//    @RequestMapping(value = "/tickers")
//    String updatetickers();
}
