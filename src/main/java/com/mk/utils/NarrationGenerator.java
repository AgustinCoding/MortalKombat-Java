package com.mk.utils;

import com.mk.model.Combat;

import java.util.ArrayList;

public class NarrationGenerator {

    public static String generateNarration(ArrayList<Combat.CombatEvent> eventos, String playerName, String oponnentName, String playerFighter, String oponnentFighter) {
        StringBuilder sb = new StringBuilder();

        // Datos del combate
        String player1Name = playerName;
        String player1Character = playerFighter;
        String player2Name = oponnentName;
        String player2Character = oponnentFighter;

        sb.append("🎬 **INSTRUCCIONES PARA NARRACIÓN ÉPICA DE MORTAL KOMBAT** 🎬\n\n");

        sb.append("**CONTEXTO DEL COMBATE:**\n");
        sb.append("⚔️ Luchador 1: ").append(player1Name).append(" empuñando el poder de ").append(player1Character).append("\n");
        sb.append("⚔️ Luchador 2: ").append(player2Name).append(" canalizando la furia de ").append(player2Character).append("\n\n");

        sb.append("**TU ROL:** Eres el comentarista más carismático y legendario del universo Mortal Kombat. ");
        sb.append("Piensa en una mezcla entre un locutor de lucha libre, un narrador épico de fantasía y un comediante con timing perfecto.\n\n");

        sb.append("**ESTILO DE NARRACIÓN REQUERIDO:**\n");
        sb.append("🔥 NO narres evento por evento como una lista aburrida\n");
        sb.append("🔥 CREA una historia épica y fluida que conecte todos los eventos\n");
        sb.append("🔥 CONSTRUYE tensión dramática, momentos de suspense y explosiones de acción\n");
        sb.append("🔥 USA humor inteligente, sarcasmo cuando sea apropiado, y referencias del universo MK\n");
        sb.append("🔥 VARIA el tono: desde suspenso hasta euforia, desde drama hasta comedia\n");
        sb.append("🔥 INCLUYE frases icónicas como 'FINISH HIM!', 'FLAWLESS VICTORY!', 'BRUTALITY!'\n");
        sb.append("🔥 DESCRIBE la personalidad y motivaciones de cada luchador durante el combate\n");
        sb.append("🔥 CREA momentum narrativo - identifica rachas ganadoras, momentos críticos, remontadas épicas\n\n");

        sb.append("**TÉCNICAS NARRATIVAS A USAR:**\n");
        sb.append("• Identifica ARCOS NARRATIVOS en los eventos (inicio dominante, remontada, final épico)\n");
        sb.append("• Conecta eventos consecutivos con transiciones cinematográficas\n");
        sb.append("• Usa metáforas y analogías divertidas (ej: 'golpea como si estuviera preparando una milanesa')\n");
        sb.append("• Crea personalidades para los luchadores basadas en sus acciones\n");
        sb.append("• Incluye reacciones del 'público' imaginario\n");
        sb.append("• Usa onomatopeyas y efectos de sonido épicos (¡CRACK!, ¡BOOM!, ¡SLASH!)\n\n");

        sb.append("**ELEMENTOS DE HUMOR A INCLUIR:**\n");
        sb.append("• Juegos de palabras con los nombres de los jugadores\n");
        sb.append("• Referencias a comida si el nombre lo permite (como 'Milanesa')\n");
        sb.append("• Comentarios sarcásticos sobre fallos consecutivos\n");
        sb.append("• Analogías divertidas con situaciones cotidianas\n");
        sb.append("• Reacciones exageradas a momentos anticlimáticos\n\n");

        sb.append("**ESTRUCTURA NARRATIVA SUGERIDA:**\n");
        sb.append("1. **APERTURA ÉPICA** - Presenta la batalla como un evento legendario\n");
        sb.append("2. **DESARROLLO DRAMÁTICO** - Narra la historia del combate con altibajos\n");
        sb.append("3. **CLÍMAX EXPLOSIVO** - El momento decisivo final\n");
        sb.append("4. **CONCLUSIÓN MEMORABLE** - Celebra al ganador con estilo\n\n");

        sb.append("**EVENTOS DEL COMBATE A NARRAR:**\n");
        for (int i = 0; i < eventos.size(); i++) {
            Combat.CombatEvent e = eventos.get(i);
            sb.append(String.format("[Evento %d] %s\n", i + 1, e.getDescription()));
        }

        sb.append("\n**INSTRUCCIÓN FINAL:**\n");
        sb.append("Toma todos estos eventos y TEJE UNA HISTORIA ÉPICA Y DIVERTIDA. ");
        sb.append("No los narres uno por uno, sino que crea una narrativa que fluya naturalmente, ");
        sb.append("con personalidad, humor, drama y emoción. Imagina que estás narrando para miles ");
        sb.append("de espectadores sedientos de sangre y entretenimiento. ");
        sb.append("No uses emojis ni caracteres especiales fuera del sistema UTF-8");
        sb.append("Genera una narracion breve pero emocionante, limitate a unos 2000 caracteres");
        sb.append("¡HAZ QUE ESTA BATALLA SEA INOLVIDABLE!\n\n");

        sb.append("¡COMIENZA LA NARRACIÓN ÉPICA! (Y humoristica, de forma lowkey, indirecta)");

        return GeminiService.getResponseTo(sb.toString());
    }
}