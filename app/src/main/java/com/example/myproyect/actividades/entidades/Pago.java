package com.example.myproyect.actividades.entidades;

public class Pago {
    private int idPago;
    private String fechaPago;
    private String codPago;
    private String dniCliPago;
    private String nombre_losa;
    private int cantidad_horas;
    private String estadoPago;
    private double montoTotal;
    private double igvPago;
    private String medioPago;

    public Pago(String fechaPago, String codPago, String dniCliPago, String nombre_losa, int cantidad_horas, String estadoPago, double montoTotal, double igvPago, String medioPago) {
        this.fechaPago = fechaPago;
        this.codPago = codPago;
        this.dniCliPago = dniCliPago;
        this.nombre_losa = nombre_losa;
        this.cantidad_horas = cantidad_horas;
        this.estadoPago = estadoPago;
        this.montoTotal = montoTotal;
        this.igvPago = igvPago;
        this.medioPago = medioPago;
    }

    public Pago(String dniCliPago, String nombre_losa, int cantidad_horas, String estadoPago, double montoTotal, String medioPago) {
        this.dniCliPago = dniCliPago;
        this.nombre_losa = nombre_losa;
        this.cantidad_horas = cantidad_horas;
        this.estadoPago = estadoPago;
        this.montoTotal = montoTotal;
        this.medioPago = medioPago;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public String getCodPago() {
        return codPago;
    }

    public void setCodPago(String codPago) {
        this.codPago = codPago;
    }

    public String getDniCliPago() {
        return dniCliPago;
    }

    public void setDniCliPago(String dniCliPago) {
        this.dniCliPago = dniCliPago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public double getIgvPago() {
        return igvPago;
    }

    public void setIgvPago(double igvPago) {
        this.igvPago = igvPago;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getCantidad_horas() {
        return cantidad_horas;
    }

    public void setCantidad_horas(int cantidad_horas) {
        this.cantidad_horas = cantidad_horas;
    }

    public String getNombre_losa() {
        return nombre_losa;
    }

    public void setNombre_losa(String nombre_losa) {
        this.nombre_losa = nombre_losa;
    }
}
