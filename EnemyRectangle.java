package sample;

/**
 * Created by Selkov Alexsandr on 22.02.2015.
 */


import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.shape.Rectangle;

public class EnemyRectangle extends Rectangle {
    private int targetIndex;
    private int x;
    private int y;
    private Gui gui;


    public EnemyRectangle(final Gui gui, double width, double height, int e) {
        this.gui=gui;

        setWidth(width);
        setHeight(height);

        this.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                if (getFill() == javafx.scene.paint.Color.GREEN) {
                    setFill(javafx.scene.paint.Color.RED);
                    gui.setTargetIndex(x+10*y);


                } else {
                    setFill(javafx.scene.paint.Color.GREEN);
                    gui.setTargetIndex(999);

                }
            }

        });

    }
    //������� � �������

    public void setXenemyRect(int x){
        this.x=x;
    }

    public void setYenemyRect(int y){
        this.y=y;

    }



}

