package io.temperley;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.event.EventRouter;
import com.vaadin.event.MethodEventSource;
import com.vaadin.ui.*;
import io.temperley.domain.*;
import io.temperley.domain.Link;

import java.io.Serializable;

/**
 * Created by will on 16/04/2017.
 */
public class LinkForm extends VerticalLayout implements MethodEventSource {

    private ActionListener actionListener;

    public enum Action {
        Create, Update, Delete
    }

    private Binder<Link> binder = new Binder<>();
    private LinkDAO linkDAO;

    public EventRouter eventRouter = new EventRouter();

    public LinkForm(LinkDAO linkDAO) {
        this.linkDAO = linkDAO;

        TextField textField = new TextField("URL");
        binder.forField(textField).bind(io.temperley.domain.Link::getUrl, io.temperley.domain.Link::setUrl);

        TextArea textArea = new TextArea("description");
        binder.forField(textArea).bind(io.temperley.domain.Link::getDescription, io.temperley.domain.Link::setDescription);

        this.addComponent(textField);
        this.addComponent(textArea);

        this.addComponent(getButtonsBar());


    }

    @FunctionalInterface
    public interface ActionListener extends Serializable {

        void action(Action action, io.temperley.domain.Link link);

    }

    public void addListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private Component getButtonsBar() {
        Button save = new Button("Save");
        save.addStyleName("primary");
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(save);


        save.addClickListener(e -> {

            Link link = getLink();
            linkDAO.update(link);
            eventRouter.fireEvent(e);
            actionListener.action(Action.Update, link);

        });

        Button create = new Button("Create");
        buttons.addComponent(create);

        create.addClickListener(e -> {

            Link link = new Link();
            setLink(link);

        });


        return buttons;
    }

    io.temperley.domain.Link getLink() {

        return binder.getBean();

    }


    void setLink(io.temperley.domain.Link link) {

        binder.setBean(link);
    }


    public void add(ValueProvider<LinksUI, LinksUI> onAction) {
    }
}
