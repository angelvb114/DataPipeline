## Compilar Proyecto

mvn clean install

Omitir pruebas

mvn clean install -Dmaven.test.skip=true

## Crear nuestra base de datos mysql en un contenedor mysql

sudo docker run -p 13306:3306 --name mysql-datapipeline -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=db_data_pipeline -e MYSQL_USER=angel -e MYSQL_PASSWORD=root -d mysql

## Ejecutar el archivo Dockerfile que creara nuestra imagen

sudo docker build -t data-pipeline .

## Crear contenedor de nuestra imagen con nuestro .jar

sudo docker run -d -p 18080:8080 --name data-pipeline --link mysql-datapipeline:mysql data-pipeline
