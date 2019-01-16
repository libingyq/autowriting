package com.dinfo.fi.func;

import com.dinfo.fi.dao.*;
import com.dinfo.fi.entity.*;
import com.dinfo.fi.service.in.ConceptDicService;
import com.dinfo.fi.service.in.IndustryDicService;
import com.dinfo.fi.utils.Response;
import com.dinfo.fi.utils.RestApiClient;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Date:2018/12/7</p>
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
@RunWith(SpringRunner.class)
@SpringBootTest
public class RebuildKnowlegeBase {

    String bkhy = "_BKHY";
    String bkgn = "_BKGN";

    @Autowired
    private ConceptDicDao conceptDicDao;
    @Autowired
    private IndustryDicDao industryDicDao;

    public List<String> getAllTagByLabel(String label){

        String getAllIndustryUrl = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&token=4f1862fc3b5e77c150a2b985b12db0fd&sty=FPGBKI&cmd=C." + label;
        String access = RestApiClient.getAccess(getAllIndustryUrl, new HashMap<>());

        Pattern compile = Pattern.compile("BK\\d{4},.*?,");
        Matcher matcher = compile.matcher(access);
        ArrayList<String> strings = new ArrayList<>();
        while (matcher.find()){
            strings.add(matcher.group());

        }

        return strings;
    }

    public HashMap<String, List<String>> getAllTagsStock(String label){

        String url = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&token=4f1862fc3b5e77c150a2b985b12db0fd&sty=FCOIATC&cmd=C.";

        List<String> allIndustryCode = getAllTagByLabel(label);

        HashMap<String, List<String>> stepMap = new HashMap<>();

        Pattern compile = Pattern.compile("\"\\d,\\d{6},.*?,");

        for (String s : allIndustryCode) {
            String[] split = s.split(",");
            String code = split[0] + "1";
            String name = split[1];

            String completeUrl = url + code;
            String access = RestApiClient.getAccess(completeUrl, new HashMap<>());

            ArrayList<String> strings = new ArrayList<>();

            Matcher matcher = compile.matcher(access);
            while (matcher.find()){
                String group = matcher.group();
                strings.add(group);
            }
            stepMap.put(s,strings);

        }

        return stepMap;
    }

    @Test
    public void consistQueryResult(){
        HashMap<String, List<String>> industry = getAllTagsStock(bkhy);
        HashMap<String, List<String>> concept = getAllTagsStock(bkgn);


        ArrayList<ConceptDic> conceptDics = new ArrayList<ConceptDic>();
        for (Map.Entry<String, List<String>> stringListEntry : concept.entrySet()) {
            String key = stringListEntry.getKey();
            String[] split = key.split(",");
            ConceptDic conceptDic = new ConceptDic();
            conceptDic.setId(split[0]);
            conceptDic.setName(split[1]);
            List<String> value = stringListEntry.getValue();
            StringBuffer stringBuffer = new StringBuffer();
            for (String s : value) {
                String[] stock = s.split(",");
                stringBuffer.append(stock[1]).append(",");
            }
            String substring = stringBuffer.substring(0, stringBuffer.length() - 1);
            conceptDic.setContainsCode(substring);
            conceptDics.add(conceptDic);
        }


        conceptDicDao.insert(conceptDics);


        ArrayList<IndustryDic> industryDics = new ArrayList<IndustryDic>();
        for (Map.Entry<String, List<String>> stringListEntry : industry.entrySet()) {
            String key = stringListEntry.getKey();
            String[] split = key.split(",");
            IndustryDic conceptDic = new IndustryDic();
            conceptDic.setId(split[0]);
            conceptDic.setName(split[1]);
            List<String> value = stringListEntry.getValue();
            StringBuffer stringBuffer = new StringBuffer();
            for (String s : value) {
                String[] stock = s.split(",");
                stringBuffer.append(stock[1]).append(",");
            }
            String substring = stringBuffer.substring(0, stringBuffer.length() - 1);
            conceptDic.setContainsCode(substring);
            industryDics.add(conceptDic);
        }

        industryDicDao.insert(industryDics);


        System.out.println("==========");
    }

