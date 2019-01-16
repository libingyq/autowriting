package com.dinfo.fi.controller;

import com.dinfo.fi.dao.StockInfoDao;
import com.dinfo.fi.service.in.StockInfoService;
import com.dinfo.fi.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Date:2018/10/10</p>
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
@Api(description = "股票信息Controller",basePath = "/stockInfo")
@RestController
@RequestMapping("/stockInfo")
public class StockInfoController {

    @Autowired
    private StockInfoService stockInfoService;
    /**
     * @return
     */
    @RequestMapping(value = "/getStockByCode",method = RequestMethod.GET)
    @ApiOperation(value="根据股票代码获得股票详细信息", notes="")
    public Response getStockByCode(@ApiParam(value = "股票代码")@RequestParam String code){
        return stockInfoService.getStockInfoByCode(code);
    }


    @RequestMapping(value = "/getStockInfoByName",method = RequestMethod.GET)
    @ApiOperation(value="根据股票名称获得股票详细信息", notes="")
    public Response getStockInfoByName(@ApiParam(value = "名称")@RequestParam String name){
        return stockInfoService.getStockInfoByName(name);
    }

    @RequestMapping(value = "/getStockByArea",method = RequestMethod.GET)
    @ApiOperation(value="根据区域获得股票详细信息", notes="")
    public Response getStockByArea(@ApiParam(value = "股票所属区域")@RequestParam String area){
        return stockInfoService.getStockInfoByArea(area);
    }


    @RequestMapping(value = "/getStockInfoByEalComponent",method = RequestMethod.GET)
    @ApiOperation(value="根据股票市场获得股票详细信息", notes="")
    public Response getStockInfoByEalComponent(@ApiParam(value = "市场")@RequestParam String ealComponent){
        return stockInfoService.getStockInfoByEalComponent(ealComponent);
    }


    @RequestMapping(value = "/getStockInfoByShareType",method = RequestMethod.GET)
    @ApiOperation(value="根据股票盘类型获得股票详细信息", notes="")
    public Response getStockInfoByShareType(@ApiParam(value = "股盘")@RequestParam String shareType){
        return stockInfoService.getStockInfoByShareType(shareType);
    }

    @RequestMapping(value = "/getStockInfoByIndustry",method = RequestMethod.GET)
    @ApiOperation(value="根据股票所属行业获得股票详细信息", notes="")
    public Response getStockInfoByIndustry(@ApiParam(value = "行业")@RequestParam String industry){
        return stockInfoService.getStockInfoByIndustry(industry);
    }

    @RequestMapping(value = "/getStockCodeByIndustry",method = RequestMethod.GET)
    @ApiOperation(value="根据股票所属行业获得股票代码", notes="")
    public Response getStockCodeByIndustry(@ApiParam(value = "行业")@RequestParam String industry){
        return stockInfoService.getStockCodeByIndustry(industry);
    }


    @RequestMapping(value = "/getStockInfoByConcept",method = RequestMethod.GET)
    @ApiOperation(value="根据股票概念获得股票详细信息", notes="")
    public Response getStockInfoByConcept(@ApiParam(value = "概念")@RequestParam String concept){
        return stockInfoService.getStockInfoByConcept(concept);
    }

    @RequestMapping(value = "/getStockCodeByConcept",method = RequestMethod.GET)
    @ApiOperation(value="根据股票概念获得股票代码", notes="")
    public Response getStockCodeByConcept(@ApiParam(value = "概念")@RequestParam String concept){
        return stockInfoService.getStockCodeByConcept(concept);
    }


    @RequestMapping(value = "/getStockCodeByName",method = RequestMethod.GET)
    @ApiOperation(value="根据股票名称获得股票详细信息", notes="")
    public Response getStockCodeByName(@ApiParam(value = "名称")@RequestParam String name){
        return stockInfoService.getStockCodeByName(name);
    }

    @RequestMapping(value = "/getStockCodeByArea",method = RequestMethod.GET)
    @ApiOperation(value="根据区域获得股票详细信息", notes="")
    public Response getStockCodeByArea(@ApiParam(value = "股票所属区域")@RequestParam String area){
        return stockInfoService.getStockCodeByArea(area);
    }


    @RequestMapping(value = "/getStockCodeByEalComponent",method = RequestMethod.GET)
    @ApiOperation(value="根据股票市场获得股票详细信息", notes="")
    public Response getStockCodeByEalComponent(@ApiParam(value = "市场")@RequestParam String ealComponent){
        return stockInfoService.getStockCodeByEalComponent(ealComponent);
    }


    @RequestMapping(value = "/getStockCodeByShareType",method = RequestMethod.GET)
    @ApiOperation(value="根据股票盘类型获得股票详细信息", notes="")
    public Response getStockCodeByShareType(@ApiParam(value = "股盘")@RequestParam String shareType){
        return stockInfoService.getStockCodeByShareType(shareType);
    }




}

