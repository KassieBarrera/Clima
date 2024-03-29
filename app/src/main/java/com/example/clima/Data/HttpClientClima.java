package com.example.clima.Data;

import com.example.clima.util.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/*ESTA HACIENDO UNA CONECCION CON EL METODO GET , INPUT STREAM LLENA LOS DATOS, BUFFER READER ALMACENA
 * ESTOS DATOS */


public class HttpClientClima{

    public String getWeatherData(String lugar) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpURLConnection) (new URL(Utils.INSTANCE.getBASE_URL() + lugar)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            StringBuilder stringBuffer = new StringBuilder();
            inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line).append("\r\n");
            }
            inputStream.close();
            connection.disconnect();

            return stringBuffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
