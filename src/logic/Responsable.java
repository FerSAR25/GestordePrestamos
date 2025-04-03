package logic;

public class Responsable extends Persona{

    private String direccion;
    private long celular;
    private long cedula;

    public Responsable(String nombre, String direccion, long celular, long cedula) {
        super(nombre);
        this.direccion = direccion;
        this.celular = celular;
        this.cedula = cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public long getCedula() {
        return cedula;
    }

    public void setCedula(long cedula) {
        this.cedula = cedula;
    }

    public long getCelular() {
        return celular;
    }

    public void setCelular(long celular) {
        this.celular = celular;
    }
}
