package ee.ttu.java.cookie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.File;


public class CookieGUI extends Application {

    
    public static final int WIDTH = 1200;
  
    public static final int HEIGTH = 800;
   
    public static final int PADDING = 20;

  
    public static final double DECREASE = 0.8;

    
    public static final int SMALL = 300;

   
    public static final int NORM = 375;

   
    public static final int TEN = 10;
  
    public static final int THIRTY = 30;

    
    public static final int HUNDRED = 100;
  
    public static final int FIFTY = 50;
   
    public static final int SIZE580 = 580;
    
    public static final int SIZE380 = 380;
 
    public static final int SIZE760 = 760;

    private static MediaPlayer player;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        CookieGame game = new CookieGame();
        primaryStage.setTitle("Кофе кликер");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        StackPane backPanel = new StackPane();
        backPanel.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        backPanel.setStyle("-fx-background-color: transparent");
        HBox split = new HBox();
        split.setAlignment(Pos.CENTER);
        split.setStyle("-fx-background-color: rgba(1,1,1,0);");

        Button exit = exit(primaryStage);

        Media media = new Media(getClass().getClassLoader().getResource("ee/ttu/java/cookie/BackgroundMusic.mp3").toString());
        player = new MediaPlayer(media);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();

        Label cookies = new Label("0" + "\n" + "Lines of (code);");
        cookies.setStyle("-fx-font-size: 50 px;" + "-fx-font-weight: bold;"
                + "-fx-background-color: rgba(1,1,1,0);" + "-fx-text-fill: gold;"
                + "-fx-font-family: Impact;" + "-fx-text-alignment: center;");

        javafx.scene.control.Button buyCursor = new Button("Купит курсор");

        javafx.scene.control.Button buyClicker = new Button("Купит клик");
        mouseEffects(buyClicker);

        Image image = background();
        ImageView imageView2 = javaClick(game, buyClicker, buyCursor, cookies);
        ImagePattern pattern = new ImagePattern(image);

        StackPane leftCanvas = new StackPane();
        leftCanvas.setPrefSize(SIZE760, SIZE580);
        leftCanvas.setStyle("-fx-background-color: rgba(1,1,1,0);");

        leftCanvas.getChildren().addAll(imageView2);
        leftCanvas.setAlignment(Pos.CENTER);

        VBox leftSplit = new VBox();
        leftSplit.setAlignment(Pos.CENTER);
        leftSplit.getChildren().addAll(cookies, leftCanvas);
        VBox rightSplit = new VBox();
        rightSplit.setAlignment(Pos.CENTER);

        VBox up = new VBox();
        up.setStyle("-fx-background-color: rgba(255,215,0,0.5);");
        up.setPrefSize(SIZE580, SIZE380);
        up.setPadding(new Insets(THIRTY, TEN, THIRTY, TEN));
        up.setAlignment(Pos.CENTER_RIGHT);

        Label price = new Label("Цена:");
        price.setStyle("-fx-font-size: 45 px;" + "-fx-background-color: rgba(1,1,1,0);"
                + "-fx-text-fill: rgba(33,33,33,0.91);" + "-fx-font-family: Impact;");

        Label cursorInf = new Label(String.format(
                "%8s\t%4d\t\t%5d", "Курсор", game.getCursorCount(), game.getCursorPrice()));
        cursorInf.setStyle("-fx-font-size: 45 px;" + "-fx-background-color: rgba(1,1,1,0);"
                + "-fx-text-fill: rgba(33,33,33,0.91);" + "-fx-font-family: Impact;"
                + "-fx-pref-width: 580;");

        Label clickerInf = new Label(String.format(
                "%8s\t%4d\t\t%5d", "Клики:", game.getClickerCount(), game.getClickerPrice()));
        clickerInf.setStyle("-fx-font-size: 45 px;" + "-fx-background-color: rgba(1,1,1,0);"
                + "-fx-text-fill: rgba(33,33,33,0.91);" + "-fx-font-family: Impact;"
                + "-fx-pref-width: 580;");

        up.getChildren().addAll(price, cursorInf, clickerInf);

        VBox down = new VBox();
        down.setStyle("-fx-background-color: rgba(127,255,212,0.5);");
        down.setPadding(new Insets(THIRTY, TEN, THIRTY, TEN));
        down.setSpacing(TEN);
        down.setPrefSize(SIZE580, SIZE380);
        down.setAlignment(Pos.TOP_CENTER);

