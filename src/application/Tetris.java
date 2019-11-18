package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;


public class Tetris extends Application {

    //Variables

    public static final int MOVE =25;
    public static final int SIZE =25;
    public static int XMAX =12*SIZE;
    public static int YMAX =24*SIZE;
    public static int[][] MESH =new int [XMAX/SIZE][YMAX/SIZE];
    public static Pane groupe =new Pane();
    public static Form object;
    public static Scene scene =new Scene(groupe,XMAX+150,YMAX);
    public static int score = 0;
    public static int top = 0;
    public static boolean game= true;
    public static Form nextObj =Controller.makeRect();
    public static int linesNo = 0;

    //creating scene and start the game
    public static void main(String[] args) {launch(args);}

    @Override
    public void start (Stage stage) throws Exception {

        for (int[] a : MESH) {

            Arrays.fill(a, 0);

        }
        //Creating score and level

        Line line = new Line(XMAX, 0, XMAX, YMAX);
        Text scoretext = new Text("Score: ");
        scoretext.setStyle("-fx-font: 20 arials;");
        scoretext.setY(50);
        scoretext.setX(XMAX + 5);
        Text level = new Text("Lines: ");
        level.setStyle("-fx-font:20 arials;");
        level.setY(100);
        level.setX(XMAX + 5);
        level.setFill(Color.GREEN);
        groupe.getChildren().addAll(scoretext, line, level);

        //creating first block and stage

        Form a = nextObj;
        groupe.getChildren().addAll(a.a, a.b, a.c, a.d);
        moveOnKeyPress(a);
        object = a;
        nextObj = Controller.makeRect();
        stage.setScene(scene);
        stage.setTitle("TETRIS");
        stage.show();

        //timer
        Timer fall = new Timer();
        TimerTask task = new TimerTask() {

            public void run() {
                Platform.runLater(new Runnable() {

                    public void run() {

                        if (object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0 || object.d.getY() == 0)
                            //oyun başladı 1 oldu
                            top++;
                        else
                            top = 0;

                        if (top == 2) {

                            //Game Over
                            Text over = new Text("GAME OVER");
                            over.setFill(Color.RED);
                            over.setStyle("-fx-font: 70 arial; ");
                            over.setY(250);
                            over.setX(10);
                            groupe.getChildren().add(over);
                            game = false;

                        }

                        //exit
                        if (top == 15) {
                            System.exit(0);

                        }

                        if (game) {

                            MoveDown(object);
                            scoretext.setText("Score: " + Integer.toString(score));

                            level.setText("Lines: " +Integer.toString(linesNo));
                        }


                    }
                });


            }

        };


        fall.schedule(task, 0, 300);


    }

