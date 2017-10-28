package lab2.serializers;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lab2.Storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.List;

public class StorageXMLSerializer implements Serializer<Storage> {
    @Override
    public void serialize(Storage object, Writer output) throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, object);
    }

    @Override
    public void serializeCollection(Collection<Storage> objects, Writer output) throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, objects);
    }

    @Override
    public Storage deserialize(InputStream input) throws IOException {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(input,Storage.class);
    }

    @Override
    public Collection<Storage> deserializeCollection(InputStream input) throws IOException {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(input,mapper.getTypeFactory().constructCollectionType(List.class, Storage.class));
    }
}