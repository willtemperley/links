package io.temperley.domain;

import com.explicatis.ext_token_field.Tokenizable;

/**
 * Created by will on 01/05/2017.
 */
public class Token implements Tokenizable {

    private long id;

    private String value;

    public void setIdentifier(long id) {
        this.id = id;
    }

    @Override
    public String getStringValue() {
        return getValue();
    }

    @Override
    public long getIdentifier() {
        return id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
