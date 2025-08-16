package com.matthub.engine.graphics.gfx.wrapper;

import com.matthub.engine.graphics.gfx.Text;

public class TextWrapper {
    private final Text text;
    private final int offX;
    private final int offY;

    public TextWrapper(Text text, int offX, int offY) {
        this.text = text;
        this.offX = offX;
        this.offY = offY;
    }

    public Text getText() {
        return text;
    }

    public int getOffX() {
        return offX;
    }

    public int getOffY() {
        return offY;
    }
}
