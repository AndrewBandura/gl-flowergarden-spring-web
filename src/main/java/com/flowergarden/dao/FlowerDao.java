package com.flowergarden.dao;

import com.flowergarden.dto.FlowerDto;
import com.flowergarden.model.flowers.GeneralFlower;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author Andrew Bandura
 */
public interface FlowerDao {

    int add(GeneralFlower flower);

    Optional<GeneralFlower> read(int id, FetchMode fetchMode);

    Optional<GeneralFlower> readFirst();

    boolean update(GeneralFlower flower);

    boolean delete(GeneralFlower flower);

    boolean deleteAll();

    List<GeneralFlower> findAll(FetchMode fetchMode);

    FlowerDto getFlowerDto(ResultSet rs) throws SQLException;

}
