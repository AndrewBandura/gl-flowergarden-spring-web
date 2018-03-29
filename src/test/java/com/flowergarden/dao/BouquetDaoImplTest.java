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

import static org.junit.Assert.*;

/**
 * @author Andrew Bandura
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/application-context.xml")
@ActiveProfiles("test")
public class BouquetDaoImplTest {

    @Autowired
    private BouquetDao bouquetDao;
    @Autowired
    private FlowerDao flowerDao;

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

        bouquetDao.add(bouquetTest);
    }

    @After
    public void tearDown(){

        bouquetDao.deleteAll();
        flowerDao.deleteAll();

    }

    @Test
    public void addTest(){

        Bouquet bouquet = new MarriedBouquet();
        int id = bouquetDao.add(bouquet);

        assertTrue(id > 0);
    }

    @Test
    public void readFirstTest(){

       Optional<Bouquet> bouquetFirst = bouquetDao.readFirst();

        assertTrue(bouquetFirst.isPresent());

    }

    @Test
    public void readLazyTest() {

        Optional<Bouquet> bouquet = Optional.empty();
        Optional<Bouquet> bouquetFirst = bouquetDao.readFirst();

        if (bouquetFirst.isPresent()) {
            int id = bouquetFirst.get().getId();
            bouquet = bouquetDao.read(id, FetchMode.LAZY);
        }

        assertTrue(bouquet.isPresent());

    }

    @Test
    public void readEagerTest() {

        Optional<Bouquet> bouquet = Optional.empty();
        Optional<Bouquet> bouquetFirst = bouquetDao.readFirst();

        if (bouquetFirst.isPresent()) {
            int id = bouquetFirst.get().getId();
            bouquet = bouquetDao.read(id, FetchMode.EAGER);
        }

        assertTrue(bouquet.isPresent());

    }

    @Test
    public void updateTest(){

        final float expected = 507;

        Optional<Bouquet> bouquetOpt = bouquetDao.readFirst();
        if(!bouquetOpt.isPresent()){
           assertTrue(false);
        }
        Bouquet bouquet = bouquetOpt.get();
        bouquet.setAssemblePrice(expected);
        bouquetDao.update(bouquet);

        Optional<Bouquet> updatedBouquetOpt = bouquetDao.read(bouquet.getId(), FetchMode.LAZY);
        if(!updatedBouquetOpt.isPresent()){
            assertTrue(false);
        }

        float actual = updatedBouquetOpt.get().getAssemblePrice();

        assertEquals(expected, actual, 0.0f);

    }

    @Test
    public void deleteTest(){

        Bouquet bouquet = new MarriedBouquet();
        int id = bouquetDao.add(bouquet);
        bouquetDao.delete(bouquet);

        assertFalse(bouquetDao.read(id, FetchMode.LAZY).isPresent());

    }

    @Test
    public void deleteAllTest(){

        bouquetDao.deleteAll();

        assertTrue(bouquetDao.findAll(FetchMode.LAZY).size() == 0);

    }

    @Test
    public void findAllLazyTest(){

        Bouquet bouquet = new MarriedBouquet();
        bouquetDao.add(bouquet);
        List<Bouquet> bouquetList =  bouquetDao.findAll(FetchMode.LAZY);

        assertTrue(bouquetList.size() > 0);

    }

    @Test
    public void findAllEagerTest(){

        List<Bouquet> bouquetList =  bouquetDao.findAll(FetchMode.EAGER);

        assertTrue(bouquetList.size() > 0);

    }

    @Test
    public void getTotalPrice() {

        float expected = bouquetTest.getPrice();
        float actual = 0f;

        Optional<Bouquet> bouquet;

        bouquet = bouquetDao.read(bouquetTest.getId(), FetchMode.EAGER);

        if (bouquet.isPresent()) {
            actual = bouquet.get().getPrice();
        }

        assertEquals(expected, actual, 0f);

    }

}