        Timeline clickerAction = clickerAction(game, buyClicker, buyCursor, cookies, imageView2);

        buyCursor.setOnMouseClicked(event -> {
            game.buyCursor();
            cookies.setText(game.getCookies() + "\n" + "Lines of (code);");
            cursorInf.setText(String.format(
                    "%8s\t%4d\t\t%5d", "Cursors:", game.getCursorCount(), game.getCursorPrice()));
            if (!game.canBuyCursor()) {
                buyCursor.setVisible(false);
            }
            if (!game.canBuyClicker()) {
                buyClicker.setVisible(false);
            }
        });

        buyClicker.setOnMouseClicked(event -> {
            game.buyClicker();
            clickerAction.stop();
            clickerAction.setRate((1.0 / game.getClickerInterval()));
            clickerAction.play();
            cookies.setText(game.getCookies() + "\n" + "Lines of (code);");
            clickerInf.setText(String.format(
                    "%8s\t%4d\t\t%5d", "Clickers:", game.getClickerCount(), game.getClickerPrice()));
            if (!game.canBuyClicker()) {
                buyClicker.setVisible(false);
            }
            if (!game.canBuyCursor()) {
                buyCursor.setVisible(false);
            }
        });

        Button info = info(pattern);

        down.getChildren().addAll(buyCursor, buyClicker);

        rightSplit.getChildren().addAll(up, down);
        split.getChildren().addAll(leftSplit, rightSplit);
        backPanel.getChildren().addAll(split, exit, info);

