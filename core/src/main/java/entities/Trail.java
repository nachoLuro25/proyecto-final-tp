package entities;

import java.awt.Point;
import java.util.LinkedList;

/**
 * Clase que representa la estela (rastro) que deja una moto
 * Es una lista de puntos que va creciendo a medida que la moto se mueve
 *
 * @author Tu Nombre
 * @version 1.0
 */
public class Trail {

    private LinkedList<Point> points;

    /**
     * Constructor de Trail
     * Inicializa la lista de puntos vacía
     */
    public Trail() {
        this.points = new LinkedList<>();
    }

    /**
     * Agrega un nuevo punto a la estela
     * @param p Punto a agregar
     */
    public void addPoint(Point p) {
        points.add(new Point(p)); // Copia defensiva
    }

    /**
     * Obtiene la lista de puntos de la estela
     * @return LinkedList con todos los puntos
     */
    public LinkedList<Point> getPoints() {
        return points;
    }

    /**
     * Limpia toda la estela (borra todos los puntos)
     */
    public void clear() {
        points.clear();
    }

    /**
     * Verifica si un punto dado está en la estela
     * @param point Punto a verificar
     * @return true si el punto está en la estela, false si no
     */
    public boolean contains(Point point) {
        return points.contains(point);
    }

    /**
     * Obtiene el tamaño de la estela
     * @return Cantidad de puntos en la estela
     */
    public int size() {
        return points.size();
    }

    /**
     * Obtiene el último punto agregado (la posición actual de la moto)
     * @return Último punto, o null si la estela está vacía
     */
    public Point getLastPoint() {
        return points.isEmpty() ? null : points.getLast();
    }
}
