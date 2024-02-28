package application;

import model.Product;
import repository.Repository;
import repository.impl.ProductRepositoryImpl;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.Date;

public class Main2 {
    public static void main(String[] args) {
    try (Connection conn = DatabaseConnection.getInstance()) {
        Repository<Product> repository = new ProductRepositoryImpl();
        System.out.println("*** List products from database");
        repository.list().stream().forEach(System.out::println);
        System.out.println("*** Get by Id: 1");
        System.out.println(repository.byId(1).toString());
        System.out.println("*** Insert new product");
        Product product = new Product();
        product.setName("Desk");
        product.setPrice(1500);
        //product.setRegistrationDate();
        repository.save(product);
        System.out.println("Results:");
        repository.list().stream().forEach(System.out::println);

        System.out.println("*** Update");
        product.setId(3);
        product.setName("Desk Gamer");
        product.setPrice(2000);
        //product.setRegistrationDate();
        repository.save(product);
        System.out.println("Updated results:");
        repository.list().stream().forEach(System.out::println);

        System.out.println("*** Delete");
        repository.delete(3);
        System.out.println("Deleted results");
        repository.list().stream().forEach(System.out::println);

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
