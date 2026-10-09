package Modelo;

import Modelo.actividades.Actividad;

import java.io.Serializable;
import java.time.LocalDate;

public class Inscripcion implements java.io.Serializable{
    private Actividad actividad;
    private Estudiante estudiante;
    private LocalDate fecha;
    private String Estado;
    private TicketDeAcceso ticket;

    public Inscripcion(Actividad actividad, Estudiante estudiante, LocalDate fecha, String estado) {
        this.estudiante = estudiante;
        this.actividad = actividad;
        this.fecha = fecha;
        Estado = estado;

    }

    public TicketDeAcceso getTicket() {
        return ticket;
    }

    public void setTicket(TicketDeAcceso ticket) {
        this.ticket = ticket;
    }
    public void confirmar(){
        Estado ="confimar";
        ticket = new TicketDeAcceso ("TKT" + estudiante.getNombre() + estudiante.getLegajo() + LocalDate.now());

    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    @Override
    public String toString() {
        return "Modelo.Inscripcion [estudiante=" + estudiante.getNombre()+ " actividad= " + actividad.getTitulo() + " fecha=" + fecha + ", estado=" + Estado + "]";
    }
    public class TicketDeAcceso implements Serializable{
        private String IdTicket;
        public LocalDate fechaEmision;

        public TicketDeAcceso(String IdTicket) {
            this.IdTicket = IdTicket;
            fechaEmision = LocalDate.now();
        }

        public void enviarTicket(){
            System.out.println("Ticket enviado" + IdTicket + "enviado a :" +estudiante.getNombre()+ "Fecha de emision" + fechaEmision );

            try{
                Thread.sleep(500);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
