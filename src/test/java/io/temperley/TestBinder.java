package io.temperley;

import com.vaadin.data.Binder;
import io.temperley.domain.Link;
import org.junit.Test;

/**
 * Created by will on 17/04/2017.
 */
public class TestBinder {

    /**
     * https://vaadin.com/docs/-/part/framework/datamodel/datamodel-forms.html
     */
    @Test
    public void testBinder() {
        LinkForm linkForm = new LinkForm(null);

        Binder<Link> binder = new Binder<>();

        Link link = new Link();
        link.setUrl("a");
        link.setDescription("b");

        binder.readBean(link);

    }



}
