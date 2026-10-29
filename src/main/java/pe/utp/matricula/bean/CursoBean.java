package pe.utp.matricula.bean;

import jakarta.faces.bean.ApplicationScoped;
import jakarta.faces.bean.ManagedBean;
import pe.utp.matricula.model.Curso;

import java.util.List;

/**
 * Managed Bean que entrega la lista de cursos a la vista.
 * Vista:  h:dataTable value="#{cursoBean.cursos}" var="c"
 * Detalle: #{cursoBean.buscar(param.id).nombre}
 */
@ManagedBean(name = "cursoBean")
@ApplicationScoped
public class CursoBean {

    // Datos de prueba en memoria (sin base de datos todavia)
    private final List<Curso> cursos = List.of(
            new Curso(1, "Desarrollo Web Integrado", "Lopez Heredia", 4, 30),
            new Curso(2, "Arquitectura de Software", "Torres Rivas", 4, 25),
            new Curso(3, "Base de Datos II", "Quispe Salazar", 3, 28),
            new Curso(4, "Ingenieria de Software", "Vega Castillo", 4, 0)
    );

    /** Lo usa h:dataTable: una fila por objeto de esta lista. */
    public List<Curso> getCursos() {
        return cursos;
    }

    /**
     * Busca un curso por el id que llega por la URL (#{param.id}).
     * Nunca confiamos en el id: si no es numero o no existe, devuelve null.
     */
    public Curso buscar(String id) {
        if (id == null || id.isBlank()) {
            return null;
        }
        try {
            int n = Integer.parseInt(id.trim());
            return cursos.stream()
                    .filter(c -> c.getId() == n)
                    .findFirst()
                    .orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String cursoDisponible(String id) {
        Curso curso = buscar(id);

        if ( curso != null && curso.getVacantes() > 0 ) {
            return "curso disponible";
        }

        return "curso sin vacantes disponibles";
    }
}
