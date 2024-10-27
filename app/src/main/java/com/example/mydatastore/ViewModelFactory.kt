package com.example.mydatastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


//Perlu diketahui, kita tidak bisa membuat ViewModel dengan constructor secara langsung. Untuk itu,
// kita perlu membuat class yang extend ke ViewModelProvider untuk bisa memasukkan constructor ke dalam ViewModel.
// Berikut adalah kode pada ViewModelFactory:
class ViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class: "+ modelClass.name)
    }
}
//Dengan ViewModelFactory, Anda dapat memasukkan constructor dengan cara mengirim data
// ke VIewModelFactory terlebih dahulu, baru setelah itu dikirimkan ke ViewModel pada fungsi create.
//etika MainActivity membutuhkan ViewModel, kita akan memanggil kelas ViewModelFactory untuk membuat ViewModel seperti berikut:
//
//val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(MainViewModel::class.java)