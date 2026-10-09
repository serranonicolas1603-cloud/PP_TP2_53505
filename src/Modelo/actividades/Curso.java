package Modelo.actividades;
import Modelo.Estudiante;
import Modelo.certificacion.Certificable;


public class Curso extends Actividad implements Certificable{
    int nivel;

    public Curso(int id, String titulo, int cupomax, int nivel) {
        super(id, titulo, cupomax);
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public double calcularCostoMateriales() {
        return 0;
    }

    @Override
    public String getTipo() {
        return "Curso";
    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "La " + ENTIDAD_EMISORA + " certifica que el alumno " + estudiante.getNombre() +
                " ha participado del " + getTipo() + " '" + titulo + "' de nivel " + nivel + ".";
    }
}
