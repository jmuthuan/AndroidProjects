package com.jmuthuan.treely.ui


import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jmuthuan.treely.TreelyApplication
import com.jmuthuan.treely.data.repository.DatabaseRepositoryImpl
import com.jmuthuan.treely.ui.home.HomeViewModel
import com.jmuthuan.treely.ui.persons.PersonEntryViewModel



object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                DatabaseRepositoryImpl(
                    treelyApplication().dataConteiner.getDatabase()
                )
            )
        }

        initializer {
            PersonEntryViewModel(
               DatabaseRepositoryImpl(
                   treelyApplication().dataConteiner.getDatabase()
               )
            )
        }

    }
}

fun CreationExtras.treelyApplication(): TreelyApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TreelyApplication)