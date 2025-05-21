package model;

import java.time.LocalDateTime;

public class Alquiler {
    private Responsable responsable;
    private Estudiante estudiante;
    private Traje traje;
    private int cantidad;
    private LocalDateTime fechaRetiro;
    private LocalDateTime fechaEntrega;
    private double deposito;
    private double multa;
    private double deuda;
    private boolean entregado;


    public Alquiler(Responsable responsable, Estudiante estudiante, Traje traje, int cantidad,
                    LocalDateTime fechaRetiro, LocalDateTime fechaEntrega, double deposito) {
        this.responsable = responsable;
        this.estudiante = estudiante;
        this.traje = traje;
        this.cantidad = cantidad;
        this.fechaRetiro = fechaRetiro;
        this.fechaEntrega = fechaEntrega;
        this.deposito = deposito;
        this.multa = 0;
        this.deuda = 0;
        this.entregado = false;
    }

    public boolean isEntregado() {
        return entregado;
    }

    public void entregar() {
        this.entregado = true;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Traje getTraje() {
        return traje;
    }

    public void setTraje(Traje traje) {
        this.traje = traje;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(LocalDateTime fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }


    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public double getDeposito() {
        return deposito;
    }

    public void setDeposito(double deposito) {
        this.deposito = deposito;
    }

    public double getMulta() {
        return multa;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }

    public double getDeuda() {
        return deuda;
    }

    public void setDeuda(double deuda) {
        this.deuda = deuda;
    }
}
