package ru.liga.oldrussiantinderbot.utils;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TextImageMaker {
    private static final String FONT_STYLE = "Old Standard TT";
    private static final int DEFAULT_TEXT_SIZE = 40;
    private static final double HEADER_FONT_FACTOR = 1.5;
    private static final double INDENT_SHARE = 0.1;
    private static final String IMAGE_FILE_EXTENSION = "png";
    private static final String FINAL_IMAGE_FILE_PATH = "ready.png";
    private static Font bodyFont;
    private static Font headerFont;
    private static final String IMAGE_FILE_PATH = "/prerev-background.jpg";

    public static File getImageFile(String text) {
        final BufferedImage image;
        File file = new File(FINAL_IMAGE_FILE_PATH);
        try {
//            URL url = TextImageMaker.class.getResource(FINAL_IMAGE_FILE_PATH);
//            image = ImageIO.read(url);
            InputStream inputStream = TextImageMaker.class.getResourceAsStream(IMAGE_FILE_PATH);
            image = ImageIO.read(inputStream);
            Graphics graphics = image.getGraphics();
            graphics.setColor(Color.BLACK);
            bodyFont = new Font(FONT_STYLE, Font.ITALIC, DEFAULT_TEXT_SIZE);
            int maxWidth = image.getWidth();
            int maxHeight = image.getHeight();
            headerFont = new Font(FONT_STYLE, Font.BOLD, (int) (bodyFont.getSize() * HEADER_FONT_FACTOR));
            List<String> lines = getStringLines(text, maxHeight, graphics, maxWidth);
            graphics.setFont(headerFont);
            int leftIndent = (int) (image.getWidth() * INDENT_SHARE);
            int topIndent = (int) (image.getHeight() * INDENT_SHARE) + (headerFont.getSize() / 2);
            writeLinesToImage(leftIndent, topIndent, graphics, lines);
            graphics.dispose();
            ImageIO.write(image, IMAGE_FILE_EXTENSION, file);
            return file;
        } catch (IOException e) {
            throw new IllegalArgumentException("картинка не записалась", e);
        }
    }

    private static List<String> getStringLines(String text, int maxHeight, Graphics graphics, int maxLineWidth) {
        String[] words = text.split("\\s");
        List<String> lines = new ArrayList<>();
        FontMetrics fontMetrics = graphics.getFontMetrics(headerFont);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; ) {
            sb.append(words[i++]);
            while (((i < words.length) && (fontMetrics.stringWidth(sb + " " + words[i]) < maxLineWidth * 0.9))) {
                sb.append(" ");
                sb.append(words[i++]);
            }

            lines.add(sb.toString());
            sb = new StringBuilder();
            fontMetrics = graphics.getFontMetrics(bodyFont);
        }
//        if ((lines.size() + 1) * fontMetrics.getHeight() > maxHeight) {
//            bodyFont.deriveFont(bodyFont.getStyle(), (int) (bodyFont.getSize() / 2));
//            return getStringLines(text, maxHeight, graphics, maxLineWidth);
//        } else {
//        }
        return lines;
    }


    private static void writeLinesToImage(int leftOffset, int topOffset, Graphics g, List<String> linesToWrite) {
        FontMetrics fm = g.getFontMetrics();
        if (linesToWrite.size() == 1) {
            String[] words = linesToWrite.get(0).split("\\s");
            g.drawString(words[0], leftOffset, topOffset);
            String descLine = Arrays.stream(words)
                    .skip(1)
                    .collect(Collectors.joining(" "));
            g.setFont(bodyFont);
            fm = g.getFontMetrics();
            topOffset += fm.getHeight();
            g.drawString(descLine, leftOffset, topOffset);
        } else {
            for (int i = 0; i < linesToWrite.size(); i++) {
                if (i == 0) {
                    g.drawString(linesToWrite.get(i), leftOffset, topOffset);
                    g.setFont(bodyFont);
                    fm = g.getFontMetrics();
                } else {
                    g.drawString(linesToWrite.get(i), leftOffset, topOffset);
                }
                topOffset += fm.getHeight();
            }
        }
    }

}