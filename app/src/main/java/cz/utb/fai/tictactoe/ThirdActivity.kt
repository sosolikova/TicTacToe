package cz.utb.fai.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import cz.utb.fai.tictactoe.databinding.ActivityMainBinding
import cz.utb.fai.tictactoe.databinding.ActivityThirdBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private lateinit var dataStore: DataStore<Preferences>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataStore = createDataStore(name = "settings")

        binding.btnSave.setOnClickListener {
            lifecycleScope.launch {
                save(
                    binding.etSaveKey.text.toString(),
                    binding.etSaveValue.text.toString(),
                )
            }
        }
    binding.btnRead.setOnClickListener {
        lifecycleScope.launch {
            val value = read(binding.etReadkey.text.toString())
            binding.tvReadValue.text = value ?: "No value found"
        }
    }
    }

    private suspend fun save(key: String, value: String) {
        val dataStoreKey = preferencesKey<String>(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }
    private suspend fun read(key: String): String? {
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    override fun onDestroy() {
        super.onDestroy()
         binding = null
    }
}