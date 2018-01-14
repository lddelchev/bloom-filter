package bloomfilter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BloomFilterTest {
    private static BloomFilter<String> bloomFilter;

    @BeforeEach
    void initialize() {
        bloomFilter = new BloomFilter<>();
    }

    @Test
    void givenBloomFilterWhenCreatedThenContainsReturnsFalseForAnyData() {
        String exampleData = "A simple data";
        assertFalse(bloomFilter.contains(exampleData));
    }

    @Test
    void givenBloomFilterWhenNewElementAddedThenContainsReturnsTrue() {
        String dataToAdd = "data";
        bloomFilter.add(dataToAdd);
        assertTrue(bloomFilter.contains(dataToAdd));
    }

    @Test
    void givenBloomFilterWhenMultipleElementsAddedThenContainsReturnsTrue() {
        String dataToAdd1 = "data 1";
        String dataToAdd2 = "data 2";
        String dataToAdd3 = "data 3";

        bloomFilter.add(dataToAdd1);
        bloomFilter.add(dataToAdd2);
        bloomFilter.add(dataToAdd3);

        assertTrue(bloomFilter.contains(dataToAdd1));
        assertTrue(bloomFilter.contains(dataToAdd2));
        assertTrue(bloomFilter.contains(dataToAdd3));
    }
}
