package edu.wearedev.firstapp.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import edu.wearedev.firstapp.R

class MainActivity : AppCompatActivity() {

    lateinit var btnCalcular : Button
    lateinit var listaCarros : RecycleView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setupView()
        setupListeners()
        setupList()
        }

    fun setupView(){
        btnCalcular = findViewById(R.id.btn_calcular)
        listaCarros = findViewById(R.id.rv_lista_carros)
    }

    fun setupList(){
        var dados = arrayOf(
            "CupCake", "Donut", "Froyo", "Gingerbread", "HoneyComb", "Ice Cream Sandwich"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dados)
        listaCarros.adapter = adapter
    }

    fun setupListeners(){
        btnCalcular.setOnClickListener {
            /*calcular()*/
            startActivity(Intent(this, CalcularAutonomiaActivity::class.java))
        }
    }

}