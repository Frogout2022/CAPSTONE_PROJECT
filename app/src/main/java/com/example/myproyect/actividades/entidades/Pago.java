package com.example.myproyect.actividades.entidades;

public class Pago {

    private String fechaPago;
    private String codPago;
    private Double montoTotal;
    private Double igvPago;
    private String medioPago;


    public Pago() {

    }

    public Pago(String fechaPago, String codPago, double montoTotal, double igvPago, String medioPago) {
        this.fechaPago = fechaPago;
        this.codPago = codPago;
        this.montoTotal = montoTotal;
        this.igvPago = igvPago;
        this.medioPago = medioPago;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getCodPago() {
        return codPago;
    }

    public void setCodPago(String codPago) {
        this.codPago = codPago;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public double getIgvPago() {
        return igvPago;
    }

    public void setIgvPago(Double igvPago) {
        this.igvPago = igvPago;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }
}
