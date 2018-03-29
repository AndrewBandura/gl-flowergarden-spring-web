package com.flowergarden.model.bouquet;

import com.flowergarden.model.flowers.GeneralFlower;
import com.flowergarden.properties.FreshnessInteger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/**
 * @author Andrew Bandura
 */

@RunWith(MockitoJUnitRunner.class)
public class MarriedBouquetTest {

    private MarriedBouquet marriedBouquet;
    @Mock
    private FreshnessInteger mockedFreshnessInteger1;
    @Mock
    private FreshnessInteger mockedFreshnessInteger2;
    @Mock
    private FreshnessInteger mockedFreshnessInteger3;
    @Mock
    private GeneralFlower mockedRose;
    @Mock
    private GeneralFlower mockedChamomile1;
    @Mock
    private GeneralFlower mockedChamomile2;

    @Before
    public void setUp() {

        when(mockedFreshnessInteger1.getFreshness()).thenReturn(1);
        when(mockedFreshnessInteger2.getFreshness()).thenReturn(2);
        when(mockedFreshnessInteger3.getFreshness()).thenReturn(3);

        when(mockedRose.getLenght()).thenReturn(30);
        when(mockedRose.getPrice()).thenReturn(10f);
        when(mockedRose.getFreshness()).thenReturn(mockedFreshnessInteger1);
        when(mockedRose.compareTo(any(GeneralFlower.class))).thenCallRealMethod();

        when(mockedChamomile1.getLenght()).thenReturn(10);
        when(mockedChamomile1.getPrice()).thenReturn(7f);
        when(mockedChamomile1.getFreshness()).thenReturn(mockedFreshnessInteger3);

        when(mockedChamomile2.getLenght()).thenReturn(12);
        when(mockedChamomile2.getPrice()).thenReturn(7f);
        when(mockedChamomile2.getFreshness()).thenReturn(mockedFreshnessInteger2);
        when(mockedChamomile2.compareTo(any(GeneralFlower.class))).thenCallRealMethod();

        marriedBouquet = new MarriedBouquet();
        marriedBouquet.setAssemblePrice(120f);
        marriedBouquet.addFlower(mockedChamomile1);
        marriedBouquet.addFlower(mockedRose);
        marriedBouquet.addFlower(mockedChamomile2);

    }

    @Test
    public void getPrice() {

        float actual = marriedBouquet.getPrice();
        float expected = 144;
        assertEquals(expected, actual);
    }

    @Test
    public void addFlower(){

        int expected = marriedBouquet.getFlowers().size() + 1;
        marriedBouquet.addFlower(mockedRose);
        int actual = marriedBouquet.getFlowers().size();
        assertEquals(expected, actual);

    }

    @Test
    public void searchFlowersByLenght() {

        int expected = 3;
        int actual = marriedBouquet.searchFlowersByLenght(10,30).size();
        assertEquals(expected, actual);
    }

    @Test
    public void searchFlowersByIncorrectLenghtIntervalShouldReturnZero() {

        int expected = 0;
        int actual = marriedBouquet.searchFlowersByLenght(30,10).size();
        assertEquals(expected, actual);
    }

    @Test
    public void sortByFreshness() {

        marriedBouquet.sortByFreshness();
        List<GeneralFlower> actual = (List<GeneralFlower>) marriedBouquet.getFlowers();
        List<GeneralFlower> expected = Arrays.asList(mockedRose, mockedChamomile2, mockedChamomile1);

       assertEquals(expected, actual);

    }

    @Test
    public void getFlowers() {

        int expected = 3;
        int actual = marriedBouquet.getFlowers().size();

        assertEquals(expected, actual);

    }

    @Test
    public void setAssembledPrice() throws NoSuchFieldException, IllegalAccessException {

        float expected = 150;
        marriedBouquet.setAssemblePrice(150);
        Field field = marriedBouquet.getClass().getDeclaredField("assemblePrice");
        field.setAccessible(true);
        float actual = field.getFloat(marriedBouquet);

        assertEquals(expected, actual);
    }
}
