# Examen Android
Examen Day Store: Desarrollador Android – Kotlin
Se le solicita al candidato lo siguiente:
Desarrollar una aplicación móvil en lenguaje Kotlin con los siguientes funcionalidades y/o
características:

● Un cuadro de login con los campos: email y password. Validar que en el campo email
sea en el formato adecuado, sin campos vacíos y no mayor a 80 caracteres. En
password deberá ser menor a 8 caracteres.
Los datos capturados en el formulario enviar al siguiente endpoint (POST):

http://backtesting.click/mobile/login/

Como respuesta obtendrá un código en el parámetro “code”, dicho dato deberá mostrarse
automáticamente en el celular (notificación)

● Una vez realizado el login, crear una nueva pantalla donde muestre un mapa y con la
ubicación en tiempo real. Colocar un botón “Enviar ubicación”, este dato deberá ser
enviado al siguiente endpoint:

http://backtesting.click/mobile/sendData/
Adicional, enviar latitud y longitud como string.

Nota:
El diseño queda a criterio del candidato, se valora diseño profesional.
Los nombres de los parámetros quedan a criterio del candidato, los endpoints están
preparados para recibir la data.
El candidato tiene 72 hrs para realizar el examen, una vez recibido en su email.
Los puntos no considerados, está realizado a propósito para que el candidato lo resuelva.
Deberá de compartirse el código a través de un repositorio GIT con todas las instrucciones
necesarias a través de un README.
Funcionalidades extras serán consideradas como un plus y se tomarán en cuenta para su
desempeño en la evaluación técnica.

Adiccionalmente se sumo una funcionalidad adiccional las cuales son mantener la sesión iniciada si sales de la aplicación y al igual el boton de cerrar sesión.

## Desarrollo de la app
<ol>
  <li>Se uso el patron de diseño MVVC para la manipulación de servicios y estados de la aplicación.</li>
  <li>Uso de retrofit2 para consumo de los servicios.</li>
  <li>Consumo de FLAVORS para definición de variables constantes como URL y versiones.</li>
  <li>Uso de componentes de navegación para facilitar el enrutamiento de la misma.</li>
  <li>Loggin interceptor para el apoyo del monitoreo de los servicios.</li>
</ol>

## Instalación
<ol>
  <li>Clonar el repositorio.</li>
  <li>Abrir el proyecto con Android Studio y esperar a que se indexen y bajen las dependencias.</li>
  <li>Presionar sync project with grandle files.</li>
  <li>Ejecutar la aplicación</li>
</ol>