    private void moveOnKeyPress(Form form){

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){


                    case RIGHT:
                        Controller.MoveRight(form);
                        break;

                    case DOWN:
                         MoveDown(form);
                         score++;
                         break;

                    case LEFT:
                        Controller.MoveLeft(form);
                        break;

                    case UP:
                        MoveTurn(form);
                        break;

                }


            }
        });


    }


    private void MoveTurn (Form form){

        int f =form.form;
        Rectangle a =form.a;
        Rectangle b =form.b;
        Rectangle c =form.c;
        Rectangle d =form.d;
        switch(form.getName()){

            case "j":
                if (f==1 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2)){

                    MoveRight(form.a);
                    MoveDown(form.a);
                    MoveDown(form.c);
                    MoveLeft(form.c);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f==2 && cB(a,-1,-1) && cB(c,-1,1) && cB(d,-2,2)){

                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f==3 && cB(a, -1, 1) && cB(c, 1, 1) && cB(d, 2, 2)){

                    MoveLeft(form.a);
                    MoveUp(form.a);
                    MoveUp(form.c);
                    MoveRight(form.c);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f==4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 2, -2)){

                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    form.changeForm();
                    break;
                }
                break;

            case "l":
                if (f == 1 && cB(a, 1, -1) && cB(c, 1, 1) && cB(b, 2, 2)) {
                    MoveRight(form.a);
                    MoveDown(form.a);
                    MoveUp(form.c);
                    MoveRight(form.c);
                    MoveUp(form.b);
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveRight(form.b);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, -1, -1) && cB(b, 2, -2) && cB(c, 1, -1)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveRight(form.b);
                    MoveRight(form.b);
                    MoveDown(form.b);
                    MoveDown(form.b);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, 1) && cB(c, -1, -1) && cB(b, -2, -2)) {
                    MoveLeft(form.a);
                    MoveUp(form.a);
                    MoveDown(form.c);
                    MoveLeft(form.c);
                    MoveDown(form.b);
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveLeft(form.b);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(b, -2, 2) && cB(c, -1, 1)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveLeft(form.b);
                    MoveLeft(form.b);
                    MoveUp(form.b);
                    MoveUp(form.b);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    form.changeForm();
                    break;
                }
                break;

            case "o":
                break;
            case "s":
                if (f == 1 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    form.changeForm();
                    break;
                }
                break;

            case "t":
                if (f == 1 && cB(a, 1, 1) && cB(d, -1, -1) && cB(c, -1, 1)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveDown(form.d);
                    MoveLeft(form.d);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, 1, -1) && cB(d, -1, 1) && cB(c, 1, 1)) {
                    MoveRight(form.a);
                    MoveDown(form.a);
                    MoveLeft(form.d);
                    MoveUp(form.d);
                    MoveUp(form.c);
                    MoveRight(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, -1) && cB(d, 1, 1) && cB(c, 1, -1)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, -1, 1) && cB(d, 1, -1) && cB(c, -1, -1)) {
                    MoveLeft(form.a);
                    MoveUp(form.a);
                    MoveRight(form.d);
                    MoveDown(form.d);
                    MoveDown(form.c);
                    MoveLeft(form.c);
                    form.changeForm();
                    break;
                }
                break;

            case "z":
                if (f == 1 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                break;


            case "i":
                if (f == 1 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
                    MoveUp(form.a);
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.a);
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveDown(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
                    MoveDown(form.a);
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.a);
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
                    MoveUp(form.a);
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.a);
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveDown(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
                    MoveDown(form.a);
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.a);
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                break;

            }
    }

    private void RemoveRows(Pane pane){

        ArrayList<Node> rects =new ArrayList<Node>();
        ArrayList<Integer> lines =new ArrayList<Integer>();
        ArrayList<Node> newRects=new ArrayList<Node>();
        int full=0;

        //check which line is full

        for (int i=0;i<MESH[0].length;i++){
            for (int j=0;j< MESH.length; j++){
                if (MESH[j][i]==1)
                    full++;

            }

            if (full==MESH.length)
                lines.add(i+lines.size());
            full=0;

        }

        //deleting the row
        if (lines.size()>0)
            do {
                for (Node node: pane.getChildren()){
                    if (node instanceof Rectangle)
                        rects.add(node);

                }

                score+=50;
                linesNo++;


                //deleting block on row

                for (Node node:rects){
                    Rectangle a =(Rectangle)node;
                    if (a.getY()==lines.get(0)*SIZE){

                        MESH[(int)a.getX()/SIZE][(int)a.getY()/SIZE]=0;
                        pane.getChildren().remove(node);

                    }
                    else
                        newRects.add(node);

                }
                    for (Node node:newRects){
                        Rectangle a= (Rectangle)node;
                        if (a.getY()<lines.get(0)*SIZE) {

                            MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                            a.setY(a.getY() + SIZE);
                        }
                        }
                        lines.remove(0);
                        rects.clear();
                        newRects.clear();

                        for (Node node : pane.getChildren()){
                            if (node instanceof Rectangle)
                                rects.add(node);
                        }

                        for (Node node: rects){
                            Rectangle a=(Rectangle)node;
                            try{
                                MESH[(int)a.getX()/SIZE][(int)a.getY()/SIZE]=1;

                            }   catch (ArrayIndexOutOfBoundsException e){


                            }

                        }
                        rects.clear();

                }while(lines.size()>0);



    }

    private void MoveDown(Rectangle rect){
        if (rect.getY()+MOVE<YMAX)
            rect.setY(rect.getY()+MOVE);
    }
    private void MoveRight(Rectangle rect){
        if (rect.getX()+MOVE<XMAX-SIZE)
            rect.setX(rect.getX()+MOVE);
    }
    private void MoveLeft(Rectangle rect){
        if (rect.getX()-MOVE >= 0)
            rect.setX(rect.getX()-MOVE);
    }
    private void MoveUp(Rectangle rect){
        if (rect.getY()- MOVE >0)
            rect.setY(rect.getY()-MOVE);
    }

    private void MoveDown (Form form){

        //moving if down is full

        if (form.a.getY()==YMAX-SIZE || form.b.getY()==YMAX-SIZE||form.c.getY()==YMAX-SIZE||form.d.getY()==YMAX-SIZE || moveA(form)|| moveB(form)||moveC(form)||moveD(form)){

            MESH[(int)form.a.getX()/SIZE][(int)form.a.getY()/SIZE]=1;
            MESH[(int)form.b.getX()/SIZE][(int)form.b.getY()/SIZE]=1;
            MESH[(int)form.c.getX()/SIZE][(int)form.c.getY()/SIZE]=1;
            MESH[(int)form.d.getX()/SIZE][(int)form.d.getY()/SIZE]=1;
            RemoveRows(groupe);

            //creating a new block and adding to the scene

            Form a =nextObj;
            nextObj= Controller.makeRect();
            object=a;
            groupe.getChildren().addAll(a.a,a.b,a.c,a.d);

            moveOnKeyPress(a);

        }

        //Moving one block down if down is not full

        if (form.a.getY()+MOVE<YMAX && form.b.getY()+MOVE<YMAX  && form.c.getY()+MOVE < YMAX && form.d.getY()+MOVE < YMAX ){

            int movea=MESH[(int)form.a.getX()/SIZE][((int)form.a.getY()/SIZE)+1];
            int moveb=MESH[(int)form.b.getX()/SIZE][((int)form.b.getY()/SIZE)+1];
            int movec=MESH[(int)form.c.getX()/SIZE][((int)form.c.getY()/SIZE)+1];
            int moved=MESH[(int)form.d.getX()/SIZE][((int)form.d.getY()/SIZE)+1];
            //System.out.println((int)form.d.getX()/SIZE);
          //  System.out.println(((int)form.d.getY()/SIZE)+1);
           // System.out.println(MESH[(int)form.d.getX()/SIZE][((int)form.d.getY()/SIZE)+1]);
            System.out.println( MESH [11][22] );
            System.out.println( MESH [11][23] );
            if (movea==0&&movea==moveb&&moveb==movec&&movec==moved){

                form.a.setY(form.a.getY()+MOVE);
                form.b.setY(form.b.getY()+MOVE);
                form.c.setY(form.c.getY()+MOVE);
                form.d.setY(form.d.getY()+MOVE);

            }


        }


    }


    private boolean moveA(Form form){
            return (MESH[(int)form.a.getX()/SIZE][((int)form.a.getY()/SIZE)+1]==1);

    }
    private boolean moveB(Form form){
        return (MESH[(int)form.b.getX()/SIZE][((int)form.b.getY()/SIZE)+1]==1);

    }
    private boolean moveC(Form form){
        return (MESH[(int)form.c.getX()/SIZE][((int)form.c.getY()/SIZE)+1]==1);

    }
    private boolean moveD(Form form){
        return (MESH[(int)form.d.getX()/SIZE][((int)form.d.getY()/SIZE)+1]==1);

    }



    private boolean cB(Rectangle rect, int x, int y){

        boolean xb=false;
        boolean yb=false;


        if (x>=0)
            xb=rect.getX()+x * MOVE<= XMAX-SIZE;
        if (x<0)
            xb=rect.getX()+ x * MOVE >= 0;
        if (y>=0)
            yb=rect.getY()-y*MOVE>0;
        if (y<0)
           yb=rect.getY()+y*MOVE<YMAX;
        return xb&&yb&&MESH[((int)rect.getX()/SIZE)+x][((int)rect.getY()/SIZE)-y]==0;

    }

}


















