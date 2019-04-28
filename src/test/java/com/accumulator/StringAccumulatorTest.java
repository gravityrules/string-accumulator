package com.accumulator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class StringAccumulatorTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();


    private StringAccumulator stringAccumulator;


    @Before
    public void setUp() {
        stringAccumulator = new StringAccumulator();
    }

    @Test
    public void addEmptyStrings(){
        assertEquals(0,stringAccumulator.add(""));
    }

    @Test
    public void addTwoNumbers_CommaDelim() {
        assertEquals(5, stringAccumulator.add("1,4"));
    }

    @Test
    public void addMoreNumbers_CommaDelim() {
        assertEquals(20,stringAccumulator.add("1,2,3,4,0,10"));
    }

    @Test
    public void addNumbers_SupportNewLineDelim() {
        assertEquals(10,stringAccumulator.add("1,4\n5"));
        assertEquals(10,stringAccumulator.add("4\n5,1\n,0"));
    }

    @Test
    public void addNumbers_WithDelimiter_In_Input_String() {
        assertEquals(8,stringAccumulator.add("//;\n1;7"));
    }

    @Test
    public void addNumbers_Support_MultiChar_Delim_In_Input() {
        assertEquals(7,stringAccumulator.add("//;*;|;;;\n1;;;4;*;2"));
    }

    @Test
    public void add_Negative_Numbers_Throws_Exception() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("negatives not allowed: -1 -5");
        stringAccumulator.add("-1,4,-5");
    }

    @Test
    public void ignore_Number_Greater_Than_1000_While_OnAdd() {
        assertEquals(105,stringAccumulator.add("100,1001\n5"));
    }

    @Test
    public void boundary_test_add_1000() {
        assertEquals(1009,stringAccumulator.add("//***\n1000***5***4"));
    }

}