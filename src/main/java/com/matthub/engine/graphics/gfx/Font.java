package com.matthub.engine.graphics.gfx;

import com.matthub.engine.io.IO;
import com.matthub.engine.util.ResourceLoadException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Font {
    private final Image fontImage;
    private final int[] offsets;
    private final int[] widths;

    public Font(String fontName, int fontSize, int color) {
        this.offsets = new int[256];
        this.widths = new int[256];
        this.fontImage = createFontImage(fontName, fontSize, color);
    }

    private Image createFontImage(String path, int fontSize, int color) {
        try {
            java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, IO.loadStream(path)).deriveFont(java.awt.Font.PLAIN, fontSize);

            BufferedImage tmp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = tmp.createGraphics();
            g2d.setFont(font);
            FontMetrics metrics = g2d.getFontMetrics();
            g2d.dispose();

            int totalWidth = 0;
            for (int i = 0; i < 256; i++) {
                widths[i] = metrics.charWidth((char) i);
                offsets[i] = totalWidth;
                totalWidth += widths[i] + 1;
            }

            int height = metrics.getHeight();

            BufferedImage img = new BufferedImage(totalWidth, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = img.createGraphics();
            g.setFont(font);
            g.setColor(new Color(color)); // Set text color to black
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int x = 0;
            for (int i = 0; i < 256; i++) {
                char ch = (char) i;
                g.drawString(String.valueOf(ch), x, metrics.getAscent());
                x += widths[i] + 1;
            }
            g.dispose();

            int[] pixels = new int[img.getWidth() * img.getHeight()];
            img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());

            return new Image(pixels, img.getWidth(), img.getHeight());
        } catch (FontFormatException | IOException e) {
            throw new ResourceLoadException("[FONT] Failed to load font: " + path);
        }
    }

    public Image getFontImage() {
        return fontImage;
    }

    public int[] getOffsets() {
        return offsets;
    }

    public int[] getWidths() {
        return widths;
    }
}
