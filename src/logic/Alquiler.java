package logic;

import java.time.LocalDateTime;

public class Alquiler {
    private Responsable responsable;
    private Estudiante estudiante;
    private Traje traje;
    private int cantidad;
    private boolean cancelado;
    private LocalDateTime fechaRetiro;
    private LocalDateTime fechaEntrega;
    private double deposito;
    private boolean devuelto;

    public Alquiler(Responsable responsable, Estudiante estudiante, Traje traje, int cantidad,
                    LocalDateTime fechaRetiro, LocalDateTime fechaEntrega, double deposito) {
        this.responsable = responsable;
        this.estudiante = estudiante;
        this.traje = traje;
        this.cantidad = cantidad;
        this.fechaRetiro = fechaRetiro;
        this.fechaEntrega = fechaEntrega;
        this.deposito = deposito;
        this.cancelado = false;
        this.devuelto = false;
    }

    public void cancelarAlquiler() {
        this.cancelado = true;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void marcarDevuelto() {
        this.devuelto = true;
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
}
