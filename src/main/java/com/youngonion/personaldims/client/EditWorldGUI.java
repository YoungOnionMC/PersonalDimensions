package com.youngonion.personaldims.client;

import com.youngonion.personaldims.common.portal.TileEntityPortal;
import com.youngonion.personaldims.common.world.DimensionConfig;
import net.minecraft.client.gui.GuiScreen;

public class EditWorldGUI extends GuiScreen {

    public TileEntityPortal portal;

    DimensionConfig config = new DimensionConfig();

    public EditWorldGUI(TileEntityPortal p) {
        super();
        this.portal = p;


    }
}
