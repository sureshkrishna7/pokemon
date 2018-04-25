package controller.States;

import java.awt.Point;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

/**
 * This view contains an animation of a running girl thought the desert. It can
 * be added to any Pane elsewhere and started with the instance method
 * 
 * public void animate()
 * 
 * @author Rick Mercer
 */
public class CobvilleTown extends Canvas implements IState {

  private Image character, background;
  private GraphicsContext g2D;
  private Timeline timeline;
  private Point playerLocation;
  private double lastValidPlayerDX;
  private double lastValidPlayerDY;
  private double playerPixelsFromTopBoundary;
  private double playerPixelsFromLeftBoundary;
  private double playerPixelsFromBottomBoundary;
  private int closeToTopPictureBounderSteps;
  private int closeToLeftPictureBounderSteps;
  private int closeToBottomPictureBounderSteps;

  private int tic = 0;
  double sx, sy, sw, sh, dx, dy, dw, dh;
  private String drawPlayerOverOrUnder;
  private KeyCode keyCode;
  private final double cameraViewSize = 20 * 16;

  public CobvilleTown(Point point, Image mapBackground) {
    this.setWidth(800);
    this.setHeight(800);
    playerLocation = point;
    closeToTopPictureBounderSteps = 0;
    closeToLeftPictureBounderSteps = 0;
    closeToBottomPictureBounderSteps = 0;

    // will only set when close to border
    playerPixelsFromTopBoundary = 0;
    playerPixelsFromLeftBoundary = 0;
    playerPixelsFromBottomBoundary = 0;

    // defaults, will be changed when movePlayer() is called
    keyCode = KeyCode.UP;
    drawPlayerOverOrUnder = "over";

    // Create both images and draw both when the controller instructs
    // spritsheet contains 6 sub images
    character = new Image("file:src/images/Game_Boy_Advance - Pokemon_FireRed_LeafGreen - RivalBlueGreenGary.png",
        false);
    background = mapBackground;
    g2D = this.getGraphicsContext2D();

    // Create a TimeLine that call AnimateStarter.handle every 100ms
    // class AnimateStarter has two method stubs you have to complete.
    timeline = new Timeline(new KeyFrame(Duration.millis(90), new AnimateStarter()));
    timeline.setCycleCount(Animation.INDEFINITE);
  }

  // Call this from the Application to begin the spritesheet animation
  public void animate() {

  }

  public void setBackGroundImage(Image changeOfMap) {
    g2D.clearRect(0, 0, 800, 800);
    background = changeOfMap;
  }
  
  public String getName() {
    return "cobTown";
  }

  public void movePlayer(KeyCode code, String overOrUnder) {
    keyCode = code;
    drawPlayerOverOrUnder = overOrUnder;
    tic = 0;
    timeline.play();
  }

  public boolean isTimelineAnimating() {
    return timeline.getStatus() == Status.RUNNING;
  }

  private class AnimateStarter implements EventHandler<ActionEvent> {

    public AnimateStarter() {
      /*
       * The images to draw are know as spritesheet (6 images) and dirt (the
       * background) Use method drawImage with 9 arguments: drawImage(theImage, sx,
       * sy, sw, sh, dx, dy, dw, dh) sx the source rectangle's X coordinate position.
       * sy the source rectangle's Y coordinate position. sw the source rectangle's
       * width. sh the source rectangle's height. dx the destination rectangle's X
       * coordinate position. dy the destination rectangle's Y coordinate position. dw
       * the destination rectangle's width. dh the destination rectangle's height.
       */

      lastValidPlayerDX = ((playerLocation.y) * 16);
      lastValidPlayerDY = ((playerLocation.y) * 16) - 8;
      sy = 5;
      sx = 50;
      sw = 17;
      sh = 21;

      dx = ((playerLocation.y) * 16); // LEFT TO RIGHT, y = col
      dy = ((playerLocation.x) * 16) - 8; // UP AND DOWN, x = row
      dw = 17;
      dh = 21;

      g2D.drawImage(background, dx - (cameraViewSize / 2), dy - (cameraViewSize / 2), cameraViewSize, cameraViewSize, 0,
          0, cameraViewSize, cameraViewSize);
      g2D.drawImage(character, sx, sy, sw, sh, cameraViewSize / 2.0, cameraViewSize / 2.0, dw, dh);
    }

