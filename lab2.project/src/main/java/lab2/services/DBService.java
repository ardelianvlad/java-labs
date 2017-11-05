package lab2.services;

import lab2.Product;
import lab2.ProductBuilder;
import lab2.Storage;
import lab2.StorageBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DBService {

    private final static String USERNAME = "user";
    private final static String PASSWORD = "password";
    private final static String URL = "jdbc:sqlite:database.db";

    /**
     * @return new connection to DB
     */
    public static Connection getNewConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Properties properties = new Properties();
        properties.setProperty("user", USERNAME);
        properties.setProperty("password", PASSWORD);
        return DriverManager.getConnection(URL, properties);
    }

    private static void createProductsTable(Connection conn) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate("CREATE TABLE products\n" +
                "(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "    name CHAR(100) NOT NULL,\n" +
                "    category CHAR(10), \n" +
                "    production_date DATE,\n" +
                "    expiration_date DATE,\n" +
                "    price REAL,\n" +
                "    storage INTEGER,\n" +
                "    FOREIGN KEY(storage) REFERENCES storage(id)\n" +
                ");");
    }

    private static void createStorageTable(Connection conn) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate("CREATE TABLE storage\n" +
                "(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "    name CHAR(100) NOT NULL\n" +
                ");");
    }

    /**
     * creates new database
     */
    public static void setDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        createProductsTable(conn);
        createStorageTable(conn);
        conn.close();
    }

    /**
     * @param product to add
     */
    public static int addProduct(Product product, int storageId) throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        int r = st.executeUpdate(
                "INSERT INTO products (name, category, production_date, expiration_date, price, storage) "+
                "VALUES ("+
                    "'" + product.getName() + "', " +
                    "'" + product.getCategory().toString() + "', " +
                    "'" + Date.valueOf(product.getProductionDate()) + "', " +
                    "'" + Date.valueOf(product.getExpiration())+ "', " +
                    product.getPrice() + ", " +
                    storageId +
                ");");
        ResultSet rs = st.executeQuery("SELECT id FROM products WHERE name='" + product.getName() +
                "' AND storage='" + storageId + "';");
        rs.next();
        product.setId(rs.getInt("id"));
        conn.close();
        return r;
    }

    /**
     * @param storage to add
     */
    public static int addStorage(Storage storage) throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        int r = st.executeUpdate(
                "INSERT INTO storage (name) "+
                        "VALUES ("+
                        "'" + storage.getName() + "'" +
                        ");");
        ResultSet rs = st.executeQuery("SELECT id FROM storage WHERE name='" + storage.getName() + "';");
        rs.next();
        int storageId = rs.getInt("id");
        storage.setId(storageId);
        conn.close();
        for (Product p: storage.getProducts()) {
            addProduct(p, storageId);
        }
        return r;
    }


    /**
     * @param id
     * @return product with id
     */
    public static Product getProduct(int id) throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM products WHERE id=" + id + ";");
        Product product = null;
        if (rs.next()) {
            product = new ProductBuilder().setId(rs.getInt("id"))
                    .setName(rs.getString("name"))
                    .setCategory(Product.Category.valueOf(rs.getString("category")))
//                    .setProductionDate(rs.getDate("production_date").toLocalDate())
//                    .setExpiration(rs.getDate("expiration_date").toLocalDate())
                    .setPrice(rs.getDouble("price"))
                    .build();
        }
        conn.close();
        return product;
    }

    /**
     * @param name
     * @return product with name
     */
    public static Product getProduct(String name) throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id FROM products WHERE name='" + name + "';");
        Product product = null;
        if(rs.next()) {
            product = getProduct(rs.getInt("id"));
        }
        conn.close();
        return product;
    }

    /**
     * @return products
     */
    public static ArrayList<Product> getProducts() throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM products;");
        ArrayList<Product> products = new ArrayList<>();
        while(rs.next()) {
            products.add(new ProductBuilder().setId(rs.getInt("id"))
                    .setName(rs.getString("name"))
                    .setCategory(Product.Category.valueOf(rs.getString("category")))
//                    .setProductionDate(rs.getDate("production_date").toLocalDate())
//                    .setExpiration(rs.getDate("expiration_date").toLocalDate())
                    .setPrice(rs.getDouble("price"))
                    .build());
        }
        conn.close();
        return products;
    }

    /**
     * @param id
     * @return storage with id
     */
    public static Storage getStorage(int id) throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(
            "SELECT s.id, s.name, p.id, p.name, p.category, p.production_date, p.expiration_date, p.price " +
                "FROM products AS p, storage AS s " +
                "WHERE p.storage=s.id AND s.id=" + id + ";"
        );
        Storage storage = null;
        if (rs.next()) {
            storage = new StorageBuilder().setId(rs.getInt(1))
                    .setName(rs.getString(2))
                    .build();
            do {
                Product p = new ProductBuilder()
                        .setId(rs.getInt(3))
                        .setName(rs.getString(4))
                        .setCategory(Product.Category.valueOf(rs.getString(5)))
//                        .setProductionDate(rs.getDate(6).toLocalDate())
//                        .setExpiration(rs.getDate(7).toLocalDate())
                        .setPrice(rs.getDouble(8))
                        .build();
                storage.addProduct(p);
            } while (rs.next());
        }
        conn.close();
        return storage;
    }

    /**
     * @param name
     * @return storage with name
     */
    public static Storage getStorage(String name) throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id FROM storage WHERE name='" + name + "';");
        Storage storage = null;
        if(rs.next()) {
            storage = getStorage(rs.getInt("id"));
        }
        conn.close();
        return storage;
    }

    /**
     * @return storages
     */
    public static ArrayList<Storage> getStorages() throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(
                "SELECT s.id, s.name, p.id, p.name, p.category, p.production_date, p.expiration_date, p.price " +
                        "FROM products AS p, storage AS s " +
                        "WHERE p.storage=s.id " +
                        "ORDER BY s.id" + ";"
        );
        ArrayList<Storage> storages = new ArrayList<>();
        Product p = null;
        while(rs.next()) {
            int oldId = rs.getInt(1);
            Storage storage = new StorageBuilder()
                    .setId(oldId)
                    .setName(rs.getString(2))
                    .build();
            if(p != null) {
                storage.addProduct(p);
            }
            do {
                p = new ProductBuilder()
                        .setId(rs.getInt(3))
                        .setName(rs.getString(4))
                        .setCategory(Product.Category.valueOf(rs.getString(5)))
//                        .setProductionDate(rs.getDate(6).toLocalDate())
//                        .setExpiration(rs.getDate(7).toLocalDate())
                        .setPrice(rs.getDouble(8))
                        .build();
                storage.addProduct(p);
            } while (rs.next() && oldId == rs.getInt(1));
            if (!rs.isClosed()) {
                p = new ProductBuilder()
                        .setId(rs.getInt(3))
                        .setName(rs.getString(4))
                        .setCategory(Product.Category.valueOf(rs.getString(5)))
//                        .setProductionDate(rs.getDate(6).toLocalDate())
//                        .setExpiration(rs.getDate(7).toLocalDate())
                        .setPrice(rs.getDouble(8))
                        .build();
            }
            storages.add(storage);
        }
        return storages;
    }

    /**
     * @param product to update
     */
    public static void updateProduct(Product product) throws SQLException, ClassNotFoundException, IllegalArgumentException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        int rs = st.executeUpdate("UPDATE products SET " +
                "name='" + product.getName() + "', " +
                "category='" + product.getCategory().toString() + "', " +
                "production_date='" + Date.valueOf(product.getProductionDate()) + "', " +
                "expiration_date='" + Date.valueOf(product.getExpiration())+ "', " +
                "price=" + product.getPrice()  + " " +
                "WHERE id=" + product.getId() + ";");
        conn.close();
        if(rs == 0) throw new IllegalArgumentException("Product not found");
    }

    /**
     * @param storage to update
     */
    public static void updateStorage(Storage storage) throws SQLException, ClassNotFoundException, IllegalArgumentException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        int rs = st.executeUpdate("UPDATE storage SET " +
                "name='" + storage.getName() + "' " +
                "WHERE id=" + storage.getId() + ";");
        conn.close();
        if(rs == 0) throw new IllegalArgumentException("Storage not found");
    }

    /**
     * @param id product to delete
     */
    public static void deleteProduct(int id) throws SQLException, ClassNotFoundException, IllegalArgumentException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        int rs = st.executeUpdate("DELETE FROM products WHERE id=" + id + ";");
        conn.close();
        if(rs == 0) throw new IllegalArgumentException("Product not found");
    }

    /**
     * @param id product to delete; products in storage also will be deleted
     */
    public static void deleteStorage(int id) throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        int rs = st.executeUpdate("DELETE FROM storage WHERE id=" + id + ";");
        st.executeUpdate("DELETE FROM products WHERE storage=" + id + ";");
        conn.close();
        if(rs == 0) throw new IllegalArgumentException("Storage not found");
    }

    private static void drop() throws SQLException, ClassNotFoundException {
        Connection conn = getNewConnection();
        Statement st = conn.createStatement();
        st.executeUpdate("DROP TABLE IF EXISTS 'products';");
        st.executeUpdate("DROP TABLE IF EXISTS 'storage';");
        conn.close();
    }

}
