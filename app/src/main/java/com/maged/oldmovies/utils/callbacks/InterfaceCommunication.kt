package com.maged.oldmovies.utils.callbacks

import com.maged.oldmovies.enums.FragType

/**
 * Used for communication between Fragments & MainActivity
 */
interface InterfaceCommunication {
    fun currentFrag(fragType: FragType, title: String = "")
    fun setupFilterCallback(filterMoviesCallback: (String) -> Unit)
}