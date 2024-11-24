mvn clean package

java --module-path C:\javafx-sdk-17.0.13\lib --add-modules javafx.controls,javafx.fxml -jar
.\target\original-controlador-1.0-SNAPSHOT.jar
substituir la ruta del javafx

# Proyecto de Aplicación de Series de TV

Autores: **Víctor Sánchez Nogueira** y **Joel Figueirido Molares**

## 1. Introducción

### 1.1 Descripción del Proyecto

Este proyecto tiene como objetivo desarrollar una aplicación para gestionar y consultar información sobre series de
televisión. La aplicación permite a los usuarios buscar entre una lista de series,filtrar según alguno de sus campos (
idioma,estado y cadena), y a su vez filtrar entre diversos a la vez.Ademas los usuarios pueden ver los episodios de una
serie seleccionada así como crear uno nuevo o modificar uno existente,tambien se pueden exportar episodios a un archivo
json.

#### Funcionalidades principales:

- **Ordenacion de series**: Los usuarios pueden seleccionar el orden segun el cual quieren ordenar las
  series,calificacion y fecha de estreno,ambas ascendente y descendente.
- **Visualización de episodios**: Permite a los usuarios consultar los episodios de la serie seleccionada, con detalles
  como estreno,título,temporada y numero de episodio.
- **Creación de episodios**: Los usuarios pueden crear un nuevo episodio y añadirlo a la lista de episodios de la serie.
- **Modificación de episodios**: Los usuarios pueden editar los detalles de un episodio existente, como el título, la
  temporada y el número de episodio.
- **Eliminación de episodios**: Los usuarios pueden eliminar un episodio de la lista de episodios de la serie.
- **Exportación de episodios**: Los usuarios pueden exportar la lista de episodios de una serie a un archivo JSON.

### 1.2 Descripción de la Base de Datos

La aplicación obtiene los datos desde dos bases de datos diferentes:

- **Base de datos Login**: Almacena las credenciales encriptadas de los usuarios para el inicio de sesión.
- **Base de datos app_series**: Almacena la información de las series de televisión, y sus episodios.

### 1.4 Tecnologías utilizadas

Este proyecto ha sido desarrollado con las siguientes tecnologías:

- **Java**: Lenguaje de programación principal utilizado.
- **JavaFX**: Para la creación de la interfaz gráfica de usuario (GUI).
- **FXML**: Lenguaje utilizado para diseñar las vistas de la aplicación.
- **Maven**: Utilizado para la gestión de dependencias y la construcción del proyecto.
- **MySQL**: Base de datos utilizada para almacenar la información de las series y los episodios y las credenciales de
  los usuarios.

## 2. Estructura del Proyecto

El proyecto sigue el patrón de arquitectura **MVC (Modelo-Vista-Controlador)**. Cada una de estas capas tiene una
responsabilidad clara:

- **Modelo**: Esta capa gestiona la lógica del negocio. Aquí se define la estructura de los datos (series, episodios,
  usuarios) y las interacciones con la Base de Datos.
- **Vista**: Los archivos **FXML** definen la interfaz gráfica. Esta capa gestiona lo que el usuario ve y con lo que
  interactúa.
- **Controlador**: Gestiona la interacción entre el modelo y la vista. Los controladores son responsables de manejar
  eventos de usuario (como el CRUDA) y procesar los datos obtenidos desde el modelo.

## <u>Estructura del código</u>

## 2.1 Descripción de las clases en el paquete `controlador`

1. **`Controlador`**: Esta clase funciona como una base para los controladores y proporciona un metodo comun, la
   visualización de mensajes en la interfaz gráfica
    - Método clave:
        - **`showWarning`**: Muestra un mensaje de advertencia en la interfaz gráfica.
2. **`EpisodiosController`**: Controlador para la vista de episodios. Se encarga de mostrar la lista de episodios de una
   serie y gestionar las operaciones CRUD.
    - Métodos clave:
        - **`initialize`**: Configura el estado inicial del controlador al cargar la interfaz gráfica.
        - **`cargarTabla`**: Llena la tabla de episodios con los datos de la base de datos.
        - **`crearEp`**: Crea un nuevo episodio en la base de datos.
        - **`modificarEp`**: Modifica los datos de un episodio existente.
        - **`eliminarEp`**: Elimina un episodio seleccionado de la base de datos.
        - **`toJSON`**: Exporta la lista de episodios a un archivo JSON.
        - **`prepareExportDirectory`**: Verifica y crea el directorio donde se guardará el archivo JSON.
