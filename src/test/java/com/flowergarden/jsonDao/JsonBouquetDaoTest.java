package com.flowergarden.jsonDao;

import com.flowergarden.dao.BouquetDao;
import com.flowergarden.dao.FetchMode;
import com.flowergarden.dao.FlowerDao;
import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.bouquet.MarriedBouquet;
import com.flowergarden.model.flowers.Chamomile;
import com.flowergarden.model.flowers.GeneralFlower;
import com.flowergarden.model.flowers.Rose;
import com.flowergarden.model.flowers.Tulip;
import com.flowergarden.properties.FreshnessInteger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/application-context.xml")
@ActiveProfiles("test")
public class JsonBouquetDaoTest {

    @Autowired
    private BouquetDao bouquetDao;
    @Autowired
    private FlowerDao flowerDao;
    @Autowired
    private JsonBouquetDao jsonBouquetDao;

    private Bouquet bouquetTest;

    @Before
    public void setUp(){

        bouquetTest = new MarriedBouquet();
        bouquetTest.setAssemblePrice(100);

        GeneralFlower flowerRose = new Rose();
        flowerRose.setPrice(50);
        flowerRose.setFreshness(new FreshnessInteger(1));
        flowerRose.setLenght(70);
        ((Rose)flowerRose).setSpike(true);
        bouquetTest.addFlower(flowerRose);

        GeneralFlower flowerChamomile = new Chamomile();
        flowerChamomile.setPrice(40);
        flowerChamomile.setFreshness(new FreshnessInteger(2));
        flowerChamomile.setLenght(50);
        ((Chamomile)flowerChamomile).setPetals(95);
        bouquetTest.addFlower(flowerChamomile);


        GeneralFlower flowerTulip = new Tulip();
        flowerTulip.setPrice(30);
        flowerTulip.setFreshness(new FreshnessInteger(3));
        flowerTulip.setLenght(45);
        bouquetTest.addFlower(flowerTulip);

        int id = bouquetDao.add(bouquetTest);
        bouquetTest.setId(id);
    }

    @After
    public void tearDown(){

        bouquetDao.deleteAll();
        flowerDao.deleteAll();

    }

    @Test
    public void readBouquetFromDataBaseThenWriteToJsonFile(){

        Optional<Bouquet>  bouquet = bouquetDao.read(bouquetTest.getId(), FetchMode.EAGER);

        if(!bouquet.isPresent()){
            assertTrue(false);
        }

        int id = jsonBouquetDao.add(bouquet.get());

        assertTrue(id > 0);

    }

    @Test
    public void readBouquetFromJsonFile(){

        Bouquet bouquet = jsonBouquetDao.read();

        int id = bouquet.getId();

        assertTrue(id > 0);

    }
}