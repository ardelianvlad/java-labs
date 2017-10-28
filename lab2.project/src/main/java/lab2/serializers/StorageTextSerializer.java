package lab2.serializers;

import lab2.Product;
import lab2.Storage;
import lab2.StorageBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class StorageTextSerializer implements Serializer<Storage> {
    @Override
    public void serialize(Storage object, Writer output) throws IOException {
        output.write(generateString(object));
        output.flush();
        output.close();
    }

    @Override
    public void serializeCollection(Collection<Storage> objects, Writer output) throws IOException {
        for (Storage storage : objects) {
            output.write(generateString(storage));
            output.write("/");
        }
        output.flush();
        output.close();
    }

    @Override
    public Storage deserialize(InputStream input) throws IOException {
        Scanner scanner = new Scanner(input, "UTF-8");
        String inputText = scanner.useDelimiter("\\A").next();
        scanner.close();
        return new StorageBuilder().fromString(inputText).build();
    }

    @Override
    public Collection<Storage> deserializeCollection(InputStream input) throws IOException {
        Scanner scanner = new Scanner(input, "UTF-8");
        String inputText = scanner.useDelimiter("\\A").next();
        ArrayList<Storage> storages = new ArrayList<>();
        for (String s : inputText.split("//")) {
            if (!s.isEmpty())
                storages.add(new StorageBuilder().fromString(s).build());
        }
        scanner.close();
        return storages;
    }

    static String generateString(Storage storage){
        StringBuilder sb = new StringBuilder();
        sb.append("name: ");
        sb.append(storage.getName());
        sb.append(";products: ");
        for(Product p: storage.getProducts()){
            sb.append(ProductTextSerializer.generateString(p));
            sb.append("/");
        }
        return sb.toString();
    }
}