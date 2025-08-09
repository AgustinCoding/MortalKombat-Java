#!/bin/bash
# --------------------------
# build.sh — Compila y ejecuta MortalKombat-Java
# --------------------------
# Este script automatiza la compilacion y ejecucion del proyecto Java usando Maven.
# Verifica primero si Java y Maven estan instalados, luego compila el codigo y, si no hay errores, ejecuta la aplicacion.

# ---------------------------------------------
# Verificar que Java este instalado en el sistema
# `command -v java` busca la ruta del ejecutable de Java
# `&> /dev/null` redirige tanto la salida estándar como la de error a /dev/null para ocultarla
# `if ! ...` significa que si el comando no se encuentra (retorna distinto de 0), se ejecuta el bloque
if ! command -v java &> /dev/null; then
  echo "Java no esta instalado. Instalala para continuar."
  exit 1  # Finaliza el script con codigo de error 1
fi

# ---------------------------------------------
# Verificar que Maven este instalado
# `command -v mvn` verifica si el comando mvn esta disponible en el PATH
if ! command -v mvn &> /dev/null; then
  echo "Maven no esta instalado. Instalala para continuar."
  exit 1
fi

# ---------------------------------------------
# Compilar el proyecto usando Maven
# `mvn clean install` hace dos cosas principales:
#   - clean: elimina los archivos compilados previos (carpeta target/)
#   - install: compila el codigo, ejecuta tests y guarda el artefacto en el repositorio local de Maven
# `if ! ...; then` se usa para detectar si la compilacion falla (codigo distinto de 0)
echo "Compilando el proyecto con Maven..."
if ! mvn clean install; then
  echo "La compilacion fallo. Revisa los errores e intenta de nuevo."
  exit 1
fi

# ---------------------------------------------
# Ejecutar la aplicacion con Maven
# `mvn exec:java` usa el plugin exec de Maven para ejecutar la clase principal indicada en el pom.xml
# Esto solo se ejecuta si la compilacion anterior fue exitosa
echo "Ejecutando la aplicacion..."
mvn exec:java
