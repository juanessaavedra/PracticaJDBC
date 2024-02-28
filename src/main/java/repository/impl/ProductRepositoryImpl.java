package repository.impl;

import application.DatabaseConnection;
import model.Product;
import repository.Repository;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements Repository<Product> {
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance();
    }

    @Override
    public List<Product> list() {
        List<Product> productList = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
            ResultSet resultset = statement.executeQuery("SELECT * FROM products")) {
           while(resultset.next()) {
               Product product = createProduct(resultset);
               productList.add(product);
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public Product byId(Integer id) {
        Product product = null;

        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM products WHERE id = ?")) {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                product = createProduct(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public void save(Product product) {
        String sql;
        if (product.getId() != null && product.getId() > 0) {
            sql = "UPDATE products SET name=?, price=?, date_register=? WHERE id = ?";
        } else {
            sql = "INSERT INTO products(name,price,date_register) VALUES (?,?,?)";
        }

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1,product.getName());
            preparedStatement.setDouble(2, product.getPrice());

            if (product.getId() != null && product.getId() > 0) {
                preparedStatement.setInt(3,product.getId());
            } else {
                // preparedStatement.setDate(3, new Date(product.getRegistrationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
            }
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE FROM products WHERE id=?")) {
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Product createProduct (ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getDouble("price"));
        product.setRegistrationDate(
                resultSet.getDate("date_register")!=null?
                        resultSet.getDate("date_register")
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime():
                        null);
                return product;
    }




}
