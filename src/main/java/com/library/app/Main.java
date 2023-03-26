package com.library.app;

import com.library.app.dialogs.LoginDialog;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginDialog loginDialog = new LoginDialog(null);
                loginDialog.setVisible(true);
                if (loginDialog.isAuthenticated()) {
                    new LibraryFrame().setVisible(true);
                } else {
                    System.exit(0);
                }
            }
        });
    }
}
