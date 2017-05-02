package io.temperley.vaadin;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * A standard pattern: create a twin column layout with full size
 *
 * Created by will on 16/04/2017.
 */
public class TwinColumnLayout extends VerticalLayout {

    private HorizontalLayout main = new HorizontalLayout();
    private  HorizontalLayout header = new HorizontalLayout();


    private VerticalLayout leftCol = new VerticalLayout();
    private VerticalLayout rightCol = new VerticalLayout();

    public TwinColumnLayout() {

        this.addComponent(header);
        header.setHeight(100, Unit.PIXELS);
        this.addComponentsAndExpand(main);

//        main.addComponentsAndExpand(leftCol);
//        main.addComponentsAndExpand(rightCol);
    }


    public void addLeft(Component component) {
        main.addComponentsAndExpand(component);
    }

}
