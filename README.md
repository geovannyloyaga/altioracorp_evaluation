# altioracorp_evaluation
Evaluación de ALTIORACORP

# Creación de BDD

1. Para crear la estructura de la BDD se requiere ejecutar el scripts adjunto 'altioracorp_database/altioraen_script' que además incluye un diagrama de base de datos realizado en el software MYSQL Workbench

# Ejecución para BACKEND

1. Abrir el proyecto de la carpeta altioracorp_evaluation y contiene la carpeta altioracorp_backend proyecto spring boot con maven

2. Realizar el cambio del usuario de la base de datos que tenga configurado en el archivo ubicado en: 
altioracorp_backend/src/main/resources/application.properties la siguiente línea:

spring.datasource.password=XXXXXXX

3. Yo use Spring Tools de la pagina oficial y correr la Aplicación de Spring Boot

# Ejecución para FRONTEND

1. Primero debe tener conexión a internet

2. Abrir el proyecto con un IDE o desde la terminal ubicarse en la carpeta altioracorp_frontend

3. Ejecutar el comando yarn install

4. Una vez concluido la instalación procedemos a ejecutar el proyecto con el comando yarn start
