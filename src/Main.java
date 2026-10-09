import Modelo.Estudiante;
import Modelo.Inscripcion;
import Modelo.Sala;
import Modelo.actividades.Taller;
import Modelo.actividades.Actividad;
import Modelo.EventoUniversitario;
import excepciones.CupoExcedidoException;
import Modelo.certificacion.Certificable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import hilos.*;

public class Main {
    public static void main(String[] args) {

        Estudiante Juan = new Estudiante("53505", "Juan");
        Estudiante Lola = new Estudiante("53501", "Lola");
        Estudiante Carlos = new Estudiante("53507", "Carlos");
        List<Estudiante> estudiante = new ArrayList<>();
        estudiante.add(Juan);
        estudiante.add(Lola);
        estudiante.add(Carlos);

        EventoUniversitario Event1 = new EventoUniversitario("EV001", "Hackathon 2026", false, 20000);


        Sala sala1 = new Sala(1508, "Aula Magna");
        Event1.asignarsala(sala1);

        Event1.crearActividad(101, "Charla apertura", 1, "Charla", "Ing. Serrano");
        Event1.crearActividad(102, "Taller Robotica", 2, "taller", false);
        Event1.crearActividad(104, "Curso de ingles", 5, "curso", 4);

        List<Actividad> actividadesEvent1 = Event1.getActividades();
        try {
            // 1. Inscripciones exitosas primero
            actividadesEvent1.get(0).inscribir(Juan);   // Charla (Cupo 1/1)
            actividadesEvent1.get(1).inscribir(Lola);   // Taller (Cupo 1/2)
            actividadesEvent1.get(1).inscribir(Juan);   // Taller (Cupo 2/2)

            // ¡Inscribimos a Lola en el Curso acá! (El curso es el get(2))
            actividadesEvent1.get(2).inscribir(Lola);   // Curso (Cupo 1/5)

            // 2. La inscripción que sabemos que va a fallar, al final
            actividadesEvent1.get(0).inscribir(Carlos); // ¡Falla! Cupo lleno -> Salta al catch

            System.out.println("Inscripción exitosa!"); // Esta línea se la saltea

        } catch (CupoExcedidoException e) {
            System.out.println("ERROR: " + e.getMessage());
        } finally {
            System.out.println("Finalizó el intento de inscripción");
        }
        Event1.mostrarDatos();
        System.out.println("Actividades de Evento:");
        for (Actividad a : actividadesEvent1) {
            a.mostraridentificacion();
        }
// EMISIÓN DE CERTIFICADOS
        System.out.println("\n--- EMISIÓN DE CERTIFICADOS ---");

        for (Actividad actividad : actividadesEvent1) {

            // Le preguntamos a la actividad si implementa la interfaz Certificable
            if (actividad instanceof Certificable) {

                // Como nos dijo que sí, la "transformamos" (casteamos) temporalmente a Certificable
               Certificable actividadQueCertifica = (Certificable) actividad;

                // Recorremos todos los alumnos inscriptos en esta actividad específica
                // Asegurate de importar modelo.Inscripcion si te marca error acá abajo
                for (Inscripcion inscripcion : actividad.getInscripciones()) {

                    // ¡Llamamos al método de la interfaz!
                    String diploma = actividadQueCertifica.generarCertificado(inscripcion.getEstudiante());
                    System.out.println(diploma);
                }
            }
        }
        //fitramos las actividades
        List<Modelo.actividades.Taller> listaTaller = Event1.filtrarActividadesPorTipo(Modelo.actividades.Taller.class);
        List<Modelo.actividades.Charla> listaCharla = Event1.filtrarActividadesPorTipo(Modelo.actividades.Charla.class);
        List<Modelo.actividades.Curso> listaCurso =  Event1.filtrarActividadesPorTipo(Modelo.actividades.Curso.class);
        // cantidad de actividades de cada tipo creadas para cada evento.

        System.out.println("\n-----cantidad de actividades de cada tipo creadas para cada evento ----");
        System.out.println("Taller:" + listaTaller.size());
        System.out.println("Charla:" + listaCharla.size());
        System.out.println("Curso:" + listaCurso.size());

        double costoCharla= Event1.calcularCostoMateriales(listaCharla);
        double CostoTaller= Event1.calcularCostoMateriales(listaTaller);
        double CostoCurso= Event1.calcularCostoMateriales(listaCurso);

        System.out.println("\n----- COSTO DE CADA ACTIVIDAD----");
        System.out.println("Costo Curso:" + CostoCurso);
        System.out.println("Costo Taller:" + CostoTaller);
        System.out.println("Costo charla: " + costoCharla);




        System.out.println("\n----- INICIANDO GUARDADO----");

        boolean guardadoExitoso= Event1.persistirEvento();
        if (guardadoExitoso){
            System.out.println("El evento se guardo correctamente en el disco");
        }else {
            System.out.println("El evento no se pudo guardar");
        }

        System.out.println("\n--- INICIANDO RECUPERACIÓN DEL EVENTO ---");

        // Llamamos al método estático pasándole el ID exacto con el que lo guardamos
        EventoUniversitario eventoRecuperado = EventoUniversitario.recuperarEvento("EV001");

        if (eventoRecuperado != null) {
            System.out.println("¡Éxito! El evento 'revivió' desde el disco duro.");
            System.out.println("Título recuperado: " + eventoRecuperado.getTitulo()); // Debería decir Hackathon 2026
            System.out.println("Cantidad de actividades recuperadas: " + eventoRecuperado.getActividades().size());
        } else {
            System.out.println("No se pudo recuperar el evento.");
        }



        //clase anidada y concurrencia
        System.out.println("\n--- INICIANDO PROCESO CONCURRENTE (EJERCICIO 4) ---");

        // 1. Confirmamos un par de inscripciones manualmente para que generen sus tickets internos
        // (El Taller está en la posición 1 y el Curso en la 2 de la lista actividadesEvent1)
        if (!actividadesEvent1.get(1).getInscripciones().isEmpty()) {
            actividadesEvent1.get(1).getInscripciones().get(0).confirmar();
        }
        if (!actividadesEvent1.get(2).getInscripciones().isEmpty()) {
            actividadesEvent1.get(2).getInscripciones().get(0).confirmar();
        }

        // 2. Creamos el motor secundario pasándole nuestro evento
        hilos.EnvioTicketThread hiloEnvio = new hilos.EnvioTicketThread(Event1);

        // 3. ¡ARRANCAMOS EL HILO SECUNDARIO! (Se usa start(), NUNCA run())
        hiloEnvio.start();

        // 4. MIENTRAS EL HILO SECUNDARIO TRABAJA, EL PRINCIPAL SIGUE SU CURSO
        System.out.println("[HILO PRINCIPAL] -> ¡El sistema no se trabó! Sigo procesando datos...");

        for (Actividad a : actividadesEvent1) {
            System.out.println("[HILO PRINCIPAL] -> Promocionando actividad: " + a.getTitulo());
            try {
                // Le ponemos una pausa al principal también para que compita con el hilo secundario
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 5. Orden de sincronización (Opcional pero recomendado)
        // Le pedimos al hilo principal que espere pacientemente a que termine el envío
        // antes de seguir hacia abajo y guardar el archivo, así la consola no se pisa.
        try {
            hiloEnvio.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
