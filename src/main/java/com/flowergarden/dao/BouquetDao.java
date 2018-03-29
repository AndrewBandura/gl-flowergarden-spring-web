package com.flowergarden.dao;

import com.flowergarden.model.bouquet.Bouquet;

import java.util.List;
import java.util.Optional;

/**
 * @author Andrew Bandura
 */
public interface BouquetDao {

    int add(Bouquet bouquet);

    Optional<Bouquet> read(int id, FetchMode fetchMode);

    Optional<Bouquet> readFirst();

    boolean update(Bouquet bouquet);

    boolean delete(Bouquet bouquet);

    boolean deleteAll();

    List<Bouquet> findAll(FetchMode fetchMode);

}