    @Override
    // This handle method gets called every so many milliseconds to
    // draw a varying subimage from a spritesheet over the desert dirt.
    public void handle(ActionEvent event) {
      tic++;

      if (KeyCode.UP == keyCode) {
        dy -= (16 / 3.0);
        if (drawPlayerOverOrUnder.equals("over")) {
          // get picture that makes trainer look going north
          if (tic == 1) {
            sx = 128;
            sy = 5;
          } else if (tic == 2) {
            sx = 147;
            sy = 5;
          } else if (tic == 3) {
            sx = 109;
            sy = 5;
          }
        } else {
          sx = 0;
          sy = 0;
        }

      } else if (KeyCode.DOWN == keyCode) {
        dy += (16 / 3.0);
        if (drawPlayerOverOrUnder.equals("over")) {
          // get picture that makes trainer look going south
          if (tic == 1) {
            sx = 69;
            sy = 5;
          } else if (tic == 2) {
            sx = 89;
            sy = 5;
          } else if (tic == 3) {
            sx = 50;
            sy = 5;
          }
        } else {
          sx = 0;
          sy = 0;
        }

      } else if (KeyCode.RIGHT == keyCode) {
        dx += (16 / 3.0);
        if (drawPlayerOverOrUnder.equals("over")) {
          // get picture that makes trainer look going east
          if (tic == 1) {
            sx = 68;
            sy = 29;
          } else if (tic == 2) {
            sx = 89;
            sy = 29;
          } else if (tic == 3) {
            sx = 50;
            sy = 29;
          }
        } else {
          sx = 0;
          sy = 0;
        }

      } else if (KeyCode.LEFT == keyCode) {
        dx -= (16 / 3.0);
        if (drawPlayerOverOrUnder.equals("over")) {
          // get picture that makes trainer look going west
          if (tic == 1) {
            sx = 127;
            sy = 29;
          } else if (tic == 2) {
            sx = 144;
            sy = 29;
          } else if (tic == 3) {
            sx = 107;
            sy = 29;
          }
        } else {
          sx = 0;
          sy = 0;
        }

      } else {
        System.out.println("KeyCode   = " + keyCode);
      }

      // System.out.println("Player at = "+ playerLocation);
      g2D.clearRect(0, 0, 800, 800);

      int row = playerLocation.x;
      int col = playerLocation.y;
      if (row <= 10 && col <= 10 && keyCode == KeyCode.UP) {
        closeToTopPictureBounderSteps++;
        playerPixelsFromTopBoundary = (cameraViewSize / 2.0) - ((closeToTopPictureBounderSteps * 16) / 3.0);
        playerPixelsFromLeftBoundary = (cameraViewSize / 2.0) - ((closeToLeftPictureBounderSteps * 16) / 3.0);
        g2D.drawImage(background, lastValidPlayerDX - (cameraViewSize / 2.0),
            lastValidPlayerDY - (cameraViewSize / 2.0), cameraViewSize, cameraViewSize, 0, 0, cameraViewSize,
            cameraViewSize);
        g2D.drawImage(character, sx, sy, sw, sh, playerPixelsFromLeftBoundary, playerPixelsFromTopBoundary, dw, dh);
        // closeToLeftPictureBounderSteps++;
      } else if (row <= 10 && col <= 10 && keyCode == KeyCode.DOWN) {
        closeToTopPictureBounderSteps--;
        playerPixelsFromTopBoundary = (cameraViewSize / 2.0) - ((closeToTopPictureBounderSteps * 16) / 3.0);
        playerPixelsFromLeftBoundary = (cameraViewSize / 2.0) - ((closeToLeftPictureBounderSteps * 16) / 3.0);
        g2D.drawImage(background, lastValidPlayerDX - (cameraViewSize / 2.0),
            lastValidPlayerDY - (cameraViewSize / 2.0), cameraViewSize, cameraViewSize, 0, 0, cameraViewSize,
            cameraViewSize);
        g2D.drawImage(character, sx, sy, sw, sh, playerPixelsFromLeftBoundary, playerPixelsFromTopBoundary, dw, dh);

        // closeToLeftPictureBounderSteps++;
      } else if (row <= 10 && col <= 10 && keyCode == KeyCode.RIGHT) {
        closeToLeftPictureBounderSteps--;
        playerPixelsFromTopBoundary = (cameraViewSize / 2.0) - ((closeToTopPictureBounderSteps * 16) / 3.0);
        playerPixelsFromLeftBoundary = (cameraViewSize / 2.0) - ((closeToLeftPictureBounderSteps * 16) / 3.0);
        g2D.drawImage(background, lastValidPlayerDX - (cameraViewSize / 2.0),
            lastValidPlayerDY - (cameraViewSize / 2.0), cameraViewSize, cameraViewSize, 0, 0, cameraViewSize,
            cameraViewSize);
        g2D.drawImage(character, sx, sy, sw, sh, playerPixelsFromLeftBoundary, playerPixelsFromTopBoundary, dw, dh);
        // closeToTopPictureBounderSteps--;
      } else if (row <= 10 && col <= 10 && keyCode == KeyCode.LEFT) {
        closeToLeftPictureBounderSteps++;
        playerPixelsFromTopBoundary = (cameraViewSize / 2.0) - ((closeToTopPictureBounderSteps * 16) / 3.0);
        playerPixelsFromLeftBoundary = (cameraViewSize / 2.0) - ((closeToLeftPictureBounderSteps * 16) / 3.0);
        g2D.drawImage(background, lastValidPlayerDX - (cameraViewSize / 2.0),
            lastValidPlayerDY - (cameraViewSize / 2.0), cameraViewSize, cameraViewSize, 0, 0, cameraViewSize,
            cameraViewSize);
        g2D.drawImage(character, sx, sy, sw, sh, playerPixelsFromLeftBoundary, playerPixelsFromTopBoundary, dw, dh);
        // closeToTopPictureBounderSteps--;
      } else if (row <= 10 && keyCode == KeyCode.UP) {
        closeToTopPictureBounderSteps++;
        playerPixelsFromTopBoundary = (cameraViewSize / 2.0) - ((closeToTopPictureBounderSteps * 16) / 3.0);
        g2D.drawImage(background, lastValidPlayerDX - (cameraViewSize / 2.0),
            lastValidPlayerDY - (cameraViewSize / 2.0), cameraViewSize, cameraViewSize, 0, 0, cameraViewSize,
            cameraViewSize);
        g2D.drawImage(character, sx, sy, sw, sh, (cameraViewSize / 2.0), playerPixelsFromTopBoundary, dw, dh);
      } else if (row <= 10 && keyCode == KeyCode.DOWN) {
        closeToTopPictureBounderSteps--;
        playerPixelsFromTopBoundary = (cameraViewSize / 2.0) - ((closeToTopPictureBounderSteps * 16) / 3.0);
        g2D.drawImage(background, lastValidPlayerDX - (cameraViewSize / 2.0),
            lastValidPlayerDY - (cameraViewSize / 2.0), cameraViewSize, cameraViewSize, 0, 0, cameraViewSize,
            cameraViewSize);
        g2D.drawImage(character, sx, sy, sw, sh, (cameraViewSize / 2.0), playerPixelsFromTopBoundary, dw, dh);
      } else if (row <= 10 && keyCode == KeyCode.LEFT) {
        lastValidPlayerDX = dx;
        g2D.drawImage(background, dx - (cameraViewSize / 2.0), lastValidPlayerDY - (cameraViewSize / 2.0),
            cameraViewSize, cameraViewSize, 0, 0, cameraViewSize, cameraViewSize);
        g2D.drawImage(character, sx, sy, sw, sh, (cameraViewSize / 2.0), playerPixelsFromTopBoundary, dw, dh);
      } else if (row <= 10 && keyCode == KeyCode.RIGHT) {
        lastValidPlayerDX = dx;
        g2D.drawImage(background, dx - (cameraViewSize / 2.0), lastValidPlayerDY - (cameraViewSize / 2.0),
            cameraViewSize, cameraViewSize, 0, 0, cameraViewSize, cameraViewSize);
        g2D.drawImage(character, sx, sy, sw, sh, (cameraViewSize / 2.0), playerPixelsFromTopBoundary, dw, dh);
      } else if (col <= 10 && keyCode == KeyCode.LEFT) {
        closeToLeftPictureBounderSteps++;
        playerPixelsFromLeftBoundary = (cameraViewSize / 2.0) - ((closeToLeftPictureBounderSteps * 16) / 3.0);
        lastValidPlayerDY = dy;
        g2D.drawImage(background, lastValidPlayerDX - (cameraViewSize / 2.0), dy - (cameraViewSize / 2.0),
            cameraViewSize, cameraViewSize, 0, 0, cameraViewSize, cameraViewSize);
        g2D.drawImage(character, sx, sy, sw, sh, playerPixelsFromLeftBoundary, cameraViewSize / 2.0, dw, dh);
      } else if (col <= 10 && keyCode == KeyCode.RIGHT) {
        closeToLeftPictureBounderSteps--;
        playerPixelsFromLeftBoundary = (cameraViewSize / 2.0) - ((closeToLeftPictureBounderSteps * 16) / 3.0);
        lastValidPlayerDY = dy;
        g2D.drawImage(background, lastValidPlayerDX - (cameraViewSize / 2.0), dy - (cameraViewSize / 2.0),
            cameraViewSize, cameraViewSize, 0, 0, cameraViewSize, cameraViewSize);
        g2D.drawImage(character, sx, sy, sw, sh, playerPixelsFromLeftBoundary, cameraViewSize / 2.0, dw, dh);
      } else if (col <= 10 && keyCode == KeyCode.UP) {
        playerPixelsFromLeftBoundary = (cameraViewSize / 2.0) - ((closeToLeftPictureBounderSteps * 16) / 3.0);
        lastValidPlayerDY = dy;
        g2D.drawImage(background, lastValidPlayerDX - (cameraViewSize / 2.0), dy - (cameraViewSize / 2.0),
            cameraViewSize, cameraViewSize, 0, 0, cameraViewSize, cameraViewSize);
        g2D.drawImage(character, sx, sy, sw, sh, playerPixelsFromLeftBoundary, cameraViewSize / 2.0, dw, dh);
      } else if (col <= 10 && keyCode == KeyCode.DOWN) {
        playerPixelsFromLeftBoundary = (cameraViewSize / 2.0) - ((closeToLeftPictureBounderSteps * 16) / 3.0);
        lastValidPlayerDY = dy;
        g2D.drawImage(background, lastValidPlayerDX - (cameraViewSize / 2.0), dy - (cameraViewSize / 2.0),
            cameraViewSize, cameraViewSize, 0, 0, cameraViewSize, cameraViewSize);
        g2D.drawImage(character, sx, sy, sw, sh, playerPixelsFromLeftBoundary, cameraViewSize / 2.0, dw, dh);
      } else if (row > 20 && keyCode == KeyCode.DOWN) {
        closeToBottomPictureBounderSteps++;
        playerPixelsFromBottomBoundary = (cameraViewSize / 2.0) + ((closeToBottomPictureBounderSteps * 16) / 3.0);
        lastValidPlayerDX = dx;
        g2D.drawImage(background, dx - (cameraViewSize / 2.0), lastValidPlayerDY - (cameraViewSize / 2.0),
            cameraViewSize, cameraViewSize, 0, 0, cameraViewSize, cameraViewSize);
        g2D.drawImage(character, sx, sy, sw, sh, cameraViewSize / 2.0, playerPixelsFromBottomBoundary, dw, dh);
      } else if (row > 20 && keyCode == KeyCode.UP) {
        closeToBottomPictureBounderSteps--;
        playerPixelsFromBottomBoundary = (cameraViewSize / 2.0) + ((closeToBottomPictureBounderSteps * 16) / 3.0);
        lastValidPlayerDX = dx;
        g2D.drawImage(background, dx - (cameraViewSize / 2.0), lastValidPlayerDY - (cameraViewSize / 2.0),
            cameraViewSize, cameraViewSize, 0, 0, cameraViewSize, cameraViewSize);
        g2D.drawImage(character, sx, sy, sw, sh, cameraViewSize / 2.0, playerPixelsFromBottomBoundary, dw, dh);
      }

      /**
       * Map layout doesnt let a user get close enough to boundary on south east /
       * south west so we only have to worry about the case where close to south
       * boundary
       */
      else if (row > 20 && (keyCode == KeyCode.LEFT || keyCode == KeyCode.RIGHT)) {
        playerPixelsFromBottomBoundary = (cameraViewSize / 2.0) + ((closeToBottomPictureBounderSteps * 16) / 3.0);
        lastValidPlayerDX = dx;
        g2D.drawImage(background, dx - (cameraViewSize / 2.0), lastValidPlayerDY - (cameraViewSize / 2.0),
            cameraViewSize, cameraViewSize, 0, 0, cameraViewSize, cameraViewSize);
        g2D.drawImage(character, sx, sy, sw, sh, cameraViewSize / 2.0, playerPixelsFromBottomBoundary, dw, dh);
      } else {
        closeToTopPictureBounderSteps = 0;
        closeToLeftPictureBounderSteps = 0;
        closeToBottomPictureBounderSteps = 0;
        lastValidPlayerDX = dx;
        lastValidPlayerDY = dy;
        g2D.drawImage(background, dx - (cameraViewSize / 2.0), dy - (cameraViewSize / 2.0), cameraViewSize,
            cameraViewSize, 0, 0, cameraViewSize, cameraViewSize);
        g2D.drawImage(character, sx, sy, sw, sh, cameraViewSize / 2.0, cameraViewSize / 2.0, dw, dh);
      }

      // stop timeline from drawing after final sprite
      if (tic == 3) {
        timeline.stop();
      }
    }
  }

  public void setPlayerLocation(Point trainerLocation) {
    playerLocation = trainerLocation;
  }

  public Point getPlayerLocation(Point trainerLocation) {
    return playerLocation;

  }

  public double getCameraViewHeight() {
    return cameraViewSize;
  }

  public double getCameraViewWidth() {
    return cameraViewSize;
  }

  @Override
  public void update(float elapsedTime) {
    // TODO Auto-generated method stub

  }

  @Override
  public Scene render() {
    BorderPane bp = new BorderPane();
    bp.setCenter(this);
    Scene scene = new Scene(bp, this.getCameraViewWidth(), this.getCameraViewHeight());
    return scene;
  }

  @Override
  public void onEnter() {
    // TODO Auto-generated method stub

  }

  @Override
  public void onExit() {
    // TODO Auto-generated method stub

  }

}