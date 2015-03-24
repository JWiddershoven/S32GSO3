/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbannerapplicatie;

import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

/**
 *
 * @author Jelle
 */
public class AEXBanner extends javafx.application.Application {

    private String koersen;
    private GraphicsContext gc;

    /**
     * Wijzig de koersen.
     *
     * @param koersen De huidige koersen.
     */
    public void setKoersen(String koersen) {
        this.koersen = koersen;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1000, 100);
        Font font = new Font("Arial", 20);
       
        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, 1000, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.toFront();
        gc = canvas.getGraphicsContext2D();
        gc.setFont(font);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 1000, 100);
        gc.setFill(Color.YELLOW);
        gc.fillText("hallo", 500,20);
        
    }
    
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
