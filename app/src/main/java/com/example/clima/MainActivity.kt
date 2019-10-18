package com.example.clima

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.clima.Data.HttpClientClima
import com.example.clima.Data.JSONParseClima
import com.example.clima.Data.PreferenciaCiudad
import com.example.clima.Model.Clima
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.text.DecimalFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var clima = Clima()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ciudadPrefiere: PreferenciaCiudad = PreferenciaCiudad(this)
        rendereClimaDatos(ciudadPrefiere.ciudad)
    }

    fun rendereClimaDatos(ciudad: String) {
        val climaTask = ClimaTask()
        climaTask.execute(*arrayOf(ciudad + "&APPID=23ca71a1c9c69e0270982851235c5221&units=metric"))
    }

    /*private inner class descargarImagen : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg params: String?): Bitmap {

            return descargaImagen(params[0]!!)
        }

        override fun onPostExecute(result: Bitmap?) {
            imgIcono.setImageBitmap(result)
        }

        fun descargaImagen(codigo: String): Bitmap {

            val cliente = DefaultHttpClient()
            val getRequest = HttpGet(Utils.ICON_URL + codigo + ".png")

            try {

                val response = cliente.execute(getRequest)
                val status = response.statusLine.statusCode

                if (status != HttpStatus1.SC_OK) {
                    Log.e("DescatgaImagen", "Error" + status)
                    return null!!
                }

                val entity = response.entity
                if (entity != null) {
                    val inputStream: InputStream?
                    inputStream = entity.content

                    val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                    return bitmap
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null!!
        }

    }*/

    private inner class ClimaTask : AsyncTask<String, Void, Clima>() {
        @SuppressLint("WrongThread")
        override fun doInBackground(vararg params: String?): Clima {
            val datos = HttpClientClima().getWeatherData(params[0])
            clima = JSONParseClima.getWheater(datos)!!
            clima.datosIcon = clima.condicionActual.icono
            // descargarImagen().execute(clima.datosIcon)
            return clima
        }

        override fun onPostExecute(result: Clima?) {
            super.onPostExecute(result)

            val formatoFecha = DateFormat.getTimeInstance()
            val amanecer = formatoFecha.format(Date(clima.lugar!!.amanecer))
            val puesta = formatoFecha.format(Date(clima.lugar!!.anochecer))
            val actualizar = formatoFecha.format(Date(clima.lugar!!.actualizacion))

            val formatoDecimal = DecimalFormat("#.#")
            val formatoTemp = formatoDecimal.format(clima.condicionActual.temperatura)

            txtCiudad.text = clima.lugar!!.ciudad + "," + clima.lugar!!.pais
            txtTemperatura.text = "" + formatoTemp + "°C"
            txtHumedad.text = "Humedad = ${clima.condicionActual.humedad} %"
            txtPrecion.text = "Presion = ${clima.condicionActual.precion}"
            txtViento.text = "Viento = ${clima.viento.velocidad}mps"
            txtSunset.text = "Anochecer = $puesta"
            txtSunrice.text = "Amanecer = $amanecer"
            txtUpdate.text = "Ultima actualizacion: $actualizar"
            txtNubes.text =
                "Nube =  ${clima.condicionActual.condicion} (${clima.condicionActual.descripcion})"

        }
    }

    fun moatrarDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cambiar Ciudad")

        val ponerCiudad = EditText(this)
        ponerCiudad.inputType = InputType.TYPE_CLASS_TEXT
        ponerCiudad.hint = "Guatemala"
        builder.setView(ponerCiudad)
        builder.setPositiveButton("ok") { dialog: DialogInterface?, i ->
            val ciudadPreferencia = PreferenciaCiudad(this)
            ciudadPreferencia.ciudad = ponerCiudad.text.toString()

            val ciudadNueva = ciudadPreferencia.ciudad
            rendereClimaDatos(ciudadNueva)
        }.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cambiar_ciudad -> moatrarDialog()
        }

        return super.onOptionsItemSelected(item)
    }
}

