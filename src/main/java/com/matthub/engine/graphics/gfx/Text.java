package com.matthub.engine.graphics.gfx;

public class Text {
    private final String text;
    private final Font font;
    private final int height;

    public Text(String text, Font font) { 
        this.text = text; 
        this.font = font;
        this.height = font.getFontImage().getHeight();
    }

    public Image getFontImage() {
        return font.getFontImage();
    }

    public int[] getOffsets() {
        return font.getOffsets();
    }

    public int[] getWidths() {
        return font.getWidths();
    }

    public int getWidth() {
        int w = 0;
        for (int i = 0; i < this.text.length(); i++) { 
            w+=getWidths()[text.codePointAt(i)];
        }
        return w; 
    }

    public int getHeight() {
        return height;
    }

    public Font getFont() {
        return font;
    }

    public String getText() {
        return text;
    }
}
