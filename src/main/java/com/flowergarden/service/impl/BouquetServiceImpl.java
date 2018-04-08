package com.flowergarden.service.impl;

import com.flowergarden.dao.BouquetDao;
import com.flowergarden.dao.FetchMode;
import com.flowergarden.jsonDao.JsonBouquetDao;
import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.service.BouquetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bouquetService")
public class BouquetServiceImpl implements BouquetService {

    @Autowired
    BouquetDao bouquetDao;

    @Autowired
    JsonBouquetDao jsonBouquetDao;

    @Override
    public List<Bouquet> getBouquets() {
        return bouquetDao.findAll(FetchMode.EAGER);
    }

    @Override
    public Bouquet readJson() {
        return jsonBouquetDao.read();
    }
}
