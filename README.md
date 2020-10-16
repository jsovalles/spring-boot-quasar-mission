# spring-boot-quasar-mission
Proyecto de Spring Boot acerca de una mision en Quasar

## Vista previa Heroku

**Si no se desea descargar el proyecto y hacer la compilación/ejecución localmente se puede consumir este servicio mediante la plataforma PaaS Heroku:**

```
topSecretMessage (POST) - https://spring-boot-quasar.herokuapp.com/quasar/v0/topsecret

createTopSecretMessageSplit (POST) - https://spring-boot-quasar.herokuapp.com/quasar/v0/topsecret_split?satellite_name=[SATELLITE_NAME]

getTopSecretMessageSplit (GET) - https://spring-boot-quasar.herokuapp.com/quasar/v0/topsecret_split
```

**NOTA:** Para el primer consumo del servicio se debe esperar aproximadamente 30 segundos, ya que Heroku apaga automáticamente todos los recursos que no estén en uso.

## Instalación/Despliegue

Se descarga el proyecto desde GitHub y después se realiza los siguientes comandos
```
mvn clean install

mvn spring-boot:run
```
Después de que el comando termine de desplegar se puede consumir los servicios correspondientes.

## Descripción de los diferentes servicios

**topSecretMessage:** Servicio web encargado de calcular la posición de la nave y el mensaje que emitió de los diferentes satélites.
En este servicio se calculó la posición de la nave mediante el uso de la intersección entre los 3 satélites descritos en el ejercicio; por la posición de los satélites se deben de tener distancias razonables ya que el algoritmo de intersección tiene un margen de error del 10%, si el resultado supera este porcentaje no se mostrara la posición de la nave. En los ejemplos de consumo del servicio se brindará unas distancias que darán el resultado deseado.

De igual forma el servicio descifra el mensaje que los diferentes satélites transmiten, se debe de tener el mensaje completamente descifrado, por lo cual se requiere que todos los campos del mensaje estén informados, sin importar el orden en como este transmitiendo los diferentes satélites. 

**createTopSecretMessageSplit:** Servicio web que permite guardar la información de distancia y el mensaje cifrado en una base de datos H2, esta base de datos depende de la instancia de la API, lo que significa que estará con la información activa mientras Heroku no se reinicie por inactividad o hasta que se reinicie/detenga el servidor local.

**getTopSecretMessageSplit:** Servicio web encargado de calcular la posición de la nave y el mensaje que emitió de los diferentes satélites.

Ya que este servicio depende de _createTopSecretMessageSplit_, se puede descifrar el mensaje si todos los campos están informados en por lo menos un satélite; sin embargo, se debe informar *TODOS* los satélites con sus nombres descritos en el ejercicio y su distancia para calcular exitosamente la posición, aunque el servicio pueda dar el mensaje descifrado, se necesita calcular exitosamente su distancia para que el servicio responda exitosamente.

A continuación se mostrara algunos ejemplos de consumos para los servicios construidos:
```
topSecretMessage (POST) - https://spring-boot-quasar.herokuapp.com/quasar/v0/topsecret

topSecretMessage Request Body example (OK response):
[
    {
        "name": "kenobi",
        "distance": 500.0,
        "message": [
            "este",
            "",
            "",
            "mensaje",
            ""
        ]
    },
    {
        "name": "skywalker",
        "distance": 200,
        "message": [
            "",
            "es",
            "",
            "",
            "secreto"
        ]
    },
    {
        "name": "sato",
        "distance": 559,
        "message": [
            "este",
            "",
            "un",
            "",
            ""
        ]
    }
]

createTopSecretMessageSplit Request Example - https://spring-boot-quasar.herokuapp.com/quasar/v0/topsecret_split?satellite_name=kenobi

createTopSecretMessageSplit Body Example :
{
        "distance": 500.0,
        "message": [
            "este",
            "",
            "",
            "mensaje",
            ""
        ]
}

getTopSecretMessageSplit (GET) - https://spring-boot-quasar.herokuapp.com/quasar/v0/topsecret_split
```

Para observar  la bases de datos H2 de la API, se puede acceder a la consola en la siguiente ruta:
```
http://localhost:8080/h2-console
```
*Donde:*
- Ruta de la base de datos: jdbc:h2:mem:quasardb
- Usuario: sebastian
- Clave: so

