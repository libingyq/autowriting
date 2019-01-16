package com.dinfo.fi.service.out;

import com.dinfo.fi.entity.StockInfo;
import com.dinfo.fi.utils.Response;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
@RequestMapping(value = "/stockInfo")
public interface StockInfoService {

    @RequestMapping(value = "/getStockByCode")
    Response<StockInfo> getStockByCode(@RequestParam(name = "code") String code);

    @RequestMapping(value = "/getStockInfoByName")
    Response getStockInfoByName(@RequestParam(name = "name") String name);

    @RequestMapping(value = "/getStockCodeByName")
    Response getStockCodeByName(@RequestParam(name = "name") String name);

    @RequestMapping(value = "/getStockByArea")
    Response getStockByArea(@RequestParam(name = "area") String area);

    @RequestMapping(value = "/getStockInfoByEalComponent")
    Response getStockInfoByEalComponent(@RequestParam(name = "ealComponent") String ealComponent);

    @RequestMapping(value = "/getStockInfoByShareType")
    Response getStockInfoByShareType(@RequestParam(name = "shareType") String shareType);

    @RequestMapping(value = "/getStockInfoByIndustry")
    Response getStockInfoByIndustry(@RequestParam(name = "industry") String industry);

    @RequestMapping(value = "/getStockCodeByIndustry")
    Response getStockCodeByIndustry(@RequestParam(name = "industry") String industry);

    @RequestMapping(value = "/getStockInfoByConcept")
    Response getStockInfoByConcept(@RequestParam(name = "concept") String concept);

    @RequestMapping(value = "/getStockCodeByConcept")
    Response getStockCodeByConcept(@RequestParam(name = "concept") String concept);

    @RequestMapping(value = "/getStockCodeByArea")
    Response getStockCodeByArea(@RequestParam(name = "area") String area);

    @RequestMapping(value = "/getStockCodeByEalComponent")
    Response getStockCodeByEalComponent(@RequestParam(name = "ealComponent") String ealComponent);

    @RequestMapping(value = "/getStockCodeByShareType")
    Response getStockCodeByShareType(@RequestParam(name = "shareType") String shareType);

}
