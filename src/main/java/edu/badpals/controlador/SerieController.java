package edu.badpals.controlador;

import edu.badpals.modelo.Conexion_App_bbdd;
import edu.badpals.modelo.Estado;
import edu.badpals.modelo.Serie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class SerieController {
    Conexion_App_bbdd cbd = new Conexion_App_bbdd();
    Connection c = cbd.crearConexion();
    Serie serie;

    @FXML
    Button btnVerEpisodios; // Campo de texto para el nombre de usuario

    @FXML
    MenuBar menuBarSeries; // Campo de texto para la contraseña

    @FXML
    private TableView<Serie> tableViewSeries; // Campo de texto para la contraseña

    @FXML
    private TableColumn<Serie, Integer> colId;
    @FXML
    private TableColumn<Serie, String> colNombre;
    @FXML
    private TableColumn<Serie, Date> colEstreno;
    @FXML
    private TableColumn<Serie, String> colTematica;
    @FXML
    private TableColumn<Serie, String> colDirector;
    @FXML
    private TableColumn<Serie, Integer> colCalificacion;
    @FXML
    private TableColumn<Serie, String> colIdioma;
    @FXML
    private TableColumn<Serie, Estado> colEstado;
    @FXML
    private TableColumn<Serie, String> colCadena;

    @FXML
    public void initialize() {
        setCells();
        cargarSeries(Conexion_App_bbdd.getSeries(c));

        // Set row factory for selection
        tableViewSeries.setOnMouseClicked(event -> {
            if (!tableViewSeries.getSelectionModel().isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                setSerie(tableViewSeries.getSelectionModel().getSelectedItem());
            }
        });


    }

    private void cargarSeries(List<Serie> series) {
        tableViewSeries.getItems().setAll(series);
    }


    public void toEpisodios(ActionEvent actionEvent) {
        if (this.serie != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/badpals/vista/episodios.fxml"));

                Scene scene = new Scene(fxmlLoader.load(), 919, 750);
                EpisodiosController controller = fxmlLoader.getController();
                controller.setSerie(this.serie);
                controller.cargarTabla();
                Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

                stage.setScene(scene);
                stage.show();
                stage.setMaximized(false);
                stage.setResizable(false);
                stage.setTitle("Episodios");
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            showWarning("Acceso Denegado", "No puedes buscar los episodios de una serie sin elegir la serie");
        }
    }

    public static void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setCells() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEstreno.setCellValueFactory(new PropertyValueFactory<>("estreno"));
        colTematica.setCellValueFactory(new PropertyValueFactory<>("tematica"));
        colDirector.setCellValueFactory(new PropertyValueFactory<>("director"));
        colCalificacion.setCellValueFactory(new PropertyValueFactory<>("calificacion"));
        colIdioma.setCellValueFactory(new PropertyValueFactory<>("idioma"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colCadena.setCellValueFactory(new PropertyValueFactory<>("cadena"));
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }
}