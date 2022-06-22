package com.example.Library.model;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PozycjaDao {
    private final DataSource dataSource;

    public PozycjaDao() {
        try {
            this.dataSource = DataSourceProvider.getDataSource();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Pozycja> findAll(){
        final String sql = "SELECT p.isbn, a.name, p.tytul, w.name, p.rok, p.cena FROM pozycje p " +
                "JOIN autor a ON p.AUTID = a.AUTID " +
                "JOIN  wydawca w ON p.WYDID = w.WYDID;";

        List<Pozycja> resultList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String ISBN = resultSet.getString("p.isbn");
                String autorName = resultSet.getString("a.name");
                String tytul = resultSet.getString("p.tytul");
                String wydawcaName = resultSet.getString("w.name");
                int rok = resultSet.getInt("p.rok");
                double cena = resultSet.getDouble("p.cena");
                resultList.add(
                        new Pozycja(
                                ISBN,
                                autorName,
                                tytul,
                                wydawcaName,
                                rok,
                                cena
                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    public Pozycja findOne(String name){
        final String sql = "SELECT p.isbn, a.name, p.tytul, w.name, p.rok, p.cena FROM pozycje p " +
                "JOIN autor a ON p.AUTID = a.AUTID " +
                "JOIN  wydawca w ON p.WYDID = w.WYDID " +
                "WHERE p.TYTUL = '" + name + "';";

        Pozycja pozycja = null;

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                pozycja = new Pozycja(
                        resultSet.getString("p.isbn"),
                        resultSet.getString("a.name"),
                        resultSet.getString("p.tytul"),
                        resultSet.getString("w.name"),
                        resultSet.getInt("p.rok"),
                        resultSet.getDouble("p.cena")
                );
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return pozycja;
    }
}
