package com.dinfo.fi.task.eastmoney;

import com.dinfo.fi.dao.ConceptDataDao;
import com.dinfo.fi.dao.IndustryDataDao;
import com.dinfo.fi.entity.ConceptData;
import com.dinfo.fi.entity.IndustryData;
import com.dinfo.fi.utils.RestApiClient;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * <p>Date:2018/12/11</p>
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
@Component
public class IndustryAndConceptGraber {
    @Autowired
    private IndustryDataDao industryDataDao;
    @Autowired
    private ConceptDataDao conceptDataDao;



    public boolean grabConceptData() throws ParseException {
        String url = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&token=4f1862fc3b5e77c150a2b985b12db0fd&sty=FPGBKI&cmd=C._BKGN";
        String access = RestApiClient.getAccess(url, new HashMap<>());
        access = access.substring(3,access.length()-3);
        String[] split = access.split("\",\"");
        StringBuffer conceptIds = new StringBuffer();
        HashMap<String, ConceptData> map = Maps.newHashMap();
        for (int i = 0; i < split.length; i++) {

            String[] shapes = split[i].split(",");

            ConceptData build = ConceptData.builder().code(shapes[1]).name(shapes[2]).infos(shapes[6])
                    .leadUp(shapes[7] + "|" + shapes[9] + "|" + shapes[10] + "|" + shapes[11])
                    .leadDown(shapes[12] + "|" + shapes[14] + "|" + shapes[15] + "|" + shapes[16]).build();
            map.put(build.getCode(),build);
            conceptIds.append(build.getCode()+"1").append(",");


        }

        String detailUrl = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd="+conceptIds+"&sty=FDPBPFB&token=7bc05d0d4c3c22ef9fca8c2a912d779c";

        String details = RestApiClient.getAccess(detailUrl, new HashMap<>());
        details = details.substring(3,details.length()-3);
        String[] detailSplit = details.split("\",\"");
        for (String s : detailSplit) {
            String[] singleDetails = s.split(",");
            String code = singleDetails[1];
            ConceptData conceptData = map.get(code);
            conceptData.fixData(s);


        }

        Collection<ConceptData> values = map.values();
        List<ConceptData> insert = conceptDataDao.insert(new ArrayList<>(values));
        return split.length == insert.size();
    }


    public boolean grabIndustryData() throws ParseException {
        String url = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&token=4f1862fc3b5e77c150a2b985b12db0fd&sty=FPGBKI&cmd=C._BKHY";
        String access = RestApiClient.getAccess(url, new HashMap<>());
        access = access.substring(3,access.length()-3);
        String[] split = access.split("\",\"");
        System.out.println(split);
        StringBuffer industryIds = new StringBuffer();
        HashMap<String, IndustryData> map = Maps.newHashMap();
        for (int i = 0; i < split.length; i++) {

            String[] shapes = split[i].split(",");

            IndustryData build = IndustryData.builder().code(shapes[1]).name(shapes[2]).infos(shapes[6])
                    .leadUp(shapes[7] + "|" + shapes[9] + "|" + shapes[10] + "|" + shapes[11])
                    .leadDown(shapes[12] + "|" + shapes[14] + "|" + shapes[15] + "|" + shapes[16]).build();
            map.put(build.getCode(),build);
            industryIds.append(build.getCode()+"1").append(",");

        }

        String detailUrl = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd="+industryIds+"&sty=FDPBPFB&token=7bc05d0d4c3c22ef9fca8c2a912d779c";

        String details = RestApiClient.getAccess(detailUrl, new HashMap<>());
        details = details.substring(3,details.length()-3);
        String[] detailSplit = details.split("\",\"");
        for (String s : detailSplit) {
            String[] singleDetails = s.split(",");
            String code = singleDetails[1];
            IndustryData industryData = map.get(code);
            industryData.fixData(s);

        }

        Collection<IndustryData> values = map.values();
        List<IndustryData> insert = industryDataDao.insert(new ArrayList<>(values));
        return split.length == insert.size();

    }

}
