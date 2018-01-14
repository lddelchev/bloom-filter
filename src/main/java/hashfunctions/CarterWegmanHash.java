package hashfunctions;

import java.math.BigInteger;
import java.util.Random;

/**
 * Implementation of the Carter Wegman hash function.
 */
public final class CarterWegmanHash implements HashFunction {
    private static final int SEED = 89478583;

    private static final CarterWegmanHash INSTANCE = new CarterWegmanHash();

    private CarterWegmanHash() { }

    /**
     *
     * @return an instance of the CarterWegman hash generator.
     */
    public static CarterWegmanHash getHashFunction() {
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
        byte[] objectBytes = getBytes(object);
        BigInteger primeNumber = new BigInteger("32416190071");
        Random randomGenerator = new Random(SEED);
        BigInteger hashValue = new BigInteger(objectBytes);

        BigInteger a = BigInteger.valueOf(randomGenerator.nextLong());
        BigInteger b = BigInteger.valueOf(randomGenerator.nextLong());
        hashValue = a.multiply(hashValue).add(b).mod(primeNumber)
                .mod(BigInteger.valueOf(rangeMax));
        return hashValue.intValue();
    }
}
