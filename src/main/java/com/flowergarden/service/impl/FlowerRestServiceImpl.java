package com.flowergarden.service.impl;

import com.flowergarden.dao.FetchMode;
import com.flowergarden.dao.FlowerDao;
import com.flowergarden.model.flowers.GeneralFlower;
import com.flowergarden.service.FlowerRestService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Optional;

public class FlowerRestServiceImpl implements FlowerRestService {

    @Autowired
    FlowerDao flowerDao;

    @Override
    @Path("/flower/{id}")
    public GeneralFlower getFlower(@PathParam("name") int id) {
        Optional<GeneralFlower> flowerOpt= flowerDao.read(id, FetchMode.EAGER);
        if(flowerOpt.isPresent()){
            return  flowerOpt.get();
        }
        return null;
    }
}
