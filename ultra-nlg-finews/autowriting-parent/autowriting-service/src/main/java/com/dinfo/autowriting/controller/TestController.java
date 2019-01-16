package com.dinfo.autowriting.controller;

import com.dinfo.autowriting.core.id.IdGenerator;
import com.dinfo.autowriting.core.util.ObjectUtil;
import com.dinfo.autowriting.entity.*;
import com.dinfo.autowriting.index.DocDataIndex;
import com.dinfo.autowriting.mapper.*;
import com.dinfo.autowriting.service.DocDataIndexService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yangxf
 */
@RestController
@RequestMapping("/test/")
@Slf4j
public class TestController {

    @Resource
    private IdGenerator idGenerator;

    @Resource
    private DocGenreInfoMapper docGenreInfoMapper;

    @Resource
    private DocSourceInfoMapper docSourceInfoMapper;

    @Resource
    private FuncTagResourceMapper funcTagResourceMapper;

    @Resource
    private SrcDataInfoMapper srcDataInfoMapper;

    @Resource
    private VarTagResourceMapper varTagResourceMapper;


    @GetMapping("getId")
    public String nextId() {
        return "id : " + idGenerator.nextId();
    }

    @Transactional
    @PostMapping("all")
    public Wrapper add(@RequestBody Wrapper wrapper) {
        log.info(wrapper.toString());

        DocGenreInfo info1 = wrapper.getInfo1();
        info1.setGenreId(idGenerator.nextId());
        docGenreInfoMapper.add(info1);

        DocSourceInfo info2 = wrapper.getInfo2();
        info2.setSourceId(idGenerator.nextId());
        docSourceInfoMapper.add(info2);

        FuncTagResource info3 = wrapper.getInfo3();
        info3.setSourceId(idGenerator.nextId());
        funcTagResourceMapper.add(info3);

        SrcDataInfo info4 = wrapper.getInfo4();
        info4.setSourceId(idGenerator.nextId());
        srcDataInfoMapper.add(info4);

        VarTagResource info5 = wrapper.getInfo5();
        info5.setSourceId(idGenerator.nextId());
        varTagResourceMapper.add(info5);

        return wrapper;
    }

    @Transactional
    @PutMapping("all")
    public Wrapper modify(@RequestBody Wrapper wrapper) {
        log.info(wrapper.toString());

        DocGenreInfo info1 = wrapper.getInfo1();
        info1.setGenreId(idGenerator.nextId());
        docGenreInfoMapper.modify(info1);

        DocSourceInfo info2 = wrapper.getInfo2();
        info2.setSourceId(idGenerator.nextId());
        docSourceInfoMapper.modify(info2);

        FuncTagResource info3 = wrapper.getInfo3();
        info3.setSourceId(idGenerator.nextId());
        funcTagResourceMapper.modify(info3);

        SrcDataInfo info4 = wrapper.getInfo4();
        info4.setSourceId(idGenerator.nextId());
        srcDataInfoMapper.modify(info4);

        VarTagResource info5 = wrapper.getInfo5();
        info5.setSourceId(idGenerator.nextId());
        varTagResourceMapper.modify(info5);

        return wrapper;
    }

    
    @GetMapping("all")
    public Wrapper find(IdWrapper idWrapper) {

        Wrapper wrapper = new Wrapper();

        wrapper.setInfo1(docGenreInfoMapper.findByGenreId(idWrapper.getId1()));

        wrapper.setInfo2(docSourceInfoMapper.findBySourceId(idWrapper.getId2()));

        wrapper.setInfo3(funcTagResourceMapper.findByFuncTagId(idWrapper.getId3()));

        wrapper.setInfo4(srcDataInfoMapper.findByDocId(idWrapper.getId4()));

        wrapper.setInfo5(varTagResourceMapper.findByVarTagId(idWrapper.getId5()));

        return wrapper;
    }

    @Transactional
    @DeleteMapping("all")
    public String remove(@RequestBody IdWrapper idWrapper) {
        docGenreInfoMapper.removeById(idWrapper.getId1());

        docSourceInfoMapper.removeBySourceId(idWrapper.getId2());

        funcTagResourceMapper.removeByFuncTagId(idWrapper.getId3());

        srcDataInfoMapper.removeByDocId(idWrapper.getId4());

        varTagResourceMapper.removeByVarTagId(idWrapper.getId5());

        return "success";
    }

    @PostMapping("srcDataInfo")
    public SrcDataInfo addSrcDataInfo(@RequestBody SrcDataInfo info) {
        log.info(info.toString());

        srcDataInfoMapper.add(info);

        return info;
    }


    @Resource
    private DocDataIndexService docDataIndexService;
    
    @PostMapping("docDataIndex")
    public DocDataIndex addDocDataIndex(@RequestBody DocDataIndex index) {
        return docDataIndexService.create(index);
    }

    @GetMapping("docDataIndex")
    public DocDataIndex findDocDataIndex(String id) {
        return docDataIndexService.retrieveById(id);
    }

    @GetMapping("docDataIndex/{field}/{exp}")
    public List<DocDataIndex> queryDocDataIndex(@PathVariable("field") String field, @PathVariable("exp") String exp) {
        return docDataIndexService.query(field, exp);
    }

    @Data
    public static class IdWrapper {
        Long id1, id2, id3, id4, id5;
    }

    @Data
    public static class Wrapper {
        DocGenreInfo info1;
        DocSourceInfo info2;
        FuncTagResource info3;
        SrcDataInfo info4;
        VarTagResource info5;
    }

}
