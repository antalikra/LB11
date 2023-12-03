package com.example.LB11;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MyCalc extends Application {

    private Text displayText;
    private final StringBuilder inputBuffer = new StringBuilder();

    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox root = new VBox();
        root.setStyle("-fx-background-color: #EDF0F2");

        Scene scene = new Scene(root);

        root.getChildren().add(configureMenu());

        HBox box = configureResultView();
        root.getChildren().add(box);
        VBox.setMargin(box, new Insets(20, 10, 2, 10));

        root.getChildren().add(configureButtons());

        primaryStage.setTitle("My calc");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class MyButton extends Button {
        MyButton(String text) {
            super(text);
            this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            this.setOnAction(e -> handleButtonClick(text));
        }
    }

    private GridPane configureButtons() {
        GridPane pane = new GridPane();

        pane.add(new MyButton("MC"), 0, 0);
        pane.add(new MyButton("MR"), 1, 0);
        pane.add(new MyButton("MS"), 2, 0);
        pane.add(new MyButton("M+"), 3, 0);
        pane.add(new MyButton("M-"), 4, 0);

        pane.add(new MyButton("<-"), 0, 1);
        pane.add(new MyButton("CE"), 1, 1);
        pane.add(new MyButton("C"), 2, 1);
        pane.add(new MyButton("±"), 3, 1);
        pane.add(new MyButton("√"), 4, 1);

        pane.add(new MyButton("7"), 0, 2);
        pane.add(new MyButton("8"), 1, 2);
        pane.add(new MyButton("9"), 2, 2);
        pane.add(new MyButton("/"), 3, 2);
        pane.add(new MyButton("%"), 4, 2);

        pane.add(new MyButton("4"), 0, 3);
        pane.add(new MyButton("5"), 1, 3);
        pane.add(new MyButton("6"), 2, 3);
        pane.add(new MyButton("*"), 3, 3);
        pane.add(new MyButton("1/х"), 4, 3);

        pane.add(new MyButton("1"), 0, 4);
        pane.add(new MyButton("2"), 1, 4);
        pane.add(new MyButton("3"), 2, 4);
        pane.add(new MyButton("-"), 3, 4);
        pane.add(new MyButton("="), 4, 4, 1, 2);

        pane.add(new MyButton("0"), 0, 5, 2, 1);
        pane.add(new MyButton(","), 2, 5);
        pane.add(new MyButton("+"), 3, 5);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setFillWidth(true);
        cc.setHgrow(Priority.ALWAYS);
        pane.getColumnConstraints().addAll(cc, cc, cc, cc, cc);

        pane.setStyle("-fx-padding: 2 10 4 10");
        pane.setHgap(6);
        pane.setVgap(6);
        return pane;
    }

    private HBox configureResultView() {
        HBox box = new HBox();

        box.setStyle("-fx-border-style: solid inside;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 3;" +
                "-fx-border-color: gray;" +
                "-fx-padding: 25 2 6 30;" +
                "-fx-background-color: linear-gradient(to bottom," +
                " #d3eefb 0%,#f4f8f9 59%);");

        displayText = new Text("0");
        displayText.setTextAlignment(TextAlignment.RIGHT);
        displayText.setFont(new Font(40));

        box.getChildren().add(displayText);
        box.setAlignment(Pos.BOTTOM_RIGHT);

        return box;
    }

    private MenuBar configureMenu() {
        MenuBar bar = new MenuBar();

        Menu viewMenu = new Menu("_Вид");

        Menu calculatorSubMenu = new Menu("Калькулятор");

        MenuItem standardMenuItem = new MenuItem("Стандартний");
        MenuItem scientificMenuItem = new MenuItem("Інженерний");
        MenuItem programmerMenuItem = new MenuItem("Програміст");

        calculatorSubMenu.getItems().addAll(standardMenuItem, scientificMenuItem, programmerMenuItem);

        viewMenu.getItems().add(calculatorSubMenu);

        Menu infoMenu = new Menu("_Інформація");

        MenuItem aboutMenuItem = new MenuItem("Про програму");
        MenuItem helpMenuItem = new MenuItem("Допомога");

        infoMenu.getItems().addAll(aboutMenuItem, helpMenuItem);

        bar.getMenus().addAll(viewMenu, infoMenu);

        return bar;
    }

    private void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "=":
                System.out.println(inputBuffer.toString()); // Додайте цей рядок
                evaluateExpression();
                break;
            case "C":
                clearDisplay();
                break;
            default:
                appendToInputBuffer(buttonText);
                break;
        }
    }

    private void evaluateExpression() {
        try {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
            Object result = engine.eval(displayText.getText());

            displayText.setText(result.toString());
            inputBuffer.setLength(0);
            inputBuffer.append(result.toString());
        } catch (Exception e) {
            displayText.setText("Error");
        }
    }

    private void clearDisplay() {
        inputBuffer.setLength(0);
        displayText.setText("0");
    }

    private void appendToInputBuffer(String text) {
        inputBuffer.append(text);
        displayText.setText(inputBuffer.toString());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
