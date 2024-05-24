package gyyrem.gui;

import gyyrem.logic.dao.ClaseDAO;
import gyyrem.logic.dao.ReservaDAO;
import gyyrem.logic.domain.Clase;
import gyyrem.logic.domain.Reserva;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;

public class FXMLverClasesController implements Initializable {
    private static final Logger log = Logger.getLogger(FXMLverClasesController.class);
    
    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblClases;

    @FXML
    private TableView<Clase> tblClases; 

    @FXML
    private TableColumn<Clase, String> colNombre; 

    @FXML
    private TableColumn<Clase, String> colHorario; 

    @FXML
    private TableColumn<Clase, String> colCupo; 

    @FXML
    private TableColumn<Clase, String> colCosto; 

    @FXML
    private Button btnReservar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnRegresar;
    
    private int tipoUsuario=0;
    
    private int idMiembro=0;
    
    private ObservableList<Clase> clases;
    
    private ClaseDAO claseDAO;
    
    private ReservaDAO reservaDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }    
    
    private void mostrarMensaje(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colHorario.setCellValueFactory(new PropertyValueFactory("horario"));
        colCupo.setCellValueFactory(new PropertyValueFactory("cupo"));
        colCosto.setCellValueFactory(new PropertyValueFactory("costo"));
    }
    
    public void recibirTipoUsuario(int tipoUsuario, int idMiembro){
        this.tipoUsuario = tipoUsuario;
        this.idMiembro = idMiembro;
        if(tipoUsuario==1){
            btnReservar.setVisible(false);
        }
        if(tipoUsuario==2){
            btnModificar.setVisible(false);
        }
    }
    
    void mostrarProfesores(){
        clases = FXCollections.observableArrayList();
        claseDAO = new ClaseDAO();
        
        List<Clase> clasesRegistradas= FXCollections.observableArrayList();
        clasesRegistradas= claseDAO.obtenerListaClasesDisponibles();
        
        for (Clase clase : clasesRegistradas) {
            clases.add(clase);
        }
        
        tblClases.setItems(clases);
    }
    
    @FXML
    private void reservar(){
        Clase claseSeleccionada = tblClases.getSelectionModel().getSelectedItem();
        reservaDAO = new ReservaDAO();
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        
        if(claseSeleccionada!=null){
            Reserva reserva = new Reserva(0, idMiembro, claseSeleccionada.getIdClase(),
                                           LocalDate.now().toString(), "reservada" );
            reservaDAO.reservarClase(reserva);
            
            Clase clase = new Clase(0, (claseSeleccionada.getCupo()-1), claseSeleccionada.getNombre(), 
                                    claseSeleccionada.getHorario(), "disponible", 
                                    claseSeleccionada.getCosto(), claseSeleccionada.getIdProfesor());
        
            claseDAO.modificarClase(claseSeleccionada.getIdClase(),clase);
            
            if(claseSeleccionada.getCupo()==1){
                claseDAO.cambiarEstadoALlena(claseSeleccionada.getIdClase());
            }
                
            confirmacion.setTitle("Confirmacion");
            confirmacion.setHeaderText("Registro exitoso");
            confirmacion.setContentText("Clase reservada correctamente");
            confirmacion.showAndWait();
            regresarMenu();
        }else{
            mostrarMensaje("Selecciona una clase en la tabla");
        }
    }
    
    @FXML
    private void regresarMenu(){
        try {
            FXMLLoader inicializador = new FXMLLoader(getClass().getResource("FXMLmenu.fxml"));
            Parent raiz = inicializador.load();
                        
            FXMLmenuController menuController = inicializador.getController();
            menuController.recibirTipoUsuario(tipoUsuario,idMiembro);
                        
            Scene escena = btnRegresar.getScene();

            Pane panelPadre = (Pane) escena.getRoot();

            panelPadre.getChildren().clear();
            panelPadre.getChildren().add(raiz);

        } catch (IOException excepcion) {
            log.error(" Error al abrir la ventana menu: " + excepcion);
        }
    }
    
    @FXML
    private void modificarClase(){
        Clase claseSeleccionada = tblClases.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader inicializador = new FXMLLoader(getClass().getResource("FXMLmodificarClase.fxml"));
            Parent raiz = inicializador.load();
                        
            FXMLmodificarClaseController modificarClase = inicializador.getController();
            modificarClase.recibirTipoUsuario(tipoUsuario,idMiembro, claseSeleccionada);
                        
            Scene escena = btnRegresar.getScene();

            Pane panelPadre = (Pane) escena.getRoot();

            panelPadre.getChildren().clear();
            panelPadre.getChildren().add(raiz);

        } catch (IOException excepcion) {
            log.error(" Error al abrir la ventana menu: " + excepcion);
        }
    }
}
