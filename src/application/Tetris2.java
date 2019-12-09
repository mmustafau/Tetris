package application;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Tetris2 extends Application {

    public static int M = 20;
    public static int N = 10;
    public int field[][] = new int[20][10];

    public static Pane groupe = new Pane();
    public static Scene scene = new Scene(groupe, 320, 480);

    public Tetris2() throws IOException {
    }

    BufferedImage image = ImageIO.read(getClass().getResource("images/tiles.png"));

    // Image i = SwingFXUtils.toFXImage(img2,null);
    //  ImageView iv= new ImageView(i);


    class Coordinate {
        int x, y;
    }

    ArrayList<Coordinate> a = new ArrayList<Coordinate>();
    ArrayList<Coordinate> b = new ArrayList<Coordinate>();

    ImageView[] piece = new ImageView[4];


    int dx = 0;
    boolean rotate = false;
    int colorNum = 1;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {


        stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if (keyEvent.getCode() == KeyCode.SPACE)
                   // rotate = true;
                    rotate();
                else if (keyEvent.getCode() == KeyCode.RIGHT)
                    move(1);
                else if (keyEvent.getCode() == KeyCode.LEFT)
                    move(-1);
                else if (keyEvent.getCode() == KeyCode.DOWN)
                    dx=0;


            }
        });




        int figures[][] =
                {
                        {1, 3, 5, 7}, // I
                        {2, 4, 5, 7}, // Z
                        {3, 5, 4, 6}, // S
                        {3, 5, 4, 7}, // T
                        {2, 3, 5, 7}, // L
                        {3, 5, 7, 6}, // J
                        {2, 3, 4, 5} // O
                };


        //  groupe.getChildren().addAll(iv);


        int n = 0;


        for (int i = 0; i < 4; i++) {

            Coordinate coordinate = new Coordinate();
            coordinate.x = figures[n][i] % 2;
            coordinate.y = figures[n][i] / 2;
            a.add(coordinate);
        }


        //  < - MOVE -> //




        for (int i = 0; i < 4; i++) {
            BufferedImage img2 = image.getSubimage(0, 0, 18, 18);
            Image im = SwingFXUtils.toFXImage(img2, null);

            piece[i] = new ImageView(im);


            piece[i].setX(a.get(i).x * 18);
            piece[i].setY(a.get(i).y * 18);

            groupe.getChildren().addAll(piece[i]);


        }

/*

        for (int i = 0; i <M ; i++) {
            for (int j = 0; j < N ; j++) {
                if (field[i][j]==0) continue;
                img2 =image.getSubimage(field[i][j]*18,0,18,18);
                Image im = SwingFXUtils.toFXImage(img2,null);
                ImageView iv= new ImageView(im);
                groupe.getChildren().addAll(iv);

            }

        }
*/
        stage.setScene(scene);
        stage.setTitle("tetris in a new way");
        stage.show();

    }

    private void move(int dx) {

        for (int i = 0; i < 4; i++) {

            piece[i].setX(piece[i].getX()+dx*18);
       }


    }

    private void rotate() {
        Coordinate rotCent =new Coordinate();
        rotCent.x= (int) piece[1].getX() ;
        rotCent.y= (int) piece[1].getY();

        for (int i = 0; i < 4; i++) {

            int xx = (int) (piece[i].getY()-rotCent.y);
            int yy = (int) (piece[i].getX()-rotCent.x);

            piece[i].setX(rotCent.x-xx);
            piece[i].setY(rotCent.y+yy);

        }

    }


}



