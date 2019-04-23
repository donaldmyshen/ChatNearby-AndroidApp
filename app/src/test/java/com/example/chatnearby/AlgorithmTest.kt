package com.example.chatnearby

import android.os.Bundle
import com.example.chatnearby.nearby.GetLocationActivity
import junit.framework.Assert
import org.junit.Test

import org.junit.Assert.*


class AlgorithmTest {
    var testClass = GetLocationActivity()
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun testCatchActivity(){
        var result =testClass.testMePlease(1)
        Assert.assertTrue(result)
    }
    @Test
    fun testCatchActivity2(){
        var result =testClass.testMePlease(-1)
        Assert.assertFalse(result)
    }
    @Test
    fun getDistanceTest1(){
        // From google to google
        var dis = testClass.getDistandce(37.4219983,-122.084,37.4219983,-122.084)
        assertEquals(0.0, dis, 0.0)
    }
    @Test
    fun getDistanceTest2() {
        // From WashU to Google
        var dis = testClass.getDistandce(38.6488, -90.3108, 37.4219983, -122.084)
        assertEquals(2771940.0, dis, 100000.0)
        // Compare with actural distance
        // seems algorithms still have error
    }
    @Test
    fun getDistanceTest3() {
        // From WashU to history museum, test a shorter distance
        var dis = testClass.getDistandce(38.6488, -90.3108, 38.6452, -90.2859)
        assertEquals(2404.0, dis, 200.0)
        // Compare with actural distance
    }
    @Test
    fun getDistanceTest4() {
        // From WashU to St.Louis science center, test a shorter distance
        var dis = testClass.getDistandce(38.6488, -90.3108, 38.6289,  -90.2708)
        assertEquals(3688.0, dis, 500.0)
        // Compare with actural distance
    }
}
