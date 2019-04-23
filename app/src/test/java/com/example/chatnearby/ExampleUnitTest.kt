package com.example.chatnearby

import com.example.chatnearby.nearby.GetLocationActivity
import junit.framework.Assert
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    var testClass = GetLocationActivity()
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun testTest(){
        var result =testClass.testMePlease(1)
        Assert.assertTrue(result)
    }
}
