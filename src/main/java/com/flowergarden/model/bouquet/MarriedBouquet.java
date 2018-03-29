package com.flowergarden.model.bouquet;

import com.flowergarden.model.flowers.Flower;
import com.flowergarden.model.flowers.GeneralFlower;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MarriedBouquet implements Bouquet<GeneralFlower> {

    @XmlElement
    int id;

    private String name;

    @XmlElement
    private float assemblePrice;

    @XmlElement
    private List<GeneralFlower> flowerList = new ArrayList<>();

    public MarriedBouquet() {
        this.name = "married";
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public float getAssemblePrice() {
        return this.assemblePrice;
    }


    public void setAssemblePrice(float price) {
        assemblePrice = price;
    }


    @Override
    public float getPrice() {
        float price = assemblePrice;
        for (GeneralFlower flower : flowerList) {
            price += flower.getPrice();
        }
        return price;
    }

    @Override
    public void addFlower(GeneralFlower flower) {
        flowerList.add(flower);
    }

    @Override
    public Collection<GeneralFlower> searchFlowersByLenght(int start, int end) {
        List<GeneralFlower> searchResult = new ArrayList<>();
        for (GeneralFlower flower : flowerList) {
            if (flower.getLenght() >= start && flower.getLenght() <= end) {
                searchResult.add(flower);
            }
        }
        return searchResult;
    }

    @Override
    public void sortByFreshness() {
        Collections.sort(flowerList);
    }

    @Override
    public Collection<GeneralFlower> getFlowers() {
        return flowerList;
    }

    @Override
    public void setFlowers(Collection<Flower> flowers) {

        this.flowerList = (ArrayList) flowers;

    }
}