3. **`LoginController`**: Controlador para la vista de inicio de sesión. Gestiona el proceso de inicio de sesión y la
   creación de nuevos usuarios.
    - **`registrarUser`**: Crea un nuevo usuario y almacena sus credenciales en la base de datos.
    - **`acceder`**: Verifica las credenciales del usuario y permite el acceso a la aplicación.
    - **`toSerie`**: Cambia a la vista de series tras un inicio de sesión exitoso.
4. **`SerieController`**: Controlador para la vista de series. Se encarga de mostrar la lista de series y gestionar las
   operaciones de búsqueda y filtrado.
    - **`initialize`**: Configura el estado inicial del controlador al cargar la interfaz gráfica.
    - **`cargarTabla`**: Llena la tabla de series con los datos de la base de datos.
    - **`buscar`**: Realiza una búsqueda de series según el nombre introducido por el usuario.
    - **`filtrar`**: Filtra las series según el idioma, estado y cadena seleccionados por el usuario.
    - **`ordenar`**: Ordena las series según la calificación y la fecha de estreno, de forma ascendente o descendente.
    - **`toEpisodios`**: Cambia a la vista de episodios de la serie seleccionada.
    - **`toCast`**: Cambia a la vista de reparto de la serie seleccionada.

## 2.2 Relación entre las clases


Las clases del paquete `controlador` están relacionadas de la siguiente manera:

- **`LoginController`**: Gestiona el inicio de sesión y la creación de nuevos usuarios. Tras un inicio de sesión exitoso, redirige a la vista de series mediante el método `toSerie`.

- **`SerieController`**: Gestiona la visualización y filtrado de series. Permite cambiar a la vista de episodios de una serie seleccionada mediante el método `toEpisodios`.

- **`EpisodiosController`**: Gestiona la visualización y operaciones CRUD de los episodios de una serie. Se inicializa con la serie seleccionada desde `SerieController`.

- **`Controlador`**: Clase base que proporciona métodos comunes para los controladores, como `showWarning` para mostrar mensajes de advertencia en la interfaz gráfica.


## 2.3 Descripción del paquete `modelo`

El paquete `modelo` se encarga de gestionar la lógica de negocio relacionada con las series y episodios. Aquí se definen las clases que modelan los datos que se obtienen de la base de datos.
A su vez se realizan las consultas necesarias para obtener los datos de la base de datos y poder realizar el CRUD.
Tambien aquí manejamos la encriptacion de las contraseñas mediante el algoritmo LZ78.

## 2.4 `src/main/resources` – Almacenamiento de FXML y CSS

La carpeta `src/main/resources` contiene todos los archivos necesarios para la interfaz gráfica de usuario y otros
recursos estáticos.

### Subcarpetas clave y archivos:

1. **`resources/edu/badpals/vista/`**: Esta subcarpeta contiene los archivos **FXML**, los cuales definen la
   estructura y diseño de la interfaz gráfica de la aplicación.

    - **Archivos FXML principales**:
        - **`login.fxml`**: Vista de inicio de sesión.
        - **`serie.fxml`**: Vista de series.
        - **`episodios.fxml`**: Vista de episodios.

# <u>Manual para Desarrolladores</u>

## Requisitos del Sistema

### Antes de comenzar, asegúrate de que tienes instalados los siguientes componentes en tu sistema:

1. JDK 21: Necesario para compilar y ejecutar aplicaciones Java.
2. JavaFX 17: Usado para la interfaz gráfica de usuario (GUI) en Java.
3. Maven: Herramienta para la gestión de proyectos y dependencias en Java.
4. Git: Sistema de control de versiones para gestionar el código fuente.
5. MySQL: Base de datos utilizada para almacenar la información de las series y los episodios y las credenciales de los
   usuarios.

## Instrucciones de Instalación

### 1. Sitúate donde quieras crear la app:

```bash
cd C:\Users\nombredeusuario\Escritorio
```

### 2. Crea un directorio donde almacenar la app:

```bash
  mkdir Directorio
```

### 3.Sitúate en el directorio:

    ```bash
    cd Directorio
    ```

### 4.Instala los requisitos:

#### Instalar JDK 21:

