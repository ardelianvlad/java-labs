package lab2.serializers;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lab2.Product;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.List;

public class ProductXMLSerializer implements Serializer<Product> {
    @Override
    public void serialize(Product object, Writer output) throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, object);
    }

    @Override
    public void serializeCollection(Collection<Product> objects, Writer output) throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, objects);
    }

    @Override
    public Product deserialize(InputStream input) throws IOException {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(input,Product.class);
    }

    @Override
    public Collection<Product> deserializeCollection(InputStream input) throws IOException {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(input,mapper.getTypeFactory().constructCollectionType(List.class, Product.class));    }
}