package com.example.myproyect.actividades.clases;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Fecha {
    public static  String dia1,dia6;
    public static String lblTablaReserva;
    private static String zonaHoraria = "America/Lima";

    public static  List<String> obtenerDiasSemanaProximos() {
        List<String> listaDiasSemana = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(zonaHoraria));

        calendar.add(Calendar.DAY_OF_YEAR, 1); // Avanzar al día de mañana
        int anioActual = calendar.get(Calendar.YEAR);
        int mesActual = calendar.get(Calendar.MONTH);

        DateFormatSymbols dfs = new DateFormatSymbols(new Locale("es", "ES")); //cambiar a español

        String[] nombresMesesAbreviados = dfs.getShortMonths();
        String nombreMesAbreviado = nombresMesesAbreviados[mesActual];
        String[] nombresDiasSemana = dfs.getShortWeekdays();

        for (int i = 0; i < 6; i++) {

            int numeroDiaSemana = calendar.get(Calendar.DAY_OF_WEEK);
            String diaSemana = nombresDiasSemana[numeroDiaSemana];
            String numeroDia = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

            String cadenaDiaSemana = diaSemana + " " + numeroDia;
            listaDiasSemana.add(cadenaDiaSemana);
            if(i==0){
                dia1 = numeroDia;
            }else if(i==5){
                dia6= numeroDia;
                lblTablaReserva = dia1+" - "+dia6+" "+nombreMesAbreviado+" "+String.valueOf(anioActual);
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1); // Avanzar al siguiente día
        }

        return listaDiasSemana;
    }

    public static List<String>  getFechas(){
        //retornar las fechas de los proximos días
        List<String> fechas = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(zonaHoraria));
        calendar.add(Calendar.DAY_OF_YEAR, 1); // Saltar al día de mañana

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        formato.setTimeZone(TimeZone.getTimeZone(zonaHoraria));

        for (int i = 0; i < 6; i++) {
            fechas.add(formato.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        return fechas;
    }

    public static int obtenerNumeroDiaActual() {
        Calendar calendar = Calendar.getInstance();
        int numeroDiaActual = calendar.get(Calendar.DAY_OF_YEAR);
        return numeroDiaActual;
    }

    public static boolean getZonaHoraria(){
        // Obtener la zona horaria del dispositivo
        TimeZone zonaHoraria = TimeZone.getDefault();

        // Obtener el nombre de la zona horaria
        String zonaNombre = zonaHoraria.getID();

        // Verificar si la zona horaria es Lima, Perú (GMT-5)
        if (zonaNombre.equals("America/Lima")) {
            return true; // Es la zona horaria de Lima, Perú
        }
        return false; // No es la zona horaria de Lima, Perú
    }
    public static boolean validarVenci(String fecha){

        // Dividir la cadena en mes y año
        String[] partes = fecha.split("/");
        int mes = Integer.parseInt(partes[0]);
        int anio = Integer.parseInt(partes[1]) + 2000; // Convertir AA a formato completo

        // Verificar que el mes esté en el rango válido
        if (mes < 1 || mes > 12) {
            return false;
        }

        // Obtener el mes y año actuales
        Calendar calendario = Calendar.getInstance();
        int mesActual = calendario.get(Calendar.MONTH) + 1; // Los meses son de 0-11
        int anioActual = calendario.get(Calendar.YEAR);

        // Validar que la fecha sea futura o actual
        if (anio > anioActual || (anio == anioActual && mes >= mesActual)) {
            return true; // Fecha válida
        }

        return false; // Fecha vencida
    }

    public static boolean esFechaPasada(String fecha) {
        // Definir la zona horaria GMT-5
        ZoneId zonaHoraria = ZoneId.of("GMT-5");

        // Formatear el string a LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaIngresada = LocalDate.parse(fecha, formatter);

        // Obtener la fecha actual en la zona horaria especificada
        ZonedDateTime fechaActual = ZonedDateTime.now(zonaHoraria);

        // Comparar las fechas
        return fechaIngresada.isBefore(fechaActual.toLocalDate());
    }



}
