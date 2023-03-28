package cz.klecansky.splaytree;

import cz.klecansky.splaytree.tree.SplayTree;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SplayTreeTest {

    @Test
    public void whenEmptyTree_thenGetReturnsNull() {
        SplayTree<String, String> splayTree = new SplayTree<>();

        assertNull(splayTree.get("NonExisting"));
    }

    @Test
    public void whenCallGetWithNull_thenGetReturnsNull() {
        SplayTree<String, String> splayTree = new SplayTree<>();
        splayTree.put("NotEmpty", "NotEmpty");

        assertNull(splayTree.get(null));
    }

    @Test
    public void whenEmpty_thenGetRootKeyReturnsNull() {
        SplayTree<String, String> splayTree = new SplayTree<>();

        assertNull(splayTree.getRootKey());
    }

    @Test
    public void whenNotEmpty_thenGetRootKeyReturnsRootKey() {
        SplayTree<String, String> splayTree = new SplayTree<>();
        splayTree.put("NotEmpty", "NotEmpty");

        assertEquals("NotEmpty", splayTree.getRootKey());
    }

    @Test
    public void whenEmpty_thenKeysReturnsEmptyList() {
        SplayTree<String, String> splayTree = new SplayTree<>();

        List<String> keys = splayTree.keys();
        assertNotNull(keys);
        assertTrue(keys.isEmpty());
    }

    @Test
    public void whenNotEmpty_thenKeysReturnsListOfAllKeys() {
        SplayTree<String, String> splayTree = new SplayTree<>();
        splayTree.put("FirstKey", "NotEmpty");
        splayTree.put("SecondKey", "NotEmpty");
        splayTree.put("ThirdKey", "NotEmpty");

        List<String> keys = splayTree.keys();
        assertNotNull(keys);
        assertEquals(3, keys.size());
        assertIterableEquals(keys, List.of("FirstKey", "SecondKey", "ThirdKey"));
    }

    @Test
    public void whenEmpty_thenSizeReturnsZero() {
        SplayTree<String, String> splayTree = new SplayTree<>();

        assertEquals(0, splayTree.size());
    }

    @Test
    public void whenNotEmpty_thenSizeReturnsRightSize() {
        SplayTree<String, String> splayTree = new SplayTree<>();
        splayTree.put("FirstKey", "NotEmpty");
        splayTree.put("SecondKey", "NotEmpty");
        splayTree.put("ThirdKey", "NotEmpty");

        assertEquals(3, splayTree.size());
    }

    @Test
    public void whenEmpty_thenHeightReturnsMinusOne() {
        SplayTree<String, String> splayTree = new SplayTree<>();

        assertEquals(-1, splayTree.height());
    }

    @Test
    public void whenTreeHaveOneElement_thenHeightReturnsZero() {
        SplayTree<String, String> splayTree = new SplayTree<>();
        splayTree.put("FirstKey", "NotEmpty");

        assertEquals(0, splayTree.height());
    }

    @Test
    public void whenTreeHaveTwoElements_thenHeightReturnsOne() {
        SplayTree<String, String> splayTree = new SplayTree<>();
        splayTree.put("FirstKey", "NotEmpty");
        splayTree.put("SecondKey", "NotEmpty");

        assertEquals(1, splayTree.height());
    }

    @Test
    public void whenEmpty_thenRemoveNotThrowException() {
        SplayTree<String, String> splayTree = new SplayTree<>();
        assertDoesNotThrow(() -> splayTree.remove("NonExistingKey"));
    }

    @Test
    public void whenRemoveNonExistingKey_thenNotThrowException() {
        SplayTree<String, String> splayTree = new SplayTree<>();
        splayTree.put("FirstKey", "NotEmpty");
        assertDoesNotThrow(() -> splayTree.remove("NonExistingKey"));
    }

    @Test
    public void whenCallRemoveWithNull_thenRemoveNotThrowException() {
        SplayTree<String, String> splayTree = new SplayTree<>();
        splayTree.put("FirstKey", "NotEmpty");

        assertDoesNotThrow(() -> splayTree.remove(null));
    }

    @Test
    public void whenRemoveExistingKey_thenElementRemoved() {
        SplayTree<String, String> splayTree = new SplayTree<>();
        splayTree.put("FirstKey", "NotEmpty");
        splayTree.put("SecondKey", "NotEmpty");
        splayTree.put("ThirdKey", "NotEmpty");
        splayTree.put("4Key", "NotEmpty");
        splayTree.put("5Key", "NotEmpty");
        splayTree.put("6Key", "NotEmpty");
        splayTree.put("7Key", "NotEmpty");

        splayTree.remove("FirstKey");

        assertEquals(6, splayTree.size());
    }

    @Test
    public void whenRemoveExistingKey_thenElementRemoved_2() {
        SplayTree<String, String> splayTree = new SplayTree<>();
        String productId = UUID.randomUUID().toString();
        splayTree.put(productId, productId);

        for (int i = 0; i < 2000; i++) {
            String id = UUID.randomUUID().toString();
            splayTree.put(id, id);
        }

        splayTree.remove(productId);

        assertEquals(2000, splayTree.size());
    }
}