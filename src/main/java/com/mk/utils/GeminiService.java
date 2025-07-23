package com.mk.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONObject;

public class GeminiService {
    private static final String CONFIG_FILE = "config.properties";
    private static final String URL_ENDPOINT;
    private static final String API_KEY;

    static {
        Properties prop = new Properties();
        try (InputStream is = GeminiService.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (is == null) {
                throw new FileNotFoundException("No se encontró config.properties en el classpath");
            }
            prop.load(is);
            URL_ENDPOINT = prop.getProperty("GEMINI_URL", "");
            API_KEY      = prop.getProperty("GEMINI_API_KEY", "");
        } catch (IOException e) {
            throw new RuntimeException("Error cargando configuración: ", e);
        }
    }


    private GeminiService() {
    } // evitar instancias

    public static String getResponseTo(String message) {
        try {
            URL url = new URL(URL_ENDPOINT + "?key=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String promptJson = buildPromptJson(message);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = promptJson.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            return jsonToMessage(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

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

    private static String jsonToMessage(String json) {
        try {
            JSONObject root       = new JSONObject(json);
            JSONArray candidates  = root.getJSONArray("candidates");
            if (candidates.isEmpty()) return "Sin candidatos en la respuesta";

            // Tomamos el primer candidato
            JSONObject firstCand   = candidates.getJSONObject(0);
            JSONObject contentObj  = firstCand.getJSONObject("content");
            JSONArray parts        = contentObj.getJSONArray("parts");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < parts.length(); i++) {
                sb.append(parts.getJSONObject(i).getString("text"));
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error parseando JSON: " + e.getMessage();
        }
    }
}
