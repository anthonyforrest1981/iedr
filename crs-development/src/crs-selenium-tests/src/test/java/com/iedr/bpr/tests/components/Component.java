package com.iedr.bpr.tests.components;

import com.iedr.bpr.tests.gui.Gui;

public abstract class Component {

    protected Gui gui;

    public Component(Gui gui) {
        this.gui = gui;
    }

}
