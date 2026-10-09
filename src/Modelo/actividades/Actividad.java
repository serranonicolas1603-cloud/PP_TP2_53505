package Modelo.actividades;
import Modelo.Inscripcion;
import Modelo.Estudiante;
import excepciones.CupoExcedidoException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public abstract class Actividad implements java.io.Serializable{
    protected int id;
    protected String titulo;
    protected int cupomax;
    public static final int cupoMin = 5;
    protected List<Inscripcion> inscripciones;

    public Actividad(int id, String titulo, int cupomax) {
        this.id = id;
        this.titulo = titulo;
        this.cupomax = cupomax;
        this.inscripciones = new ArrayList<>();

    }
    public Inscripcion inscribir (Estudiante estudiante) throws CupoExcedidoException{
            if (inscripciones.size() >= cupomax) {
                throw new CupoExcedidoException("No hay cupo disponible en " + titulo + " para " + estudiante.getNombre());
            }
        Inscripcion inscripto1 = new Inscripcion(this, estudiante , LocalDate.now(), "confirmado");
        inscripciones.add(inscripto1);
        return inscripto1;
    }
    public void mostrarincripcion (){
        for (Inscripcion i: inscripciones){
            System.out.println("" + i);

        }

    }
    public final void mostraridentificacion(){
        System.out.println(" id: " + id + " titulo " +titulo+ " tipo;" +getTipo()   );
    }
    public abstract double calcularCostoMateriales();
    public abstract String getTipo();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getCupomax() {
        return cupomax;
    }

    public void setCupomax(int cupomax) {
        this.cupomax = cupomax;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }
}
