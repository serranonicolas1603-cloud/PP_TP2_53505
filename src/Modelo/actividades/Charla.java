package Modelo.actividades;

public class Charla extends Actividad implements java.io.Serializable{
    private String disertante;

    public Charla(int id, String titulo, int cupomax, String diseertante) {
        super(id, titulo, cupomax);
        this.disertante = diseertante;
    }

    @Override
    public double calcularCostoMateriales() {
        return 0;
    }

    @Override
    public String getTipo() {
        return "Charla";
    }

    public String getDiseertante() {
        return disertante;
    }

    public void setDiseertante(String diseertante) {
        this.disertante = diseertante;
    }
}
