package sample;

/**
 * Created by Selkov Alexsandr on 22.02.2015.
 */


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.JFXPanel;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Gui extends Application {

    /*
    *Класс GUI
    *Все постраение описывается в JAVA коде.
    *Файл FXML не используется
    *Изначально создан в Eclipse
    */
    static public String line = " ";
    public static MyRectangle[] rectMY = new MyRectangle[100];
    public static EnemyRectangle[] rectENEMY = new EnemyRectangle[100];
    public static int saveX;
    public static int saveY;
    public static int saveX1;
    public static int saveY1;
    public static int saveX2;
    public static int saveY2;

    static Object tt = new JFXPanel();
    static TextArea commonChat = new TextArea();
    static TextArea sendingMessage = new TextArea();
    static Button bStart = new Button("Start");
    static Button bsendMessage = new Button("Send");
    static Button fireButton = new Button("Огонь");
    static ToggleGroup group = new ToggleGroup();
    static RadioButton ranking = new RadioButton("Ranking");
    static RadioButton no = new RadioButton("NO");
    static ToggleGroup ships = new ToggleGroup();
    static RadioButton four = new RadioButton("Four 1 pcs.");
    static RadioButton three = new RadioButton("Three 2 pcs.");
    static RadioButton two = new RadioButton("Two 3 pcs.");
    static RadioButton one = new RadioButton("One 4 pcs.");
    static int count2 = 0;
    static int count3 = 0;
    static int count4 = 0;
    static boolean forCircle = false;
    static int oneAmount = 4;
    static int twoAmount = 3;
    static int threeAmount = 2;
    static int fourAmount = 1;
    GridPane mySeaField = new GridPane();
    GridPane myPane = new GridPane();
    GridPane enemySeaField = new GridPane();
    GridPane shipType = new GridPane();
    int i = 0;
    int e = 0;
    int a = 0;

    public static void main(String[] args) throws Exception {

        launch(args);

    }


    @Override
    public void start(Stage primaryStage) throws IOException,
            InterruptedException {


        commonChat.setEditable(false);
        commonChat.selectEndOfNextWord();
        commonChat.setPrefSize(200, 500);
        commonChat.setTooltip(new Tooltip("Чат Окно"));
        commonChat.setWrapText(true);


        try {
            commonChat.setText(commonChat.getText() + "\n" + line);
        } catch (NullPointerException e) {

        }

        GridPane chatPain = new GridPane();
        ranking.setToggleGroup(group);
        no.setToggleGroup(group);
        ranking.setSelected(true);

        four.setToggleGroup(ships);
        three.setToggleGroup(ships);
        two.setToggleGroup(ships);
        one.setToggleGroup(ships);
        one.setSelected(true);


        myPane.setAlignment(Pos.CENTER_LEFT);
        myPane.setHgap(10);
        myPane.setVgap(10);
        myPane.setPadding(new Insets(25, 25, 25, 25));


        mySeaField.setAlignment(Pos.CENTER_LEFT);
        mySeaField.setHgap(10);
        mySeaField.setVgap(10);
        mySeaField.setPadding(new Insets(10, 10, 10, 10));


        enemySeaField.setAlignment(Pos.CENTER_RIGHT);
        enemySeaField.setVgap(10);
        enemySeaField.setHgap(10);
        enemySeaField.setPadding(new Insets(10, 10, 10, 10));


        shipType.setAlignment(Pos.CENTER_LEFT);
        shipType.setPadding(new Insets(0, 0, 0, 0));
        shipType.setHgap(50);
        shipType.add(four, 0, 0, 1, 1);
        shipType.add(three, 1, 0, 1, 1);
        shipType.add(two, 2, 0, 1, 1);
        shipType.add(one, 3, 0, 1, 1);

        myPane.add(mySeaField, 0, 0, 1, 1);
        myPane.add(enemySeaField, 1, 0, 1, 1);
        myPane.add(ranking, 0, 1, 1, 1);
        myPane.add(no, 1, 1, 1, 1);
        myPane.add(shipType, 0, 2, 2, 1);
        myPane.add(fireButton, 0, 3, 1, 1);
        myPane.add(commonChat, 0, 11, 2, 1);
        myPane.add(sendingMessage, 0, 12, 2, 1);
        myPane.add(bsendMessage, 0, 13, 1, 1);
        myPane.add(bStart, 0, 14, 5, 1);

        ranking.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                if (ranking.isSelected()) {
                    four.setDisable(false);
                    three.setDisable(false);
                    two.setDisable(false);
                    one.setDisable(false);
                } else if (!ranking.isSelected()) {
                    four.setDisable(true);
                    three.setDisable(true);
                    two.setDisable(true);
                    one.setDisable(true);
                }

            }
        });

        bStart.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                // Создание класса Task, существующий для работы с JavaFX
                final StartClientServer taskClSr = new StartClientServer();
                // Делаем слушателя на текстовое свойство

                taskClSr.messageProperty().addListener(
                        new ChangeListener<String>() {

                            @Override
                            public void changed(
                                    ObservableValue<? extends String> observable,
                                    String oldValue, String newValue) {

                                whatDoWhenMessageDiliverd(taskClSr);
                            }
                        });

                Service service = new Service<Void>() {

                    @Override
                    protected Task<Void> createTask() {
                        // TODO Auto-generated method stub
                        return taskClSr;
                    }

                };
                service.start();
            }

        });

        fireButton.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                final SendingTargetCoordinate sendMess = new SendingTargetCoordinate();

                sendMess.messageProperty().addListener(
                        new ChangeListener<String>() {

                            @Override
                            public void changed(
                                    ObservableValue<? extends String> observable,
                                    String oldValue, String newValue) {
                                commonChat.setText(commonChat.getText() + "\n"
                                        + sendMess.getMessage());

                            }
                        });


                Service service = new Service<Void>() {

                    @Override
                    protected Task<Void> createTask() {
                        // TODO Auto-generated method stub
                        return sendMess;
                    }

                };

                service.start();


            }

        });

        bsendMessage.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                final SendingMessage sendMess = new SendingMessage();

                sendMess.messageProperty().addListener(
                        new ChangeListener<String>() {

                            @Override
                            public void changed(
                                    ObservableValue<? extends String> observable,
                                    String oldValue, String newValue) {
                                commonChat.setText(commonChat.getText() + "\n"
                                        + sendMess.getMessage());

                            }
                        });

                Service service = new Service<Void>() {

                    @Override
                    protected Task<Void> createTask() {
                        // TODO Auto-generated method stub
                        return sendMess;
                    }

                };
                service.start();
            }
        });

        one.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                one.setSelected(true);

            }

        });

        two.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                two.setSelected(true);

            }

        });

        three.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                three.setSelected(true);

            }

        });

        four.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                four.setSelected(true);

            }

        });

        makeEnemyAndMyField();


        Scene scene = new Scene(myPane, 700, 700);

        scene.getStylesheets().add(
                getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    //ДАЛЕЕ НАХОДИТСЯ ЭКСПОРТИРУЕМЫЕ ФУНКЦИИ
    private void makeEnemyAndMyField() {
        for (i = 0; i <= 99; ++i) {
            makeOneIterationRectMY(i);
            makeOneIterationRectENEMY(i);

        }
    }

    //makeOneIterationRectENEMY создает одну клетку(квадрат) поля для оппонента
    private void makeOneIterationRectENEMY(int i) {
        rectENEMY[i] = new EnemyRectangle(10, 10, i);
        rectENEMY[i].setFill(Color.GREEN);

        int numLine = (int) (10 - (10 - i * 0.1));
        rectENEMY[i].x = (i - numLine * 10);
        rectENEMY[i].y = numLine;


        enemySeaField.add(rectENEMY[i], (i - numLine * 10), numLine);
    }

    //makeOneIterationRectMY создает одну клетку(квадрат) поля для игрока
    private void makeOneIterationRectMY(int i) {

        rectMY[i] = new MyRectangle(10, 10, i);
        rectMY[i].setFill(Color.GREEN);


        int numLine = (int) (10 - (10 - i * 0.1));


        rectMY[i].x = (i - numLine * 10);
        rectMY[i].y = numLine;

        mySeaField.add(rectMY[i], (i - numLine * 10), numLine);
    }

    private void whatDoWhenMessageDiliverd(StartClientServer taskClSr) {
        try {
            if (taskClSr.getMessage().charAt(0) != '!') {
                setTextInCommonChat(taskClSr);
                //System.out.println("taskClSr.messageProperty().toString().charAt(0) " + taskClSr.messageProperty().toString());
            } else if (taskClSr.getMessage().charAt(0) == '!') {
                markAquamarineRect(taskClSr);
                //System.out.println("n " + n);

            }
        } catch (StringIndexOutOfBoundsException e) {

            System.out.println("А вот ХУЙ ");
        }
    }

    private void markAquamarineRect(StartClientServer taskClSr) {
        //System.out.println("GUI list");

        int dX = Integer.parseInt(taskClSr.getMessage().substring(taskClSr.getMessage().indexOf("$") + 1, taskClSr.getMessage().indexOf("%")));
        int dY = Integer.parseInt(taskClSr.getMessage().substring(taskClSr.getMessage().indexOf("%") + 1, taskClSr.getMessage().indexOf("*")));
        int n = dX + (dY * 10);
        Gui.rectENEMY[n].setFill(Color.AQUAMARINE);
    }

    private void setTextInCommonChat(StartClientServer taskClSr) {
        commonChat.setText(commonChat.getText() + "\n"
                + taskClSr.getMessage());
    }
}

