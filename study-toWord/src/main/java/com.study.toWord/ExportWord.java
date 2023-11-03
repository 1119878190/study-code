package com.study.toWord;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @author lx
 * @date 2023/04/28
 */
public class ExportWord {

    private static final String WHITE = "ffffff";
    private static final String YELLOW = "ff8b38";
    private static final String BLUE = "2bb4fe";
    private static final String LIGHT_GREEN = "f1fcf6";
    private static final String BLACK = "000000";
    private static final String GREEN = "2ecc6f";


    public static void main(String[] args) throws Exception {
        //Blank Document
        XWPFDocument document = new XWPFDocument();

        String path = "D:\\var\\xnfz\\exportWord\\";

        long currentTimeMillis = System.currentTimeMillis();


        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(path + currentTimeMillis + ".docx");


        //=============标题================
        exportTitle(document);


        //=============用户得分================
        exportUserTotalScore(document);


        //==============试题模块====================
        exportQuestionModule(document);


        // ================题组======================
        exportQuestionGroup(document);


        //==============试题=================
        // 方式一：通过table表格
        //exportQuestionGroupByTable(document);
        // 方式二： 添加制表符
        exportQuestionByLine(document);


        //==============用户答题==================
        // 分析思路扣分

        // 错误
        exportQuestionAnswerFalse(document);
        // 正确




        //=============html===============
        exportOnlyHtml(document);


        document.write(out);


        document.close();

        out.close();
    }

    private static void exportQuestionAnswerFalse(XWPFDocument document) {

        StringBuilder builder = new StringBuilder();
        builder.append("答错");
        builder.append(3);
        builder.append("次： ");







    }

    private static void exportQuestionGroup(XWPFDocument document) {

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("1.1微信注册信息分析");
        run.setFontSize(11);
        run.setColor("45bdfe");

    }

    private static void exportQuestionByLine(XWPFDocument document) {
        // Create new page with two paragraphs
        XWPFParagraph para = document.createParagraph();

        // Check if paragraph has properties

        if (para.getCTP().getPPr() == null){
            para.getCTP().addNewPPr();
        }
        // Create tab stop for right alignment
        CTTabStop tabStop = para.getCTP().getPPr().addNewTabs().addNewTab();
        tabStop.setVal(STTabJc.RIGHT);
        tabStop.setPos(BigInteger.valueOf(9072)); // 6.5 inches

        // Add left-aligned text to first run
        XWPFRun leftRun = para.createRun();
        leftRun.setText("请结合嫌疑人微信注册信息与根据受害人描述的嫌疑人特征进行对比，你发现了什么有效信息？（3分）");

        // Add tab to separate left- and right-aligned text
        leftRun.addTab();

        // Add right-aligned text to second run
        XWPFRun rightRun = para.createRun();
        rightRun.setColor("FF0000");
        rightRun.setFontSize(15);
        rightRun.setText("30.25");

    }

    private static void exportQuestionByTable(XWPFDocument document) {

        XWPFTable table = document.createTable(1, 2);

        // 设置表格宽度为100%
        table.setWidthType(TableWidthType.PCT);

        // 设置表格边框样式
        CTTblPr tblPr = table.getCTTbl().addNewTblPr();
        CTTblBorders borders = tblPr.addNewTblBorders();
        borders.addNewBottom().setColor("FFFFFF");
        borders.addNewLeft().setColor("FFFFFF");
        borders.addNewRight().setColor("FFFFFF");
        borders.addNewTop().setColor("FFFFFF");
        borders.addNewInsideH().setColor("FFFFFF");
        borders.addNewInsideV().setColor("FFFFFF");

        // 获取表格的第一行
        XWPFTableRow row = table.getRow(0);

        // 获取表格的第一列
        XWPFTableCell cellLeft = row.getCell(0);

        // 如果第一列不存在，则创建它
        if (cellLeft == null) {
            cellLeft = row.createCell();
        }

        // 创建左对齐的文本
        XWPFParagraph paraLeft = cellLeft.addParagraph();
        XWPFRun runLeft = paraLeft.createRun();
        runLeft.setText("这是做对此的文本");

        // 获取表格的第二列
        XWPFTableCell cellRight = row.getCell(1);

        // 如果第二列不存在，则创建它
        if (cellRight == null) {
            cellRight = row.createCell();
        }

        // 创建右对齐的文本
        XWPFParagraph paraRight = cellRight.addParagraph();
        paraRight.setBorderLeft(Borders.BASIC_WHITE_DASHES);
        XWPFRun runRight = paraRight.createRun();
        runRight.setText("30.25");
        runRight.setFontSize(20);
        runRight.setColor("FF0000"); // 设置颜色为红色
        runRight.setBold(true); // 设置为加粗

        // 设置第一列的宽度为30%
        cellRight.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf((long)(table.getCTTbl().getTblPr().getTblW().getW().intValue() * 0.8)));
        //cellLeft.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(9000));

