package Modelo.actividades;
import Modelo.Estudiante;
import Modelo.certificacion.Certificable;
public class Taller extends Actividad implements Certificable {
    private boolean requiereNotebook;

    public Taller(int id, String titulo, int cupomax, boolean requiereNotebook) {
        super(id, titulo, cupomax);//invoca al constructor de actividad
        this.requiereNotebook = requiereNotebook;
    }

    @Override
    public double calcularCostoMateriales() {
        if (requiereNotebook){
            return 5000.0;
        }
        return 2000.0;
    }

    @Override
    public String getTipo() {
        return "Taller";
    }

    public boolean isRequiereNotebook() {
        return requiereNotebook;
    }

    public void setRequiereNotebook(boolean requiereNotebook) {
        this.requiereNotebook = requiereNotebook;
    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "La " + ENTIDAD_EMISORA + " certifica que el alumno " + estudiante.getNombre() +
                " ha participado del " + getTipo() + " '" + titulo ;
    }
}
