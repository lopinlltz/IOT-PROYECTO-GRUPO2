package com.example.proyecto_final_iot;

import java.util.ArrayList;
import java.util.List;

public class NotificacionManager {
    private static List<Notificacion> notificaciones = new ArrayList<>();

    public static void agregarNotificacion(Notificacion notificacion) {
        notificaciones.add(notificacion);
    }

    public static List<Notificacion> obtenerNotificaciones() {
        return notificaciones;
    }
}