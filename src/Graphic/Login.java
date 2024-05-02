package Graphic;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.noise.CurvedLineNoiseProducer;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Login {

    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    TextField input1;
    @FXML
    TextField input2;
    @FXML
    TextField input3;
    @FXML
    ImageView imageView;


    public void LoginApp(ActionEvent e) throws IOException {
        String username = input1.getText();
        String userpass = input2.getText();
        String captchainput = input3.getText();

        // Captcha
        Captcha captcha = new Captcha.Builder(200,100)
                .addText()
                .addNoise()
                .addBackground()
                .addNoise(new CurvedLineNoiseProducer())
                .build();

        BufferedImage image = captcha.getImage();
        Image captchaimage = SwingFXUtils.toFXImage(image,null);
        // Show Image In Imageview
        imageView.setImage(captchaimage);

        if ( !captcha.getAnswer().equals( captchainput )) {
            // CAPTCHA verification failed
            // Handle error or show message
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("result.fxml"));
        root = loader.load();

        result resultControl = loader.getController();
        resultControl.Display(username);

        scene = new Scene(root);
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
