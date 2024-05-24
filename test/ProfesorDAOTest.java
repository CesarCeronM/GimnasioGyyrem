import gyyrem.logic.dao.ProfesorDAO;
import gyyrem.logic.domain.Profesor;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ProfesorDAOTest {
    
    private ProfesorDAO profesorDAO;

    @Before
    public void setUp() {
        profesorDAO = new ProfesorDAO();
    }

    @Test
    public void testObtenerListaProfesoresExitoso() {
        profesorDAO = new ProfesorDAO();
        List<Profesor> resultadoEsperado = new ArrayList<>();

        resultadoEsperado.add(new Profesor(1, "Juan", "Perez", "Gonzalez"));
        
        List<Profesor> resultadoObtenido = profesorDAO.obtenerListaProfesores();

        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testObtenerListaProfesoresFallido() {
        profesorDAO = new ProfesorDAO();

        List<Profesor> resultadoObtenido = profesorDAO.obtenerListaProfesores();

        assertNotEquals(100, resultadoObtenido.size());
    }

    @Test
    public void testObtenerInformacionProfesorExitoso() {
        profesorDAO = new ProfesorDAO();

        int idProfesor = 1;

        Profesor profesorEsperado = new Profesor(1, "Juan", "Perez", "Gonzalez");

        Profesor profesorObtenido = profesorDAO.obtenerInformacionProfesor(idProfesor);

        assertNotNull(profesorObtenido);
        assertEquals(profesorEsperado, profesorObtenido);
    }

    @Test
    public void testObtenerInformacionProfesorFallido() {
        profesorDAO = new ProfesorDAO();

        int idProfesor = 999;

        Profesor profesorObtenido = profesorDAO.obtenerInformacionProfesor(idProfesor);

        assertNull(profesorObtenido);
    }
}
