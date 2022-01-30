package millich.michael.bordcasttest.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import millich.michael.bordcasttest.databse.UnlockDatabaseDAO

class HomeViewModelFactory(
    private val application: Application,
    private val databaseDAO: UnlockDatabaseDAO
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(databaseDAO,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}