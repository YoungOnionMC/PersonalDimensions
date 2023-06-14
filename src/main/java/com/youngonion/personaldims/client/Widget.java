package com.youngonion.personaldims.client;

import org.lwjgl.opengl.GL11;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Widget {

    public Rectangle pos = new Rectangle(0,0,1, 1);
    public boolean visible = true, enabled = true;
    public ArrayList<Widget> children = new ArrayList<>();
    protected boolean dragged = false;

    public final void addChild(Widget w) {
        if(w != null) {
            children.add(w);
        }
    }

    public final void update() {
        this.updateImpl();
        for(Widget w : children) {
            w.update();
        }
    }

    protected void updateImpl() {}

    public final boolean testPoint(int x, int y) {
        return visible && x >= pos.x && y >= pos.y && x < pos.getMaxX() && y < pos.getMaxY();
    }

    public final void draw(int mouseX, int mouseY, float partialTicks) {
        GL11.glTranslatef(pos.x, pos.y, 0);
        if(visible) {
            drawImpl(mouseX, mouseY, partialTicks);
            for(Widget w : children) {
                w.draw(mouseX - pos.x, mouseY - pos.y, partialTicks);
            }
        }
        GL11.glTranslatef(-pos.x, -pos.y, 0);
    }

    protected final void drawImpl(int mouseX, int mouseY, float partialTicks) {}

    public final void drawForeground(int mouseX, int mouseY, float partialTicks) {
        GL11.glTranslatef(pos.x, pos.y, 0);
        if(visible) {
            drawForegroundImpl(mouseX, mouseY, partialTicks);
            for(Widget w : children) {
                w.drawForeground(mouseX - pos.x, mouseY - pos.y, partialTicks);
            }
        }
        GL11.glTranslatef(-pos.x, -pos.y, 0);
    }

    protected final void drawForegroundImpl(int mouseX, int mouseY, float partialTicks) {}
}
