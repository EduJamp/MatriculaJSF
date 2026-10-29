# matricula-jsf

Proyecto base para la sesión **JSF: navegación, etiquetas, EL y tablas de datos**.

## Requisitos
- Java 21 (probado con 21.0.x)
- Maven 3.9+
- Jetty 11.0.15 (lo trae el plugin `jetty-maven-plugin`, no se instala aparte)

## Ejecutar
```
mvn jetty:run
```
Abrir: http://localhost:8080/matricula/

Detener: `Ctrl + C`

## Estructura
```
matricula-jsf/
├── pom.xml                         <- Jetty 11.0.15 + Java 21 + Mojarra (JSF)
└── src/main/
    ├── java/pe/utp/matricula/
    │   ├── model/Curso.java        <- POJO (getters para EL)
    │   └── bean/
    │       ├── CursoBean.java      <- lista de cursos + buscar(id)
    │       └── MatriculaBean.java  <- formulario, guardar() devuelve outcome
    └── webapp/
        ├── index.xhtml             <- inicio (navegación implícita)
        ├── listado.xhtml           <- h:dataTable + h:outputLink + f:param
        ├── detalle.xhtml           <- #{param.id} + bean.buscar(...)
        ├── matricula.xhtml         <- h:inputText + f:validateLength
        ├── confirmacion.xhtml      <- destino del outcome "exito"
        ├── resources/css/estilos.css
        └── WEB-INF/
            ├── web.xml             <- FacesServlet para *.xhtml
            └── faces-config.xml    <- navigation-rule (navegación explícita)
```

## Qué diapositiva practica cada archivo
| Diapositiva | Tema | Dónde verlo |
|---|---|---|
| 5 | Botón → action → outcome | `matricula.xhtml` + `MatriculaBean.guardar()` |
| 6 | Navegación implícita / explícita | `index.xhtml` (implícita) y `faces-config.xml` (explícita, con redirect) |
| 7 | Etiquetas `h:` y `f:` | todas las vistas |
| 8 | Expression Language | `#{cursoBean...}`, `#{matriculaBean...}`, `#{param.id}` |
| 9 | `h:dataTable` | `listado.xhtml` |
| 10-11 | Listado → detalle con `f:param` | `listado.xhtml` → `detalle.xhtml` |
| 13 | Validar el id en el servidor | `CursoBean.buscar()` (id no numérico o inexistente → null) |

## Pruebas para los alumnos (evidencia de la diapositiva 11)
1. `/matricula/listado.xhtml` muestra 4 filas.
2. Clic en "Ver" → URL `detalle.xhtml?id=2` y datos del curso 2.
3. Probar `detalle.xhtml?id=999` y `detalle.xhtml?id=abc` → mensaje "El curso solicitado no existe", sin excepciones.
4. Formulario vacío → vuelve a `matricula.xhtml` (outcome `error`). Completo → `confirmacion.xhtml` y la URL cambia (redirect).

## Notas
- Jetty 11 = Jakarta EE 9 (`jakarta.*`), por eso se usa Mojarra **3.0.x**. No usar Mojarra 2.x ni imports `javax.*`.
- `@ManagedBean` (paquete `jakarta.faces.bean`) está deprecado pero funciona en Faces 3.0 y no requiere CDI; es el que corresponde a la diapositiva. En Faces 4.0 se reemplaza por CDI (`@Named`).
- Si Maven no baja `jakarta.faces:3.0.4`, probar con otra 3.0.x.