        primaryStage.setScene(new Scene(backPanel, WIDTH, HEIGTH, pattern));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    private void mouseEffects(Button button) {
        button.managedProperty().bind(button.visibleProperty());
        button.setVisible(false);
        button.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                + "-fx-border-width: 10;" + "-fx-pref-width: 580;"
                + "-fx-font-size: 30 px;" + "-fx-text-fill: gold;"
                + "-fx-font-family: Impact;" + "-fx-font-weight: bold;");
        button.setOnMouseEntered(event -> {
            button.setStyle("-fx-background-color: rgba(33,33,33,1);" + "-fx-border-color: gold;"
                    + "-fx-border-width: 10;" + "-fx-pref-width: 580;"
                    + "-fx-font-size: 30 px;" + "-fx-text-fill: gold;"
                    + "-fx-font-family: Impact;" + "-fx-font-weight: bold;");
        });
        button.setOnMouseExited(event -> {
            button.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                    + "-fx-border-width: 10;" + "-fx-pref-width: 580;"
                    + "-fx-font-size: 30 px;" + "-fx-text-fill: gold;"
                    + "-fx-font-family: Impact;" + "-fx-font-weight: bold;");
        });
    }


    private Image background() {
        return image("ee/ttu/java/cookie/Background.jpg");
    }


    private ImageView javaClick(CookieGame game, Button buyClicker, Button buyCursor, Label cookies) {
        Image image2 = image("ee/ttu/java/cookie/JavaImg.png");
        ImageView imageView2 = new ImageView(image2);

        imageView2.setFitHeight(NORM);
        imageView2.setFitWidth(NORM);
        imageView2.setOnMouseClicked(event -> {
            game.click();
            if (game.canBuyClicker()) {
                buyClicker.setVisible(true);
            }
            if (game.canBuyCursor()) {
                buyCursor.setVisible(true);
            }
            cookies.setText(game.getCookies() + "\n" + "Lines of (code);");
            Timeline timeline2 = new Timeline(new KeyFrame(
                    Duration.millis(HUNDRED),
                    ae -> {
                        imageView2.setFitHeight(NORM);
                        imageView2.setFitWidth(NORM);
                    }));
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(FIFTY),
                    ae -> {
                        imageView2.setFitHeight(SMALL);
                        imageView2.setFitWidth(SMALL);
                    }));
            timeline.play();
            timeline2.play();
        });
        return imageView2;
    }


    private Image image(String pathname) {
        javafx.scene.image.Image image = new Image(getClass().getClassLoader().getResource(pathname).toString());
        return image;
    }

    private Timeline clickerAction(
            CookieGame game, Button buyClicker, Button buyCursor, Label cookies, ImageView imageView2) {
        Timeline clickerAction = new Timeline(new KeyFrame(
                Duration.millis(1),
                ae -> {
                    if (game.getClickerCount() != 0) {
                        game.clickerAction();
                        if (game.canBuyClicker()) {
                            buyClicker.setVisible(true);
                        }
                        if (game.canBuyCursor()) {
                            buyCursor.setVisible(true);
                        }
                        cookies.setText(game.getCookies() + "\n" + "Lines of (code);");

                        Timeline timeline2 = new Timeline(new KeyFrame(
                                Duration.millis(HUNDRED),
                                e -> {
                                    imageView2.setFitHeight(NORM);
                                    imageView2.setFitWidth(NORM);
                                }));
                        Timeline timeline = new Timeline(new KeyFrame(
                                Duration.millis(FIFTY),
                                e -> {
                                    imageView2.setFitHeight(SMALL);
                                    imageView2.setFitWidth(SMALL);
                                }));
                        timeline.play();
                        timeline2.play();
                    }
                }));
        clickerAction.setCycleCount(Timeline.INDEFINITE);
        clickerAction.play();
        return clickerAction;
    }

    /**
     * Creates a new window.
     * @param pattern background
     */
    private void infoStage(ImagePattern pattern) {
        Stage infostage = new Stage();
        infostage.initStyle(StageStyle.UNDECORATED);
        StackPane infoscreen = new StackPane();
        infoscreen.setStyle("-fx-background-color: rgba(33,33,33,0.77)");
        infoscreen.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        infostage.setTitle("Информация");
        infostage.setResizable(false);

        Label infolabel = new Label("Курсовая работа");
        infolabel.setStyle("-fx-font-size: 45 px;" + "-fx-background-color: rgba(1,1,1,0);"
                + "-fx-text-fill: gold;" + "-fx-font-family: Impact;" + "-fx-text-alignment: center;");
        StackPane.setAlignment(infolabel, Pos.CENTER);

        Button close = new Button("Закрыть");
        close.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                + "-fx-font-size: 25 px;" + "-fx-text-fill: gold;"
                + "-fx-font-family: Impact;" + "-fx-text-alignment: center;");
        StackPane.setAlignment(close, Pos.BOTTOM_LEFT);
        close.setOnMouseEntered(eventclose -> {
            close.setStyle("-fx-background-color: rgba(33,33,33,1);" + "-fx-border-color: gold;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: gold;" + "-fx-font-family: Impact;");
        });
        close.setOnMouseExited(eventclose -> {
            close.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: gold;" + "-fx-font-family: Impact;");
        });
        close.setOnMouseClicked(eventclose -> infostage.close());

        infoscreen.getChildren().addAll(close, infolabel);
        infostage.setScene(new Scene(infoscreen, WIDTH * DECREASE, HEIGTH * DECREASE, pattern));
        infostage.show();
    }


    private Button exit(Stage primaryStage) {
        Button exit = new Button("Выход");
        exit.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                + "-fx-font-size: 25 px;" + "-fx-text-fill: gold;"
                + "-fx-font-family: Impact;" + "-fx-text-alignment: center;");
        StackPane.setAlignment(exit, Pos.TOP_LEFT);
        exit.setOnMouseEntered(event -> {
            exit.setStyle("-fx-background-color: rgba(33,33,33,1);" + "-fx-border-color: gold;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: gold;" + "-fx-font-family: Impact;");
        });
        exit.setOnMouseExited(event -> {
            exit.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: gold;" + "-fx-font-family: Impact;");
        });
        exit.setOnMouseClicked(event -> primaryStage.close());
        return exit;
    }


    private Button info(ImagePattern pattern) {
        Button info = new Button("Информация");
        info.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                + "-fx-font-size: 25 px;" + "-fx-text-fill: gold;"
                + "-fx-font-family: Impact;" + "-fx-text-alignment: center;");
        StackPane.setAlignment(info, Pos.BOTTOM_LEFT);
        info.setOnMouseEntered(event -> {
            info.setStyle("-fx-background-color: rgba(33,33,33,1);" + "-fx-border-color: gold;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: gold;" + "-fx-font-family: Impact;");
        });
        info.setOnMouseExited(event -> {
            info.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: gold;" + "-fx-font-family: Impact;");
        });
        info.setOnMouseClicked(event -> infoStage(pattern));
        return info;
    }
}
