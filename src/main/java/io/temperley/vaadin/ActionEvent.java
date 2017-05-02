package io.temperley.vaadin;

import java.util.EventObject;

/**
 * Created by will on 30/04/2017.
 */
public abstract class ActionEvent<T> extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ActionEvent(Object source) {
        super(source);
    }


//    abstract void onAction(Action action, T bean);

}
