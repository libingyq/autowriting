package com.dinfo.fi;

import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.func.TriadModel;
import com.dinfo.fi.func.form.cul.Report;
import com.dinfo.fi.func.form.cul.UpRate;
import com.dinfo.fi.func.object.GrabData;
import com.dinfo.fi.func.time.MorningSession;
import com.dinfo.fi.spy.FuncLocator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.dinfo.fi.inter.base.FuncInterface.DATA_KEY;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UltraNlgFiFuncApplicationTests {

    @Test
    public void getAllFunc(){
        FuncLocator funcLocator = new FuncLocator();
        Map<FuncType, List<String>> allFuncNameByType = funcLocator.getAllFuncNameByType();
        for (Map.Entry<FuncType, List<String>> funcTypeListEntry : allFuncNameByType.entrySet()) {
            List<String> value = funcTypeListEntry.getValue();
            for (String s : value) {
                System.out.println(s);
            }
            System.out.println("=============");
        }
    }

	@Test
	public void contextLoads() {

		String exp = "今日早盘+603986+[低开高走+涨幅]";

		String trendExp = "2018-10-17{早盘}+stock:603986+低开高走";

		String rateExp = "2018-10-17{早盘}+stock:603986+涨幅";

        String regex = "\\+";
        String[] split = trendExp.split(regex);
        String time = split[0];
        String object = split[1];
        String trend = split[2];

        MorningSession morningSession = new MorningSession();
        HashMap<String, Object> input = new HashMap<>();
        input.put(DATA_KEY,new Date());
        morningSession.run(input);




    }



    @Test
    public void contextLoads_testTemplate() {

        StringBuffer param = new StringBuffer();
        param.append("<{早盘}+[{index:399106},{stock:603978}]+[{高开高走},{涨幅},{跌幅}]>")
                    .append("+")
                    .append("<{截止发稿}+[{index:000002}]+[{报点}]>");

    }


    @Test
    public void contextLoads_testTemplate2() {

        ArrayList<String> paragraph = new ArrayList<>();
        paragraph.add("<{早盘}+[{index:399106},{stock:603978}]+[{高开高走},{涨幅},{跌幅}]>");
        paragraph.add("<{截止发稿}+[{index:000002}]+[{报点}]>");
    }

    @Test
    public void contextLoads_testTemplate3(){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar instance = Calendar.getInstance();
        instance.set(2018,9,17);
        Date time = instance.getTime();
        System.out.println(format.format(time));

        ArrayList<TriadModel> triadModels = new ArrayList<>();

        TriadModel triadModel = new TriadModel(SpringUtil.getBean(MorningSession.class),
                SpringUtil.getBean(GrabData.class), SpringUtil.getBean(UpRate.class), "index:399001");

        triadModels.add(triadModel);



        TriadModel triadModel2 = new TriadModel(SpringUtil.getBean(MorningSession.class),
                SpringUtil.getBean(GrabData.class), SpringUtil.getBean(UpRate.class), "stock:603977");

        TriadModel triadModel3 = new TriadModel(SpringUtil.getBean(MorningSession.class),
                SpringUtil.getBean(GrabData.class), SpringUtil.getBean(Report.class), "stock:603977");

        triadModels.add(triadModel2);
        triadModels.add(triadModel3);


        List<Map> collect = triadModels.stream().map(triad -> triad.run(time)).collect(Collectors.toList());
        for (Map map : collect) {
            System.out.println(map);

        }


    }

}
