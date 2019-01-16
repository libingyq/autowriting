//package com.dinfo.fi.task;
//
//import com.dinfo.common.MapBean;
//import com.dinfo.common.autoconfig.AutoConfigManager;
//import com.dinfo.fi.entity.Sina;
//import com.dinfo.fi.utils.TimeUtils;
//import com.dinfo.hbase.MapBeanHbaseDao;
//import com.dinfo.hbase.MapBeanHbaseDaoFactory;
//import com.dinfo.hbase.security.HbaseAuthenticationUtil;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.CellType;
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.*;
//
///**
// * @auther rongzihao
// * @date 2018/10/22 16:19
// */
//
////@Component
//public class JsoupTask {
//
//    @Value("${producer.kbs-path}")
//    private String kbs_path;
//
//    @Value("${producer.file-path}")
//    private String file_path;
//
//    @Scheduled(cron = "0 0 15 * * 7")
//    public void getSinaFinanceDaPanNews() throws Exception{
//        List<Sina> sina_list = new ArrayList<>();
//        boolean flag = false;
//        for(int i = 1; i < 100; i++){
//            if(flag){
//                break;
//            }
//            try{
//                String path_sina = "http://roll.finance.sina.com.cn/finance/zq1/gsjsy/index_"+ i + ".shtml";
//                Connection con = Jsoup.connect(path_sina);
//                Document doc = con.ignoreContentType(true).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:55.0) Gecko/20100101 Google/55.0").timeout(5000).get();
//                Elements elements = doc.select("li");
//                int j = 0;
//                for(Element element : elements){
//                    j++;
//                    try{
//                        if(j < 3){
//                            continue;
//                        }
//                        Sina sina = new Sina();
//                        //标题
//                        String title = element.children().get(0).text();
//                        //来源
//                        String url = element.children().get(0).attr("href");
//                        //获取结束时间
//                        String nowTime = TimeUtils.getDate(0);
//                        int nowTime_  = Integer.valueOf(TimeUtils.strToTimestamp(nowTime +" 23:59:59"));
//                        //获取前七天时间
//                        String beforeTime = TimeUtils.getDate(-7);
//                        int beforeTime_  = Integer.valueOf(TimeUtils.strToTimestamp(beforeTime +" 00:00:00"));
//                        //发布时间
//                        String releaseTime = element.children().get(1).text().substring(1,element.children().get(1).text().length() -1);
//                        if(releaseTime.contains("年") || releaseTime.contains("月") || releaseTime.contains("日")){
//                            releaseTime = releaseTime.replace("年","-");
//                            releaseTime = releaseTime.replace("月","-");
//                            releaseTime = releaseTime.replace("日","");
//                        }
//                        if(!releaseTime.contains("年")){
//                            releaseTime = "2018-" + releaseTime;
//                        }
//                        int releaseTime_  = Integer.valueOf(TimeUtils.strToTimestamp(releaseTime+":00"));
//                        if(releaseTime_ < beforeTime_){
//                            flag = true;
//                            break;
//                        }
//                        if(releaseTime_ >=beforeTime_ &&  releaseTime_ <= nowTime_){
//                            if(StringUtils.isEmpty(url)){
//                                continue;
//                            }
//                            System.out.println(url);
//                            Connection con1 = Jsoup.connect(url);
//                            Document doc1 = con1.ignoreContentType(true).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:55.0) Gecko/20100101 Firefox/55.0").timeout(5000).get();
//                            if(doc1 == null){
//                                continue;
//                            }
//                            //分类
//                            String classification = "大盘";
//                            String text = doc1.getElementById("artibody").select("p").text();
//                            if(StringUtils.isEmpty(text)){
//                                continue;
//                            }
//                            System.out.println(text);
//                            sina.setClassification(classification);
//                            sina.setTitle(title);
//                            //采集时间
//                            sina.setCollectionTime(TimeUtils.getTimestampStr());
//                            //发布时间
//                            sina.setReleaseTime(releaseTime);
//                            sina.setUrl(url);
//                            sina.setText(text);
//                            sina_list.add(sina);
//
//                        }
//                    }catch (Exception e){
//                        continue;
//                    }
//                }
//            }catch (Exception e){
//                continue;
//            }
//        }
//        writeExcelSina(sina_list);
//        insertModels(sina_list);
//    }
//
//
//    public void insertModels(List<Sina> sina_list) throws Exception {
//        HbaseAuthenticationUtil.setConfigFilePathAndIsEnabled(kbs_path, true);
//
//        Map<String, String> configMap = new HashMap<String, String>();
//        configMap.put("test.kerberos.ip", "192.168.181.197:2181,192.168.181.198:2181,192.168.181.199:2181");
////        configMap.put("test.kerberos.ip", "hadoop4:2181,hadoop5:2181,hadoop6:2181");
//        AutoConfigManager.loadAutoConfig(configMap);
//
//        MapBean mapBean = MapBean.buildTableName("news")
//                .buildPrimaryKey("rowkey:rowkey");
//
//        String prefix = "test.kerberos";
//
//        MapBeanHbaseDao dao = MapBeanHbaseDaoFactory.createMapBeanHbaseDao(prefix);
//        dao.setMapBean(mapBean);
//
//        //动态构造要插入的数据集
//        List<MapBean> models = new ArrayList<MapBean>();
//
//        for(Sina sina : sina_list){
//            Map<String, Object> columnValueMap = new HashMap<String, Object>();
//            String rowkey = UUID.randomUUID().toString();
//            columnValueMap.put("rowkey:rowkey",rowkey);
//            columnValueMap.put("A:classification",sina.getClassification());
//            columnValueMap.put("A:title",sina.getTitle());
//            columnValueMap.put("A:releaseTime",sina.getReleaseTime());
//            columnValueMap.put("A:source",sina.getUrl());
//            columnValueMap.put("A:content",sina.getText());
//            //采集时间
//            columnValueMap.put("A:collectionTime",sina.getCollectionTime());
//            //动态指定要插入的数据集
//            MapBean model = MapBean.buildTableName("news").buildPrimaryKey("rowkey:rowkey").buildColumnValueMap(columnValueMap);
//            models.add(model);
//        }
//        //执行批量插入操作
//        dao.insert(models);
//        System.out.println("批量插入成功!");
//    }
//
//
//    /**
//     * 导出数据
//     * @throws IOException
//     */
//    public void writeExcelSina(List<Sina> sina_list) throws IOException {
//        //创建工作簿
//        HSSFWorkbook workBook = new HSSFWorkbook();
//        //创建工作表  工作表的名字叫helloWorld
//        HSSFSheet sheet = workBook.createSheet("sina_news");
//        HSSFRow row = sheet.createRow(0);
//        // 第四步，创建单元格，并设置值表头 设置表头居中
//        HSSFCell cell1 = row.createCell(0, CellType.STRING);
//        cell1.setCellValue("标题");
//        HSSFCell cell2 = row.createCell(1, CellType.STRING);
//        cell2.setCellValue("分类");
//        HSSFCell cell3 = row.createCell(2, CellType.STRING);
//        cell3.setCellValue("发布时间");
//        HSSFCell cell4 = row.createCell(3, CellType.STRING);
//        cell4.setCellValue("采集时间");
//        HSSFCell cell5 = row.createCell(4, CellType.STRING);
//        cell5.setCellValue("来源");
//        HSSFCell cell6 = row.createCell(5, CellType.STRING);
//        cell6.setCellValue("内容");
//
//        int j = 1;
//        for (int i = 0; i < sina_list.size(); i++) {
//            row = sheet.createRow(j++);
//            //标题
//            HSSFCell cell11 = row.createCell(0, CellType.STRING);
//            cell11.setCellValue(sina_list.get(i).getTitle());
//            //分类
//            HSSFCell cell22 = row.createCell(1, CellType.STRING);
//            cell22.setCellValue(sina_list.get(i).getClassification());
//            //发布时间
//            HSSFCell cell33 = row.createCell(2, CellType.STRING);
//            cell33.setCellValue(sina_list.get(i).getReleaseTime());
//            //采集时间
//            HSSFCell cell44 = row.createCell(3, CellType.STRING);
//            cell44.setCellValue(sina_list.get(i).getCollectionTime());
//            //来源
//            HSSFCell cell55 = row.createCell(4, CellType.STRING);
//            cell55.setCellValue(sina_list.get(i).getUrl());
//            //内容
//            HSSFCell cell66 = row.createCell(5, CellType.STRING);
//            if (sina_list.get(i).getText().length() > 32767) {
//                String text = sina_list.get(i).getText();
//                text = text.substring(0, 32000);
//                cell66.setCellValue(text);
//            } else {
//                cell66.setCellValue(sina_list.get(i).getText());
//            }
//        }
//        String title = TimeUtils.getFileNameNew();
//        String path = file_path + title + ".xls";
//        workBook.write(new File( path));
//        workBook.close();//最后记得关闭工作簿
//    }
//}
