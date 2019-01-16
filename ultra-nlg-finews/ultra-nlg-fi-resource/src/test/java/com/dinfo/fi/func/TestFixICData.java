package com.dinfo.fi.func;

import com.dinfo.fi.entity.ConceptData;
import com.dinfo.fi.utils.RestApiClient;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * <p>Date:2018/12/10</p>
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
public class TestFixICData {

    @Test
    public void grepIndustryData(){
        String url = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&token=4f1862fc3b5e77c150a2b985b12db0fd&sty=FPGBKI&cmd=C._BKHY";
        String access = RestApiClient.getAccess(url, new HashMap<>());
        String[] split = access.split("\",\"");
        System.out.println(split);
    }

    @Test
    public void grepConceptData() throws ParseException {
        String url = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&token=4f1862fc3b5e77c150a2b985b12db0fd&sty=FPGBKI&cmd=C._BKGN";
        String access = RestApiClient.getAccess(url, new HashMap<>());
        access = access.substring(3,access.length()-3);
        String[] split = access.split("\",\"");
        System.out.println(split);
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (String s : detailSplit) {
            String[] singleDetails = s.split(",");
            System.out.println(singleDetails);
            String code = singleDetails[1];
            ConceptData conceptData = map.get(code);
            conceptData.setTime(new Timestamp(format.parse(singleDetails[3]).getTime()));
            conceptData.setClose(Double.parseDouble(singleDetails[4]));
            conceptData.setChangePercent(Double.parseDouble(singleDetails[5]));
            conceptData.setOpen(Double.parseDouble(singleDetails[7]));
            conceptData.setHigh(Double.parseDouble(singleDetails[8]));
            conceptData.setSumNum(Long.parseLong(singleDetails[9]));
            conceptData.setSwing(Double.parseDouble(singleDetails[12]));
            conceptData.setSettlement(Double.parseDouble(singleDetails[13]));
            conceptData.setLow(Double.parseDouble(singleDetails[14]));
            conceptData.setTurnOverRate(Double.parseDouble(singleDetails[23]));


        }



        System.out.println(map);

    }


}
