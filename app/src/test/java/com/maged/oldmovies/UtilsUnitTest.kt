package com.maged.oldmovies

import com.maged.oldmovies.source.database.Converters
import org.junit.Test

class UtilsUnitTest {

    @Test
    fun stringListConversion() {
        val converters = Converters()

        val originalList = arrayListOf("1", "2", "3", "4")
        val joinedString = converters.stringCollectionToString(originalList)
        val expandedList = converters.stringToStringCollection(joinedString)
        assert(originalList == expandedList)
    }
}