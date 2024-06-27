package edu.wearedev.firstapp.data

import edu.wearedev.firstapp.domain.Carro
import java.util.UUID

object CarFactory {

    val list = listOf<Carro>(
        Carro(
            id = ,
            preco = "R$ 300.000,00",
            potencia = "200cv",
            recarga = "30 min",
            bateria = "300 kWh",
            urlPhoto = "www.google.com.br"
        ),
        Carro(
            id = UUID.randomUUID(),
            preco = "R$ 250.000,00",
            potencia = "180cv",
            recarga = "50 min",
            bateria = "500 kWh",
            urlPhoto = "www.google.com.br"
        )
    )
}