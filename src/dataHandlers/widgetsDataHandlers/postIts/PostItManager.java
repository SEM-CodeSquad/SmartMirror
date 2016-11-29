package dataHandlers.widgetsDataHandlers.postIts;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("unchecked")
public class PostItManager {
    private GraphicsContext gc;
    private List<Particle>[] particlesLists = new List[12];
    private Image[] images = new Image[12];

    private StackPane[] stackPanes;
    private Canvas[] canvases;
    private TextArea[] textAreas;

    public void setStackPanes(StackPane... args) {
        this.stackPanes = args;
    }

    public void setCanvases(Canvas... args) {
        this.canvases = args;
    }

    public void setTextAreas(TextArea... args) {
        this.textAreas = args;
    }

    public void setImage(int i, String userStyle) {
        File f = new File("resources/" + userStyle + ".png");
        Image image = new Image(f.toURI().toString());
        this.images[i] = image;
    }

    public void generateGraphicalNote(int i, String msg) {
        Platform.runLater(() -> generate(i, msg));
    }

    private void generate(int i, String msg) {
        try {

            gc = canvases[i].getGraphicsContext2D();


            gc.drawImage(images[i], 0, 0);
            disintegrate(this.images[i], i);
            setPostMessage(i, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPostMessage(int i, String msg) {
        String newLine = System.getProperty("line.separator");
        if (msg.length() > 40) textAreas[i].setText(newLine + msg);
        else textAreas[i].setText(newLine + newLine + newLine + msg);
    }

    public void deleteGraphicalNote(int i) {
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(i);
            }
        };
        animationTimer.start();
        textAreas[i].setText("");
    }

    private void disintegrate(Image image, int i) {
        PixelReader pixelReader = image.getPixelReader();
        List<Particle> particles = new ArrayList<>();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = pixelReader.getColor(x, y);

                if (!color.equals(Color.TRANSPARENT)) {
                    Particle p = new Particle(x, y, color);
                    particles.add(p);
                    particlesLists[i] = particles;
                }
            }
        }
    }

    private void update(int i) {
        gc = canvases[i].getGraphicsContext2D();

        gc.clearRect(0, 0, images[i].getWidth(), images[i].getHeight());

        particlesLists[i].removeIf(Particle::isDead);

        particlesLists[i].parallelStream()
                .filter(p -> !p.isActive())
                .forEach(p -> p.activate(new Point2D(-Math.random() * 19, -Math.random() * 15)));

        particlesLists[i].forEach(p -> {
            p.update();
            p.draw(gc);
        });
    }
}
