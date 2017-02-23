package io.temperley;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.aceeditor.AceEditor;
import org.vaadin.aceeditor.AceMode;
import org.wikidata.wdtk.datamodel.interfaces.EntityDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.MonolingualTextValue;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

import java.util.Locale;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    private String language = "en";

    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Locale locale = UI.getCurrent().getLocale();

        language = locale.getLanguage();

        final VerticalLayout layout = new VerticalLayout();

        Label label = new Label();

        layout.addComponent(label);

        setContent(layout);

        AceEditor editor = new AceEditor();
        editor.setMode(AceMode.javascript);
        editor.setValue("Hello world!");
        layout.addComponent(editor);

        Button b = new Button("Run");
        layout.addComponent(b);
        b.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                editor.getValue();
                System.out.println(editor.getValue());
                try {
                    engine.eval(editor.getValue());
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
            }
        });

        Svg svg = new Svg();
        layout.addComponent(svg);

        try {
            ItemDocument pa = getPA();
            MonolingualTextValue monolingualTextValue = pa.getLabels().get(language);

            label.setValue(monolingualTextValue.getText());

//            pa.getLabels().forEach((f, g) -> {
//
//                String text = g.getLanguageCode().concat(":").concat(g.getText());
//                layout.addComponent(new Label(text));
//            });


        } catch (MediaWikiApiErrorException e) {
            e.printStackTrace();
        }
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    public ItemDocument getPA() throws MediaWikiApiErrorException {
        WikibaseDataFetcher wbdf = WikibaseDataFetcher.getWikidataDataFetcher();

        EntityDocument q42 = wbdf.getEntityDocument("Q223589");

        if (q42 instanceof ItemDocument) {
            ItemDocument itemDocument = (ItemDocument) q42;

            return itemDocument;
//            itemDocument.getLabels().forEach(f -> f.);
//
//            System.out.println("The English name for entity Q42 is "
//                    + itemDocument.getLabels().get("en").getText());
        }

        return null;
    }
}
