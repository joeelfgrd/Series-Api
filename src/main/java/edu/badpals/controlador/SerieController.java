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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class SerieController {
    private Conexion_App_bbdd cbd = new Conexion_App_bbdd();
    private Connection c;
    private Serie serie;

    @FXML
    private Button btnVerEpisodios; // Campo de texto para el nombre de usuario

    @FXML
    private MenuBar menuBarSeries; // Campo de texto para la contraseña

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
    private TextField txtIdiomaSerie;
    @FXML
    private TextField txtEstadoSerie;
    @FXML
    private TextField txtCadenaSerie;
    @FXML
    private CheckBox chkIdiomaSerie;
    @FXML
    private CheckBox chkEstadoSerie;
    @FXML
    private CheckBox chkCadenaSerie;
    @FXML
    private ImageView imgViewLogo;


    @FXML
    public void initialize() {
        c = cbd.crearConexion();
        setCells();
        imgViewLogo.setImage(new Image(getClass().getResource("/img/logo.png").toExternalForm()));
        cargarSeries(Conexion_App_bbdd.getSeries(c));

        tableViewSeries.setOnMouseClicked(event -> {
            if (!tableViewSeries.getSelectionModel().isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                setSerie(tableViewSeries.getSelectionModel().getSelectedItem());
            }
        });
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public void ordenarTablaCalAsc() {
        cargarSeries(Conexion_App_bbdd.getSeriesCal(c, "ASC"));
        setCells();
    }

    public void ordenarTablaCalDes() {
        cargarSeries(Conexion_App_bbdd.getSeriesCal(c, "DESC"));
        setCells();
    }

    public void ordenarTablaEstrenoAsc() {
        cargarSeries(Conexion_App_bbdd.getSeriesEstreno(c, "ASC"));
        setCells();
    }

    public void ordenarTablaEstrenoDesc() {
        cargarSeries(Conexion_App_bbdd.getSeriesEstreno(c, "DESC"));
        setCells();
    }

    public void filtrarSeries(ActionEvent event) {
        List<Serie> series;
        String[] filtros = new String[3];
        if (chkIdiomaSerie.isSelected()) {
            filtros[0] = txtIdiomaSerie.getText();
        }
        if (chkEstadoSerie.isSelected()) {
            filtros[1] = txtEstadoSerie.getText().replace("_", " ");
        }
        if (chkCadenaSerie.isSelected()) {
            filtros[2] = txtCadenaSerie.getText();
        }
        series = Conexion_App_bbdd.getSeries(c, filtros);
        cargarSeries(series);
    }

    public void toLogin(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/badpals/vista/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 500, 600);
            Stage stage = (Stage) ((MenuItem) actionEvent.getSource()).getParentPopup().getOwnerWindow();
            stage.setScene(scene);
            stage.show();
            stage.setMaximized(false);
            stage.setResizable(false);
            stage.setTitle("Login");
            Conexion_App_bbdd.cerrarConexion(c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toEpisodios(ActionEvent actionEvent) {
        if (this.serie != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/badpals/vista/episodios.fxml"));

                Scene scene = new Scene(fxmlLoader.load(), 1200, 750);
                Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

                EpisodiosController controller = fxmlLoader.getController();
                controller.setSerie(this.serie);
                controller.cargarTabla();
                stage.setScene(scene);
                stage.show();
                stage.setMaximized(false);
                stage.setResizable(false);
                stage.setTitle("Episodios");
                Conexion_App_bbdd.cerrarConexion(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Controlador.showWarning("Acceso Denegado", "No puedes buscar los episodios de una serie sin elegir la serie");
        }
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

    private void cargarSeries(List<Serie> series) {
        tableViewSeries.getItems().setAll(series);
    }
}