
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.Arrays;

import javax.imageio.ImageIO;

import static java.lang.Integer.parseInt;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class EditorController {

	// Size of one tile in pixels
	private static final int TILE_SIZE = 64;

	// Time between item drops
	private final int[] dropRates;

	private String levelName;

	// Size of game map
	private int width;
	private int height;

	// Game losing conditions (maximum number of rats, time taken for a level)
	private int maxRats;
	private int parTime;

	//Current tile selected to draw
	private Tile selectedTile = new Grass();

	@FXML
	public Canvas levelCanvas;

	public RadioButton rbGrass;
	public RadioButton rbPath;
	public RadioButton rbTunnel;

	public HBox ratSpawnToolbar;

	public TextField widthTextField;
	public TextField heightTextField;
	public Text sizeChangeErrorText;

	public Pane settingsDialoguePane;

	public TextField bombTextField;
	public TextField gasTextField;
	public TextField sterilizationTextField;
	public TextField poisonTextField;
	public TextField maleSwapTextField;
	public TextField femaleSwapTextField;
	public TextField stopSignTextField;
	public TextField deathRatTextField;
	public TextField[] powerTextFields;

	public TextField maxRatTextField;
	public TextField gameTimerTextField;

	public Text settingsErrorText;

	public Pane saveLevelPane;
	public TextField levelNameTextField;
	public Text savingErrorText;

	public Button sizeChangeButton;
	public Button levelSettingsButton;
	public Button saveLevelButton;
	public Button saveAndExitButton;

	// Level map
	private static Tile[][] tileMap = new Tile[0][0];

	public EditorController() {
		width = 10;
		height = 7;
		tileMap = new Tile[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tileMap[i][j] = new Grass();
			}
		}
		maxRats = 20;
		parTime = 150;
		dropRates = new int[8];
		Arrays.fill(dropRates, 1); // TODO: change these to millis when saving level, from millis when loading one
	}

	public void initialize() {
		renderBoard();
		setupRadioButtons();
		setupDraggableSpawns();
		setupCanvasDrawing();
		setupCanvasDragBehaviour();

		heightTextField.setText(String.valueOf(height));
		widthTextField.setText(String.valueOf(width));

		powerTextFields = new TextField[] { bombTextField, gasTextField, sterilizationTextField, poisonTextField,
				maleSwapTextField, femaleSwapTextField, stopSignTextField, deathRatTextField };
		for (int i = 0; i < dropRates.length; i++) {
			powerTextFields[i].setText(String.valueOf(dropRates[i]));
		}

		maxRatTextField.setText(String.valueOf(maxRats));
		gameTimerTextField.setText(String.valueOf(parTime));
	}

	/**
	 * Sets up the ability to drag things onto canvas.
	 */
	private void setupCanvasDragBehaviour() {

		levelCanvas.setOnDragOver(event -> {
			int x = (int) event.getX() / TILE_SIZE;
			int y = (int) event.getY() / TILE_SIZE;

			if (event.getGestureSource() instanceof ImageView) {
				if (x < width && x >= 0 && y >= 0 && y < height) { // if x and y are in the size of the tilemap
					if (tileMap[x][y] instanceof Path) { // if the tile at (x,y) is a path
						event.acceptTransferModes(TransferMode.ANY);
					}
				}
				event.consume();
			}
		});

		// This code allows the canvas to react to a dragged object when it is finally
		// dropped.
		levelCanvas.setOnDragDropped(event -> {
			String dbContent = event.getDragboard().getString();
			char ratType = dbContent.charAt(0);
			spawnDropped(event, ratType);
		});
	}

	/**
	 * Handles dropping things onto canvas.
	 * 
	 * @param event drag event.
	 * @param type  type of item dropped.
	 */
	private void spawnDropped(DragEvent event, char type) {
		int x = (int) event.getX() / TILE_SIZE;
		int y = (int) event.getY() / TILE_SIZE;

		if (tileMap[x][y].getOccupantRats().size() != 0) {
			tileMap[x][y].getOccupantRats().clear();
		}
		switch (type) {
		case 'm':
			tileMap[x][y].addOccupantRat(new AdultMale(1, Rat.Direction.NORTH, 0, 0, 0, false));
			break;
		case 'f':
			tileMap[x][y].addOccupantRat(new AdultFemale(1, Rat.Direction.NORTH, 0, 0, 0, false, 0, 0));
			break;
		case 'i':
			tileMap[x][y].addOccupantRat(new AdultIntersex(1, Rat.Direction.NORTH, 0, 0, 0, false, 0, 0));
			break;
		}

		renderBoard();
	}

	/**
	 * Sets up ability to drag rat spawns onto tilemap.
	 */
	private void setupDraggableSpawns() {
		AdultMale adultMale = new AdultMale(1, Rat.Direction.NORTH, 0, 0, 0, false);
		AdultFemale adultFemale = new AdultFemale(1, Rat.Direction.NORTH, 0, 0, 0, false, 0, 0);
		AdultIntersex adultIntersex = new AdultIntersex(1, Rat.Direction.NORTH, 0, 0, 0, false, 0, 0);

		ImageView adultMaleImageView = new ImageView(adultMale.getImg());
		ImageView adultFemaleImageView = new ImageView(adultFemale.getImg());
		ImageView adultIntersexImageView = new ImageView(adultIntersex.getImg());
		ImageView deleteImageView = new ImageView(new Image("file:resources/delete.png"));

		ratSpawnToolbar.getChildren().add(adultMaleImageView);
		ratSpawnToolbar.getChildren().add(adultFemaleImageView);
		ratSpawnToolbar.getChildren().add(adultIntersexImageView);
		ratSpawnToolbar.getChildren().add(deleteImageView);

		makeDraggable(adultMaleImageView, 'm');
		makeDraggable(adultFemaleImageView, 'f');
		makeDraggable(adultIntersexImageView, 'i');
		makeDraggable(deleteImageView, 'd');
	}

	/**
	 * Makes ImageView draggable onto tilemap.
	 * 
	 * @param item image view to be made draggable.
	 * @param type type of item.
	 */
	private static void makeDraggable(final ImageView item, char type) {
		item.setOnDragDetected(event -> {
			Dragboard dragboard = item.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent clipboardContent = new ClipboardContent();
			clipboardContent.putString(String.valueOf(type));
			dragboard.setContent(clipboardContent);
			event.consume();
		});
	}

	/**
	 * Sets up the ability to draw selected tile onto tilemap.
	 */
	private void setupCanvasDrawing() {
		levelCanvas.setOnMousePressed(event -> {
			int x = (int) event.getX() / TILE_SIZE;
			int y = (int) event.getY() / TILE_SIZE;

			if (x < width - 1 && x >= 1 && y >= 1 && y < height - 1) {
				if ((!(tileMap[x][y].getClass() == selectedTile.getClass()))) {
					if (selectedTile instanceof Grass) {
						tileMap[x][y] = new Grass();
					} else if (selectedTile instanceof Path) {
						tileMap[x][y] = new Path();
					} else {
						tileMap[x][y] = new Tunnel();
					}
					renderBoard();
				}
			}
		});
		levelCanvas.setOnMouseDragged(event -> {
			int x = (int) event.getX() / TILE_SIZE;
			int y = (int) event.getY() / TILE_SIZE;

			if (x < width - 1 && x >= 1 && y >= 1 && y < height - 1) {
				if ((!(tileMap[x][y].getClass() == selectedTile.getClass()))) {
					if (selectedTile instanceof Grass) {
						tileMap[x][y] = new Grass();
					} else if (selectedTile instanceof Path) {
						tileMap[x][y] = new Path();
					} else {
						tileMap[x][y] = new Tunnel();
					}
					renderBoard();
				}
			}
		});
	}

	/**
	 * Sets up radio buttons for grass/path/tunnel selection.
	 */
	private void setupRadioButtons() {
		final ToggleGroup tileRadioButtons = new ToggleGroup();

		rbGrass.setToggleGroup(tileRadioButtons);
		rbPath.setToggleGroup(tileRadioButtons);
		rbTunnel.setToggleGroup(tileRadioButtons);

		ImageView grassImageView = new ImageView(new Grass().getImg());
		ImageView pathImageView = new ImageView(new Path().getImg());
		ImageView tunnelImageView = new ImageView(new Tunnel().getImg());

		ImageView grassImageViewSelected = new ImageView(new Image("file:resources/grass_selected.png"));
		ImageView pathImageViewSelected = new ImageView(new Image("file:resources/path_selected.png"));
		ImageView tunnelImageViewSelected = new ImageView(new Image("file:resources/tunnel_selected.png"));

		rbGrass.setSelected(true);

		rbGrass.setGraphic(grassImageViewSelected);
		rbPath.setGraphic(pathImageView);
		rbTunnel.setGraphic(tunnelImageView);

		tileRadioButtons.selectedToggleProperty().addListener((ob, o, n) -> {
			if (rbGrass.isSelected()) {
				selectedTile = new Grass();
				rbGrass.setGraphic(grassImageViewSelected);
				rbPath.setGraphic(pathImageView);
				rbTunnel.setGraphic(tunnelImageView);
			} else if (rbPath.isSelected()) {
				selectedTile = new Path();
				rbGrass.setGraphic(grassImageView);
				rbPath.setGraphic(pathImageViewSelected);
				rbTunnel.setGraphic(tunnelImageView);
			} else if (rbTunnel.isSelected()) {
				selectedTile = new Tunnel();
				rbGrass.setGraphic(grassImageView);
				rbPath.setGraphic(pathImageView);
				rbTunnel.setGraphic(tunnelImageViewSelected);
			}
		});
	}

	/**
	 * Renders tilemap onto window.
	 */
	private void renderBoard() {
		GraphicsContext gc = levelCanvas.getGraphicsContext2D();

		gc.setFill(Color.web("#2d4945"));
		gc.fillRect(0, 0, levelCanvas.getWidth(), levelCanvas.getHeight());

		if (tileMap != null) {
			for (int i = 0; i < tileMap.length; i++) {
				for (int j = 0; j < tileMap[i].length; j++) {
					tileMap[i][j].draw(i, j, gc);
				}
			}
		}
	}

	/**
	 * Changes level size once "Apply Changes" is pressed. Unless invalid input, in
	 * which case it prompts the user to change their input.
	 */
	@FXML
	public void changeLevelSize() {
		try {
			int newWidth = parseInt(widthTextField.getText());
			int newHeight = parseInt(heightTextField.getText());
			if (newWidth > (levelCanvas.getWidth() / TILE_SIZE) || newWidth < 3
					|| newHeight > (levelCanvas.getHeight() / TILE_SIZE) || newHeight < 3) {
				sizeChangeErrorText.setText("Maximum map size: 16x14");
			} else {
				sizeChangeErrorText.setText("");
				changeTileMapSize(newWidth, newHeight);
			}
		} catch (NumberFormatException nfe) {
			sizeChangeErrorText.setText("Please enter an integer number");
		}
	}

	/**
	 * Changes size of tile map. For levels that need to be made bigger, replaces
	 * empty space with grass.
	 * 
	 * @param newWidth  width of new tile map.
	 * @param newHeight height of new tile map.
	 */
	private void changeTileMapSize(int newWidth, int newHeight) {
		Tile[][] newTileMap = new Tile[newWidth][newHeight];

		for (int i = 0; i < newWidth; i++) {
			for (int j = 0; j < newHeight; j++) {
				if ((i >= tileMap.length) || (j >= tileMap[0].length) || (i == newWidth - 1) || (j == newHeight - 1)) {
					newTileMap[i][j] = new Grass();
				} else {
					newTileMap[i][j] = tileMap[i][j];
				}
			}
		}
		tileMap = newTileMap;
		width = newWidth;
		height = newHeight;
		renderBoard();
	}

	/**
	 * Displays level settings box when button is pressed.
	 */
	public void displayLevelSettings() {
		setButtonDisabling(true);
		disableCanvas();
		settingsDialoguePane.setVisible(true);
	}

	/**
	 * Saves level settings when button is pressed, unless the user has made invalid
	 * input. If they have, it prompts the user to enter something else
	 */
	public void saveSettings() {
		try {
			maxRats = parseInt(maxRatTextField.getText());
			parTime = parseInt(gameTimerTextField.getText());
			boolean wrongDropRate = false;
			for (int i = 0; i < dropRates.length; i++) {
				dropRates[i] = parseInt(powerTextFields[i].getText());
				if (dropRates[i] < 0) {
					wrongDropRate = true;
				}
			}
			if (maxRats <= getNumOfRats()) {
				settingsErrorText.setText("Please enter a valid number of rats.");
			} else if (parTime < 0) {
				settingsErrorText.setText("Please enter a valid game time.");
			} else if (wrongDropRate) {
				settingsErrorText.setText("Please enter valid drop rates.");
			} else {
				settingsErrorText.setText("");
				settingsDialoguePane.setVisible(false);
				setupCanvasDrawing();
				setupCanvasDragBehaviour();
				setButtonDisabling(false);
			}
		} catch (NumberFormatException nfe) {
			settingsErrorText.setText("Please enter integer numbers only.");
		}
	}

	/**
	 * Gets current number of rats on the board.
	 * @return number of rats.
	 */
	private int getNumOfRats() {
		int num = 0;
		for (Tile[] tiles : tileMap) {
			for (Tile tile : tiles) {
				if (tile.getOccupantRats().size() != 0) {
					num++;
				}
			}
		}
		return num;
	}

	/**
	 * Disables all canvas events.
	 */
	private void disableCanvas() {
		levelCanvas.setOnMousePressed(null);
		levelCanvas.setOnMouseDragged(null);
		levelCanvas.setOnDragDropped(null);
		levelCanvas.setOnDragOver(null);
	}

	/**
	 * Sets button disabling for all side panel buttons.
	 * @param val whether buttons should be disabled.
	 */
	private void setButtonDisabling(boolean val) {
		rbGrass.setDisable(val);
		rbTunnel.setDisable(val);
		rbPath.setDisable(val);
		saveLevelButton.setDisable(val);
		levelSettingsButton.setDisable(val);
		sizeChangeButton.setDisable(val);
		widthTextField.setDisable(val);
		heightTextField.setDisable(val);
	}

	/**
	 * Opens level saving dialogue box.
	 */
	@FXML
	public void openSavingDialogueBox() {
		saveLevelPane.setVisible(true);
		setButtonDisabling(true);
		disableCanvas();
		if (getNumOfRats() <= 1) {
			savingErrorText.setText("Please add more rat spawns to the level.");
			saveAndExitButton.setDisable(true);
		} else if (maxRats <= getNumOfRats()) {
			savingErrorText.setText("Please fix level settings before saving.");
			saveAndExitButton.setDisable(true);
		} else {
			saveAndExitButton.setDisable(false);
			savingErrorText.setText("");
		}
	}

	/**
	 * Goes back to editor from level saving dialogue box.
	 */
	public void goBackToEditor() {
		saveLevelPane.setVisible(false);
		setupCanvasDrawing();
		setupCanvasDragBehaviour();
		setButtonDisabling(false);
	}

	/**
	 * Saves level once "Save Level" button is pressed.
	 */
	@FXML
	public void saveLevel() {
		String newLevelName = levelNameTextField.getText();
		if(newLevelName.contains(" ")){
			savingErrorText.setText("Level name cannot contain spaces");
		} else  if(newLevelName.length() == 0) {
			savingErrorText.setText("Level name cannot be empty");
		} else {
			savingErrorText.setText("");
			System.out.println("Can't do that yet :(");
			// TODO: add actual saving
		}
	}

	public void makeScreenShot() throws IOException {
		// TODO: need to use this code when saving custom level (set name of the file
		// the same as level name)

		// TODO: generate file name
		File file = new File("resources/chart.png");

		WritableImage writableImage = new WritableImage(TILE_SIZE * width, TILE_SIZE * height);
		SnapshotParameters params = new SnapshotParameters();
		levelCanvas.snapshot(params, writableImage);
		
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
		} catch (Throwable th) {
			//TODO: handle this exception
		}

	}
}
