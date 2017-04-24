package io.temperley.vaadin;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * A standard pattern: create a twin column layout with full size
 *
 * Created by will on 16/04/2017.
 */
public class TwinColumnLayout extends HorizontalLayout {

    private VerticalLayout leftCol = new VerticalLayout();
    private VerticalLayout rightCol = new VerticalLayout();

    public TwinColumnLayout() {

        this.addComponent(leftCol);
        this.addComponent(rightCol);
    }

    public void addLeft(Component component) {
        leftCol.addComponent(component);
    }

    public void addRight(Component component) {
        rightCol.addComponent(component);
    }

//    public void setSizeFull() {
//        super.setSizeFull();
//        leftCol.setSizeFull();
//        rightCol.setSizeFull();
//    }

}