![enter image description here](https://i.ibb.co/vQPNZML/H2database.png)

## Descripción del proyecto

### *Operación Fuego de Quasar*

Han Solo ha sido recientemente nombrado General de la Alianza Rebelde y busca dar un gran golpe contra el Imperio Galáctico para reavivar la llama de la resistencia. El servicio de inteligencia rebelde ha detectado un llamado de auxilio de una nave portacarga imperial a la deriva en un campo de asteroides. El manifiesto de la nave es ultra clasificado, pero se rumorea que transporta raciones y armamento para una legión entera.

### Desafío

Como jefe de comunicaciones rebelde, tu misión es crear un programa en Golang que retorne la fuente y contenido del mensaje de auxilio. Para esto, cuentas con tres satélites que te permitirán triangular la posición, ¡pero cuidado! el mensaje puede no llegar completo a cada satélite debido al campo de asteroides frente a la nave.

*Posición de los satélites actualmente en servicio*
- Kenobi: [-500, -200]
- Skywalker: [100, -100]
- Sato: [500, 100]

### Nivel 1

Crear un programa con las siguientes firmas:
```
// input: distancia al emisor tal cual se recibe en cada satélite
// output: las coordenadas ‘x’ e ‘y’ del emisor del mensaje
func GetLocation(distances ...float32) (x, y float32)
// input: el mensaje tal cual es recibido en cada satélite
// output: el mensaje tal cual lo genera el emisor del mensaje
func GetMessage(messages ...[]string) (msg string)
```

Consideraciones:
- La unidad de distancia en los parámetros de GetLocation es la misma que la que se utiliza para indicar la posición de cada satélite.
- El mensaje recibido en cada satélite se recibe en forma de arreglo de strings.
- Cuando una palabra del mensaje no pueda ser determinada, se reemplaza por un string en blanco en el array.

Ejemplo: [“este”, “es”, “”, “mensaje”]
- Considerar que existe un desfasaje (a determinar) en el mensaje que se recibe en cada satélite.

Ejemplo:
```
Kenobi: [“”, “este”, “es”, “un”, “mensaje”]
Skywalker: [“este”, “”, “un”, “mensaje”]
Sato: [“”, ””, ”es”, ””, ”mensaje”]
```
### Nivel 2
Crear una API REST, hostear esa API en un cloud computing libre (Google App Engine, Amazon AWS, etc), crear el servicio /topsecret/ en donde se pueda obtener la ubicación de la nave y el mensaje que emite.

El servicio recibirá la información de la nave a través de un HTTP POST con un payload con el siguiente formato:
```
POST → /topsecret/
{
"satellites": [
{
“name”: "kenobi",
“distance”: 100.0,
“message”: ["este", "", "", "mensaje", ""]
},
{
“name”: "skywalker",
“distance”: 115.5
“message”: ["", "es", "", "", "secreto"]
},
{
“name”: "sato",
“distance”: 142.7
“message”: ["este", "", "un", "", ""]
}
]
}
```
La respuesta, por otro lado, deberá tener la siguiente forma:
```
RESPONSE CODE: 200
{
"position": {
"x": -100.0,
"y": 75.5
},
"message": "este es un mensaje secreto"
}
```

En caso que no se pueda determinar la posición o el mensaje, retorna:
```
RESPONSE CODE: 404
```

### Nivel 3
Considerar que el mensaje ahora debe poder recibirse en diferentes POST al nuevo servicio /topsecret_split/, respetando la misma firma que antes. Por ejemplo:
```
POST → /topsecret_split/{satellite_name}
{
"distance": 100.0,
"message": ["este", "", "", "mensaje", ""]
}
```
Crear un nuevo servicio /topsecret_split/ que acepte POST y GET. En el GET la respuesta deberá indicar la posición y el mensaje en caso que sea posible determinarlo y tener la misma estructura del ejemplo del Nivel 2. Caso contrario, deberá responder un mensaje de error indicando que no hay suficiente información.

# Desarrollado en

* [IntelliJ IDEA](https://www.jetbrains.com/idea/) - The Java IDE
* [Maven](https://maven.apache.org/) - Dependency Management
* Java 11 JDK
