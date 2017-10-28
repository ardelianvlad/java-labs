package lab2.serializers;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Collection;


public interface Serializer<T> {

    /**
     * @param object object to write
     * @param output writer which will be used
     * @throws IOException unable to write
     */
    void serialize(T object, Writer output) throws IOException;

    /**
     * @param objects collection of objects to write
     * @param output  writer which will be used
     * @throws IOException unable to write
     */
    void serializeCollection(Collection<T> objects, Writer output) throws IOException;

    /**
     * @param input stream to read object from
     * @return object read from stream
     * @throws IOException unable to read
     */
    T deserialize(InputStream input) throws IOException;

    /**
     * @param input stream to read object from
     * @return LinkedList of objects
     * @throws IOException unable to read
     */
    Collection<T> deserializeCollection(InputStream input) throws IOException;

}