# ScreenMatch

**ScreenMatch** es una aplicación basada en Java que permite a los usuarios buscar series de TV y episodios, obtener información detallada y gestionar una colección de series. La aplicación utiliza **Spring Boot** y **Maven** para la gestión de dependencias y compilaciones.

## Características

- **Buscar Series**: Buscar series de TV utilizando una API externa y obtener información detallada.
- **Buscar Episodios**: Buscar episodios de una serie específica y obtener información detallada.
- **Mostrar Series Buscadas**: Mostrar una lista de todas las series que se han buscado.
- **Buscar Serie por Título**: Encontrar una serie por su título.
- **Top 5 Series**: Mostrar las 5 mejores series según la evaluación.
- **Buscar Serie por Categoría**: Encontrar series por su género.
- **Filtrar Series**: Filtrar series según el número de temporadas y evaluación.
- **Buscar Episodios por Título**: Encontrar episodios por su título.
- **Top 5 Episodios**: Mostrar los 5 mejores episodios de una serie específica.

## Tecnologías Utilizadas

- **Java**: El principal lenguaje de programación utilizado para la aplicación.
- **Spring Boot**: Framework utilizado para construir la aplicación.
- **Maven**: Herramienta de gestión de dependencias y compilación.
- **JPA/Hibernate**: Para ORM e interacciones con la base de datos.
- **OMDb API**: API externa utilizada para obtener datos de series y episodios.

## Comenzando

### Clonar el repositorio:

```bash
git clone https://github.com/yourusername/screenmatch.git
cd screenmatch
Configurar variables de entorno:
Asegúrate de tener una variable de entorno API_KEY_SERIES configurada con tu clave de API de OMDb.

Construir el proyecto:
bash
Copiar código
mvn clean install
Ejecutar la aplicación:
bash
Copiar código
mvn spring-boot:run
Uso
Ejecuta la aplicación y sigue las opciones del menú para buscar series, episodios y gestionar tu colección.
Utiliza las opciones proporcionadas para filtrar y mostrar series y episodios según varios criterios.

Contribuciones
¡Las contribuciones son bienvenidas!
Por favor, haz un fork del repositorio y crea un pull request con tus cambios.

Licencia
Este proyecto está licenciado bajo la Licencia MIT.
