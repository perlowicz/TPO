package zad1;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;

public class MyGUI {

    private Service s;

    public MyGUI(Service service, String jsonWeather, Double rate1, Double rate2){

        this.s = service;
        JButton jb5 = new JButton("CHANGE");
        JTextField firstArea = new JTextField(service.getKraj());
        JTextField secondArea = new JTextField(service.getMiasto());
        JTextArea weather = new JTextArea();
        JTextField firstRate = new JTextField();
        JTextField secondRate = new JTextField();
        JTextField userCurrency = new JTextField("USD");

        JFrame frame = new JFrame("WEBCLIENT");
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,3));
        panel.add(firstArea);
        panel.add(secondArea);
        panel.add(userCurrency);
        panel.add(jb5);
        panel.add(weather);
        panel.add(firstRate);
        panel.add(secondRate);

        weather.setText("Pogoda dla miasta " + secondArea.getText() + " to: " + s.getWeather(secondArea.getText()));
        firstRate.setText("1 " + s.getCurrency().getCurrencyCode() + " = " + s.getRateFor(userCurrency.getText()) + " " + userCurrency.getText());
        secondRate.setText("1 PLN = " + s.getNBPRate() + " " + s.getCurrency().getCurrencyCode());

        JFXPanel jfxPanel = new JFXPanel();
        panel.add(jfxPanel);

        Platform.runLater(() -> {
            WebView webView = new WebView();
            jfxPanel.setScene(new Scene(webView));
            webView.getEngine().load("http://en.wikipedia.org/wiki/"+s.getMiasto());
        });

        jb5.addActionListener(
                (e) -> {
                    this.s = new Service(firstArea.getText());
                    weather.setText("Pogoda dla miasta " + secondArea.getText() + " to: " + s.getWeather(secondArea.getText()));
                    firstRate.setText("1 " + s.getCurrency().getCurrencyCode() + " = " + s.getRateFor(userCurrency.getText()) + " " + userCurrency.getText());
                    secondRate.setText("1 PLN = " + s.getNBPRate() + " " + s.getCurrency().getCurrencyCode());
                    Platform.runLater(() -> {
                        WebView webView = new WebView();
                        jfxPanel.setScene(new Scene(webView));
                        webView.getEngine().load("http://en.wikipedia.org/wiki/"+s.getMiasto());
                    });
                });

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}