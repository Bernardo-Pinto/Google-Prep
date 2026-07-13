package DataStructures.Week1.Tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;  

import DataStructures.Week1.HashMap;

public class TestHashMap {
    @Test
    void add_toEmptyMap_mapHasOnlyThatElement(){
        HashMap<String,Integer> map = new HashMap<>();
        map.put("a", 1);
        assertEquals(1,map.get("a"));
    }

    @Test
    void putSameKey_twice_overwriteValueAndSameSize(){
        HashMap<String,Integer> map = new HashMap<>();
        map.put("a", 1);
        assertEquals(1, map.get("a"));
        assertAll(()-> {
            assertEquals(1, map.get("a"));
            assertEquals(1, map.size());
        });
        map.put("a",2);
        assertAll(()-> {
            assertEquals(2, map.get("a"));
            assertEquals(1, map.size());
        });
    }
    @Test
    void remove_ExistingKeyTwice_returnsAndRemovesOnlyOnceWithoutErrors(){
        HashMap<String,Integer> map = new HashMap<>();
        map.put("a", 1);
        assertEquals(1, map.remove("a"));
        assertNull(map.remove("a"));
    }
}
