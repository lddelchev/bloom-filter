package bloomfilter;

import hashfunctions.CarterWegmanHash;
import hashfunctions.HashFunction;
import hashfunctions.Java7DefaultObjectsHash;
import hashfunctions.LCGHash;

import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a classical bloom filter implementation with adding and conatining
 * functionality. False positives are possible, but False negatives are
 * impossible.
 * @param <E> the type of the containing elements.
 */
@SuppressWarnings("checkstyle:magicnumber")
public class BloomFilter<E> {
    /**
     * The default size of the bits set.
     */
    private static int defaultBitsetSize = 1_000;

    /**
     * Stores the elements hash result. When a hash functions return the hash of
     * an element the bit that has this index is set to 1.
     */
    private BitSet elementsBits;

    /**
     * The hash functions that every element passes through to be saved and
     * to be found in the BitSet array.
     */
    private Set<HashFunction> hashFunctions;

    /**
     * Creates a new BloomFilter with the default bits set size.
     */
    public BloomFilter() {
        elementsBits = new BitSet(defaultBitsetSize);
        hashFunctions = allHashFunctions();
    }

    /**
     * Creates a new BloomFilter with numberOfBits bits set size.
     * @param numberOfBits the number of bits
     */
    public BloomFilter(final int numberOfBits) {
        elementsBits = new BitSet(numberOfBits);
        hashFunctions = allHashFunctions();
    }

    /**
     * Creates a new BloomFilter with fixed bits set size and hash
     * functions hashFunctions.
     * @param numberOgBits the number of bits
     * @param hashFunctions the hash functions of the newly created BloomFilter
     */
    public BloomFilter(final int numberOgBits,
                       final Collection<HashFunction> hashFunctions) {
        elementsBits = new BitSet(numberOgBits);
        for (HashFunction hashFunction : hashFunctions) {
            this.hashFunctions.add(hashFunction);
        }
    }
    /**
     * Adds a new element to the Bloom filter.
     * @param element the element to be added
     */
    public void add(final E element) {
        int numberOfBits = elementsBits.size();

        assert hashFunctions.size() > 0
                : "The hash functions should be more than 0.";
        for (HashFunction hashFunction : hashFunctions) {
            elementsBits.set(hashFunction.getHash(element, numberOfBits) - 1,
                    true);
        }
    }

    /**
     * Checks if the bloom filter contains the given element. False positives
     * are possible, but false negatives not.
     * @param element the element to be checked.
     * @return true if the element is in the bloom filter and false if not.
     * False psitives are possible.
     */
    public boolean contains(final E element) {
        int numberOfBits = elementsBits.size();

        assert hashFunctions.size() > 0
                : "The hash functions should be more than 0.";
        for (HashFunction hashFunction : hashFunctions) {
            boolean currentBitIsSet =
                    elementsBits.get(hashFunction.getHash(element,
                            numberOfBits) - 1);
            if (!currentBitIsSet) {
                return false;
            }
        }
        return true;
    }

    private Set<HashFunction> allHashFunctions() {
        Set<HashFunction> hashFunctions = new HashSet<>();

        hashFunctions.add(CarterWegmanHash.getHashFunction());
        hashFunctions.add(Java7DefaultObjectsHash.getHashFunction());
        hashFunctions.add(LCGHash.getHashFunction());
        hashFunctions.add((Object object, int range) -> {
            int objectsHash = Objects.hash(object);
            if (objectsHash < 0) {
                objectsHash = -objectsHash;
            }
            return objectsHash % range + 1;
        });

        return hashFunctions;
    }
}
