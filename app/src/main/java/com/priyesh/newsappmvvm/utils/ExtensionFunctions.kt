package com.priyesh.newsappmvvm.utils

import androidx.appcompat.widget.SearchView


fun SearchView.onQueryTextSubmit(onQuerySubmit: (String?) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            onQuerySubmit.invoke(query)
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    })
}