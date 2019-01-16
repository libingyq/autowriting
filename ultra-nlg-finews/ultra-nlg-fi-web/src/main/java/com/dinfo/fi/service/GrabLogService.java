package com.dinfo.fi.service;


import com.dinfo.fi.dto.GrabStatusDto;
import com.dinfo.fi.enums.ShareType;
import com.dinfo.fi.utils.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "ultra-nlg-fi-resource")
@RequestMapping(value = "/grabLog")
public interface GrabLogService {

    @RequestMapping(value = "/getGrabStatus", method = RequestMethod.GET)
    Response<Map<ShareType,GrabStatusDto>> getGrabStatus();
}
