package io.temperley;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import io.reactivex.Flowable;
import io.temperley.vaadin.TwinColumnLayout;
import org.vaadin.aceeditor.AceEditor;
import org.vaadin.aceeditor.AceMode;

import java.util.List;
import java.util.Locale;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("links")
public class LinksUI extends UI {

    private LinkDAO linkDAO = new LinkDAO();
    private LinkForm linkForm = new LinkForm();
    Grid<Link> linkGrid = new Grid<>();

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Locale locale = UI.getCurrent().getLocale();

        TwinColumnLayout layout = new TwinColumnLayout();
//        layout.setSizeFull();
        setContent(layout);

        Label label = new Label();
        layout.addLeft(label);

        List<Component> fields = linkForm.getFields();
        fields.forEach(layout::addLeft);

        Button save = new Button("Save");
        save.addStyleName("primary");
        layout.addLeft(save);
        save.addClickListener(e -> {

            Link link = linkForm.getLink();
            linkDAO.persist(link);
            linkGrid.getDataProvider().refreshAll();

        });

//        List<Link> list = linkDAO.list();
//        DataProvider.fromFilteringCallbacks();
        ListDataProvider<Link> linkListDataProvider = DataProvider.ofCollection(linkDAO.list());

        linkGrid.addColumn(Link::getUrl).setCaption("URL").setWidth(200);
        linkGrid.addColumn(Link::getDescription).setCaption("Description");
        linkGrid.setDataProvider(linkListDataProvider);


        linkGrid.addItemClickListener(e -> {
            Link item = e.getItem();
            linkForm.setLink(item);
        });


        layout.addRight(linkGrid);

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = LinksUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

}
