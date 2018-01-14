package hashfunctions;

/**
 * Implementation of the LCG hash function.
 */
public final class LCGHash implements HashFunction {
    private static final int SEED = 89478583;

    private static final LCGHash INSTANCE = new LCGHash();

    private LCGHash() { }

    /**
     *
     * @return an instance of the CarterWegman hash generator.
     */
    public static LCGHash getHashFunction() {
        return INSTANCE;
    }

    /**
     * Calculates a hash value in the range [1, rangeUpperBound] of a given
     * non-null object. For the calculation of the hash only the bytes
     * representation of the String representation of the object and the range
     * make difference. In other words, if two object have equal String
     * representations, they would have the same hash value for a given range.
     * @param object the object whose hash value would be calculated
     * @param rangeMax the maximum possible value of the hash
     * @return the hash value of the object in the given range
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public int getHash(final Object object, final int rangeMax) {
        byte[] objectBytes = getBytes(object);
        final long multiplier = 0x5DEECE66DL;
        final long addend = 0xBL;
        final long mask = (1L << 48) - 1;

        int reduced = Math.abs(hashBytes(objectBytes));
        if (reduced == Integer.MIN_VALUE) {
            reduced = 42;
        }

        long seed = reduced;
        int hashValue = (int) (seed >>> (18)) % rangeMax;
        return hashValue;
    }

    private int hashBytes(final byte[] bytes) {
        final long fnvPrime = 16777619;
        final long fnvOffsetBasis = 2166136261L;

        if (bytes == null) {
            return 0;
        }

        long result = fnvOffsetBasis;
        for (byte element : bytes) {
            result = (result * fnvPrime) & 0xFFFFFFFF;
            result ^= element;
        }

        return (int) result;
    }
}