    @Autowired
    private IndustryDicService industryDicService;
    @Autowired
    private StockInfoDao stockInfoDao;
    @Autowired
    private ConceptDicService conceptDicService;

    @Test
    public void updateKownlegeBase(){

        List<StockInfo> stockInfos = stockInfoDao.queryList();
        for (StockInfo stockInfo : stockInfos) {
            String code = stockInfo.getCode();
            Response<List> conceptsByCode = conceptDicService.getConceptsByCode(code);
            List<ConceptDic> data = conceptsByCode.getData();
            StringBuffer conceptNames = new StringBuffer();
            StringBuffer conceptIds = new StringBuffer();
            if(data == null){
                data = new ArrayList<>();
            }
            for (ConceptDic datum : data) {
                String name = datum.getName();
                String id = datum.getId();
                conceptNames.append(name).append(",");
                conceptIds.append(id).append(",");
            }
            if(conceptIds.length() > 0){
                String conceptIdStr = conceptIds.substring(0, conceptIds.length() - 1);
                String conceptNameStr = conceptNames.substring(0, conceptNames.length() - 1);
                stockInfo.setConcepts(conceptNameStr);
                stockInfo.setConceptsIds(conceptIdStr);

            }else {
                stockInfo.setConcepts("");
                stockInfo.setConceptsIds("");
            }
            Response<List> industryByCode = industryDicService.getIndustryByCode(code);
            List<IndustryDic> industryData = industryByCode.getData();
            if(industryData == null){
                industryData = new ArrayList<>();
            }
            StringBuffer industryIds = new StringBuffer();
            StringBuffer industryNames = new StringBuffer();
            for (IndustryDic datum : industryData) {
                String name = datum.getName();
                String id = datum.getId();
                industryIds.append(id).append(",");
                industryNames.append(name).append(",");
            }
            if(industryIds.length() > 0){
                stockInfo.setIndustry(industryNames.substring(0,industryNames.length() - 1));
                stockInfo.setIndustryIds(industryIds.substring(0,industryIds.length() - 1));
            }else {
                stockInfo.setIndustryIds("");
                stockInfo.setIndustry("");
            }

            Boolean update = stockInfoDao.update(stockInfo);
            System.out.println(update);


        }


    }

    @Autowired
    private IndustryDataDao industryDataDao;
    @Autowired
    private ConceptDataDao conceptDataDao;


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
        Collection<ConceptData> values = map.values();
        List<ConceptData> insert = conceptDataDao.insert(new ArrayList<>(values));
        System.out.println(insert);

    }



    @Test
    public void grepIndustryData() throws ParseException {
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (String s : detailSplit) {
            String[] singleDetails = s.split(",");
            System.out.println(singleDetails);
            String code = singleDetails[1];
            IndustryData industryData = map.get(code);
            industryData.setTime(new Timestamp(format.parse(singleDetails[3]).getTime()));
            industryData.setClose(Double.parseDouble(singleDetails[4]));
            industryData.setChangePercent(Double.parseDouble(singleDetails[5]));
            industryData.setOpen(Double.parseDouble(singleDetails[7]));
            industryData.setHigh(Double.parseDouble(singleDetails[8]));
            industryData.setSumNum(Long.parseLong(singleDetails[9]));
            industryData.setSwing(Double.parseDouble(singleDetails[12]));
            industryData.setSettlement(Double.parseDouble(singleDetails[13]));
            industryData.setLow(Double.parseDouble(singleDetails[14]));
            industryData.setTurnOverRate(Double.parseDouble(singleDetails[23]));


        }



        System.out.println(map);
        Collection<IndustryData> values = map.values();
        List<IndustryData> insert = industryDataDao.insert(new ArrayList<>(values));
        System.out.println(insert);

    }





}
