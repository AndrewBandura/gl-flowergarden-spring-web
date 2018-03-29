package com.flowergarden.dto;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.flowers.Chamomile;
import com.flowergarden.model.flowers.GeneralFlower;
import com.flowergarden.model.flowers.Rose;
import com.flowergarden.model.flowers.Tulip;
import com.flowergarden.properties.FreshnessInteger;
import com.flowergarden.util.Property;

import java.util.Optional;
import java.util.Properties;

/**
 * @author Andrew Bandura
 */
public class DtoMapper {

    private static final Properties properties = (new Property()).getProperties();

    public static Optional<Bouquet> getPojo(BouquetDto dto) {

        String className = properties.getProperty(dto.getName());
        Class clazz;

        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        Bouquet bouquet;
        try {
            bouquet = (Bouquet) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        bouquet.setId(dto.getId());
        bouquet.setAssemblePrice(dto.getAssemblePrice());

        return Optional.of(bouquet);

    }

    public static Optional<GeneralFlower> getPojo(FlowerDto dto) {

        String className = properties.getProperty(dto.getName());
        Class clazz;

        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        GeneralFlower flower;
        try {
            flower = (GeneralFlower) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        flower.setId(dto.getId());
        flower.setLenght(dto.getLenght());
        flower.setPrice(dto.getPrice());
        flower.setFreshness(new FreshnessInteger(dto.getFreshness()));

        if (flower instanceof Rose) {
            ((Rose) flower).setSpike(dto.isSpike());
            return Optional.of(flower);
        } else if (flower instanceof Chamomile) {
            ((Chamomile) flower).setPetals(dto.getPetals());
            return Optional.of(flower);
        } else if (flower instanceof Tulip) {
            return Optional.of(flower);
        } else {

            return Optional.empty();

        }

    }
}
