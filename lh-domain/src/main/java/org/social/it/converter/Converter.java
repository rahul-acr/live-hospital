package org.social.it.converter;

public interface Converter <I,O> {
    O convert(I input);
}
