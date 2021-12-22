package tdd.collection.lifo;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class StackTest {

    @Test
    void testUntypedStackInitialization() {
        Stack stack = new Stack();

        assertNotNull(stack);
        assertFalse(stack.isTyped());
    }

    @Test
    void testUntypedStackDefaultCapacity() {
        Stack stack = new Stack();

        assertEquals(10, stack.capacity());
        assertEquals(0, stack.size());
    }

    @Test
    void testUntypedStackDefaultCapacityClear() {
        Stack stack = new Stack();
        stack.clear();

        assertEquals(0, stack.size());
        assertEquals(0, stack.capacity());
    }

    @Test
    void testUntypedStackInitialCapacity() {
        Stack stack = new Stack(1_000);

        assertEquals(1_000, stack.capacity());
        assertEquals(0, stack.size());
    }

    @Test
    void testUntypedStackInitialCapacityClear() {
        Stack stack = new Stack(1_000);
        stack.clear();

        assertEquals(0, stack.size());
        assertEquals(0, stack.capacity());
    }

    @Test
    void testTypedStackInitialization() {
        Stack stringStack = new Stack(String.class);

        assertNotNull(stringStack);
        assertTrue(stringStack.isTyped());
    }

    @Test
    void testTypedStackDefaultCapacity() {
        Stack stringStack = new Stack(String.class);

        assertEquals(10, stringStack.capacity());
        assertEquals(0, stringStack.size());
    }

    @Test
    void testTypedStackDefaultCapacityClear() {
        Stack stringStack = new Stack(String.class);
        stringStack.clear();

        assertEquals(0, stringStack.size());
        assertEquals(0, stringStack.capacity());
    }

    @Test
    void testTypedStackInitialCapacity() {
        Stack stringStack = new Stack(String.class, 1_000);

        assertEquals(1_000, stringStack.capacity());
        assertEquals(0, stringStack.size());
    }

    @Test
    void testTypedStackInitialCapacityClear() {
        Stack stringStack = new Stack(String.class, 1_000);
        stringStack.clear();

        assertEquals(0, stringStack.size());
        assertEquals(0, stringStack.capacity());
    }

    @Test
    void testPushUnexpectedType() {
        Stack stringStack = new Stack(String.class);

        Exception exception = assertThrows(RuntimeException.class, () -> stringStack.push(new Object()));

        assertEquals("Unable to push type Object to stack typed by String", exception.getMessage());
        assertEquals(0, stringStack.size());
    }

    @Test
    void testPushTypedEntry() {
        Stack stringStack = new Stack(String.class);
        stringStack.push("Hello world");

        assertEquals(1, stringStack.size());
    }

    @Test
    void testPushMoreEntriesThanDefaultCapacity() {
        Stack stringStack = new Stack(String.class);

        for (int i = 0; i < 11; i++) {
            stringStack.push("Entry" + i);
        }

        assertEquals(11, stringStack.size());
        assertEquals(17, stringStack.capacity());
    }

    @Test
    void testPushMoreEntriesThanInitialCapacity() {
        Stack stringStack = new Stack(String.class, 3);

        assertEquals(3, stringStack.capacity());
        assertEquals(0, stringStack.size());

        stringStack.push("Entry1");
        stringStack.push("Entry2");
        stringStack.push("Entry3");
        stringStack.push("Entry4");
        stringStack.push("Entry5");

        assertEquals(5, stringStack.size());
        assertEquals(5, stringStack.capacity());
    }

    @Test
    void testPullTypedEntry() {
        Stack stringStack = new Stack(String.class);
        String entry1 = "String typed entry";

        stringStack.push(entry1);

        assertEquals(1, stringStack.size());

        String entry2 = (String) stringStack.pull();

        assertEquals(entry1, entry2);
        assertEquals(0, stringStack.size());
    }

    @Test
    void testPullTypedEntries() {
        Stack stringStack = new Stack(String.class);
        String entry = "String typed entry ";

        int i = 0;
        for (; i < 100; i ++) {
            stringStack.push(entry + i);
        }

        assertEquals(100, stringStack.size());

        i = 99;
        for (; i >= 0; i--) {
            String pulled = (String) stringStack.pull();
            assertEquals(entry + i, pulled);
        }

        assertEquals(0, stringStack.size());
        assertEquals(134, stringStack.capacity());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testPullFromUntypedStack() {
        Stack stack = new Stack();
        assertFalse(stack.isTyped());

        String stringEntry = "String entry";
        stack.push(stringEntry);

        Integer intEntry = 1_000_000_000;
        stack.push(intEntry);

        Double doubleEntry = 1.123;
        stack.push(doubleEntry);

        Long longEntry = 1_000_000_000_000_000_000L;
        stack.push(longEntry);

        Map<Integer, String> mapEntry = new HashMap<>();
        mapEntry.put(1, "String value");
        stack.push(mapEntry);

        List<Double> listEntry = new ArrayList<>();
        listEntry.add(2.123);
        stack.push(listEntry);

        assertEquals(6, stack.size());

        List<Double> pulledList = (List<Double>) stack.pull();
        assertEquals(2.123, pulledList.get(0));

        Map<Integer, String> pulledMap = (Map<Integer, String>) stack.pull();
        assertEquals("String value", pulledMap.get(1));

        Long pulledLong = (Long) stack.pull();
        assertEquals(1_000_000_000_000_000_000L, pulledLong);

        Double pulledDouble = (Double) stack.pull();
        assertEquals(1.123, pulledDouble);

        Integer pulledInt = (Integer) stack.pull();
        assertEquals(1_000_000_000, pulledInt);

        String pulledString = (String) stack.pull();
        assertEquals("String entry", pulledString);

        assertEquals(0, stack.size());
     }

    @Test
    void testTrimToSize() {
        Stack stringStack = new Stack(String.class, 15);
        assertEquals(15, stringStack.capacity());
        assertEquals(0, stringStack.size());

        for (int i = 0; i < 10; i ++) {
            stringStack.push("Entry " + i);
        }

        assertEquals(15, stringStack.capacity());
        assertEquals(10, stringStack.size());
        stringStack.trim();
        assertEquals(stringStack.size(), stringStack.capacity());
    }

    @Test
    void testPullEmptyStack() {
        Stack stringStack = new Stack(String.class);
        stringStack.push("Entry1");
        stringStack.push("Entry2");
        stringStack.push("Entry3");
        stringStack.push("Entry4");
        stringStack.push("Entry5");

        assertEquals(5, stringStack.size());

        assertEquals("Entry5", stringStack.pull());
        assertEquals("Entry4", stringStack.pull());
        assertEquals("Entry3", stringStack.pull());
        assertEquals("Entry2", stringStack.pull());
        assertEquals("Entry1", stringStack.pull());

        assertNull(stringStack.pull());
    }

    @Test
    void testPullWhileNotNull() {
        Stack stringStack = new Stack(String.class);

        for (int i = 0; i < 10_000; i ++) {
            stringStack.push("Entry " + i);
        }

        assertEquals(10_000, stringStack.size());

        Set<String> stringSet = new HashSet<>();
        String entry;
        while((entry = (String) stringStack.pull()) != null) {
            stringSet.add(entry);
        }

        assertEquals(10_000, stringSet.size());

        assertEquals(0, stringStack.size());
        assertEquals(15_764, stringStack.capacity());
        stringStack.trim();
        assertEquals(0, stringStack.capacity());
    }
}
