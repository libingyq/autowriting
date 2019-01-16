package com.dinfo.fi.controller;

import com.dinfo.fi.dto.GrabStatusDto;
import com.dinfo.fi.dto.TemplateAllContent;
import com.dinfo.fi.entity.SemiTemplate;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.enums.GrabStatusType;
import com.dinfo.fi.enums.ShareType;
import com.dinfo.fi.service.FuncService;
import com.dinfo.fi.service.GrabLogService;
import com.dinfo.fi.service.SemiTemplateService;
import com.dinfo.fi.service.TemplateService;
import com.dinfo.fi.utils.Response;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Date:2018/1/23</p>
 * <p>Module:</p>
 * <p>Description: </p>
 * <p>Remark: </p>
 *
 * @author wuxiangbo
 * @version 1.0
 * <p>------------------------------------------------------------</p>
 * <p> Change history</p>
 * <p> Serial number: date:modified person: modification reason:</p>
 */
@Controller
@Slf4j
public class PageController {

    @Autowired
    private FuncService funcService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private GrabLogService grabLogService;

    @Autowired
    private SemiTemplateService semiTemplateService;

    @RequestMapping("tem")
    public String goTem(HttpServletRequest request) {
        return "template";
    }


    @RequestMapping(value = {
            "/",
            "index",
            "index.html"
    })
    public String goIndex(HttpServletRequest request, Model model) {
        Response<Map<ShareType, GrabStatusDto>> grabLogServiceLastGrabStatus = grabLogService.getGrabStatus();
        if (grabLogServiceLastGrabStatus.isSuccess()) {
            for (ShareType shareType : ShareType.values()) {
                if (grabLogServiceLastGrabStatus.getData().get(shareType) != null) {
                    model.addAttribute(shareType.getType().toLowerCase() + "Status", grabLogServiceLastGrabStatus.getData().get(shareType).getGrabStatusType().getDiscription());
                    model.addAttribute(shareType.getType().toLowerCase() + "LastGrabTime", grabLogServiceLastGrabStatus.getData().get(shareType).getGrabTime());
                }else {
                    model.addAttribute(shareType.getType().toLowerCase() + "Status", GrabStatusType.FAILED.getDiscription());
                    model.addAttribute(shareType.getType().toLowerCase() + "LastGrabTime", "unknown");
                }
            }
        }
        return "index";
    }


    @RequestMapping("selfDefineDataBase")
    public String goSelfDefine(HttpServletRequest request) {
        request.getSession().setAttribute("list", templateService.getAllTemplate().getData());

        return "selfDefine";
    }
    @RequestMapping("chooseObj")
    public String chooseObj(HttpServletRequest request) {


        request.getSession().setAttribute("list", semiTemplateService.getAllTemplate().getData());


        return "chooseObj";
    }


    @RequestMapping("createTemplate")
    public String createTemplate(HttpServletRequest request) {
        if (request.getSession().getAttribute(FuncType.TimeFunc.toString()) != null) {
            return "createTemplate";
        }
        Response<Map<FuncType, List<String>>> allFunc = funcService.getAllFunc();
        Map<FuncType, List<String>> data = allFunc.getData();
        request.getSession().setAttribute(FuncType.TimeFunc.toString(), data.get(FuncType.TimeFunc));
        request.getSession().setAttribute(FuncType.UpperFunc.toString(), data.get(FuncType.UpperFunc));

        return "createTemplate";
    }


    @RequestMapping("useTem")
    public String useTem(HttpServletRequest request,Integer temId) {

        Response contentByTemplate = funcService.createContentByTemplate(temId);
        Response<TemplateAllContent> templateById = templateService.getTemplateById(temId);
        TemplateAllContent temp = templateById.getData();


        String data = (String)contentByTemplate.getData();
        String[] split = data.split("\n");
        ArrayList<String> strings = new ArrayList<>();
        for (String s : split) {
            if(!StringUtils.isBlank(s)){
                strings.add(s);
            }
        }
        request.getSession().setAttribute("content", strings);
        request.getSession().setAttribute("title",temp.getTemplateName());

        return "useTem";
    }



    @RequestMapping("completeTemplate")
    public String completeTemplate(HttpServletRequest request,Integer temId) {

        Response<SemiTemplate> templateById = semiTemplateService.getTemplateById(temId);
        SemiTemplate data = templateById.getData();
        request.getSession().setAttribute("name",data.getTemplateName());
        request.getSession().setAttribute("template",data.getTemplate());

        return "completeTemplate";
    }


}
