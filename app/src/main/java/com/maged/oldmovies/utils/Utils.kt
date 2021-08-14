package com.maged.oldmovies.utils

import com.maged.oldmovies.utils.callbacks.functions.Func1


/**
 * Utils class used for various utility methods
 */
class Utils {

    companion object {

        /**
         * runs the given parameter-less method in a try-catch block for safety. Will fire the Exception Callback passed on in case an exception occurs.
         */
        fun runWithCaution(
            action: () -> Unit,
            exceptionCallback: Func1<Exception>? = null
        ) {
            try {
                action.invoke()
            } catch (e: Exception) {
                e.printStackTrace()
                exceptionCallback?.apply(e)
            }
        }

    }

}