package com.mk.controller;

import com.mk.dao.FighterDAO;
import com.mk.model.Player;
import com.mk.view.FighterSelectionView;
import com.mk.model.Fighter;

import javax.swing.*;

/**
 * Controlador que gestiona la seleccion de luchador por parte de un jugador.

 * Se encarga de:
 *  - Obtener la lista de luchadores desde la base de datos.
 *  - Mostrar la vista de seleccion (FighterSelectionView).
 *  - Responder a los botones de navegacion e confirmacion.
 *  - Asignar el luchador elegido al jugador.
 */
public class FighterSelectionController {

    // Vista que muestra los luchadores y permite desplazarse entre ellos
    private final FighterSelectionView view;

    // Lista de luchadores disponibles obtenidos del DAO
    private final Fighter[] fighters = FighterDAO.getAllObjects().toArray(new Fighter[0]);

    // Jugador al que se le asignara el luchador seleccionado
    private Player plr;

    /**
     * Constructor que recibe al jugador que esta eligiendo luchador.
     * Muestra la ventana de seleccion como modal (bloquea la ejecucion hasta que se cierra).
     */
    public FighterSelectionController(Player player) {
        // Inicializa la vista con la lista de luchadores
        this.view = new FighterSelectionView(fighters);

        // Guarda referencia al jugador
        this.plr = player;

        // Configura los listeners de los botones
        initListeners();

        // Hace que la vista sea modal (bloquea hasta que el usuario decida)
        view.setModal(true);

        // Muestra la ventana
        view.setVisible(true);
    }

    /**
     * Configura las acciones de los botones de la vista.
     *
     * Boton Izquierda: muestra el luchador anterior.
     * Boton Derecha: muestra el luchador siguiente.
     * Boton Confirmar: asigna el luchador actual al jugador y cierra la ventana.
     */
    private void initListeners() {
        // Boton para retroceder en la lista de luchadores
        view.getLeftButton().addActionListener(e -> {
            view.previousFighter();
        });

        // Boton para avanzar en la lista de luchadores
        view.getRightButton().addActionListener(e -> {
            view.nextFighter();
        });

        // Boton para confirmar la seleccion actual
        view.getConfirmButton().addActionListener(e -> {
            // Asigna el luchador seleccionado al jugador
            plr.setSelectedFighter(view.getCurrentFighter());

            // Cierra la ventana
            closeWindow();
        });
    }

    /**
     * Cierra la ventana de seleccion.
     */
    private void closeWindow() {
        view.dispose();
    }
}
