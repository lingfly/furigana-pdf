package com.lingfly.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * pdf转图片
 */
public class PDFToImage {

    public static void main(String[] args) {
        String pdfFilePath = "D:\\剪辑\\京紫\\1.pdf";
        String outputDir = ".\\images\\";

        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                // 保存图像
                String fileName = outputDir + "page_" + (page + 1) + ".png";
                ImageIO.write(bim, "png", new File(fileName));
                System.out.println("Saved " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}