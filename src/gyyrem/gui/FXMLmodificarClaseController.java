package gyyrem.gui;

import gyyrem.logic.dao.ClaseDAO;
import gyyrem.logic.domain.Clase;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;


public class FXMLmodificarClaseController implements Initializable {
    private static final Logger log = Logger.getLogger(FXMLmodificarClaseController.class);
    
    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblModificarClase;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblCosto;

    @FXML
    private Label lblProfesor;

    @FXML
    private Label lblCupo;

    @FXML
    private Label lblHorario;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCosto;

    @FXML
    private TextField txtCupo;

    @FXML
    private TextField txtHorario;

    @FXML
    private ChoiceBox<String> cmbProfesor;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnCancelar;
    
    private int tipoUsuario=0;
    
    private int idMiembro=0;
    
    private Clase claseSeleccionada;
    
    private ClaseDAO claseDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbProfesor.getItems().addAll("Juan", "Maria", "Carlos", "Ana", "Jose");
        claseDAO = new ClaseDAO();
    }    
    
    public void recibirTipoUsuario(int tipoUsuario, int idMiembro, Clase clase){
        this.tipoUsuario = tipoUsuario;
        this.idMiembro = idMiembro;
        this.claseSeleccionada=clase;
        txtNombre.setText(claseSeleccionada.getNombre());
        txtCosto.setText(String.valueOf(claseSeleccionada.getCosto()));
        txtCupo.setText(String.valueOf(claseSeleccionada.getCupo()));
        txtHorario.setText(claseSeleccionada.getHorario());
    }
    
    private void mostrarMensaje(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    @FXML
    private void validar() {
        String nombre = txtNombre.getText();
        String costo = txtCosto.getText();
        String cupo = txtCupo.getText();
        String horario = txtHorario.getText();
        String profesor = cmbProfesor.getValue();
        
        if (nombre.isEmpty() || nombre.length() > 20 ||
            costo.isEmpty() || costo.length() > 4 ||
            cupo.isEmpty() || cupo.length() > 4 ||
            horario.isEmpty() || horario.length() > 20) {
                mostrarMensaje("Los campos no pueden estar vacíos o tienen demasiados caracteres.");
        }else{
            if (!nombre.matches("[a-zA-ZÀ-ÿ0-9\u00f1\u00d1 ]+") ||
                !costo.matches("[0-9]+") ||
                !cupo.matches("[0-9]+") ||
                !horario.matches("[a-zA-ZÀ-ÿ0-9:\u00f1\u00d1 ]+")) {
                    mostrarMensaje("Los campos no pueden contener caracteres especiales.");
            }else{  
                if(!(profesor==null)){
                    modificarClase();
                }else{
                    mostrarMensaje("Selecciona un profesor");
                }
            }
        }
    };
    
    private void modificarClase() {
        String nombre = txtNombre.getText();
        String costo = txtCosto.getText();
        String cupo = txtCupo.getText();
        String horario = txtHorario.getText();
        String profesor = cmbProfesor.getValue();
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        
        int costoInt = Integer.parseInt(costo);
        int cupoInt = Integer.parseInt(cupo);
        int idProfesor=0;
        if(profesor=="Juan"){
            idProfesor=1;
        }
        if(profesor=="Maria"){
            idProfesor=2;
        }
        if(profesor=="Carlos"){
            idProfesor=3;
        }
        if(profesor=="Ana"){
            idProfesor=4;
        }
        if(profesor=="Jose"){
            idProfesor=5;
        }
        
        Clase clase = new Clase(0, cupoInt, nombre, horario, "disponible", costoInt, idProfesor);
        
        claseDAO.modificarClase(claseSeleccionada.getIdClase(),clase);
        
        confirmacion.setTitle("Confirmacion");
        confirmacion.setHeaderText("Modificacion exitosa");
        confirmacion.setContentText("Clase modificada correctamente");
        confirmacion.showAndWait();
        regresarMenu();
    };
    
    @FXML
    private void regresarMenu(){
        try {
            FXMLLoader inicializador = new FXMLLoader(getClass().getResource("FXMLmenu.fxml"));
            Parent raiz = inicializador.load();
                        
            FXMLmenuController menuController = inicializador.getController();
            menuController.recibirTipoUsuario(tipoUsuario,idMiembro);
                        
            Scene escena = cmbProfesor.getScene();

            Pane panelPadre = (Pane) escena.getRoot();

            panelPadre.getChildren().clear();
            panelPadre.getChildren().add(raiz);

        } catch (IOException excepcion) {
            log.error(" Error al abrir la ventana menu: " + excepcion);
        }
    }
}
