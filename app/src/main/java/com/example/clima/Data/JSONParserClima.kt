package com.example.clima.Data

import com.example.clima.Model.Clima
import com.example.clima.Model.Lugar
import com.example.clima.util.Utils
import org.json.JSONException
import org.json.JSONObject

object JSONParseClima {

    fun getWheater(data: String): Clima?{
        val clima = Clima()

        try {

            val jsonObject = JSONObject(data) //se declaran objetos
            val lugar = Lugar()

            val coordObject = Utils.getObject("coord", jsonObject)  //Creamos el objeto coord, que tiene latitud y longitud
            lugar.latitud =  Utils.getFloat("lat", coordObject)  //atributos del objeto
            lugar.longitud = Utils.getFloat("lon",coordObject)

            val sysObj = Utils.getObject("sys", jsonObject)
            lugar.pais = Utils.getString("country", sysObj)
            lugar.actualizacion = Utils.getLong("dt", jsonObject)
            lugar.amanecer = Utils.getLong("sunrise", sysObj)
            lugar.anochecer = Utils.getLong("sunset", sysObj)
            lugar.ciudad = Utils.getString("name", jsonObject)

            clima.lugar = lugar

            val mainObj = Utils.getObject("main", jsonObject)
            clima.condicionActual.humedad = Utils.getFloat("humidity", mainObj)
            clima.condicionActual.temperatura = Utils.getDouble("temp", mainObj)
            clima.condicionActual.precion = Utils.getFloat("pressure", mainObj)
            clima.condicionActual.maxTemp = Utils.getFloat("temp_max", mainObj)
            clima.condicionActual.minTemp = Utils.getFloat("temp_min", mainObj)

            val jsonArray = jsonObject.getJSONArray("weather")
            val jsonWeather = jsonArray.getJSONObject(0)
            clima.condicionActual.weatherId = Utils.getInt("id", jsonWeather)
            clima.condicionActual.descripcion = Utils.getString("description", jsonWeather)
            clima.condicionActual.condicion = Utils.getString("main", jsonWeather)
            clima.condicionActual.icono = Utils.getString("icon", jsonWeather)

            val vientoObj = Utils.getObject("wind", jsonObject)
            clima.viento.velocidad = Utils.getFloat("speed", vientoObj)
            clima.viento.cent = Utils.getFloat("deg", vientoObj)

            val nubeObj = Utils.getObject("clouds", jsonObject)
            clima.nubes.precipitacion = Utils.getInt("all", nubeObj)

            return clima
        } catch (e:JSONException){
            e.printStackTrace()
        }
        return null
    }
}