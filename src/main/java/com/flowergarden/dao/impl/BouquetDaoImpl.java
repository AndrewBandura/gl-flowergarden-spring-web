package com.flowergarden.dao.impl;

import com.flowergarden.dao.BouquetDao;
import com.flowergarden.dao.FetchMode;
import com.flowergarden.dao.FlowerDao;
import com.flowergarden.dto.BouquetDto;
import com.flowergarden.dto.DtoMapper;
import com.flowergarden.dto.FlowerDto;
import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.flowers.GeneralFlower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

/**
 * @author Andrew Bandura
 */
@Repository
public class BouquetDaoImpl implements BouquetDao {

    private final static String SQL_ADD = "INSERT INTO bouquet(`name`, `assemble_price`) " +
            "VALUES(?, ?)";
    private final static String SQL_UPDATE = "Update bouquet set name = ?, assemble_price= ? where id = ?";

    private final static String SQL_READ_LAZY = "SELECT bouquet.id as bouquet_id, bouquet.name as bouquet_name, bouquet.assemble_price AS bouquet_assemble_price FROM  bouquet WHERE id = ?";

    private final static String SQL_READ_EAGER = "SELECT bouquet.id as bouquet_id, bouquet.name as bouquet_name, bouquet.assemble_price AS bouquet_assemble_price," +
            "flower.*, flower.bouquet_id AS flower_bouquet_id FROM bouquet" +
            " LEFT JOIN flower ON bouquet.id = flower.bouquet_id WHERE bouquet.id = ? ORDER BY bouquet_id";

    private final static String SQL_READ_FIRST = "SELECT bouquet.id as bouquet_id, bouquet.name as bouquet_name, bouquet.assemble_price AS bouquet_assemble_price FROM bouquet ORDER BY ID ASC LIMIT 1";

    private final static String SQL_DELETE = "DELETE FROM bouquet WHERE Id = ?";

    private final static String SQL_DELETE_ALL = "DELETE FROM bouquet";

    private final static String SQL_FIND_ALL_LAZY = "SELECT bouquet.id as bouquet_id, bouquet.name as bouquet_name, bouquet.assemble_price  AS bouquet_assemble_price FROM  bouquet";

    private final static String SQL_FIND_ALL_EAGER = "SELECT bouquet.id AS bouquet_id, bouquet.name AS bouquet_name, bouquet.assemble_price  AS bouquet_assemble_price, " +
            "flower.*, flower.bouquet_id AS flower_bouquet_id FROM bouquet" +
            " LEFT JOIN flower ON bouquet.id = flower.bouquet_id  ORDER BY bouquet_id";


    @Autowired
    private FlowerDao flowerDao;

    @Autowired
    private DriverManagerDataSource dataSource;

    @Override
    public int add(Bouquet bouquet) {

        int newId = 0;

        try(Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(SQL_ADD);
            statement.setObject(1, bouquet.getName());
            statement.setObject(2, bouquet.getAssemblePrice());
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newId = generatedKeys.getInt(1);
                    bouquet.setId(newId);
                } else {
                    throw new SQLException("Creating bouquet failed, no ID obtained.");
                }
            }

            Collection<GeneralFlower> flowers = bouquet.getFlowers();
            for (GeneralFlower flower : flowers) {
                flower.setBouquet(bouquet);
                flowerDao.add(flower);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return newId;
    }

