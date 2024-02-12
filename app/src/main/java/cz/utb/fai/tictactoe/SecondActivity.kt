package cz.utb.fai.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cz.utb.fai.tictactoe.databinding.ActivityMainBinding
import java.net.HttpURLConnection
import java.net.URL
import android.widget.TextView


class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchCurrencyData().start()
    }

    private fun fetchCurrencyData(): Thread
    {
        return Thread {

            val url = URL("https://open.er-api.com/v6/latest/czk")
            val connection = url.openConnection() as HttpURLConnection

            if(connection.responseCode == 200)
            {
                val inputSystem = connection.inputStream
                println(inputSystem.toString())
            }
            else
            {
                binding.baseCurrency.text = "Failed Connection"
            }
        }

    }
}