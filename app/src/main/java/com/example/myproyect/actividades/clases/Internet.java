package com.example.myproyect.actividades.clases;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

public class Internet {

    public static boolean tieneConexionInternet(Context contexto) {
        // Obtener el sistema de conectividad
        ConnectivityManager cm = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Obtener la red activa
        Network activeNetwork = cm.getActiveNetwork();

        if (activeNetwork != null) {
            // Obtener las capacidades de la red activa
            NetworkCapabilities networkCapabilities = cm.getNetworkCapabilities(activeNetwork);

            // Verificar si la red está conectada y si tiene acceso a Internet
            return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        }

        return false; // No hay red activa
    }

}
