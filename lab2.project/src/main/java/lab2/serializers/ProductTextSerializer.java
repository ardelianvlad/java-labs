package lab2.serializers;

import lab2.Product;
import lab2.ProductBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class ProductTextSerializer implements Serializer<Product> {
    @Override
    public void serialize(Product object, Writer output) throws IOException {
        output.write(generateString(object));
        output.flush();
        output.close();
    }

    @Override
    public void serializeCollection(Collection<Product> objects, Writer output) throws IOException {
        //PrintWriter writer = new PrintWriter(output, "UTF-8");
        for (Product product : objects) {
            output.write(generateString(product));
            output.write("/");
        }
        output.flush();
        output.close();
    }

    @Override
    public Product deserialize(InputStream input) throws IOException {
        Scanner scanner = new Scanner(input, "UTF-8");
        String inputText = scanner.useDelimiter("\\A").next();
        scanner.close();
        return new ProductBuilder().fromString(inputText).build();
    }

    @Override
    public Collection<Product> deserializeCollection(InputStream input) throws IOException {
        Scanner scanner = new Scanner(input, "UTF-8");
        String inputText = scanner.useDelimiter("\\A").next();
        ArrayList<Product> faculties = new ArrayList<>();
        for (String s : inputText.split("/")) {
            if (!s.isEmpty())
                faculties.add(new ProductBuilder().fromString(s).build());
        }
        scanner.close();
        return faculties;
    }

    static String generateString(Product product) {

        StringBuilder sb = new StringBuilder();
        sb.append("name: ");
        sb.append(product.getName());
        sb.append(";category: ");
        sb.append(product.getCategory().toString());
        sb.append(";production: ");
        sb.append(product.getProductionDate().toString());
        sb.append(";expiration: ");
        sb.append(product.getExpiration().toString());
        sb.append(";price: ");
        sb.append(product.getPrice());
        sb.append(";");

        return sb.toString();
    }
}