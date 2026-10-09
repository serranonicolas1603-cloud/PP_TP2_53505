package Modelo;

import Modelo.actividades.Actividad;
import Modelo.actividades.Charla;
import Modelo.actividades.Curso;
import Modelo.actividades.Taller;


import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.io.*;

public class EventoUniversitario implements java.io.Serializable {
    private final String id;
    private String titulo;
    private boolean gratuito;
    private double costoBase;
    private static int cantidadEventos;
    private List<Actividad> actividades;


    public EventoUniversitario(String id, String titulo, boolean gratuito, double costoBase) {
        this.id = id;
        this.titulo = titulo;
        this.gratuito = gratuito;
        this.costoBase = costoBase;
        this.actividades = new ArrayList<>();
        cantidadEventos++;
    }

    public EventoUniversitario(EventoUniversitario otro) {
        this.id = otro.id;
        this.titulo = otro.titulo;
        this.gratuito = otro.gratuito;
        this.costoBase = otro.costoBase;
        this.actividades = new ArrayList(otro.actividades);
        cantidadEventos++;
    }

    public String getTitulo() {
        return titulo;
    }

    public double calcularCostoEstimado() {
        if (gratuito) {
            return 0;

        } else {
            double costoTotaldeActividades = 0;
            for (Actividad i : actividades) {
                costoTotaldeActividades += i.calcularCostoMateriales();
            }
            return (costoBase + costoTotaldeActividades) * 1.21;
        }
    }

    public void asignarsala(Sala sala) {
        System.out.println("EL nombre de la sala asignada es: " + sala.getNombre() + " de id: " + sala.getId() + " al evento: " + titulo);
    }

    public void crearActividad(int id, String titulo, int cupo, String tipo) {
        if (tipo.equalsIgnoreCase("Taller")) {
            crearActividad(id, titulo, cupo, tipo, false); // valor por defecto
        } else if (tipo.equalsIgnoreCase("Charla")) {
            crearActividad(id, titulo, cupo, tipo, "Sin especificar");
        } else if (tipo.equalsIgnoreCase("Curso")) {
            crearActividad(id, titulo, cupo, tipo, 1);
        } else {
            System.out.println("clasificacion de actividad invalida");
        }
    }

    public void crearActividad(int id, String titulo, int cupo, String tipo, int nivel) {
        Actividad nuevaactividad = new Curso(id, titulo, cupo, nivel);
        this.actividades.add(nuevaactividad);
    }

    public void crearActividad(int id, String titulo, int cupo, String tipo, boolean requiereNotebook) {
        Actividad nuevaactividad = new Taller(id, titulo, cupo, requiereNotebook);
        this.actividades.add(nuevaactividad);
    }

    public void crearActividad(int id, String titulo, int cupo, String tipo, String disertante) {
        Actividad nuevaactividad = new Charla(id, titulo, cupo, disertante);
        this.actividades.add(nuevaactividad);
    }

    public void mostrarDatos() {
        System.out.println("Evento:" + titulo
                + "\nID:" + id
                + "\nGRATUITO:" + gratuito
                + "\nPRECIO INICIAL:" + costoBase
                + "\nCOSTO FINAL:" + calcularCostoEstimado());

    }

    public static int getCantidadEventos() {
        return cantidadEventos;

    }

    public List<Actividad> getActividades() {
        return Collections.unmodifiableList(actividades);
    }

    public boolean persistirEvento() {
        try {
            // 1. Creamos el túnel físico de salida desde el archivo
            FileOutputStream archivo = new FileOutputStream(id + ".dat");
            // 2. Creamos el traductor que va a convertir el objeto a un flujo de bytes
            ObjectOutputStream salida = new ObjectOutputStream(archivo);

            salida.writeObject(this);
            salida.close();
            return true;
        } catch (IOException e) {
            System.out.println("ERROR al guardad el archivo en el disco" + e.getMessage());
            return false;
        }


    }

    public static EventoUniversitario recuperarEvento(String id) {
        // Envolvemos todo en un try-catch porque leer el disco duro también puede fallar
        try {
            // 1. Creamos el túnel físico de ENTRADA desde el archivo
            FileInputStream archivo = new FileInputStream(id + ".dat");

            // 2. Creamos el traductor que va a convertir los bytes de nuevo a objetos
            ObjectInputStream entrada = new ObjectInputStream(archivo);

            // 3. Leemos el objeto y lo "casteamos" a EventoUniversitario
            EventoUniversitario eventoRecuperado = (EventoUniversitario) entrada.readObject();

            // 4. Cerramos el túnel
            entrada.close();

            return eventoRecuperado;

        } catch (IOException | ClassNotFoundException e) {
            // Atrapamos cualquier error de lectura (como que el archivo no exista)[cite: 2]
            System.out.println("Error grave al intentar recuperar el archivo: " + e.getMessage());
            return null;
        }
    }
            //acotacion                     //metodo
    public <T extends Actividad> List<T> filtrarActividadesPorTipo(Class<T> tipo) {
        List<T> resultado = new ArrayList<>();
        for (Actividad i : actividades) {
            if (tipo.isInstance(i)) {
                resultado.add(tipo.cast(i));
            }
        }
        return resultado;
    }
    public double calcularCostoMateriales(List<? extends Actividad> actividades){
        double costoTotaldeActividades = 0;
        for (Actividad i : actividades) {
            costoTotaldeActividades += i.calcularCostoMateriales();
        }
        return costoTotaldeActividades;
    }




}
