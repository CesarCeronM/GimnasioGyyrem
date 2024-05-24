import gyyrem.logic.dao.MiembroDAO;
import gyyrem.logic.domain.Miembro;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;


public class MiembroDAOTest {
     private MiembroDAO miembroDAO;

    @Before
    public void setUp() {
        miembroDAO = new MiembroDAO();
    }

    @Test
    public void testExisteMiembroExitoso() {
        String correo = "Administrador";
        String contrasenia = "admin123";

        miembroDAO = new MiembroDAO();
        int idMiembroEsperado = 1;

        int idMiembroObtenido = miembroDAO.existeMiembro(correo, contrasenia);

        assertEquals(idMiembroEsperado, idMiembroObtenido);
    }

    @Test
    public void testExisteMiembroFallido() {
        String correo = "alguien@correo.com";
        String contrasenia = "contraseña123";

        miembroDAO = new MiembroDAO();
        int idMiembroEsperado = -1;

        int idMiembroObtenido = miembroDAO.existeMiembro(correo, contrasenia);

        assertEquals(idMiembroEsperado, idMiembroObtenido);
    }

    @Test
    public void testRegistrarMiembroExitoso() {
        // Crear una instancia de la clase Miembro con los datos de prueba
        Miembro nuevoMiembro = new Miembro(0, "Juan", "López", "García",
                "2000-01-01", "5551234567", "juan@example.com", "contraseña123", 15, "activo");

        miembroDAO = new MiembroDAO();
        int idMiembroRegistrado = miembroDAO.registrarMiembro(nuevoMiembro);

        // Se espera que el ID del miembro registrado sea mayor que 0
        assertEquals(3, idMiembroRegistrado);
    }

    @Test
    public void testRegistrarMiembroFallido() {
        Miembro nuevoMiembro = new Miembro(0, "Ana", "Gómez", "Pérez",
                "2003-02-01", "5559876543", "juan@example.com", "contraseña123", 20, "activo");

        miembroDAO = new MiembroDAO();
        int idMiembroRegistrado = miembroDAO.registrarMiembro(nuevoMiembro);

        assertEquals(-1, idMiembroRegistrado);
    }

    @Test
    public void testModificarMiembroExitoso() {
        Miembro miembroModificado = new Miembro(1, "Juan", "Pérez", "González",
                "2000-01-01", "5551234567", "juan@example.com", "nuevaContraseña", 15, "activo");

        miembroDAO = new MiembroDAO();
        int resultado = miembroDAO.modificarMiembro(1, miembroModificado);

        assertEquals(1, resultado);
    }

    @Test
    public void testModificarMiembroFallido() {
        Miembro miembroModificado = new Miembro(1000, "Ana", "Gómez", "Pérez",
                "2003-02-01", "5559876543", "correoInvalido@example.com", "nuevaContraseña", 20, "activo");

        miembroDAO = new MiembroDAO();
        int resultado = miembroDAO.modificarMiembro(1000, miembroModificado);

        assertEquals(-1, resultado);
    }

    @Test
    public void testSuspenderMiembroExitoso() {
        int idMiembroASuspender = 2;

        miembroDAO = new MiembroDAO();
        int resultado = miembroDAO.suspenderMiembro(idMiembroASuspender);

        assertEquals(1, resultado);
    }

    @Test
    public void testSuspenderMiembroFallido() {
        int idMiembroASuspender = 1000;

        miembroDAO = new MiembroDAO();
        int resultado = miembroDAO.suspenderMiembro(idMiembroASuspender);

        assertEquals(-1, resultado);
    }

    @Test
    public void testActivarMiembroExitoso() {
        int idMiembroAActivar = 2;

        miembroDAO = new MiembroDAO();
        int resultado = miembroDAO.activarMiembro(idMiembroAActivar);

        assertNotEquals(1, resultado);
    }

    @Test
    public void testActivarMiembroFallido() {
        int idMiembroAActivar = 9999;

        miembroDAO = new MiembroDAO();
        int resultado = miembroDAO.activarMiembro(idMiembroAActivar);

        assertNotEquals(-1, resultado);
    }

    @Test
    public void testObtenerListaMiembroExitoso() {
        miembroDAO = new MiembroDAO();
        List<Miembro> resultadoEsperado = new ArrayList<>();

        Miembro miembro1 = new Miembro(
                1, "Maria", "Navarro", "Guerrero",
                "28-04-2024", "2281420209", "Administrador",
                "d0f72e893636eef4924c741ce0cf90dd8faa194828bc8a6cdc9135fcd5b73dee",
                50, "activo"
        );
        resultadoEsperado.add(miembro1);

        List<Miembro> resultadoObtenido = miembroDAO.obtenerListaMiembro();

        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testObtenerListaMiembroFallido() {
        List<Miembro> miembros = miembroDAO.obtenerListaMiembro();

        assertEquals(100, miembros.size());
    }

    @Test
    public void testObtenerListaMiembrosSuspendidosExitoso() {
        miembroDAO = new MiembroDAO();
        List<Miembro> resultadoEsperado = new ArrayList<>();

        Miembro miembro2 = new Miembro(
                2, "Cesar Eduardo", "Ceron", "Martinez",
                "11-02-2024", "7711896342", "cecm1010@gmail.com",
                "5a11b2850396d2b0726e6a5a0cfbf7d5683772e447a1945529fede82bbb70ff1",
                23, "suspendido"
        );
        resultadoEsperado.add(miembro2);

        List<Miembro> resultadoObtenido = miembroDAO.obtenerListaMiembrosSuspendidos();

        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testObtenerListaMiembrosSuspendidosFallido() {
        List<Miembro> miembrosSuspendidos = miembroDAO.obtenerListaMiembrosSuspendidos();
        
        assertEquals(100, miembrosSuspendidos.size());
    }

    @Test
    public void testObtenerInformacionMiembroExitoso() {
        miembroDAO = new MiembroDAO();
        int idMiembro = 2;

        Miembro miembroEsperado = new Miembro(
                2, "Cesar Eduardo", "Ceron", "Martinez",
                "11-02-2024", "7711896342", "cecm1010@gmail.com",
                "5a11b2850396d2b0726e6a5a0cfbf7d5683772e447a1945529fede82bbb70ff1",
                23, "activo"
        );

        Miembro miembroObtenido = miembroDAO.obtenerInformacionMiembro(idMiembro);

        assertNotNull(miembroObtenido);
        assertEquals(miembroEsperado, miembroObtenido);
    }

    @Test
    public void testObtenerInformacionMiembroFallido() {
        miembroDAO = new MiembroDAO();
        int idMiembroNoExistente = 999; // ID inexistente

        Miembro miembroObtenido = miembroDAO.obtenerInformacionMiembro(idMiembroNoExistente);

        assertNull(miembroObtenido);
    }
}
