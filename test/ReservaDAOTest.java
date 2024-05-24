import gyyrem.logic.dao.ReservaDAO;
import gyyrem.logic.domain.Clase;
import gyyrem.logic.domain.Reserva;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author CESAR
 */
public class ReservaDAOTest {
    
    private ReservaDAO reservarClaseDAO;
    
    @Before
    public void setUp() {
        reservarClaseDAO = new ReservaDAO();
    }

    @Test
    public void testReservarClaseExitoso() {
        Reserva reserva = new Reserva(1, 1, 1,"2024-05-23", "reservado");

        int resultadoReal = reservarClaseDAO.reservarClase(reserva);

        assertTrue(resultadoReal > 0);
    }

    @Test
    public void testReservarClaseFallido() {
        Reserva reserva = new Reserva(-1, 1,1, "2024-05-23", "reservado");

        int resultadoReal = reservarClaseDAO.reservarClase(reserva);

        assertEquals(-1, resultadoReal);
    }

    @Test
    public void testCancelarReservaExitoso() {
        int idReserva = 1; // Supongamos que el ID de la reserva es 1

        int resultadoReal = reservarClaseDAO.cancelarReserva(idReserva);

        assertTrue(resultadoReal > 0);
    }

    @Test
    public void testCancelarReservaFallido() {
        int idReservaNoExistente = -1; // Supongamos que el ID de la reserva no existe

        int resultadoReal = reservarClaseDAO.cancelarReserva(idReservaNoExistente);

        assertEquals(-1, resultadoReal);
    }

    @Test
    public void testPagarReservaExitoso() {
        int idClase = 1;
        int idMiembro = 1;

        int resultadoReal = reservarClaseDAO.pagarReserva(idClase, idMiembro);

        assertTrue(resultadoReal > 0);
    }

    @Test
    public void testPagarReservaFallido() {
        int idClase = -1;
        int idMiembro = -1;

        int resultadoReal = reservarClaseDAO.pagarReserva(idClase,idMiembro);

        assertEquals(-1, resultadoReal);
    }

    @Test
    public void testObtenerClasesReservadasPorMiembroExitoso() {
        int idMiembro = 1;

        List<Clase> clasesReservadas = reservarClaseDAO.obtenerClasesReservadasPorMiembro(idMiembro);

        assertNotNull(clasesReservadas);
        assertFalse(clasesReservadas.isEmpty());
    }

    @Test
    public void testObtenerClasesReservadasPorMiembroFallido() {
        int idMiembroNoExistente = -1;

        List<Clase> clasesReservadas = reservarClaseDAO.obtenerClasesReservadasPorMiembro(idMiembroNoExistente);

        assertNotNull(clasesReservadas);
        assertTrue(clasesReservadas.isEmpty());
    }
}