        // 设置第二列的宽度为70%
        //cellRight.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(1000));
        cellRight.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf((long)(table.getCTTbl().getTblPr().getTblW().getW().intValue() * 0.2)));



    }

    private static void exportQuestionModule(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("一、嫌疑人微信数据");
        run.setBold(true);
        run.setFontSize(12);
        run.setColor("ff8c3a");
    }

    private static void exportUserTotalScore(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("张三");
        CTR ctr = run.getCTR();
        CTRPr rpr = ctr.isSetRPr() ? ctr.getRPr() : ctr.addNewRPr();
        CTShd shd = rpr.isSetShd() ? rpr.getShd() : rpr.addNewShd();
        // 设置背景色为橙色
        shd.setFill(YELLOW);
        run.setColor(WHITE);
        run.setBold(true);
        run.setFontSize(15);
        paragraph.addRun(run);

        run = paragraph.createRun();
        run.setText(" ,得分 ");
        paragraph.addRun(run);
    }

    private static void exportTitle(XWPFDocument document) {
        //添加标题
        XWPFParagraph titleParagraph = document.createParagraph();
        //设置段落居中
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleParagraphRun = titleParagraph.createRun();
        titleParagraphRun.setText("虚拟探案（电诈）分析过程");
        titleParagraphRun.setBold(true);
        titleParagraphRun.setColor(BLACK);
        titleParagraphRun.setFontSize(20);


    }

    //public static void main(String[] args) throws Exception {
    //    exportOnlyHtml();
    //}

    public static void exportOnlyHtml(XWPFDocument document) throws Exception {
        //String html = "<h1>sadfsfasadf</h1><p>fsdsdvsdvsd</p><p>bfbdfbdf</p><p><span style=\"color: rgb(230, 0, 0);\">这是中文</span></p><p>你好</p><ol><li>22</li><li><span style=\"background-color: rgb(0, 138, 0);\">312</span></li><li><span style=\"background-color: rgb(0, 138, 0);\">411312</span></li></ol><p class=\"ql-align-right\"><br></p><p><img src=\"C:\\Users\\dell\\Desktop\\other\\test\\iTab-0.jpg\"></p>";


        String html = "<p><strong>qwe</strong></p><p><strong><em>dddddddd</em></strong></p><p><u>cccccccc</u></p><p><s>dddddddddddd</s></p><ol><li>dsada</li><li>asda</li></ol><ul><li>fasf</li></ul><p><br></p><p><br></p><ul><li>1</li><li>2</li><li>3</li></ul><h1>dddd</h1><h3 class=\"ql-indent-1\">1231231</h3><p><br></p><p class=\"ql-indent-1\"><span style=\"color: rgb(230, 0, 0);\">1231231231ffffffff</span></p><p class=\"ql-indent-1\"><br></p><p class=\"ql-indent-1\"><br></p><p class=\"ql-indent-1\"><span style=\"color: rgb(230, 0, 0); background-color: rgb(250, 204, 204);\">asdasdasdasdasda</span></p><p class=\"ql-indent-1\"><br></p><p class=\"ql-indent-1 ql-align-center\">dddddddddddccc</p><p class=\"ql-indent-1\"><br></p><p class=\"ql-indent-1\"><br></p>";

        html = html + "<img src=\"C:\\Users\\dell\\Desktop\\other\\test\\1cf755256b600c335877e7005f4c510fd8f9a106.png\">";

        //html.replace("<p>","</p>");

        // 使用 Jsoup 解析 HTML 内容，得到一个 Document 对象
        Document doc = Jsoup.parse(html);


        // 解析 HTML 中的每个标签，逐个创建段落和文本对象，并设置相应的样式
        Elements elements = doc.body().children();
        for (Element e : elements) {
            Elements img = e.select("img");
            if (img.size() > 0) {
                for (Element element : img) {
                    dealHtmlElement(document, element);
                }
            } else {
                dealHtmlElement(document, e);
            }
        }


    }

    private static void dealHtmlElement(XWPFDocument document, Element e) throws InvalidFormatException, IOException {
        if ("h1".equals(e.tagName())) {
            // 处理标题（h1）标签
            XWPFParagraph p = document.createParagraph();
            p.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun r = p.createRun();
            r.setText(e.text());
            r.setBold(true);
            r.setFontSize(16);
            //r.addCarriageReturn();
        } else if ("h2".equals(e.tagName())) {
            // 处理二级标题（h2）标签
            XWPFParagraph p = document.createParagraph();
            p.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun r = p.createRun();
            r.setText(e.text());
            r.setBold(true);
            r.setFontSize(14);
            //r.addCarriageReturn();
        } else if ("p".equals(e.tagName())) {
            // 处理段落（p）标签
            XWPFParagraph p = document.createParagraph();
            p.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun r = p.createRun();
            r.setText(e.text());
            r.setFontSize(12);
            //r.addCarriageReturn();
        } else if ("ul".equals(e.tagName())) {
            // 处理无序列表（ul）标签
            XWPFParagraph p = document.createParagraph();
            p.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun r = p.createRun();
            r.addCarriageReturn();
            Elements lis = e.children();
            for (int i = 0; i < lis.size(); i++) {
                Element element = lis.get(i);
                r.setText("\u2022 " + element.text());
                r.setFontSize(12);
                if (i != lis.size() - 1) {
                    r.addCarriageReturn();
                }
            }
        } else if ("span".equals(e.tagName())) {
            // 处理 span 标签
            XWPFParagraph p = document.createParagraph();
            p.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun r = p.createRun();
            r.setText(e.text());
            r.setFontSize(12);
            String style = e.attr("style");
            if (style != null && !style.isEmpty()) {
                // 如果 span 标签中有 style 属性，则设置相应的样式
                String[] styles = style.split(";");
                for (String s : styles) {
                    String[] kv = s.split(":");
                    if (kv.length == 2) {
                        String key = kv[0].trim().toLowerCase();
                        String value = kv[1].trim();
                        switch (key) {
                            case "color":
                                r.setColor(value);
                                break;
                            case "font-size":
                                r.setFontSize(Integer.parseInt(value));
                                break;
                            // 添加其他样式的处理逻辑
                            default:
                                break;
                        }
                    }
                }
            }
            r.addCarriageReturn();
        } else if ("img".equals(e.tagName())) {


            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();


            String src = e.attr("src");
            if (StringUtils.isNotBlank(src)) {

                FileInputStream inputStream = new FileInputStream(src);

                String substring = src.substring(src.lastIndexOf(".") + 1);
                if (StringUtils.isNotBlank(substring)) {


                    int picType = -1;
                    if ("png".equalsIgnoreCase(substring)) {
                        picType = XWPFDocument.PICTURE_TYPE_PNG;
                    } else if ("jpeg".equalsIgnoreCase(substring)) {
                        picType = XWPFDocument.PICTURE_TYPE_JPEG;
                    } else if ("gif".equalsIgnoreCase(substring)) {
                        picType = XWPFDocument.PICTURE_TYPE_GIF;
                    }

                    if (-1 != picType) {
                        run.addPicture(inputStream, picType, src, Units.toEMU(300), Units.toEMU(200));

                    }

                }

            }


        } else if ("ol".equals(e.tagName())) {
            // 处理无序列表（ul）标签
            XWPFParagraph p = document.createParagraph();
            p.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun r = p.createRun();
            r.addCarriageReturn();
            Elements lis = e.children();
            for (int i = 0; i < lis.size(); i++) {

                Element element = lis.get(i);
                r.setText(i + 1 + "." + element.text());
                r.setFontSize(12);
                if (i != lis.size() - 1) {
                    r.addCarriageReturn();
                }

            }

        }


    }
}