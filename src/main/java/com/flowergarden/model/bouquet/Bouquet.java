package com.flowergarden.model.bouquet;


import com.flowergarden.model.flowers.Flower;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;

@XmlTransient
public interface Bouquet<T> {

    void setId(int id);

    int getId();

    String getName();

    float getAssemblePrice();

    void setAssemblePrice(float price);

    float getPrice();

    void addFlower(T flower);

    Collection<T> searchFlowersByLenght(int start, int end);

    void sortByFreshness();

    void setFlowers(Collection<Flower> flowers);

    Collection<T> getFlowers();
}
