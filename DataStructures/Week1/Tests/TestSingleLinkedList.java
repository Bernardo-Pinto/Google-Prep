package DataStructures.Week1.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;   

import org.junit.jupiter.api.Test;   

import DataStructures.Week1.SingleLinkedList;

class TestSingleLinkedList {
    
    @Test
    void add_toEmptyList_listHas1Element(){
        SingleLinkedList<Integer> list = new SingleLinkedList<>();
        assertEquals(0,list.size());
        list.add(1);
        assertEquals(1,list.size());
    }

    @Test
    void removeFirst_fromEmptyList_listStillHas0Elements(){
        SingleLinkedList<Integer> list = new SingleLinkedList<>();
        assertEquals(0,list.size());
        list.removeFirst();
        assertEquals(0,list.size());
    }

    @Test
    void removeByIndex_zero_onEmpty_throwsIndexOutOfBoundsException(){
        SingleLinkedList<Integer> list = new SingleLinkedList<>();
        assertEquals(0,list.size());
        assertThrows(IndexOutOfBoundsException.class, () -> list.removeByIndex(0) );
    }

        @Test
    void get_zero_onEmpty_throwsIndexOutOfBoundsException(){
        SingleLinkedList<Integer> list = new SingleLinkedList<>();
        assertEquals(0,list.size());
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0) );
    }


    @Test
    void addThenGetZero_EmptyList_listReturnsThatElement(){
        SingleLinkedList<Integer> list = new SingleLinkedList<>();
        list.add(6);
        assertEquals(6,list.get(0).element);
    }

    @Test
    void addThenRemoveIndex0_EmptyList_listIsEmpty(){
        SingleLinkedList<Integer> list = new SingleLinkedList<>();
        list.add(6);
        list.removeFirst();
        assertEquals(0,list.size());
    }

        @Test
    void addTwo_removeLast_sizeAndOrderCorrect(){
        SingleLinkedList<Integer> list = new SingleLinkedList<>();
        list.add(6);
        list.add(2);
        list.removeLast();
        assertEquals(1,list.size());
        assertEquals(6, list.get(0).element);
    }

    
}
