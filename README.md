# Hola mundo en Java
Aplicación HTTP original. Requiere Docker Engine activo y curl. Ejecuta estos comandos dentro de `01-java`. No necesitas instalar Java en el host.

## Construir y ejecutar
```bash
docker build -t csanto/hola-java:1.0 .
docker run -d --name hola-java -p 127.0.0.1:8081:8080 csanto/hola-java:1.0
curl --fail http://localhost:8081/
curl --fail http://localhost:8081/health
```
Respuesta esperada: `Hola mundo desde Java`. `/health` devuelve `ok`; una ruta desconocida devuelve 404. Los puertos publicados son diferentes para permitir ejecución simultánea. `EXPOSE 8080` documenta el puerto interno; `-p` lo publica en el host. Se usa 127.0.0.1 para acceso local.

## Publicar en Docker Hub
Crea el repositorio `hola-java` en tu cuenta `csanto` si es necesario. Inicia sesión de forma interactiva; usa tu token de acceso cuando Docker solicite contraseña. No guardes el token en los archivos.
```bash
docker login -u csanto
docker push csanto/hola-java:1.0
```
Después de publicar, cualquier equipo autorizado puede ejecutar:
```bash
docker pull csanto/hola-java:1.0
docker run -d --name hola-java -p 127.0.0.1:8081:8080 csanto/hola-java:1.0
```
No ejecutes este último `run` si ya existe un contenedor del mismo nombre.

## Inspeccionar y detener
```bash
docker logs hola-java
docker ps --filter name=hola-java
docker rm -f hola-java
```
El último comando detiene y elimina únicamente este contenedor. Si el puerto está ocupado, cambia el número a la izquierda de `:8080` y ajusta la URL.

## Dockerfile
La etapa build usa un JDK 21 para compilar Main.java. La etapa final usa JRE 21, copia Main.class y ejecuta Java con el módulo HTTP. No incorpora herramientas de compilación.
WORKDIR fija el directorio de trabajo. USER ejecuta la aplicación sin privilegios de root. El servidor escucha en todas las interfaces dentro del contenedor.
# contenedores
