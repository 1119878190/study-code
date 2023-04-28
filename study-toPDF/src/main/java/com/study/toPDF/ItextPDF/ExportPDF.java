package com.study.toPDF.ItextPDF;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;

/**
 * @author lx
 * @date 2023/04/26
 */
public class ExportPDF {


    public static void main(String[] args) {

        // 1-创建文本对象 Document
        //指定A4页面，左右边距为60，上下边距为40
        Document document = new Document(PageSize.A4, 60, 60, 40, 40);
        try {

            long timeMillis = System.currentTimeMillis();
            String name = timeMillis + ".pdf";

            FileOutputStream fileOutputStream = new FileOutputStream("D:\\var\\xnfz\\expoftPDF\\" + name);

            // 2-初始化 pdf输出对象 PdfWriter
            PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);
            // 3-打开 Document
            document.open();

            // 4-往 Document 添加内容


            // ===============标题===============
            exportTiTle(document);


            // 空白行
            document.add(Chunk.NEWLINE);

            //===============试题模块标题=================
            exportQuestionModelTitle(document);


            document.add(new Paragraph(" "));

            // ============题组名称================
            exportQuestionGroupTitle(document);


            // ==============试题标题======================
            Paragraph paragraph;
            paragraph = new Paragraph("请结合嫌疑人微信注册信息与根据受害人描述的嫌疑人特征进行对比，你发现了什么有效信息？（3分）", getChineseFont(8));
            document.add(paragraph);


            // ===============答对/错次数=========== 在单元格中嵌套表格
            exportQuestionAnswer(document);


            // 导出分析过程  html+图片
            //exportHTMLByByte(document, writer);

            //exportHTMLByHelper(document, writer);

            // 可用  思路：通过jsoup组装成dom树，遍历元素，parseHtml
            test(document,writer);

            //exportHTML(document, writer);

            //Image image = Image.getInstance("C:\\Users\\dell\\Desktop\\other\\test\\1cf755256b600c335877e7005f4c510fd8f9a106.png");
            ////设置大小
            //image.scalePercent(70,69);


            //document.add(image);


        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            // 5-关闭 Document
            document.close();
        }


    }

    private static void exportQuestionAnswer(Document document) throws DocumentException {
        // 创建两列一行的表格，第一个column为 "答对x次"  第二个column为答题内容表格
        PdfPTable pdfPTable = new PdfPTable(2);
        // 将第一列的宽度比例设置为20%，将第二列的宽度比例设置为80%
        pdfPTable.setWidths(new float[]{0.1f, 0.9f});
        pdfPTable.setWidthPercentage(100);


        PdfPCell countCell = new PdfPCell();
        countCell.setPaddingTop(8);
        countCell.setBorder(0);
        countCell.addElement(new Phrase("答对1次", getChineseFont(8, Font.BOLD, new BaseColor(88, 223, 143))));


        PdfPCell userAnswersCell = new PdfPCell();
        userAnswersCell.setBorder(0);

        // 创建一个 3 列的答题内容表格
        PdfPTable table = new PdfPTable(3);
        // 表格宽度占页面宽度的百分比
        table.setWidthPercentage(100);


        // 假设有多个内容，个数不确定
        java.util.List<String> contents = new ArrayList<>();
        contents.add("这是我的答案，不知道对不对，需要你看一下 1");
        contents.add("这是我的答案，不知道对不对，需要你看一下 2");
        contents.add("这是我的答案，不知道对不对，需要你看一下 3");
        contents.add("这是我的答案，不知道对不对，需要你看一下 4");
        contents.add("这是我的答案，不知道对不对，需要你看一下 5");

        for (String content : contents) {
            // 创建外部单元格
            PdfPCell cell = new PdfPCell();
            // 设置外部单元格的内边距
            cell.setPadding(5f);
            cell.setBorder(0);

            // 创建内部表格
            PdfPTable innerTable = new PdfPTable(1);
            innerTable.setWidthPercentage(100);
            // 创建内部单元格并设置内容
            PdfPCell innerCell = new PdfPCell(new Paragraph(content, getChineseFont(8, Font.NORMAL, new BaseColor(88, 223, 143))));
            // 设置内部单元格的边框
            innerCell.setBorder(15);
            innerCell.setBorderColor(new BaseColor(88, 223, 143));
            innerCell.setBackgroundColor(new BaseColor(241, 252, 246));
            // 设置内部单元格的内边距
            innerCell.setPadding(5f);
            innerTable.addCell(innerCell);
            // 将内部表格添加到外部单元格中
            cell.addElement(innerTable);
            // 将外部单元格添加到表格中
            table.addCell(cell);
        }

        // 计算需要添加的空白单元格数量，以便让每行最多展示三个内容
        int remainingCells = contents.size() % 3;
        if (remainingCells > 0) {
            for (int i = 0; i < 3 - remainingCells; i++) {
                PdfPCell emptyCell = new PdfPCell();
                // 设置空白单元格的边框为无
                emptyCell.setBorder(0);
                table.addCell(emptyCell);
            }
        }
        userAnswersCell.addElement(table);
        pdfPTable.addCell(countCell);
        pdfPTable.addCell(userAnswersCell);


        Paragraph paragraph = new Paragraph();
        paragraph.add(pdfPTable);

        document.add(paragraph);
    }

    private static void exportQuestionGroupTitle(Document document) throws DocumentException {
        // 创建一个带有两个文字的表格  构造参为列数
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        // 在单元格中添加居左的段落
        Paragraph leftParagraph = new Paragraph("1.1微信注册信息分析", getChineseFont(10, Font.NORMAL, new BaseColor(1, 165, 255)));
        leftParagraph.setAlignment(Element.ALIGN_LEFT);
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(leftParagraph);
        table.addCell(cell);
        // 在单元格中添加居右的段落
        Paragraph rightParagraph = new Paragraph("3分", getChineseFont(13, Font.BOLD, BaseColor.RED));
        rightParagraph.setAlignment(Element.ALIGN_RIGHT);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(rightParagraph);
        table.addCell(cell);
        document.add(table);
    }

    private static void exportQuestionModelTitle(Document document) throws DocumentException {
        Paragraph paragraph = new Paragraph("一、嫌疑人微信数据", getChineseFont(12, Font.NORMAL, new BaseColor(255, 157, 89)));
        document.add(paragraph);
    }

    private static void exportTiTle(Document document) throws DocumentException {
        Paragraph title = new Paragraph("虚拟探案（电诈）分析过程", getChineseFont(18, Font.BOLDITALIC, BaseColor.BLACK));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph paragraph = new Paragraph();
        Chunk chunk = new Chunk("张三", getChineseFont(12, Font.BOLD, BaseColor.WHITE));
        chunk.setBackground(new BaseColor(255, 139, 56));
        paragraph.add(chunk);

        chunk = new Chunk(" 得分 ", getChineseFont(12, Font.NORMAL, BaseColor.BLACK));
        paragraph.add(chunk);
        chunk = new Chunk("90", getChineseFont(14, Font.BOLDITALIC, BaseColor.RED));
        paragraph.add(chunk);
        Chunk chunk3 = new Chunk(" 分，满分100分", getChineseFont(12, Font.NORMAL, BaseColor.BLACK));
        paragraph.add(chunk3);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        document.add(paragraph);
    }


    /**
     * 支持中文
     *
     * @return
     */
    public static Font getChineseFont(int size, int style, BaseColor baseColor) {
        BaseFont bfChinese;
        Font fontChinese = null;
        try {
            bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            //fontChinese = new Font(bfChinese, 12,Font.NORMAL);
            fontChinese = new Font(bfChinese, size, style, baseColor);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fontChinese;

    }


    public static Font getChineseFont(int size) {
        BaseFont bfChinese;
        Font fontChinese = null;
        try {
            bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            //fontChinese = new Font(bfChinese, 12,Font.NORMAL);
            fontChinese = new Font(bfChinese, size, Font.NORMAL, BaseColor.BLACK);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fontChinese;

    }


    public static void exportHTMLByByte(Document document, PdfWriter writer) {

        //String html = "<p>eeeeeeeeeeeeeeeeeeeeeeeeeeee</p>";

        //String html = "<p><strong>qwe</strong></p><p><strong><em>dddddddd</em></strong></p><p><u>cccccccc</u></p><p><s>dddddddddddd</s></p><ol><li>dsada</li><li>asda</li></ol><ul><li>fasf</li></ul><p><br></p><p><br></p><ul><li>1</li><li>2</li><li>3</li></ul><h1>dddd</h1><h3 class=\"ql-indent-1\">1231231</h3><p><br></p><p class=\"ql-indent-1\"><span style=\"color: rgb(230, 0, 0);\">1231231231ffffffff</span></p><p class=\"ql-indent-1\"><br></p><p class=\"ql-indent-1\"><br></p><p class=\"ql-indent-1\"><span style=\"color: rgb(230, 0, 0); background-color: rgb(250, 204, 204);\">asdasdasdasdasda</span></p><p class=\"ql-indent-1\"><br></p><p class=\"ql-indent-1 ql-align-center\">dddddddddddccc</p><p class=\"ql-indent-1\"><br></p><p class=\"ql-indent-1\"><br></p>";


        //html = html + "<p><img src=\"C:\\Users\\dell\\Desktop\\other\\test\\1cf755256b600c335877e7005f4c510fd8f9a106.png\"></p>";

        //html = html.replace("<br>", "<br/>").replace("<hr>", "<hr/>").replace("<param>", "").replace("<link>", "");


        String html = "<p><strong>1231313</strong></p><p><strong><em>123131231</em></strong></p><p><strong><em><u>ffsdfsfsfsfs</u></em></strong></p><p><strong><em><u>13</u></em></strong></p><p><strong><em><u>123</u></em></strong></p><p><strong><em><u>123</u></em></strong></p><p>adasdasd</p><p><img src=\"C:\\Users\\dell\\Desktop\\other\\test\\iTab-0.jpg\"></p>";


        // Use Jsoup to select image tags and replace their src attribute with base64-encoded image
        org.jsoup.nodes.Document dom = Jsoup.parseBodyFragment(html);
        // 遍历整个 DOM 树
        java.util.List<Node> nodes = dom.childNodes();

        org.jsoup.nodes.Element body = dom.body();
        Elements children = body.children();

        try {
            for (org.jsoup.nodes.Element child : children) {
                if (child.tagName().equals("img")) {
                    // 处理img标签
                    String imageUrl = child.attr("src");
                    byte[] imageData = getImageData(imageUrl);
                    Image image = Image.getInstance(imageData);
                    if (image != null) {
                        document.add(image);

                    }
                } else {
                    // 处理其它标签
                    XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                            new ByteArrayInputStream(child.html().getBytes("UTF-8")),
                            null,
                            Charset.forName("UTF-8"));
                    //Paragraph para = new Paragraph(child.html());

                    //document.add(para);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }


    private static byte[] getImageData(String imageUrl) throws IOException {

        byte[] bytes = FileUtils.readFileToByteArray(new File(imageUrl));

        //return Base64.getEncoder().encodeToString(bytes);
        return bytes;
    }

    private static String getImageDataBase64(String imageUrl) throws IOException {

        byte[] bytes = FileUtils.readFileToByteArray(new File(imageUrl));

        return Base64.getEncoder().encodeToString(bytes);

    }

    private static void addNodeToDocument(Node node, Document document) {
        try {
            if (node instanceof TextNode) {
                // 处理文本节点
                TextNode textNode = (TextNode) node;
                String text = textNode.text();
                document.add(new Paragraph(text));
            } else if (node instanceof Element) {
                // 处理元素节点
                org.jsoup.nodes.Element element = (org.jsoup.nodes.Element) node;
                String tagName = element.tagName();

                if (tagName.equals("img")) {
                    // 处理图片节点
                    String imageUrl = element.attr("src");
                    byte[] imageData = getImageData(imageUrl);
                    Image image = Image.getInstance(imageData);
                    document.add(image);
                } else {
                    // 处理其它元素节点
                    java.util.List<Node> children = element.childNodes();
                    for (Node child : children) {
                        addNodeToDocument(child, document);
                    }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void exportHTML(Document document, PdfWriter write) {


        //String html = "<p><strong>qwe</strong></p><p><strong><em>dddddddd</em></strong></p><p><u>cccccccc</u></p><p><s>dddddddddddd</s></p><ol><li>dsada</li><li>asda</li></ol><ul><li>fasf</li></ul><p><br></p><p><br></p><ul><li>1</li><li>2</li><li>3</li></ul><h1>dddd</h1><h3 class=\"ql-indent-1\">1231231</h3><p><br></p><p class=\"ql-indent-1\"><span style=\"color: rgb(230, 0, 0);\">1231231231ffffffff</span></p><p class=\"ql-indent-1\"><br></p><p class=\"ql-indent-1\"><br></p><p class=\"ql-indent-1\"><span style=\"color: rgb(230, 0, 0); background-color: rgb(250, 204, 204);\">asdasdasdasdasda</span></p><p class=\"ql-indent-1\"><br></p><p class=\"ql-indent-1 ql-align-center\">dddddddddddccc</p><p class=\"ql-indent-1\"><br></p><p class=\"ql-indent-1\"><br></p>";
        //
        //html = html + "<p><img src=\"C:\\Users\\dell\\Desktop\\other\\test\\1cf755256b600c335877e7005f4c510fd8f9a106.png\"></p>";


        String html = "<p><strong>1231313</strong></p><p><strong><em>123131231</em></strong></p><p><strong><em><u>ffsdfsfsfsfs</u></em></strong></p><p><strong><em><u>13</u></em></strong></p><p><strong><em><u>123</u></em></strong></p><p><strong><em><u>123</u></em></strong></p><p>adasdasd</p><p><img src=\"C:\\Users\\dell\\Desktop\\other\\test\\iTab-0.jpg\"></p>";


        // Parse the HTML using Jsoup
        org.jsoup.nodes.Document doc = Jsoup.parseBodyFragment(html);

        // Traverse the DOM tree and add elements to PDF
        doc.body().traverse(new NodeVisitor() {
            @Override
            public void head(Node node, int depth) {
                if (node instanceof org.jsoup.nodes.Element) {
                    org.jsoup.nodes.Element element = (org.jsoup.nodes.Element) node;

                    // Handle img tag
                    if ("img".equalsIgnoreCase(element.tagName())) {
                        try {
                            // Get the image data as base64 encoded string
                            String imageData = getImageDataBase64(element.attr("src"));

                            // Create an image object from the base64 encoded string
                            Image image = Image.getInstance(Base64.getDecoder().decode(imageData));

                            // Add the image to the PDF
                            document.add(image);
                        } catch (IOException | DocumentException e) {
                            e.printStackTrace();
                        }
                    }
                    // Handle other tags
                    else {
                        // Create a new paragraph and add the text to it
                        String html1 = element.html();
                        try {
                            XMLWorkerHelper.getInstance().parseXHtml(write, document,
                                    new ByteArrayInputStream(html1.getBytes("UTF-8")),
                                    null,
                                    Charset.forName("UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

            @Override
            public void tail(Node node, int depth) {

            }
        });

    }


    public static void exportHTMLByHelper(Document document, PdfWriter writer) {
        // 将 HTML 转换为 PDF

        //String html = "<p><strong>qwe</strong></p><p><strong><em>dddddddd</em></strong></p><p><u>cccccccc</u></p><p><s>dddddddddddd</s></p><ol><li>dsada</li><li>asda</li></ol><ul><li>fasf</li></ul><p><br></p><p><br></p><ul><li>1</li><li>2</li><li>3</li></ul><h1>dddd</h1><h3 class=\"ql-indent-1\">1231231</h3><p><br></p><p class=\"ql-indent-1\"><span style=\"color: rgb(230, 0, 0);\">1231231231ffffffff</span></p><p class=\"ql-indent-1\"><br></p><p class=\"ql-indent-1\"><br></p><p class=\"ql-indent-1\"><span style=\"color: rgb(230, 0, 0); background-color: rgb(250, 204, 204);\">asdasdasdasdasda</span></p><p class=\"ql-indent-1\"><br></p><p class=\"ql-indent-1 ql-align-center\">dddddddddddccc</p><p class=\"ql-indent-1\"><br></p><p class=\"ql-indent-1\"><br></p>";
        //
        //
        //html = html + "<p><img src=\"C:\\Users\\dell\\Desktop\\other\\test\\1cf755256b600c335877e7005f4c510fd8f9a106.png\"></p>";
        //html = html.replace("<br>", "<br/>").replace("<hr>", "<hr/>").replace("<param>", "").replace("<link>", "");//不支持单独标签


        //String html = "<p><strong>1231313</strong></p><p><strong><em>123131231</em></strong></p><p><strong><em><u>ffsdfsfsfsfs</u></em></strong></p><p><strong><em><u>13</u></em></strong></p><p><strong><em><u>123</u></em></strong></p><p><strong><em><u>123</u></em></strong></p><p>adasdasd</p><p><img src=\"C:\\Users\\dell\\Desktop\\other\\test\\iTab-0.jpg\"></p>";


        //String html = "<h1>sadfsfasadf</h1><p>fsdsdvsdvsd</p><p>bfbdfbdf</p><p class=\\\"ql-align-right\\\"><span style=\\\"color: rgb(230, 0, 0);\\\">asdfagbdfbdfb</span></p>";


        String html = "<h1>sadfsfasadf</h1><p>fsdsdvsdvsd</p><p>bfbdfbdf</p><p><span style=\"color: rgb(230, 0, 0);\">这是中文</span></p><p>你好</p><ol><li>22</li><li><span style=\"background-color: rgb(0, 138, 0);\">312</span></li><li><span style=\"background-color: rgb(0, 138, 0);\">411312</span></li></ol><p class=\"ql-align-right\"><br></p><p><img src=\"C:\\Users\\dell\\Desktop\\other\\test\\iTab-0.jpg\"></p>";

        html = html + "<img src=\"C:\\Users\\dell\\Desktop\\other\\test\\1cf755256b600c335877e7005f4c510fd8f9a106.png\">";


        try {

            org.jsoup.nodes.Document doc = Jsoup.parseBodyFragment(html);
            String html1 = doc.body().html();


            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();

            CustomElementHandler elementHandler = new CustomElementHandler(writer, document);
            worker.parseXHtml(elementHandler, new StringReader(html));

            //XMLWorkerHelper.getInstance().parseXHtml(writer, document,
            //        new ByteArrayInputStream(html1.getBytes("UTF-8")),
            //        null,
            //        Charset.forName("UTF-8"), new FontProvider() {
            //            @Override
            //            public boolean isRegistered(String fontname) {
            //                return false;
            //            }
            //
            //            @Override
            //            public Font getFont(String fontname, String encoding, boolean embedded, float size, int style, BaseColor color) {
            //                return getChineseFont((int)size,style,color);
            //            }
            //        });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void test(Document document,PdfWriter writer) {

        String html = "<h1>sadfsfasadf</h1><p>fsdsdvsdvsd</p><p>bfbdfbdf</p><p><span style=\"color: rgb(230, 0, 0);\">这是中文</span></p><p>你好</p><ol><li>22</li><li><span style=\"background-color: rgb(0, 138, 0);\">312</span></li><li><span style=\"background-color: rgb(0, 138, 0);\">411312</span></li></ol><p class=\"ql-align-right\"><br></p><p><img src=\"C:\\Users\\dell\\Desktop\\other\\test\\iTab-0.jpg\"></p>";

        html = html + "<img src=\"C:\\Users\\dell\\Desktop\\other\\test\\1cf755256b600c335877e7005f4c510fd8f9a106.png\">";

        org.jsoup.nodes.Document doc = Jsoup.parse(html);

        // 遍历 HTML 中的标签
        try {
            for (Node node : doc.body().childNodes()) {

                String s = node.toString();

                if (s.startsWith("<img src=")) {
                    // 处理元素节点
                    org.jsoup.nodes.Element element = (org.jsoup.nodes.Element) node;

                    // 处理图片节点
                    String imageUrl = element.attr("src");

                    byte[] imageData = getImageData(imageUrl);
                    Image image = Image.getInstance(imageData);
                    document.add(image);
                }else {
                    XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                                    new ByteArrayInputStream(s.getBytes("UTF-8")),
                                    null,
                                    Charset.forName("UTF-8"), new FontProvider() {
                                        @Override
                                        public boolean isRegistered(String fontname) {
                                            return false;
                                        }

                                        @Override
                                        public Font getFont(String fontname, String encoding, boolean embedded, float size, int style, BaseColor color) {
                                            return getChineseFont((int)size,style,color);
                                        }
                                    });
                }

            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

}
