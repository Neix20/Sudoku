package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;

public class Sudoku extends Application {

    private TextField tf[][];
    private int data[][],noRep[],ptr;
    private HBox hBox;
    private VBox vBox,vBox2,vBoxMain;
    private GridPane gPane[],mainGPane;
    private Button b[];

    public Sudoku(){
        this.tf = new TextField[9][9];
        this.data = new int[9][9];
        noRep = new int[9];
        ptr = 0;

        this.hBox=new HBox();

        this.vBox=new VBox(20);
        vBox.setAlignment(Pos.CENTER_LEFT);

        this.vBox2 = new VBox(20);
        vBox2.setAlignment(Pos.CENTER_LEFT);

        this.vBoxMain = new VBox(20);
        vBoxMain.setAlignment(Pos.CENTER);


        this.gPane = new GridPane[9];
        this.mainGPane=new GridPane();

        for(int i=0;i<tf.length;i++){
            for(int j=0;j<tf.length;j++){
                tf[i][j] = new TextField();
                tf[i][j].setPrefSize(30,30);
            }
        }

        for(int i=0;i<gPane.length;i++){
            gPane[i] = new GridPane();
            gPane[i].setAlignment(Pos.CENTER);
            gPane[i].setPadding(new Insets(10, 10, 10, 10));
            gPane[i].setVgap(5);
            gPane[i].setHgap(5);
        }

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                for(int k=0;k<3;k++){
                    for(int l=0;l<3;l++){
                        gPane[3*i+j].add(tf[k+3*i][l+3*j],k,l);
                    }
                }
            }
        }

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                mainGPane.add(gPane[j+3*i],j,i);
            }
        }
    }

    public void generateRandomSudoku(){
        int numOfFilledBox = 0,num;
        boolean flag;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                for(int k=0;k<3;k++){
                    for(int l=0;l<3;l++){
                        if(numOfFilledBox<9){
                            do{
                                flag = false;
                                num = (int)(Math.random()*9+1);
                                for(int z=0;z<ptr;z++){
                                    if(num == noRep[z])
                                        flag=true;
                                }
                            }while(flag);
                            noRep[ptr++]=num;
                            data[k+3*i][l+3*j]=noRep[ptr-1];
                        }
                        numOfFilledBox++;
                    }
                }
            }
        }
    }

    public void solution(){
        solveSudoku ss = new solveSudoku(data);
        ss.solveSudoku(data,9);
    }

    public void generateDifficulty(String str){
        double x=0;
        switch(str.toLowerCase()){
            case "easy":x=0.5;break;
            case "hard":x=0.8;break;
        }
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                tf[i][j].setEditable(true);
                tf[i][j].setStyle("-fx-control-inner-background: white");
                boolean prob = Math.random()<x;
                int num = (prob)?0:data[i][j];
                tf[i][j].setText(num+"");
                if(!prob)
                    tf[i][j].setEditable(false);
            }
        }
    }

    public void retrieve(){
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                data[i][j]=(tf[i][j].getText().isEmpty())?0:Integer.valueOf(tf[i][j].getText());
    }

    public void createNew(){
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++){
                tf[i][j].setText("");
                tf[i][j].setEditable(true);
                tf[i][j].setStyle("-fx-control-inner-background: white");
            }
    }

    @Override
    public void start(Stage stage) throws Exception {
        generateRandomSudoku();
        solution();
        generateDifficulty("Easy");
        retrieve();

        b = new Button[7];
        for(int i=0;i<b.length;i++){
            b[i] = new Button();
        }

        b[0].setText("New");
        b[0].setOnAction(e -> {
            createNew();
        });

        b[1].setText("Retrieve");
        b[1].setOnAction(e -> {
            retrieve();
        });

        b[2].setText("Solve");
        b[2].setOnAction(e -> {
            solution();
            for(int i=0;i<9;i++)
                for(int j=0;j<9;j++) {
                    tf[i][j].setStyle("-fx-control-inner-background: white");
                    tf[i][j].setText(data[i][j] + "");
                }
        });
        vBox2.getChildren().addAll(new Label("For solving Sudoku: "),b[0],b[1],b[2]);

        b[3].setText("Easy");
        b[3].setOnAction(e -> {
            generateDifficulty(e.getSource().toString().substring(e.getSource().toString().length()-5,e.getSource().toString().length()-1));
        });

        b[4].setText("Hard");
        b[4].setOnAction(e -> {
            generateDifficulty(e.getSource().toString().substring(e.getSource().toString().length()-5,e.getSource().toString().length()-1));
        });

        b[5].setText("Hint");
        b[5].setOnAction(e -> {
            int temX=0,temY=0;
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    if(data[i][j]==0){
                        temX=i;
                        temY=j;
                        break;
                    }
                }
            }
            solution();
            tf[temX][temY].setText(data[temX][temY]+"");
            tf[temX][temY].setStyle("-fx-control-inner-background: yellow");
            retrieve();
        });

        vBox.getChildren().addAll(new Label("Difficulty: "),b[3],b[4],b[5]);

        vBoxMain.getChildren().addAll(vBox,vBox2);
        hBox.getChildren().addAll(mainGPane,vBoxMain);

        stage.setTitle("Simple Sudoku");
        stage.setScene(new Scene(hBox,600,400));
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
