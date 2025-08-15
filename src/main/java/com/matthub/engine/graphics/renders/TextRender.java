package com.matthub.engine.graphics.renders;

import com.matthub.engine.graphics.gfx.Text;
import com.matthub.engine.graphics.gfx.wrapper.TextWrapper;

import java.util.ArrayList;

public class TextRender implements RenderInterface{
    private final ArrayList<TextWrapper> texts;
    private final PixelRender render;

    public TextRender(PixelRender render) {
        this.render = render;
        this.texts = new ArrayList<>();
    }

    @Override
    public void process() {
        for (TextWrapper text : this.texts) {
            drawText(text.getText(), text.getOffX(), text.getOffY(), text.isMovable());
        }
    }

    @Override
    public void clear() {
        this.texts.clear();
    }

    public void addText(Text text, int offX, int offY, boolean movable) {
        this.texts.add(new TextWrapper(text, offX, offY, movable));
    }

    private void drawText(Text text, int offX, int offY, boolean movable) {
        String txt = text.getText();

        int offset = 0;
        for (int i = 0; i < txt.length(); i++) {
            int unicode = txt.codePointAt(i);
            int charWidth = text.getWidths()[unicode];
            for (int y = 0; y < text.getHeight(); y++) {
                for (int x = 0; x < charWidth; x++) {
                    int pixel = text.getFontImage().getPixels()[(x + text.getOffsets()[unicode]) + y * text.getFontImage().getWidth()];
                    if ((pixel & 0xFF000000) != 0) {
                        this.render.setPixel(x + offset + offX, y + offY, pixel, movable);
                    }
                }
            }
            offset += charWidth + 1;
        }
    }
    
}
