package hashfunctions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarterWegmanHashTest {
    private static CarterWegmanHash hashFunction;
    private static int hashUpperBound;
    private static int hashLowerBound;
    private static String stringToHash;

    @BeforeEach
    private void initialize() {
        hashFunction = CarterWegmanHash.getHashFunction();
        hashUpperBound = 1_000;
        hashLowerBound = 1;
        stringToHash = "String to be hashed";
    }

    @Test
    void givenTwoEqualObjectsWhenGetHashInvolkedThenReturnEqualHashes() {
        String secondString = stringToHash;

        assertEquals(hashFunction.getHash(stringToHash, hashUpperBound),
                hashFunction.getHash(secondString, hashUpperBound));
    }

    @Test
    void givenObjectWhenGetHashInvlokedThenReturnHashBiggerThanOne() {
        assertTrue(hashFunction.getHash(stringToHash, hashUpperBound) >= hashLowerBound);
    }

    @Test
    void givenObjectWhenGetHashInvlokedThenReturnHashLessThanRangeMax() {
        assertTrue(hashFunction.getHash(stringToHash, hashUpperBound) <= hashUpperBound);
    }

    @Test
    void givenObjectWhenGetHashInvlokedMultipleTimesThenHashValueIsTheSame() {
        int firstCallResult = hashFunction.getHash(stringToHash, hashUpperBound);
        for (int i = 0; i < 10; i++) {
            assertEquals(firstCallResult, hashFunction.getHash(stringToHash, hashUpperBound));
        }
    }

    @Test
    void givenTwoObjectsWhenGetHashInvokedThenHashResultsDoNotInterfere() {
        String firstString = stringToHash;
        String secondString = "Second string to hash";
        int firstStringFirstCallResult = hashFunction.getHash(firstString, hashUpperBound);
        int secondStringFirstCallResult = hashFunction.getHash(secondString, hashUpperBound);

        assertEquals(firstStringFirstCallResult, hashFunction.getHash(firstString, hashUpperBound));
        assertEquals(secondStringFirstCallResult, hashFunction.getHash(secondString, hashUpperBound));
    }
}
