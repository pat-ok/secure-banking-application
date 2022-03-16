package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static jdk.nashorn.internal.objects.NativeString.length;
import static model.Security.hashFunction;
import static model.Security.salt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecurityTest {

    @Test
    void testConstructor() {
        Security testSecurity = new Security();
        assertEquals("for junit", testSecurity.getJunit());
    }

    @Test
    void testHashFunctionEmpty() {
        assertEquals(6047, hashFunction(""));
    }

    @Test
    void testHashFunctionString() {
        assertEquals(-1571049144, hashFunction("vancouver"));
    }

    @Test
    void testHashFunctionStringRepeated() {
        assertEquals(-1571049144, hashFunction("vancouver"));
        assertEquals(-1571049144, hashFunction("vancouver"));
        assertEquals(-1571049144, hashFunction("vancouver"));
    }

    @Test
    void testHashFunctionAlmostSameString() {
        assertEquals(-1456987864, hashFunction("Vancouver"));
    }

    @Test
    void testSaltAlphanumeric() {
        assertEquals(5, length(salt()));
        assertTrue(salt().matches("(\\d?\\w?){5}"));
    }

    @Test
    void testSaltRandom() {
        ArrayList<String> salts = new ArrayList<>();
        int i = 0;
        while (i < 100) {
            String temp = salt();
            salts.add(temp);
            i++;
        }
        Set<String> saltSet = new HashSet<>(salts);

        assertEquals(saltSet.size(), salts.size());
    }
}