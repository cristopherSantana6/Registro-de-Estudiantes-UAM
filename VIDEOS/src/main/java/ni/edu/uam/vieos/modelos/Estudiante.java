package ni.edu.uam.vieos.modelos;

import java.time.LocalDate;

public class Estudiante {
    private String nombres;
    private String apellidos;
    private String carrera;
    private LocalDate fechaNacimiento;
    private boolean tieneBeca;

    public Estudiante() {
    }

    public Estudiante(String nombres, String apellidos, String carrera,
                      LocalDate fechaNacimiento, boolean tieneBeca) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.carrera = carrera;
        this.fechaNacimiento = fechaNacimiento;
        this.tieneBeca = tieneBeca;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean isTieneBeca() {
        return tieneBeca;
    }

    public void setTieneBeca(boolean tieneBeca) {
        this.tieneBeca = tieneBeca;
    }

    @Override
    public String toString() {
        return nombres + " " + apellidos;
    }
}
