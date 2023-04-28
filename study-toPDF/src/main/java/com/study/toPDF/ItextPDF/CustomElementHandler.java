package com.study.toPDF.ItextPDF;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementHandler;
import com.itextpdf.tool.xml.Writable;

public class CustomElementHandler implements ElementHandler {

    private final PdfWriter writer;
    private final Document document;

    public CustomElementHandler(PdfWriter writer, Document document) {
        this.writer = writer;
        this.document = document;
    }

    @Override
    public void add(Writable element) {
        try {
            // 判断当前页面剩余空间是否足够放置该元素
            if (element instanceof Image) {
                Image img = (Image) element;
                if (!img.hasAbsoluteY() || img.getAbsoluteY() + img.getScaledHeight() > document.top()) {
                    document.newPage();
                }
            }
            // 添加元素
            document.add((Element) element);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
