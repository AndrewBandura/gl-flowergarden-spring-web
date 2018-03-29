package com.flowergarden.model.flowers;

import com.flowergarden.properties.Freshness;

public interface Flower<T> {

    Freshness<T> getFreshness();

    float getPrice();

    int getLenght();

}
