package com.study.toPDF.ItextPDF;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.ArrayList;

import static com.study.toPDF.ItextPDF.ExportPDF.getChineseFont;

/**
 * @author lx
 * @date 2023/04/27
 */
public class TestInnerCell {


    public static void main(String[] args) {
        Document document = new Document();
        try {

            long timeMillis = System.currentTimeMillis();
            String name = timeMillis + ".pdf";

            FileOutputStream fileOutputStream = new FileOutputStream("D:\\var\\xnfz\\expoftPDF\\" + name);

            PdfWriter.getInstance(document, fileOutputStream);
            document.open();


            // 创建一个 3 列的表格
            // 创建一个 3 列的表格
            PdfPTable table = new PdfPTable(3); // 每行最多展示三个内容
            table.setWidthPercentage(70); // 表格宽度占页面宽度的百分比

// 创建 "答对次数" 单元格并设置其属性
            PdfPCell headerCell = new PdfPCell(new Phrase("答对次数", getChineseFont(8)));
            headerCell.setColspan(3); // 将单元格合并到三列
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER); // 设置单元格内容水平居中
            headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置单元格内容垂直居中
            headerCell.setBorder(0); // 设置单元格边框为无
            table.addCell(headerCell); // 将单元格添加到表格中

// 假设有多个内容，个数不确定
            java.util.List<String> contents = new ArrayList<>();
            contents.add("这是我的答案，不知道对不对，需要你看一下 1");
            contents.add("这是我的答案，不知道对不对，需要你看一下 2");
            contents.add("这是我的答案，不知道对不对，需要你看一下 3");
            contents.add("这是我的答案，不知道对不对，需要你看一下 4");
            contents.add("这是我的答案，不知道对不对，需要你看一下 5");

            for (String content : contents) {
                PdfPCell cell = new PdfPCell(); // 创建外部单元格
                cell.setPadding(5f); // 设置外部单元格的内边距
                cell.setBorder(0);

                PdfPTable innerTable = new PdfPTable(1); // 创建内部表格
                innerTable.setWidthPercentage(100);
                PdfPCell innerCell = new PdfPCell(new Paragraph(content, getChineseFont(8, Font.NORMAL, new BaseColor(88, 223, 143)))); // 创建内部单元格并设置内容
                innerCell.setBorder(15); // 设置内部单元格的边框
                innerCell.setBorderColor(new BaseColor(88, 223, 143));// 设置内部单元格的边框颜色
                innerCell.setBackgroundColor(new BaseColor(241, 252, 246)); // 设置内部单元格的背景色为浅绿色
                innerCell.setPadding(5f); // 设置内部单元格的内边距
                innerTable.addCell(innerCell);
                cell.addElement(innerTable); // 将内部表格添加到外部单元格中

                table.addCell(cell); // 将外部单元格添加到表格中
            }

// 计算需要添加的空白单元格数量，以便让每行最多展示三个内容
            int remainingCells = contents.size() % 3;
            if (remainingCells > 0) {
                for (int i = 0; i < 3 - remainingCells; i++) {
                    PdfPCell emptyCell = new PdfPCell();
                    emptyCell.setBorder(0); // 设置空白单元格的边框为无
                    table.addCell(emptyCell);
                }
            }

// 将表格添加到文档中
            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
