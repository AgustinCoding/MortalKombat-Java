package com.mk.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONObject;

public class GeminiService {
    // Nombre del archivo de configuración que contiene URL y API key
    private static final String CONFIG_FILE = "config.properties";

    // URL del endpoint y clave API que se cargan desde el archivo config.properties
    private static final String URL_ENDPOINT;
    private static final String API_KEY;

    static {
        Properties prop = new Properties();
        try (InputStream is = GeminiService.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            // Se lee el archivo config.properties del classpath
            if (is == null) {
                throw new FileNotFoundException("No se encontro config.properties en el classpath");
            }
            prop.load(is);
            // Se obtienen la URL y la API key para usar el servicio externo
            URL_ENDPOINT = prop.getProperty("GEMINI_URL", "");
            API_KEY      = prop.getProperty("GEMINI_API_KEY", "");
        } catch (IOException e) {
            // Si hay error cargando la configuracion, se lanza una excepcion que detiene la app
            throw new RuntimeException("Error cargando configuracion: ", e);
        }
    }

    private GeminiService() {
        // Constructor privado para evitar instancias (clase utilitaria con solo metodos estaticos)
    }

    /**
     * Metodo principal que envia un mensaje (prompt) al servicio Gemini y devuelve la respuesta en texto.
     * Este metodo realiza:
     *  - Construccion del JSON con el mensaje
     *  - Conexion HTTP POST con headers y cuerpo
     *  - Lectura de la respuesta JSON
     *  - Parseo para extraer solo el texto relevante y devolverlo
     */
    public static String getResponseTo(String message) {
        try {
            // Se arma la URL con el endpoint y la clave de API como parametro
            URL url = new URL(URL_ENDPOINT + "?key=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST"); // Metodo POST para enviar datos
            conn.setRequestProperty("Content-Type", "application/json"); // Tipo de contenido JSON
            conn.setDoOutput(true); // Indicamos que vamos a enviar datos en el cuerpo de la peticion

            // Construimos el JSON con el mensaje del usuario
            String promptJson = buildPromptJson(message);

            // Enviamos el JSON en el cuerpo de la peticion
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = promptJson.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Leemos la respuesta recibida desde el servidor
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // Convertimos el JSON de respuesta en texto plano para usar en la aplicacion
            return jsonToMessage(response.toString());

        } catch (IOException e) {
            // En caso de error, se imprime para debug y se devuelve un mensaje con el error
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // Metodo que arma el JSON con la estructura esperada por el servicio, incluyendo el texto de entrada
    private static String buildPromptJson(String input) {
        return "{\n" +
                "  \"contents\": [\n" +
                "    {\n" +
                "      \"role\": \"user\",\n" +
                "      \"parts\": [\n" +
                "        {\n" +
                "          \"text\": \"" + input.replace("\"", "\\\"") + "\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    /**
     * Metodo que recibe el JSON de respuesta del servicio y extrae el texto
     * relevante del primer candidato de respuesta.
     * Devuelve el texto para mostrar en la aplicacion.
     */
    private static String jsonToMessage(String json) {
        try {
            JSONObject root       = new JSONObject(json);
            JSONArray candidates  = root.getJSONArray("candidates");
            if (candidates.isEmpty()) return "Sin candidatos en la respuesta";

            // Se toma el primer candidato y se obtiene su contenido y partes (texto)
            JSONObject firstCand   = candidates.getJSONObject(0);
            JSONObject contentObj  = firstCand.getJSONObject("content");
            JSONArray parts        = contentObj.getJSONArray("parts");

            StringBuilder sb = new StringBuilder();
            // Se concatena todo el texto de las partes para armar la respuesta completa
            for (int i = 0; i < parts.length(); i++) {
                sb.append(parts.getJSONObject(i).getString("text"));
            }
            return sb.toString();

        } catch (Exception e) {
            // Si hay error en el parseo del JSON se devuelve mensaje de error con detalles
            e.printStackTrace();
            return "Error parseando JSON: " + e.getMessage();
        }
    }
}
