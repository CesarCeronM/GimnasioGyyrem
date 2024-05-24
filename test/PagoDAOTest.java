import gyyrem.logic.dao.PagoDAO;
import gyyrem.logic.domain.Pago;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class PagoDAOTest {
    private PagoDAO pagoDAO;

    @Before
    public void setUp() {
        pagoDAO = new PagoDAO();
    }

    @Test
    public void testRegistrarPago() {
        pagoDAO = new PagoDAO();
        Pago pago = new Pago(0, 200, "2024-05-01", "Pago por clase de Zumba", 2);

        int resultado = pagoDAO.registrarPago(pago);

        assertTrue(resultado > 0);
    }

    @Test
    public void testRegistrarPagoFallido() {
        pagoDAO = new PagoDAO();
        Pago pago = new Pago(0, -200, "2024-05-01", "Pago inválido", 2);

        int resultado = pagoDAO.registrarPago(pago);

        assertEquals(-1, resultado);
    }

    @Test
    public void testObtenerListaPagos() {
        pagoDAO = new PagoDAO();
        List<Pago> resultadoEsperado = new ArrayList<>();

        Pago pago1 = new Pago(
                1, 200,"2024-05-01", "Pago por clase de Zumba", 2
        );

        resultadoEsperado.add(pago1);

        List<Pago> resultadoObtenido = pagoDAO.obtenerListaPagos(2);

        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testObtenerListaPagosFallido() {
        List<Pago> pagos = pagoDAO.obtenerListaPagos(999); // Suponiendo que 999 es el ID de un miembro existente

        assertEquals(null, pagos.size());
    }

    @Test
    public void testObtenerInformacionPagoExitoso() {
        pagoDAO = new PagoDAO();

        int idPago = 1;

        Pago pagoEsperado = new Pago(
                idPago, 200, "2024-05-01","Pago por clase de Zumba", 2
        );

        Pago pagoObtenido = pagoDAO.obtenerInformacionPago(idPago);

        assertNotNull(pagoObtenido);
        assertEquals(pagoEsperado, pagoObtenido);
    }

    @Test
    public void testObtenerInformacionPagoFallido() {
        pagoDAO = new PagoDAO();

        int idPago = 999;

        Pago pagoObtenido = pagoDAO.obtenerInformacionPago(idPago);

        assertEquals(null,pagoObtenido);
    }
}
