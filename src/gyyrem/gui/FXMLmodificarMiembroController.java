package gyyrem.gui;

import gyyrem.logic.dao.MiembroDAO;
import gyyrem.logic.domain.Miembro;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;

public class FXMLmodificarMiembroController implements Initializable {
    private static final Logger log = Logger.getLogger(FXMLmodificarMiembroController.class);
    
    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblModificarMiembro;

    @FXML
    private Label lblNombre;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellidoPaterno;

    @FXML
    private TextField txtApellidoMaterno;

    @FXML
    private Label lblFechaNacimiento;

    @FXML
    private Label lblTelefono;

    @FXML
    private Label lblApellidoMaterno;

    @FXML
    private Label lblApellidoPaterno;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Label lblCorreo;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtCorreo;

    @FXML
    private Label lblConstraseña;

    @FXML
    private TextField txtContraseña;

    @FXML
    private DatePicker dpFechaNacimiento;
    
    private int tipoUsuario=0;
    
    private int idMiembro=0;
    
    private MiembroDAO miembroDAO;
    
    private Miembro miembroSeleccionado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        miembroDAO = new MiembroDAO();
    }    
    
    public void recibirTipoUsuario(int tipoUsuario, int idMiembro, Miembro miembro){
        this.tipoUsuario = tipoUsuario;
        this.idMiembro = idMiembro;
        this.miembroSeleccionado = miembro;
        txtNombre.setText(miembroSeleccionado.getNombre());
        txtApellidoPaterno.setText(String.valueOf(miembroSeleccionado.getApellidoPaterno()));
        txtApellidoMaterno.setText(String.valueOf(miembroSeleccionado.getApellidoMaterno()));
        txtCorreo.setText(miembroSeleccionado.getCorreo());
        txtTelefono.setText(miembroSeleccionado.getTelefono());
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
        String apellidoPaterno = txtApellidoPaterno.getText();
        String apellidoMaterno = txtApellidoMaterno.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();
        String contraseña = txtContraseña.getText();
        LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
        
        if (correo.isEmpty() || correo.length() > 40 ||
            nombre.isEmpty() || nombre.length() > 20 ||
            apellidoPaterno.isEmpty() || apellidoPaterno.length() > 20 ||
            apellidoMaterno.isEmpty() || apellidoMaterno.length() > 20) {
                mostrarMensaje("Los campos no pueden estar vacíos o tienen demasiados caracteres.");
        }else{
            if (!correo.matches("[a-zA-Z0-9.@\u00f1\u00d1]+") ||
                !nombre.matches("[a-zA-ZÀ-ÿ\u00f1\u00d1 ]+") ||
                !apellidoPaterno.matches("[a-zA-ZÀ-ÿ\u00f1\u00d1 ]+") ||
                !apellidoMaterno.matches("[a-zA-ZÀ-ÿ\u00f1\u00d1 ]+")) {
                    mostrarMensaje("Los campos no pueden contener caracteres especiales.");
            }else{
                if (telefono.isEmpty() || telefono.length() != 10 || !telefono.matches("[0-9]+")) {
                    mostrarMensaje("El campo telefono debe tener 10 números.");
                }else{
                    if (!(fechaNacimiento.compareTo(LocalDate.now())>0)) {
                        if(contraseña.length()< 8 ||
                                !contraseña.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).+$")){
                            mostrarMensaje("El campo contraseña debe ser de longitud mayor a 8 y contener al menos un digito, una mayuscula y un caracter especial");
                        }else{
                            modificarMiembro();
                        }
                    }else{
                        mostrarMensaje("La fecha de nacimiento tiene que ser menor a la actual");
                    }
                }
            }
        }
    };
    
    private void modificarMiembro() {
        String nombre = txtNombre.getText();
        String apellidoPaterno = txtApellidoPaterno.getText();
        String apellidoMaterno = txtApellidoMaterno.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();
        String contraseña = txtContraseña.getText();
        LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaNacimientoString = fechaNacimiento.format(formato);
        
        int diaDePago = LocalDate.now().getDayOfMonth();
        
        Miembro miembro = new Miembro(0, nombre, apellidoPaterno, apellidoMaterno, 
                                fechaNacimientoString, telefono, correo, contraseña, 
                                diaDePago, "disponible");
        
        miembroDAO.modificarMiembro(idMiembro, miembro);
        
        confirmacion.setTitle("Confirmacion");
        confirmacion.setHeaderText("Registro exitoso");
        confirmacion.setContentText("Miembro modificado correctamente");
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
                        
            Scene escena = btnModificar.getScene();

            Pane panelPadre = (Pane) escena.getRoot();

            panelPadre.getChildren().clear();
            panelPadre.getChildren().add(raiz);

        } catch (IOException excepcion) {
            log.error(" Error al abrir la ventana menu: " + excepcion);
        }
    }  
}
