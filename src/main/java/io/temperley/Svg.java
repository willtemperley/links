package io.temperley;

import com.vaadin.server.StreamResource;
import com.vaadin.ui.Embedded;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by will on 22/02/2017.
 */
public class Svg extends Embedded {


    public Svg() {
        super();
        this.setMimeType("image/svg+xml");


        StreamResource.StreamSource source = new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                return this.getClass().getClassLoader().getResourceAsStream("north_arrow.svg");
            }
        };
        
        this.setSource(new StreamResource(source, "image.svg"));
    }



}
