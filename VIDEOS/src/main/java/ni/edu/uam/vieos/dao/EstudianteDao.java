package ni.edu.uam.vieos.dao;

import ni.edu.uam.vieos.interfaces.CRUD;
import ni.edu.uam.vieos.modelos.Estudiante;

import java.util.ArrayList;
import java.util.List;

public class EstudianteDao implements CRUD<Estudiante> {
    private final List<Estudiante> estudiantes = new ArrayList<>();

    @Override
    public void agregar(Estudiante entidad) {
        if (entidad == null) {
            throw new IllegalArgumentException("El estudiante no puede ser nulo.");
        }
        estudiantes.add(entidad);
    }

    @Override
    public List<Estudiante> obtenerRegistros() {
        return estudiantes;
    }

    public void actualizar(int indice, Estudiante estudiante) {
        if (indice < 0 || indice >= estudiantes.size()) {
            throw new IndexOutOfBoundsException("Índice de estudiante inválido.");
        }
        estudiantes.set(indice, estudiante);
    }

    public void eliminar(int indice) {
        if (indice < 0 || indice >= estudiantes.size()) {
            throw new IndexOutOfBoundsException("Índice de estudiante inválido.");
        }
        estudiantes.remove(indice);
    }
}
