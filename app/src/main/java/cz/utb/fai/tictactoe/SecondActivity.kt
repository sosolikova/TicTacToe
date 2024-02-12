package cz.utb.fai.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cz.utb.fai.tictactoe.databinding.ActivityMainBinding
import java.net.HttpURLConnection
import java.net.URL
import android.widget.TextView
import com.google.gson.Gson
import cz.utb.fai.tictactoe.databinding.ActivitySecondBinding
import java.io.InputStreamReader


class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
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
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, Request::class.java)
                updateUI(request)
                inputStreamReader.close()
                inputSystem.close()
            }
            else
            {
                binding.baseCurrency.text = "Failed Connection"
            }
        }
    }
    private fun updateUI(request: Request)
    {
        runOnUiThread{
            kotlin.run {
                binding.lastUpdated.text = request.time_last_update_utc
                binding.eur.text = String.format("EUR: %.2f", request.rates.EUR)
                binding.usd.text = String.format("USD: %.2f", request.rates.USD)
                binding.gbp.text = String.format("EUR: %.2f", request.rates.GBP)
            }
        }
    }

}