package com.flowergarden.jsonDao;

import com.flowergarden.model.bouquet.Bouquet;

public interface JsonBouquetDao {

    int add(Bouquet bouquet);

    Bouquet read();

}
