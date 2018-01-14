package hashfunctions;

import java.util.Objects;

/**
 * Wrapper of Java 7's default hash function.
 */
public final class Java7DefaultObjectsHash implements HashFunction {
    private static final Java7DefaultObjectsHash INSTANCE =
            new Java7DefaultObjectsHash();

    private Java7DefaultObjectsHash() { }

    /**
     *
     * @return an instance of the Basic hash generator.
     */
    public static Java7DefaultObjectsHash getHashFunction() {
        return INSTANCE;
    }

    /**
     * Calculates a hash value in the range [1, rangeUpperBound]
     * of a given non-null object. For the calculation of the hash only the
     * bytes representation of the String representation of the object and
     * the range make difference. In other words, if two object have equal
     * String representations, they would have the same hash value for a given
     * range.
     * @param object the object whose hash value would be calculated
     * @param rangeMax the maximum possible value of the hash
     * @return the hash value of the object in the given range
     */
    public int getHash(final Object object, final int rangeMax) {
        int objectsHash = Objects.hash(object);
        if (objectsHash < 0) {
            objectsHash = -objectsHash;
        }
        return objectsHash % rangeMax + 1;
    }
}
