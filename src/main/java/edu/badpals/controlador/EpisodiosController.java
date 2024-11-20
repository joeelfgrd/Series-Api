package edu.badpals.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.badpals.modelo.Conexion_App_bbdd;
import edu.badpals.modelo.Episodio;
import edu.badpals.modelo.Serie;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class EpisodiosController implements Initializable {
    Conexion_App_bbdd cbd = new Conexion_App_bbdd();
    Connection c = cbd.crearConexion();
    private Serie serie; // Objeto Serie actual

    private List<Episodio> episodios;
    private Episodio episodio;

    @FXML
    private javafx.scene.control.Label lblNameSerieEpisodios; // Etiqueta para mostrar el nombre de la serie

    @FXML
    private TableView<Episodio> tableViewEpisodios;
    @FXML
    private TableColumn<Episodio, Integer> colId;
    @FXML
    private TableColumn<Episodio, Integer> colNumero;
    @FXML
    private TableColumn<Episodio, Integer> colTemporada;
    @FXML
    private TableColumn<Episodio, String> colNombre;
    @FXML
    private TableColumn<Episodio, String> colSerie;
    @FXML
    private TableColumn<Episodio, String> colFechaDeSalida;
    @FXML
    private TableColumn<Episodio, Integer> colDuracion;

    @FXML
    private Label lblidSerie;
    @FXML
    private TextField txtNumEp;
    @FXML
    private TextField txtTempEp;
    @FXML
    private TextField txtNombreEp;
    @FXML
    private TextField txtFechaDeSalidaEp;
    @FXML
    private TextField txtDurEp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set row factory for selection
        tableViewEpisodios.setOnMouseClicked(event -> {
            if (!tableViewEpisodios.getSelectionModel().isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                setEpisodio(tableViewEpisodios.getSelectionModel().getSelectedItem());
                cargarTextsEpisodio();
            }
        });
    }

    private void cargarTextsEpisodio() {
        lblidSerie.setText(String.valueOf(this.episodio.getId()));
        txtNumEp.setText(String.valueOf(this.episodio.getNumero()));
        txtTempEp.setText(String.valueOf(this.episodio.getTemporada()));
        txtNombreEp.setText(this.episodio.getNombre());
        txtFechaDeSalidaEp.setText(String.valueOf(this.episodio.getFechaDeSalida()));
        txtDurEp.setText(String.valueOf(this.episodio.getDuracion()));
    }

    private void setEpisodio(Episodio episodio) {
        this.episodio = episodio;
    }

    public void toSerie(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/badpals/vista/serie.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 750);
            Stage stage = (Stage) ((MenuItem) actionEvent.getSource()).getParentPopup().getOwnerWindow();
            stage.setScene(scene);
            stage.show();
            stage.setMaximized(false);
            stage.setResizable(false);
            stage.setTitle("Series");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modificarEp(ActionEvent actionEvent) {
        Episodio ep = cargarEpisodioTexts();
        if (ep != null){
            int respuesta = Conexion_App_bbdd.modificarEpisodio(c, ep);
            if (respuesta == 0) {
                showWarning("Error Modificar", "El ID Seleccionado no Existe");
            } else if(respuesta == 2){
                showWarning("Error Modificar", "La temporada y el numero de episodio ya existen");
            }
        }
        cargarTabla();
    }

    public void eliminarEp(ActionEvent actionEvent) {
        Episodio ep = cargarEpisodioTexts();
        if (ep != null && !Conexion_App_bbdd.eliminarEpisodio(c, ep)) {
            showWarning("Error Eliminar", "El ID Seleccionado no Existe");
        }
        cargarTabla();
    }

    public void crearEp(ActionEvent actionEvent) {
        Episodio ep = cargarEpisodioTexts();
        if (ep != null && !Conexion_App_bbdd.crearEpisodio(c, ep)) {
            showWarning("Error Crear", "La temporada y el numero de episodio ya existen");
        }
        cargarTabla();
    }

    private Episodio cargarEpisodioTexts() {
        Episodio episodio = new Episodio();
        if (!Objects.equals(lblidSerie.getText(), "")) {
            episodio.setId(Integer.parseInt(lblidSerie.getText()));
        }
        try {
            episodio.setNumero(Integer.parseInt(txtNumEp.getText()));
        } catch (NumberFormatException e) {
            showWarning("Numero incorrecto", "Escribe un Numero de Episodio correcto");
            return null;
        }
        try {
            episodio.setTemporada(Integer.parseInt(txtTempEp.getText()));
        } catch (NumberFormatException e) {
            showWarning("Numero incorrecto", "Escribe un Numero de Temporada correcta");
            return null;
        }

        episodio.setNombre(txtNombreEp.getText());

        episodio.setSerie(this.serie);

        try {
            episodio.setFechaDeSalida(Date.valueOf(txtFechaDeSalidaEp.getText()));
        } catch (IllegalArgumentException e) {
            showWarning("Fecha Incorrecta", "Escribe una fecha con el formato Correcto yyyy-[m] m-[d] d");
            return null;
        }
        try {
            episodio.setDuracion(Time.valueOf(txtDurEp.getText()));
        } catch (IllegalArgumentException e) {
            showWarning("Duracion Incorrecta", "Escribe una Duracion con el formato Correcto hh:mm:ss");
            return null;
        }
        return episodio;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public void cargarEpisodios(List<Episodio> episodios) {
        setEpisodios(episodios);
        tableViewEpisodios.getItems().setAll(episodios);
    }

    private void setCells() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colTemporada.setCellValueFactory(new PropertyValueFactory<>("temporada"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colSerie.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSerie().getNombre()));
        colFechaDeSalida.setCellValueFactory(new PropertyValueFactory<>("FechaDeSalida"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
    }

    private boolean prepareExportDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            return directory.mkdirs();
        }
        return true;
    }

    public void toJSON(ActionEvent actionEvent) {
        cargarEpisodios(Conexion_App_bbdd.getEpisodios(c, this.serie));
        if (!prepareExportDirectory("data/exportaciones"))
            return;
        try {
            File outputFile = new File("data/exportaciones/episodios.json");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(outputFile, this.episodios);
            System.out.println("Episodios exportados correctamente a " + outputFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarTabla() {
        cargarEpisodios(Conexion_App_bbdd.getEpisodios(c, this.serie));
        setCells();
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        this.episodios = episodios;
    }

    public static void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /*

    private void cargarSerie() {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            xmlMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            setSerie(xmlMapper.readValue(new File("data/Serie.xml"), Serie.class)); // Leer la serie desde un archivo XML
        } catch (Exception e) {
            System.out.println("Error al cargar la ventana de serie en episodiosController");
        }
    }
    public void setSerie(Serie serie) {
        this.serie = serie;
        lblNameSerieEpisodios.setText(serie.getName().toUpperCase()); // Establecer el nombre de la serie en la etiqueta
        cargarEpisodios(); // Cargar los episodios de la serie
    }

    public void saveXML(List<Episodio> episodiosFromXML) {
        try {
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                dataDir.mkdirs(); // Crear directorio de datos si no existe
            }
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(JSONHandler.episodiosToXML(episodiosFromXML));
            StreamResult result = new StreamResult(new File(dataDir, "episodios.xml"));
            transformer.transform(source, result); // Transformar y guardar episodios en un archivo XML
        } catch (Exception e) {
            System.out.println("Error al guardar el episodios.xml");
        }
    }


    private void cargarEpisodios() {
        try {
            List<Episodio> episodiosFromXML = conexion.getEpisodios(serie.getId()); // Obtener episodios desde la base de datos
            saveXML(episodiosFromXML); // Guardar episodios en un archivo XML
            ObservableList<String> episodiosList = FXCollections.observableArrayList();
            for (Episodio episodio : episodiosFromXML) {
                String textoEpisodio = "Temporada " + episodio.getSeason() + ", Episodio " + episodio.getNumber() + ", " + episodio.getName();
                episodiosList.add(textoEpisodio); // Agregar información del episodio a la lista observable
            }

            listViewEpisodios.setItems(episodiosList); // Establecer la lista de episodios en la vista de lista

        } catch (Exception e) {
            System.out.println("Error al cargar los episodios");
        }
    } */
}