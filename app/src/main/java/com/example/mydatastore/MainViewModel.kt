package com.example.mydatastore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {
    fun getThemeSetting(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
    //asLiveData merupakan fungsi yang digunakan untuk mengubah Flow menjadi LiveData.
    // Anda dapat melakukan ini karena telah menambahkan library lifecycle-livedata-ktx sebelumnya di awal latihan.

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
//viewModelScope merupakan scope yang sudah disediakan library lifecycle-viewmodel-ktx untuk menjalankan coroutine
// pada ViewModel yang sudah aware dengan lifecycle. Dengan begitu instance coroutine akan otomatis dihapus ketika ViewModel
// dibersihkan sehingga aplikasi tidak mengalami memory leak (kebocoran memori).
// Selanjutnya di sini kita menggunakan method launch karena kita akan memulai background process tanpa nilai kembalian alias fire and forget.
}