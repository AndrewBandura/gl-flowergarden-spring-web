package com.flowergarden.service;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.flowers.Flower;
import com.flowergarden.model.flowers.GeneralFlower;

import java.util.List;

public interface BouquetRestService {

    float getPrice(int bouquetId);
    Bouquet reduceFreshness(int bouquetId);
    List<GeneralFlower> getFlowers(int bouquetId);

}