    @Override
    public boolean update(Bouquet bouquet) {

        boolean updated = false;

        try(Connection connection = dataSource.getConnection()) {
            Bouquet storedBouquet;
            Optional<Bouquet> storedBouquetOpt = read(bouquet.getId(), FetchMode.EAGER);
            if (storedBouquetOpt.isPresent()) {
                storedBouquet = storedBouquetOpt.get();
                Collection<GeneralFlower> flowers = storedBouquet.getFlowers();
                for (GeneralFlower flower : flowers) {
                    flower.setBouquet(null);
                    flowerDao.update(flower);
                }
            }

            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setObject(1, bouquet.getName());
            statement.setObject(2, bouquet.getAssemblePrice());
            statement.setObject(3, bouquet.getId());
            updated = statement.execute();

            Collection<GeneralFlower> flowers = bouquet.getFlowers();
            for (GeneralFlower flower : flowers) {
                flower.setBouquet(bouquet);
                flowerDao.update(flower);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return updated;
    }

    @Override
    public Optional<Bouquet> read(int id, FetchMode fetchMode) {

        Optional<Bouquet> bouquet = Optional.empty();

        String query = fetchMode == FetchMode.EAGER ? SQL_READ_EAGER : SQL_READ_LAZY;

        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();

            bouquet = composeBouquet(rs, bouquet, fetchMode);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bouquet;
    }

    @Override
    public Optional<Bouquet> readFirst() {

        Optional<Bouquet> bouquet = Optional.empty();

        try(Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_READ_FIRST);

            bouquet = composeBouquet(rs, bouquet, FetchMode.LAZY);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bouquet;
    }

    @Override
    public boolean delete(Bouquet bouquet) {

        boolean deleted = false;

        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setObject(1, bouquet.getId());
            deleted = statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleted;
    }

    @Override
    public boolean deleteAll() {

        boolean deleted = false;

        try(Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(SQL_DELETE_ALL);
            deleted = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleted;
    }

    @Override
    public List<Bouquet> findAll(FetchMode fetchMode) {

        List<Bouquet> bouquetList = new ArrayList<>();
        Map<Integer, List<GeneralFlower>> bouquetFlowers = new HashMap<>();
        Map<Integer, Bouquet> bouquets = new TreeMap<>();

        try(Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            String query = fetchMode == FetchMode.EAGER ? SQL_FIND_ALL_EAGER : SQL_FIND_ALL_LAZY;

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Integer bouquetId = rs.getInt("bouquet_id");
                Bouquet bouquet = bouquets.get(bouquetId);

                if (bouquet == null) {
                    BouquetDto dto = getBouquetDto(rs);
                    Optional<Bouquet> bouquetOpt = DtoMapper.getPojo(dto);
                    if (bouquetOpt.isPresent()) {
                        bouquet = bouquetOpt.get();
                        bouquets.put(bouquetId, bouquet);
                    }

                    bouquetFlowers.put(bouquetId, new ArrayList<>());
                }

                if (fetchMode == FetchMode.EAGER) {
                    if (!rs.wasNull() && !(rs.getInt("flower_bouquet_id") == 0)) {
                        List<GeneralFlower> flowers = bouquetFlowers.get(bouquetId);
                        FlowerDto flowerDto = flowerDao.getFlowerDto(rs);
                        Optional<GeneralFlower> flowerOpt = DtoMapper.getPojo(flowerDto);
                        flowerOpt.ifPresent(flowers::add);
                    }
                }
            }

            for (Bouquet bouq : bouquets.values()) {
                bouq.setFlowers(bouquetFlowers.get(bouq.getId()));
                bouquetList.add(bouq);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bouquetList;

    }

    private BouquetDto getBouquetDto(ResultSet rs) throws SQLException {

        BouquetDto dto = new BouquetDto();
        dto.setName(rs.getString("bouquet_name"));
        dto.setId(rs.getInt("bouquet_id"));
        dto.setAssemblePrice(rs.getFloat("bouquet_assemble_price"));

        return dto;

    }

    private Optional<Bouquet> composeBouquet(ResultSet rs, Optional<Bouquet> bouquet, FetchMode fetchMode) throws SQLException {

        while (rs.next()) {

            if (!bouquet.isPresent()) {
                BouquetDto dto = getBouquetDto(rs);
                bouquet = DtoMapper.getPojo(dto);
            }

            if (fetchMode == FetchMode.EAGER) {
                boolean isFlowerRecordPresent = rs.getInt("flower_bouquet_id") > 0;
                if (isFlowerRecordPresent) {
                    FlowerDto flowerDto = flowerDao.getFlowerDto(rs);
                    Optional<GeneralFlower> flowerOpt = DtoMapper.getPojo(flowerDto);
                    if (bouquet.isPresent() && flowerOpt.isPresent()) {
                        GeneralFlower flower = flowerOpt.get();
                        bouquet.get().addFlower(flower);
                    }
                }
            }
        }

        return bouquet;

    }

}
