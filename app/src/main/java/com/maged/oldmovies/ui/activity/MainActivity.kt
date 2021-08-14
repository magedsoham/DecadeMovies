package com.maged.oldmovies.ui.activity

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.get
import com.maged.oldmovies.R
import com.maged.oldmovies.databinding.ActivityMainBinding
import com.maged.oldmovies.enums.FragType
import com.maged.oldmovies.utils.callbacks.InterfaceCommunication
import com.maged.oldmovies.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), InterfaceCommunication {
    private lateinit var binding: ActivityMainBinding
    private lateinit var currentFrag: FragType //determines the current Fragment
    private lateinit var searchView: SearchView //used for searching Movies by 'title'

    private lateinit var filterMoviesCallback: (String) -> Unit //communicates the text to be used for filtering movies back to Fragment.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
    }


    /**
     * Returns true if it was handled & close the SearchView, false otherwise
     */
    private fun closeSearchView(): Boolean {
        var result = false
        if (this::searchView.isInitialized && searchView.isIconified.not()) {
            searchView.isIconified = true
            result = true
        }
        return result
    }


    // InterfaceCommuncation
    override fun currentFrag(fragType: FragType, title: String) {
        currentFrag = fragType
        invalidateOptionsMenu() //refreshes Menu creation

        supportActionBar?.run {
            this.title = title //displays the given title on Toolbar
            setDisplayHomeAsUpEnabled(fragType == FragType.MovieDetails) //displays back button on Toolbar
        }
    }

    override fun setupFilterCallback(filterMoviesCallback: (String) -> Unit) {
        this.filterMoviesCallback = filterMoviesCallback
    }
    //end


    // menu methods
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager: SearchManager? =
            getSystemService(Context.SEARCH_SERVICE) as? SearchManager
        val searchView: SearchView? = menu?.get(0)?.actionView as? SearchView

        if (searchManager != null && searchView != null) {
            this.searchView = searchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.maxWidth = Int.MAX_VALUE

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    hideKeyboard()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.run(filterMoviesCallback)
                    return true
                }
            })
        }

        return currentFrag == FragType.Movies
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.wtf -> return true
        }
        return super.onOptionsItemSelected(item)
    }
    //end


    override fun onBackPressed() {
        if (closeSearchView().not())
            super.onBackPressed()
    }
}