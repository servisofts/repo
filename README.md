# `REPOSITORY`
[Servisofts SRL](https://servisofts.com/)
#

## `Informacion`

### Que es el repositorio de archivos Servisofts?
Servisofts cuenta con su repositorio de archivos que nos facilita encontrar y descargar los diferentes elementos necesarios para el debido manejo de los Servidores, Codigos, Micro Servicios, Librerias, Herramientas, Etc.

### Como ingresar?

Para ingresar tenemos que ir a la siguiente direccion:

[https://repo.servisofts.com/](https://repo.servisofts.com/)

(ATENCION) El uso de este repositorio es permitido solamente a travez de `HTTPS`

### Probar conexion con la repo?
Para probar la conexion ejecute el siguiente comando, si la respuesta es exitosa deberia mostrar el mensaje (`OK. Test passed!`)

```bash
curl https://repo.servisofts.com/.test
```
### Descargar un archivo con cUrl?

```bash
#    test.txt is saved directory filed
#    '.test' is path to download on repo
curl -o "./test.txt" https://repo.servisofts.com/'.test'

```


### Subir un archivo con cUrl?

```bash
#    /test/README.md is a directory and name final on repo
#    @"./README.md"  is a local path file for upload  
curl --request POST "https://repo.servisofts.com/up/" \
    -F '/test/README.md=@"./README.md"'

```
### Eliminar un archivo con cUrl?

```bash
#    /test/README.md is a directory and name final on repo
#    rm  is command for remove file or folder
curl --request POST "https://repo.servisofts.com/up/" \
    -F '/test/README.md=rm'

```

## `Instalation`

### System requriments:
    - Docker
    - Docker Compose
  
### Files Necessary

![Screen Shot 2022-12-01 at 07 11 14](https://user-images.githubusercontent.com/35882906/205038222-fc6b660d-558c-4e8b-832a-9d8e32a0a9f1.png)

```bash
#Start
./sbin/start

#Stop
./sbin/stop

#Restart
./sbin/restart
```








