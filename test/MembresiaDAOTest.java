import gyyrem.logic.dao.MembresiaDAO;
import gyyrem.logic.domain.Membresia;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class MembresiaDAOTest {
    private MembresiaDAO membresiaDAO;

    @Before
    public void setUp() {
        membresiaDAO = new MembresiaDAO();
    }

    @Test
    public void testRegistrarMembresiaExitoso() {
        Membresia membresia = new Membresia(1, "20/05/24", "20/06/24", "mensual", "expirada", 200, 1);

        int resultadoEsperado = 3;
        int resultadoReal = membresiaDAO.registrarMembresia(membresia);

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void testRegistrarMmembresiaFallido() {
        Membresia membresia = new Membresia(1, null, "20/06/24", "mensual", "expirada", 200, 1);

        int resultadoEsperado = -1;
        int resultadoReal = membresiaDAO.registrarMembresia(membresia);

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void testExpirarMembresiaExitoso() {
        int idMiembro = 1;

        int resultadoEsperado = 1;
        int resultadoReal = membresiaDAO.expirarMembresia(idMiembro);

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void testExpirarMembresiaFallido() {
        int idMiembroInvalido = -1; // Un ID de miembro inválido

        int resultadoEsperado = -1; // El resultado esperado para un fallo en la operación
        int resultadoReal = membresiaDAO.expirarMembresia(idMiembroInvalido);

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void testPagarMembresiaExitoso() {
        int idMiembro = 1;

        int resultadoEsperado = 1;
        int resultadoReal = membresiaDAO.pagarMembresia(idMiembro);

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void testPagarMembresiaFallido() {
        int idMiembroInvalido = -1;

        int resultadoEsperado = -1;
        int resultadoReal = membresiaDAO.pagarMembresia(idMiembroInvalido);

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void testMembresiaEstaPagadaExitoso() {
        int idMiembro = 1;

        Membresia membresiaObtenida = membresiaDAO.membresiaEstaPagada(idMiembro);

        assertNotNull(membresiaObtenida);
        assertEquals("vigente", membresiaObtenida.getEstado());
    }

    @Test
    public void testMembresiaEstaPagadaFallido() {
        int idMiembroInvalido = -1;

        Membresia membresiaObtenida = membresiaDAO.membresiaEstaPagada(idMiembroInvalido);

        assertEquals(null, membresiaObtenida);
    }
}
