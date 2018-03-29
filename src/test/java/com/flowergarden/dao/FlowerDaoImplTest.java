package com.flowergarden.dao;

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

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Andrew Bandura
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/application-context.xml")
@ActiveProfiles("test")
public class FlowerDaoImplTest {

    @Autowired
    private  FlowerDao flowerDao;
    @Autowired
    private  BouquetDao bouquetDao;

    @Before
    public void setUp(){

        Bouquet bouquetTest = new MarriedBouquet();
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

        bouquetDao.add(bouquetTest);

    }

    @After
    public void tearDown(){

        flowerDao.deleteAll();

    }

    @Test
    public void addTest(){

        GeneralFlower flowerTulip = new Tulip();
        flowerTulip.setPrice(30);
        flowerTulip.setFreshness(new FreshnessInteger(3));
        flowerTulip.setLenght(45);
        int id = flowerDao.add(flowerTulip);

        assertTrue(id > 0);
    }

    @Test
    public void readFirstTest(){

        Optional<GeneralFlower> flowerFirst = flowerDao.readFirst();

        assertTrue(flowerFirst.isPresent());

    }

    @Test
    public void readTest() {

        Optional<GeneralFlower> flower = Optional.empty();
        Optional<GeneralFlower> flowerFirst = flowerDao.readFirst();

        if (flowerFirst.isPresent()) {
            int id = flowerFirst.get().getId();
            flower = flowerDao.read(id, FetchMode.LAZY);
        }

        assertTrue(flower.isPresent());

    }

    @Test
    public void readEagerTest() {

        Optional<GeneralFlower> flower = Optional.empty();
        Optional<GeneralFlower> flowerFirst = flowerDao.readFirst();

        if (flowerFirst.isPresent()) {
            int id = flowerFirst.get().getId();
            flower = flowerDao.read(id, FetchMode.EAGER);
        }

        assertTrue(flower.isPresent());

    }

    @Test
    public void updateTest(){

        final float expected = 50;

        Optional<GeneralFlower> flowerOpt = flowerDao.readFirst();

        if(!flowerOpt.isPresent()){
            assertTrue(false);
        }

        GeneralFlower flower = flowerOpt.get();
        flower.setPrice(expected);
        flowerDao.update(flower);

        Optional<GeneralFlower> updatedFlowerOpt = flowerDao.read(flower.getId(), FetchMode.LAZY);
        float actual = 0f;
        if (updatedFlowerOpt.isPresent()) {
            actual = updatedFlowerOpt.get().getPrice();
        }

        assertEquals(expected, actual, 0.0f);

    }

    @Test
    public void deleteTest(){

        Optional<GeneralFlower> flowerOpt = flowerDao.readFirst();
        int id = 0;
        if(!flowerOpt.isPresent()){
            GeneralFlower flower = flowerOpt.get();
            id = flower.getId();
            flowerDao.delete(flower);
        }

        assertFalse(flowerDao.read(id, FetchMode.LAZY).isPresent());

    }

    @Test
    public void deleteAllTest(){

        flowerDao.deleteAll();

        assertTrue(flowerDao.findAll(FetchMode.LAZY).size() == 0);

    }

    @Test
    public void findAllTest(){

        List flowersList =  flowerDao.findAll(FetchMode.LAZY);

        assertTrue(flowersList.size()==3);

    }

    @Test
    public void findAllEagerTest(){

        List flowersList =  flowerDao.findAll(FetchMode.EAGER);

        assertTrue(flowersList.size()==3);

    }

}
