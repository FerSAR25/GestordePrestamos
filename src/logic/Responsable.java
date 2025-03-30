package logic;

public class Responsable extends Persona{

    private String direccion;
    private int celular;
    private int cedula;

    public Responsable(String nombre, String direccion, int celular, int cedula) {
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

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }
}
