package com.study.toWord;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @author lx
 * @date 2023/05/05
 */
public class TestAnswer {

    public static void main(String[] args) throws IOException {


        FileOutputStream out = null;
        XWPFDocument document = null;

        //Blank Document
        document = new XWPFDocument();

        String path = "D:\\var\\xnfz\\exportWord\\";
        long currentTimeMillis = System.currentTimeMillis();
        //Write the Document in file system
        out = new FileOutputStream(path + currentTimeMillis + ".docx");

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
        runRight.setText("a骄傲就嗲家是解放军空军发 给i哦骄傲囧发按时间覅加哦就jeijfwjejgoij");

        // 设置第一列的宽度为30%
        //cellRight.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf((long) (table.getCTTbl().getTblPr().getTblW().getW().intValue() * 0.1)));
        cellLeft.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));

        // 设置第二列的宽度为70%
        cellRight.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(7000));
        //cellRight.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf((long) (table.getCTTbl().getTblPr().getTblW().getW().intValue() * 0.9)));


        document.write(out);
        document.close();
        out.close();
    }


    //public static void main(String[] args) throws FileNotFoundException {
    //
    //
    //    FileOutputStream out = null;
    //    XWPFDocument document = null;
    //
    //    //Blank Document
    //    document = new XWPFDocument();
    //
    //    String path = "D:\\var\\xnfz\\exportWord\\";
    //    long currentTimeMillis = System.currentTimeMillis();
    //    //Write the Document in file system
    //    out = new FileOutputStream(path + currentTimeMillis + ".docx");
    //
    //    XWPFTable table = document.createTable(1, 1);
    //    XWPFTableRow row = table.getRow(0);
    //    XWPFTableCell cell = row.getCell(0);
    //    cell.setText("大队一次");
    //
    //
    //
    //}
}
