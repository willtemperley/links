package io.temperley;

import javax.servlet.annotation.WebServlet;

import com.explicatis.ext_token_field.ExtTokenField;
import com.explicatis.ext_token_field.SimpleTokenizable;
import com.explicatis.ext_token_field.Tokenizable;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import io.temperley.domain.*;
import io.temperley.domain.Link;
import io.temperley.vaadin.TwinColumnLayout;

import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private TokenDAO tokenDAO = new TokenDAO();
    private LinkForm linkForm = new LinkForm(linkDAO);
    Grid<io.temperley.domain.Link> linkGrid = new Grid<>();

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Locale locale = UI.getCurrent().getLocale();

        TwinColumnLayout twinColumnLayout = new TwinColumnLayout();
        setContent(twinColumnLayout);

//        SimpleTokenizable tokenizable = new SimpleTokenizable(1L, "x");
        ExtTokenField tokenField = new ExtTokenField();
        ComboBox<Token> comboBox = new ComboBox<>("hi", initTokenCollection());
        comboBox.setItemCaptionGenerator(Token::getStringValue);
        comboBox.setPlaceholder("Type here to add");
        tokenField.setInputField(comboBox);
        comboBox.addValueChangeListener(getComboBoxValueChange(tokenField));

        linkForm.addComponent(tokenField);

        twinColumnLayout.addLeft(linkForm);

        ListDataProvider<Link> linkListDataProvider = DataProvider.ofCollection(linkDAO.all());

        linkGrid.addColumn(Link::getUrl).setCaption("URL").setWidth(200);
        linkGrid.addColumn(Link::getDescription).setCaption("Description");
        linkGrid.setDataProvider(linkListDataProvider);


//        linkForm.eventRouter.addListener(ActionEvent.class, this, this::onAction);

        linkGrid.addItemClickListener(e -> {
            Link item = e.getItem();
            linkForm.setLink(item);
        });

        linkForm.addListener((action, link) -> {
            System.out.println("action = " + action);
            linkGrid.getDataProvider().refreshItem(link);
        });

        twinColumnLayout.addLeft(linkGrid);

    }

    private HasValue.ValueChangeListener<Token> getComboBoxValueChange(ExtTokenField extTokenField)
    {
        return event -> {
            Token value = event.getValue();

            if (value != null)
            {
                extTokenField.addTokenizable(value);
                event.getSource().setValue(null);
            }
        };

    }

    private  Collection<Token> initTokenCollection() {
        return tokenDAO.all();
    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = LinksUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

}
