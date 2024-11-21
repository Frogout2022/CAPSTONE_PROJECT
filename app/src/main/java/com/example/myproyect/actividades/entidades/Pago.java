package com.example.myproyect.actividades.entidades;

public class Pago {
    private String dniCliPago;
    private String codPago;
    private String estadoPago;
    private String fechaPago;
    private double montoTotal;
    private double igvPago;
    private String medioPago;

    public Pago(String fechaPago, double montoTotal, String medioPago, String codPago) {
        this.fechaPago = fechaPago;
        this.montoTotal = montoTotal;
        this.medioPago = medioPago;
        this.codPago = codPago;
    }

    public Pago(String dniCliPago, String codPago, String estadoPago, String fechaPago, double montoTotal, double igvPago, String medioPago) {
        this.dniCliPago = dniCliPago;
        this.codPago = codPago;
        this.estadoPago = estadoPago;
        this.fechaPago = fechaPago;
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
}
