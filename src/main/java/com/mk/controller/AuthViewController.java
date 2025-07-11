package com.mk.controller;

import com.mk.dao.PlayerDAO;
import com.mk.model.Player;
import com.mk.view.AuthView;

import javax.swing.*;
import java.awt.*;
import java.util.EventListener;

public class AuthViewController {

    private static AuthView view;
    private static String USERNAME;
    private static String PASSWORD;
    private static volatile boolean IS_USER_LOGGED = false;

    private Player loggedPlayerInstance;

    public AuthViewController(){
        view = new AuthView();
        view.getErrorLabel().setForeground(Color.RED);

        // LOGIN BUTTON ACTION LISTENER
        view.getLoginButton().addActionListener(e -> {

            // Get the text from fields
            USERNAME = view.getUsernameField().getText();
            PASSWORD = new String(view.getPasswordField().getPassword());

            // If no input show error
            if(isAnyFieldEmpty()){
                view.showError("No puedes dejar ningun campo en blanco");
                view.clearFields();
                return;
            }

            // If credentials are correct login and close window else show error
            if(PlayerDAO.isPasswordCorrect(USERNAME, PASSWORD)){
                view.showMessage("Inicio de sesion exitoso. Espere..");
                IS_USER_LOGGED = true;
                view.clearFields();
                loggedPlayerInstance = PlayerDAO.getPlayerInstance(USERNAME);
                new Timer(3000, event -> {
                    ((Timer) event.getSource()).stop();

                    closeWindow();
                }).start();


                return;
            }else{
                view.showError("Credenciales incorrectas");
                view.clearFields();
                return;
            }
        }); // END LOGIN ACTION LISTENER


        view.getRegisterButton().addActionListener(e -> {
            // Get the text from fields
            USERNAME = view.getUsernameField().getText();
            PASSWORD = new String(view.getPasswordField().getPassword());

            if(isRegisterValid()){
                PlayerDAO.registerUser(USERNAME, PASSWORD);
                view.showMessage("Registrado correctamente");
                view.clearFields();
                return;
            }




        });


    }

    private static boolean isAnyFieldEmpty(){
        return USERNAME.isEmpty() || PASSWORD.isEmpty();
    }

    private static void directLogin(){
        // Directly logs the user in -> Auto login on register
    }

    private static boolean isRegisterValid(){
        if(isAnyFieldEmpty()){
            view.showError("No puedes dejar ningun campo en blanco");
            view.clearFields();
            return false;
        }

        if(PlayerDAO.userExists(USERNAME)){
            view.showError("El usuario ya existe");
            view.clearFields();
            return false;
        }

        if(USERNAME.contains(" ")){
            view.showError("El usuario no puede contener espacios");
            view.clearFields();
            return false;
        }


        return true;
    }

    public boolean isIsUserLogged() {return IS_USER_LOGGED;}


    public Player getLoggedPlayerInstance(){
        return this.loggedPlayerInstance;
    }


    public void closeWindow(){
        view.dispose();
    }


}
