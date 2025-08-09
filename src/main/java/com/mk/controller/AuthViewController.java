package com.mk.controller;

import com.mk.dao.PlayerDAO;
import com.mk.model.Player;
import com.mk.view.AuthView;

import javax.swing.*;
import java.awt.*;

/**
 * Controlador que maneja la vista de autenticacion (login/registro de jugadores).
 *
 * Funciones principales:
 *  - Mostrar la ventana de inicio de sesion y registro.
 *  - Validar entradas del usuario (campos vacios, existencia de usuario, etc.).
 *  - Coordinar con PlayerDAO para verificar credenciales o registrar nuevos jugadores.
 *  - Guardar la instancia del jugador que ha iniciado sesion.
 */
public class AuthViewController {

    // Vista de autenticacion
    private static AuthView view;

    // Campos para usuario y contraseña ingresados
    private static String USERNAME;
    private static String PASSWORD;

    // Bandera que indica si el usuario inicio sesion
    private volatile boolean IS_USER_LOGGED = false;

    // Instancia del jugador autenticado
    private Player loggedPlayerInstance;

    /**
     * Constructor: inicializa la vista, configura colores y listeners para login y registro.
     */
    public AuthViewController() {
        view = new AuthView();

        // Colorea el mensaje de error en rojo
        view.getErrorLabel().setForeground(Color.RED);

        // -------------------
        // LISTENER: LOGIN
        // -------------------
        view.getLoginButton().addActionListener(e -> {

            // Obtiene datos de los campos de texto
            USERNAME = view.getUsernameField().getText();
            PASSWORD = new String(view.getPasswordField().getPassword());

            // Valida que no haya campos vacios
            if (isAnyFieldEmpty()) {
                view.showError("No puedes dejar ningun campo en blanco");
                view.clearFields();
                return;
            }

            // Verifica las credenciales con el DAO
            if (PlayerDAO.isPasswordCorrect(USERNAME, PASSWORD)) {
                view.showMessage("Inicio de sesion exitoso. Espere..");
                view.clearFields();

                // Obtiene la instancia del jugador desde la base de datos
                loggedPlayerInstance = PlayerDAO.getPlayerInstance(USERNAME);

                // Muestra dialogo de confirmacion
                view.showSuccessLoginDialog();

                // Marca que ya esta logueado
                IS_USER_LOGGED = true;

                // Usa un Timer para cerrar la ventana despues de 300 ms
                new Timer(300, event -> {
                    ((Timer) event.getSource()).stop();
                    closeWindow();
                }).start();
                return;
            } else {
                // Credenciales incorrectas
                view.showError("Credenciales incorrectas");
                view.clearFields();
                return;
            }
        }); // END LOGIN LISTENER

        // -------------------
        // LISTENER: REGISTER
        // -------------------
        view.getRegisterButton().addActionListener(e -> {
            // Obtiene datos de los campos
            USERNAME = view.getUsernameField().getText();
            PASSWORD = new String(view.getPasswordField().getPassword());

            // Si la validacion es correcta, registra el usuario
            if (isRegisterValid()) {
                PlayerDAO.registerUser(USERNAME, PASSWORD);
                view.showMessage("Registrado correctamente");
                view.clearFields();
                return;
            }
        });
    }

    /**
     * Comprueba si alguno de los campos usuario/contraseña esta vacio.
     */
    private static boolean isAnyFieldEmpty() {
        return USERNAME.isEmpty() || PASSWORD.isEmpty();
    }

    /**
     * Valida si el registro es posible:
     *  - No hay campos vacios.
     *  - El usuario no existe ya en la BD.
     *  - El nombre de usuario no contiene espacios.
     */
    private static boolean isRegisterValid() {
        if (isAnyFieldEmpty()) {
            view.showError("No puedes dejar ningun campo en blanco");
            view.clearFields();
            return false;
        }

        if (PlayerDAO.userExists(USERNAME)) {
            view.showError("El usuario ya existe");
            view.clearFields();
            return false;
        }

        if (USERNAME.contains(" ")) {
            view.showError("El usuario no puede contener espacios");
            view.clearFields();
            return false;
        }

        return true;
    }

    /**
     * Retorna true si el usuario ya inicio sesion.
     */
    public boolean isUserLogged() {
        return IS_USER_LOGGED;
    }

    /**
     * Retorna la instancia del jugador autenticado.
     */
    public Player getLoggedPlayerInstance() {
        return this.loggedPlayerInstance;
    }

    /**
     * Cierra la ventana de autenticacion.
     */
    public void closeWindow() {
        view.dispose();
    }
}
