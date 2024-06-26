package edu.wearedev.firstapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CalcularAutonomiaActivity : AppCompatActivity() {

    lateinit var preco : EditText
    lateinit var kmPercorrido : EditText
    lateinit var resultado: TextView
    lateinit var btnCalcular : Button
    lateinit var btnClose : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcular_autonomia)
        setupView()
        setupListeners()
    }

    fun setupView(){
          btnCalcular = findViewById(R.id.btn_calcular)
          preco = findViewById(R.id.et_preco_kwh)
          kmPercorrido = findViewById(R.id.et_km_percorrido)
          resultado = findViewById(R.id.tv_resultado)
          btnClose = findViewById(R.id.iv_close)
    }

    fun setupListeners(){
        btnCalcular.setOnClickListener {
            calcular()
        }

        btnClose.setOnClickListener {
            finish()
        }

    }

    fun calcular() {
        val preco = preco.text.toString().toFloat()
        val km = kmPercorrido.text.toString().toFloat()

        val result = preco / km

        resultado.text = result.toString()

    }
}