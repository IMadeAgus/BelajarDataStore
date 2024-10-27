package com.example.mydatastore
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//Pada latihan ini, instance DataStore dibuat di MainActivity menggunakan kode berikut
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
//Pada kode di atas, kita membuat extension properties pada Context dengan nama dataStore yang dibuat dengan
// menggunakan property delegation by preferencesDataStore. Property delegation adalah sebuah mekanisme untuk mendelegasikan suatu tugas kepada class lain.
// Dengan menggunakan cara ini, Anda tidak perlu tahu bagaimana cara membuat DataStore secara detail,
// Anda cukup menyerahkannya saja ke class preferencesDataStore.
// Selain itu, kode ini dibuat di top-level supaya menjadi Singleton yang cukup dipanggil sekali.
// Lalu “settings” adalah string yang digunakan untuk memberi nama file DataStore. Berikut adalah contoh file DataStore


class SettingPreferences  private constructor(private val dataStore: DataStore<Preferences>) {
    //Menyimpan Data ke Preferences DataStore
    //Untuk menyimpan data, yang Anda perlukan hanyalah instance DataStore dan Key pada SettingPreferences.
    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    //Membaca Data dari Preferences DataStore
    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }
    //Untuk mengambil data yang sudah disimpan, kita menggunakan fungsi map pada variabel data. Pastikan Anda menggunakan key yang sama dengan saat
    // Anda menyimpannya untuk mendapatkan data yang tepat.
    // Selain itu, Anda juga dapat menambahkan elvis operator untuk memberikan nilai default jika datanya masih kosong/null.





    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }
    //Perlu diketahui, Key ini harus unik supaya tidak tercampur dengan data lain. Key ini juga akan diperlukan untuk mengambil data yang sama.
    // Selain itu, key juga harus sesuai dengan tipe data yang akan disimpan. Sebagai contoh, jika ingin menyimpan pengaturan tema yang berupa True/False,
    // gunakanlah Boolean. Apabila Anda ingin menyimpan data lain dengan tipe data yang berbeda, Anda harus menyesuaikan bagian ini juga.
    //Untuk menyimpan data, kita menggunakan fungsi lambda edit dengan parameter preferences yang merupakan MutablePrefereces.
    // Untuk mengubah data, Anda perlu menentukan key data yang ingin diubah dan isi datanya.
    // Selain itu, karena edit adalah suspend function, maka ia harus dijalankan di coroutine atau suspend function juga.





//Membuat SettingPreferences Singleton
//Perlu diketahui bahwa instance DataStore harus berupa Singleton. Singleton hanya memperbolehkan ada satu instance yang digunakan di banyak tempat.
//Karena itu untuk membuat SettingPreference, kita tidak menggunakan constructor secara langsung, melainkan melalui fungsi getInstance yang berfungsi sebagai Singleton seperti berikut:
    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}