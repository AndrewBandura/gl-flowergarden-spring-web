package com.flowergarden.service.impl;

import com.flowergarden.dao.BouquetDao;
import com.flowergarden.dao.FetchMode;
import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.service.BouquetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BouquetServiceImpl implements BouquetService {

    @Autowired
    BouquetDao bouquetDao;

    @Override
    public List<Bouquet> getBouquets() {
        return bouquetDao.findAll(FetchMode.EAGER);
    }
}
