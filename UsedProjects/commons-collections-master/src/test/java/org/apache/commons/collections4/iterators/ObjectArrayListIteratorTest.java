/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections4.iterators;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Tests the ObjectArrayListIterator class.
 */
public class ObjectArrayListIteratorTest<E> extends ObjectArrayIteratorTest<E> {

    public ObjectArrayListIteratorTest(final String testName) {
        super(testName);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ObjectArrayListIterator<E> makeEmptyIterator() {
        return new ObjectArrayListIterator<>((E[]) new Object[0]);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ObjectArrayListIterator<E> makeObject() {
        return new ObjectArrayListIterator<>((E[]) testArray);
    }

    public ObjectArrayListIterator<E> makeArrayListIterator(final E[] array) {
        return new ObjectArrayListIterator<>(array);
    }

    /**
     * Test the basic ListIterator functionality - going backwards using
     * {@code previous()}.
     */
    public void testListIterator() {
        final ListIterator<E> iter = makeObject();

        // TestArrayIterator#testIterator() has already tested the iterator forward,
        //  now we need to test it in reverse

        // fast-forward the iterator to the end...
        while (iter.hasNext()) {
            iter.next();
        }

        for (int x = testArray.length - 1; x >= 0; x--) {
            final Object testValue = testArray[x];
            final Object iterValue = iter.previous();

            assertEquals("Iteration value is correct", testValue, iterValue);
        }

        assertFalse("Iterator should now be empty", iter.hasPrevious());

        try {
            iter.previous();
        } catch (final Exception e) {
            assertEquals("NoSuchElementException must be thrown", e.getClass(), new NoSuchElementException().getClass());
        }

    }

    /**
     * Tests the {@link java.util.ListIterator#set} operation.
     */
    @SuppressWarnings("unchecked")
    public void testListIteratorSet() {
        final String[] testData = new String[] { "a", "b", "c" };

        final String[] result = new String[] { "0", "1", "2" };

        ListIterator<E> iter = makeArrayListIterator((E[]) testData);
        int x = 0;

        while (iter.hasNext()) {
            iter.next();
            iter.set((E) Integer.toString(x));
            x++;
        }

        assertArrayEquals(testData, result, "The two arrays should have the same value, i.e. {0,1,2}");

        // a call to set() before a call to next() or previous() should throw an IllegalStateException
        iter = makeArrayListIterator((E[]) testArray);

        try {
            iter.set((E) "should fail");
            fail("ListIterator#set should fail if next() or previous() have not yet been called.");
        } catch (final IllegalStateException e) {
            // expected
        } catch (final Throwable t) { // should never happen
            fail(t.toString());
        }

    }

}
