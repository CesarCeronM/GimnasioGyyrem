import gyyrem.logic.dao.ClaseDAO;
import gyyrem.logic.domain.Clase;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClaseDAOTest {
    
    private ClaseDAO claseDAO;
    
    @Test
    public void testExisteClaseExitoso() {
        // Datos de prueba
        String nombreClaseExistente = "Pilates";

        assertTrue(claseDAO.existeClase(nombreClaseExistente));

    }

    @Test
    public void testExisteClaseFallido() {
        String nombreClaseNoExistente = "Yoga";

        assertFalse(claseDAO.existeClase(nombreClaseNoExistente));
    }

    @Test
    public void testRegistrarClaseExitoso() {
        Clase clase = new Clase(1, 30, "FIT", "10:00",
                "disponible", 200, 3);

        int resultadoEsperado = 3;
        int resultadoReal = claseDAO.registrarClase(clase);

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void testRegistrarClaseFallido() {
        Clase clase = new Clase(1, 30, "FIT", "10:00",
                null, 200, 3);

        int resultadoEsperado = -1;
        int resultadoReal = claseDAO.registrarClase(clase);

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void testModificarClaseExitoso() {
        Clase claseNueva = new Clase(1, 30, "Yoga", "08:00",
                "disponible", 150, 4);

        claseDAO = new ClaseDAO();
        int idClase = 1;
        int resultado = claseDAO.modificarClase(idClase, claseNueva);

        assertEquals(1, resultado);
    }

    @Test
    public void testModificarClaseFallido() {
        Clase claseNueva = new Clase(1, 30, "Yoga", "08:00",
                "disponible", -150, 4);

        claseDAO = new ClaseDAO();
        int idClase = -1;
        int resultado = claseDAO.modificarClase(idClase, claseNueva);

        assertEquals(-1, resultado);
    }

    @Test
    public void testCambiarEstadoALlenaExitoso() {
        int idClase = 1;

        claseDAO = new ClaseDAO();
        int resultado = claseDAO.cambiarEstadoALlena(idClase);

        assertEquals(1, resultado);
    }

    @Test
    public void testCambiarEstadoALlenaFallido() {
        int idClase = 9999;

        claseDAO = new ClaseDAO();
        int resultado = claseDAO.cambiarEstadoALlena(idClase);

        assertEquals(-1, resultado);
    }

    @Test
    public void testCambiarEstadoADisponibleExitoso() {
        int idClase = 2;

        claseDAO = new ClaseDAO();
        int resultado = claseDAO.cambiarEstadoADisponible(idClase);

        assertEquals(1, resultado);
    }

    @Test
    public void testCambiarEstadoADisponibleFallido() {
        int idClase = 999;

        claseDAO = new ClaseDAO();
        int resultado = claseDAO.cambiarEstadoADisponible(idClase);

        assertEquals(-1, resultado);
    }

    @Test
    public void testObtenerListaClasesDisponiblesExitoso() {
        Clase clase1 = new Clase(1, 29, "Pilates", "3 de la tarde", "disponible", 500, 1);
        Clase clase2 = new Clase(2, 19, "Zumba", "1 de la tarde", "disponible", 200, 5);

        List<Clase> clasesEsperadas = new ArrayList<>();
        clasesEsperadas.add(clase1);
        clasesEsperadas.add(clase2);

        claseDAO = new ClaseDAO();
        List<Clase> clasesObtenidas = claseDAO.obtenerListaClasesDisponibles();

        assertEquals(clasesEsperadas, clasesObtenidas);
    }

    @Test
    public void testObtenerListaClasesDisponiblesFallido() {
        claseDAO = new ClaseDAO();

        List<Clase> clases = claseDAO.obtenerListaClasesDisponibles();
        assertNotEquals(100, clases.size());
    }

    @Test
    public void testObtenerInformacionClaseExitoso() {
        int idClaseZumba = 2;

        Clase claseZumba = new Clase(2, 19, "Zumba", "1 de la tarde",
                "disponible", 200, 5);

        ClaseDAO claseDAO = new ClaseDAO();
        Clase claseObtenida = claseDAO.obtenerInformacionClase(idClaseZumba);

        assertEquals(claseZumba, claseObtenida);
    }

    @Test
    public void testObtenerInformacionClaseFallido() {
        int idClaseZumba = 2;

        Clase claseZumba = new Clase(2, 19, "Zumba", "1 de la tarde",
                "disponible", 200, 5);

        ClaseDAO claseDAO = new ClaseDAO();
        Clase claseObtenida = claseDAO.obtenerInformacionClase(idClaseZumba);

        claseObtenida.setNombre("Aeróbicos");

        assertEquals(null, claseObtenida);
    }
    
}
