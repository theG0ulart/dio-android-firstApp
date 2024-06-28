package edu.wearedev.firstapp.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.wearedev.firstapp.R
import edu.wearedev.firstapp.data.CarFactory
import edu.wearedev.firstapp.domain.Carro
import edu.wearedev.firstapp.ui.adapter.CarAdapter
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.zip.Inflater

class CarFragment: Fragment() {
    lateinit var fabCalcular : FloatingActionButton
    lateinit var listaCarros : RecyclerView
    lateinit var progress: ProgressBar
    lateinit var noInternetImage: ImageView
    lateinit var noInternetText: TextView

    var carrosArray: ArrayList<Carro> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupListeners()

    }

    override fun onResume() {
        super.onResume()
        if(checkForInternet(context)){
            callService()
        } else {
            emptyState()
        }
    }

    fun emptyState(){
        progress.isVisible = false
        listaCarros.isVisible = false
        noInternetImage.isVisible = true
        noInternetText.isVisible = true
    }

    fun setupView(view: View){
        view.apply{
            fabCalcular = findViewById(R.id.fab_calcular)
            listaCarros = findViewById(R.id.rv_lista_carros)
            progress = findViewById(R.id.pb_loader)
            noInternetText = findViewById(R.id.tv_no_wifi)
            noInternetImage = findViewById(R.id.iv_empty_state)
        }
    }

    fun setupList(){
        var carroAdapter = CarAdapter(carrosArray)

        listaCarros.apply{

        visibility = View.VISIBLE
        adapter = carroAdapter
        }
    }

    fun setupListeners(){
        fabCalcular.setOnClickListener {
           startActivity(Intent(context, CalcularAutonomiaActivity::class.java))
        }

    }

    fun callService (){
        val urlBase = "https://igorbag.github.io/cars-api/cars.json"
        progress.isVisible = true
        MyTask().execute(urlBase)

    }

    fun checkForInternet(context: Context?) :Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
           val network = connectivityManager.activeNetwork ?: return false

            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false

            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }


    inner class MyTask: AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            progress.isVisible = true
        }

        override fun doInBackground(vararg url: String?): String {
            var urlConnection: HttpURLConnection? = null

            try {
                val urlBase = URL(url[0])

                urlConnection = urlBase.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 6000
                urlConnection.readTimeout = 6000
                urlConnection.setRequestProperty(
                    "Accept",
                    "application/json"
                )

                val responseCode = urlConnection.responseCode

                if(responseCode == HttpURLConnection.HTTP_OK){
                    var response = urlConnection.inputStream.bufferedReader().use { it.readText() }
                    publishProgress(response)
                } else {
                    Log.e("Error", "Serviço não disponível")
                }


            } catch (ex: Exception){
                Log.e("Error", "Erro ao realizar conexão")
            } finally {
                if(urlConnection != null)
                    urlConnection.disconnect()
            }

            return " "
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {

                val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray

                for( i in 0 until jsonArray.length()) {
                    val id = jsonArray.getJSONObject(i).getString("id")

                    val preco = jsonArray.getJSONObject(i).getString("preco")

                    val bateria = jsonArray.getJSONObject(i).getString("bateria")

                    val potencia = jsonArray.getJSONObject(i).getString("potencia")

                    val recarga = jsonArray.getJSONObject(i).getString("recarga")

                    val urlPhoto = jsonArray.getJSONObject(i).getString("urlPhoto")


                    val model = Carro(
                        id = id.toInt(),
                        preco = preco,
                        bateria = bateria,
                        potencia = potencia,
                        recarga = recarga,
                        urlPhoto = urlPhoto

                    )

                    carrosArray.add(model)


                    Log.d("model -> ", model.toString())
                }
                progress.isVisible = false
                noInternetImage.isVisible = false
                noInternetText.isVisible = false
                setupList()

            } catch (ex: Exception){
        Log.e("Erro", ex.message.toString())
            }
        }

        fun streamToString(inputStream: InputStream) : String {
            var bufferReader = BufferedReader(InputStreamReader(inputStream))
            var line: String
            var result = ""

            try {
                do {
                    line = bufferReader.readLine()
                    line?.let {
                        result += line
                    }
                } while (line != null)
            }catch (ex: Exception) {
                Log.e("Erro", "Erro ao parcelar o Stream")
            }

            return result
        }
    }
}