- Si no tienes JDK instalado, descárgalo e instálalo desde Oracle JDK 21.
- Durante la instalación, asegúrate de seleccionar la opción de añadir Java al PATH para que puedas usarlo desde la
  línea de comandos.

- Verifica la instalación de Java ejecutando el siguiente comando en la terminal:

```bash
java -version

```

#### Instalar JavaFX 17:

- Descarga JavaFX 17 desde [Gluon](https://gluonhq.com/products/javafx/).
- Descomprime el archivo descargado en un directorio de tu elección.
- Debes configurar las variables de entorno para JavaFX. En Windows, añade la ruta del directorio lib de JavaFX a la
  variable de entorno PATH.
- Para verificar, puedes ejecutar el siguiente comando, reemplazando ruta_a_javafx por la ruta de la carpeta lib:

```bash
set PATH=%PATH%;ruta_a_javafx\lib
```

#### Instalar Maven:

- Si no tienes Maven instalado, descárgalo e instálalo desde [Apache Maven](https://maven.apache.org/download.cgi).
- Descomprime el archivo descargado en un directorio de tu elección.
- Añaade la ruta de la carpeta bin de Maven a la variable de entorno PATH.
- Para verificar la instalación, ejecuta el siguiente comando en la terminal:

```bash
mvn -version
```

#### Instalar Git:

- Si no tienes Git instalado, descárgalo e instálalo desde [Git](https://git-scm.com/downloads).
- Durante la instalación, asegúrate de seleccionar la opción de añadir Git al PATH para que puedas usarlo desde la línea
  de comandos.
- Verifica la instalación de Git ejecutando el siguiente comando en la terminal:

```bash
git --version
```

### 5. Clona el repositorio de la aplicación:

```bash
   git clone https://github.com/joeelfgrd/Series-Api.git
```

### 6. Sitúate en el directorio del proyecto:

```bash
    cd Series-Api
  ```

### 7. Instala las dependencias del proyecto:

```bash
    mvn  install
```

### 8. Ejecuta la aplicación:

#### Para Crear el JAR

```bash
    mvn clean package
```

#### Para ejecutar con el JAR
-Ejecuta el siguiente comando en la terminal pero sustituyendo la ruta del javafx por la tuya:

```bash
    
 java --module-path C:\javafx-sdk-17.0.13\lib --add-modules javafx.controls,javafx.fxml -jar
.\target\original-controlador-1.0-SNAPSHOT.jar
```

## Notas:

- Si solo vas a usar el programa sin necesidad de realizar cambios en el código, asegúrate de tener JDK 21, JavaFX 17 y
  el archivo JAR ejecutable en la carpeta del proyecto.
- Git es opcional; puedes descargar el ZIP del repositorio y extraerlo sin necesidad de clonar.

# <u>Manual de Usuario</u>

## Inicio de Sesión

1. Al abrir la aplicación, se mostrará la pantalla de inicio de sesión.
   ![Inicio sesion app](src/main/resources/img/Login1.png)
2. Introduce tus credenciales (usuario y contraseña) y haz clic en **Acceder**.

- Si intentas acceder con credenciales incorrectas, se mostrará un mensaje de error.
- Si no tienes una cuenta, puedes registrarte haciendo clic en **Registrarse**.
  ![Inicio sesion app](src/main/resources/img/login2.png)
- Cuando creas una contraseña e usuario, esta se cifrará y almacenará para proteger tu información.
  ![Inicio sesion app](src/main/resources/img/cifrado.png)

3. Después de iniciar sesión correctamente, se mostrará una ventana para restaurar la sesión anterior o iniciar una
   nueva.
   ![Inicio sesion app](src/main/resources/img/Login3.png)

- Si eliges **Aceptar**, se cargarán los datos de la sesión anterior.
  ![Inicio sesion app](src/main/resources/img/Login4.png)
- Si eliges **Cancelar**, se mostrará la pantalla principal de la aplicación.

4. Busca una serie por su nombre en el campo de búsqueda y haz clic en **Buscar**.
   ![Inicio sesion app](src/main/resources/img/Busqueda.png)
5. Si pulsas en ver episodios, se mostrará una lista con los episodios de la serie seleccionada ordenados por temporada
   y número de episodio.
   ![Inicio sesion app](src/main/resources/img/Episodios2.png)
6. Si pulsas en **Ver Cast** , se mostrará una lista con los actores que participan en la serie y los personajes que
   interpretan.
   ![Inicio sesion app](src/main/resources/img/Cast.png)

# Tiempo dedicado

- **Víctor Sánchez Nogueira**: 38 horas
- Tareas:
    - Caché
    - JSONHandler
    - Controlador(Login y LinkPaginas)
    - Conexion
    - Encriptado de contraseñas
    - Exportaciones (BIN TXT XML)

- **Joel Figueirido Molares**: 31 horas
- Tareas:
    - Interfaz gráfica
    - Controlador(Episodios y Cast)
    - Exportaciones JSON
    - JSONHandler
    - Conexion
    - Documentación

# Extras Realizados

1. **Ordenación de resultados** de las consultas y almacenaje de los datos.

- Las series aparecen ordenadas por temporada y número de episodio.

2. **Control de errores** (errores de ficheros, consultas sin resultados...).

- Se muestran mensajes de error si no se encuentran resultados o si hay problemas con los ficheros.
- Se manejan excepciones para evitar fallos en la aplicación.
- Si el usuario introduce credenciales incorrectas, se muestra un mensaje de error.
- Si el usuario borra el paquete data,este se genera automáticamente.
- Si el usuario borra todos los usuarios,se puede crear uno nuevo sin problemas.

3. **Uso de la aplicación offline** (en lugar de recurrir a la API, carga de ficheros en caché)

- Se almacenan los datos de las series en un archivo XML para poder acceder a ellos sin conexión.
- Si no hay conexión a Internet, la aplicación carga los datos de las series desde el archivo XML.

4. **Almacenamiento del último estado de ejecución** de la aplicación (última consulta y resultados devueltos)

- Se guarda el estado de la aplicación y despues de iniciar sesión, se pregunta al usuario si desea restaurar la sesión
  anterior.
- Si el usuario acepta, se cargan los datos de la sesión anterior.
- Si el usuario cancela, se muestra la pantalla principal de la aplicación.

5. Adición de un login (control de acceso restringido) con usuario y contraseña contenidos en un fichero de Properties.

- Se añade un sistema de inicio de sesión con credenciales almacenadas en un archivo Users.txt.
- Los usuarios deben autenticarse para acceder a la aplicación.
- Si el usuario no tiene una cuenta, puede registrarse y crear un nuevo usuario.

6. Si el contenido se encripta

- Las contraseñas se cifran antes de ser almacenadas en el archivo Users.txt.
- Se utiliza el algoritmo de cifrado LZ78 para proteger la información de los usuarios.

# Propuestas de Mejora

- **Mejora de la interfaz gráfica** para hacerla más atractiva y fácil de usar.
- **Añadir más funcionalidades** como la posibilidad de marcar series como favoritas, ver recomendaciones
  personalizadas, etc.
- **Incluir un sistema de notificaciones** para informar a los usuarios sobre nuevas series, episodios, etc.
- **Mejorar el sistema de búsqueda** para que los usuarios puedan buscar series por género, año de estreno, etc.
- **Añadir soporte para más idiomas** para que la aplicación pueda ser utilizada por usuarios de todo el mundo.
- **Implementar un sistema de valoración** para que los usuarios puedan calificar las series y dejar comentarios.
- **Incluir un sistema de recomendaciones** para sugerir series similares a las que le gustan al usuario.

# Conclusiones

- En este proyecto,hemos aprendido a utilizar y manejar javafx,hemos aprendido a crear una interfaz gráfica de usuario y
  a conectarla con la lógica de negocio.
- Hemos aprendido a trabajar con archivos XML y a almacenar datos en caché para mejorar el rendimiento de la aplicación.
- Nos gustaría que las especificaciones de los requisitos hubieran sido mas detalladas desde el principio,comenzando por
  la eleccion de la api a utilizar.
- También nos gustaría haber tenido mas especificaciones sobre algun extra,ya que no sabíamos si era necesario
  implementar todos los extras ni tampoco como podiamos hacerlo,ya que usamos cosas que no dimos en clase,como el
  cifrado de contraseñas asi como el mismo javafx,ya que empezamos a ciegas.
- En general,ha sido un proyecto interesante a la par que desquiciante debido a la cantidad de configuraciones y
  problemas que hemos tenido que solucionar tanto de java como tal,como de javafx y maven ademas de incompatibilidades
  entre si.
