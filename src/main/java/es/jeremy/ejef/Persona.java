package es.jeremy.ejef;

import java.util.Objects;

/**
 * La clase {@code Persona} representa una persona con un nombre, apellidos y edad.
 * Proporciona métodos para acceder y modificar estos datos, y también incluye
 * validación para asegurar que la edad no sea negativa.
 */
public class Persona {

    /** El nombre de la persona. */
    private String nombre;

    /** Los apellidos de la persona. */
    private String apellidos;

    /** La edad de la persona. */
    private int edad;

    /**
     * Crea una nueva instancia de {@code Persona}.
     *
     * @param nombre El nombre de la persona.
     * @param apellidos Los apellidos de la persona.
     * @param edad La edad de la persona.
     * @throws IllegalArgumentException si la edad es negativa.
     */
    public Persona(String nombre, String apellidos, int edad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        setEdad(edad); // Usar el setter para validar la edad
    }

    /**
     * Obtiene el nombre de la persona.
     *
     * @return El nombre de la persona.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene los apellidos de la persona.
     *
     * @return Los apellidos de la persona.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Obtiene la edad de la persona.
     *
     * @return La edad de la persona.
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Establece el nombre de la persona.
     *
     * @param nombre El nombre que se quiere asignar a la persona.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece los apellidos de la persona.
     *
     * @param apellidos Los apellidos que se quieren asignar a la persona.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Establece la edad de la persona.
     * Valida que la edad no sea negativa.
     *
     * @param edad La edad que se quiere asignar a la persona.
     * @throws IllegalArgumentException si la edad es negativa.
     */
    public void setEdad(int edad) {
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa.");
        }
        this.edad = edad;
    }

    /**
     * Compara si dos objetos {@code Persona} son iguales.
     * La igualdad se basa en los valores de nombre, apellidos y edad.
     *
     * @param obj El objeto que se va a comparar con esta persona.
     * @return {@code true} si los objetos son iguales; {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Persona)) return false;
        Persona persona = (Persona) obj;
        return edad == persona.edad &&
                Objects.equals(nombre, persona.nombre) &&
                Objects.equals(apellidos, persona.apellidos);
    }

    /**
     * Devuelve el código hash basado en el nombre, apellidos y edad de la persona.
     *
     * @return Un valor entero que representa el código hash de esta persona.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellidos, edad);
    }

    /**
     * Devuelve una representación en cadena de la persona.
     * El formato es: "Nombre Apellidos (Edad años)".
     *
     * @return Una cadena con el formato de visualización de la persona.
     */
    @Override
    public String toString() {
        return nombre + " " + apellidos + " (" + edad + " años)";
    }
}
