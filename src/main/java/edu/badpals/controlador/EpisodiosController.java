package edu.badpals.controlador;

import edu.badpals.modelo.Episodio;
import edu.badpals.modelo.Serie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EpisodiosController implements Initializable {
    private Serie serie; // Objeto Serie actual

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
    private TableColumn<Episodio, String> colEstreno;
    @FXML
    private TableColumn<Episodio, Integer> colDuracion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colTemporada.setCellValueFactory(new PropertyValueFactory<>("temporada"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colSerie.setCellValueFactory(new PropertyValueFactory<>("serie"));
        colEstreno.setCellValueFactory(new PropertyValueFactory<>("estreno"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        // Load episodes data here
        cargarEpisodios();
    }


    public void toSerie(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("serie.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 919, 750);
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.setTitle("Series");

        SerieController controller = fxmlLoader.getController();
        controller.setSerie(this.serie);
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public void cargarEpisodios(){}
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