package hilos;
import java.sql.Connection;
import Modelo.EventoUniversitario;
import Modelo.actividades.Actividad;
import Modelo.Inscripcion;

public class EnvioTicketThread extends Thread {
    public EventoUniversitario evento;

    public EnvioTicketThread(EventoUniversitario evento) {
        this.evento = evento;
    }
    @Override
    public void run() {
        System.out.println("\n[HILO SECUNDARIO] -> Iniciando escaneo y envío de tickets en segundo plano...");

        // 1. Recorremos todas las actividades del evento
        for (Actividad actividad : evento.getActividades()) {

            // 2. Recorremos todas las inscripciones de esa actividad
            for (Inscripcion inscripcion : actividad.getInscripciones()) {

                // 3. Filtramos: Solo enviamos si está confirmada y el ticket ya fue fabricado
                if ("Confirmada".equals(inscripcion.getEstado()) && inscripcion.getTicket() != null) {

                    // Ejecutamos el método de la clase anidada que tiene el Thread.sleep(500)
                    inscripcion.getTicket().enviarTicket();
                }
            }
        }

        System.out.println("[HILO SECUNDARIO] -> Envío de tickets finalizado.");


    }
}
