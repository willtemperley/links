package io.temperley;

import com.vaadin.data.Binder;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by will on 16/04/2017.
 */
public class LinkForm {


    private Binder<Link> binder = new Binder<>();

    private List<Component> fields = new ArrayList<>();

    public LinkForm() {

        TextField textField = new TextField("URL");
        binder.forField(textField).bind(Link::getUrl, Link::setUrl);

        TextArea textArea = new TextArea("description");
        binder.forField(textArea).bind(Link::getDescription, Link::setDescription);

        fields.add(textField);
        fields.add(textArea);
    }

    public List<Component> getFields() {
        return fields;
    }

    public Link getLink() {

        return binder.getBean();

    }


    public void setLink(Link link) {

        binder.readBean(link);
    }

}
