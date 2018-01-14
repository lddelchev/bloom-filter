package hashfunctions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * Represents a hash function.
 */
@FunctionalInterface
public interface HashFunction {
    /**
     * Calculates the hash value in the range between 1 and rangeUpperBound
     * of a given object. For the calculation of the hash only the bytes
     * representation and the range make difference. In other words, if two
     * object have equal bytes representation, they would have the same hash
     * value for a given range.
     *
     * @param object the object, whose hash value would be calculated
     * @param rangeUpperBound the biggest value that the hash function can
     * return
     * @return the hash value of the given object
     */
    int getHash(Object object, int rangeUpperBound);

    /**
     *
     * @param object the object for which te byte array to be computed.
     * @return the byte array of the given object.
     */
    default byte[] getBytes(final Object object) {
        byte[] objectByteRepresentation = null;
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();) {
            ObjectOutput outputBytesStream = new ObjectOutputStream(byteStream);
            outputBytesStream.writeObject(object);
            outputBytesStream.flush();
            objectByteRepresentation = byteStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectByteRepresentation;
    }
}
