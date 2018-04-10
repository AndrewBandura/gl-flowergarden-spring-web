package com.flowergarden.service.impl;

import com.flowergarden.dao.BouquetDao;
import com.flowergarden.dao.FetchMode;
import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.flowers.GeneralFlower;
import com.flowergarden.properties.FreshnessInteger;
import com.flowergarden.service.BouquetRestService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class BouquetRestServiceImpl implements BouquetRestService {

    @Autowired
    BouquetDao bouquetDao;

    @Override
    public float getPrice(int bouquetId) {

        float price = 0f;

        Optional<Bouquet> bouquetOpt = bouquetDao.read(bouquetId, FetchMode.EAGER);
        if(bouquetOpt.isPresent()){
            Bouquet bouquet = bouquetOpt.get();
            price = bouquet.getPrice();
        }

        return price;
    }

    @Override
    public Bouquet reduceFreshness(int bouquetId) {

        Bouquet bouquet = null;

        Optional<Bouquet> bouquetOpt = bouquetDao.read(bouquetId, FetchMode.EAGER);
        if(bouquetOpt.isPresent()){
            bouquet = bouquetOpt.get();
            for(Object flower: bouquet.getFlowers()){
                int freshness = ((GeneralFlower)flower).getFreshness().getFreshness();
                if(freshness > 0){
                    ((GeneralFlower)flower).setFreshness(new FreshnessInteger(--freshness));
                }
            }
            bouquetDao.update(bouquet);
        }
        return bouquet;
    }

    @Override
    public List<GeneralFlower> getFlowers(int bouquetId) {
        Optional<Bouquet> bouquetOpt = bouquetDao.read(bouquetId, FetchMode.EAGER);
        if(bouquetOpt.isPresent()){
            return (List<GeneralFlower>)bouquetOpt.get().getFlowers();
        }
        return null;
    }

}
