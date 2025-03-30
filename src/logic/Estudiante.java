package logic;

public class Estudiante extends Persona{

    private String grado;
    private String colegio;
    private String talla;

    public Estudiante(String nombre, String grado, String colegio, String talla) {
        super(nombre);
        this.grado = grado;
        this.colegio = colegio;
        this.talla = talla;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getColegio() {
        return colegio;
    }

    public void setColegio(String colegio) {
        this.colegio = colegio;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }
}
