package ni.edu.uam.vieos.interfaces;

import java.util.List;

public interface CRUD<T> {
    void agregar(T entidad);
    List<T> obtenerRegistros();
}
