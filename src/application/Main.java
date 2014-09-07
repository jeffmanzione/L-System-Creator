package application;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import lsystem.LSystem;
import lsystem.parser.LSystems;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


public class Main extends Application {

	public static final int DEFAULT_WIDTH = 1000, DEFAULT_HEIGHT = 800;

	private Label initL, ruleL, nL, rL, sizeL, startAngleL, coorL, angleL, errorL, sig, dimLL, dimRL;
	private TextField initF, nF, rF, sizeF, startAngleF, coorXF, coorYF, angleF;

	private TextArea ruleF;
	private Button gen;
	private ComboBox<String> defs;
	private Pane left;
	private Group root, right;
	private LSystem system;
	private Map<String, LSystem> defaults, all;

	public void start(Stage primaryStage) {
		try {
			root = new Group();

			left = new Pane();

			right = new Group();

			defaults = new TreeMap<>();

			defaults.put("Koch Snowflake", LSystems.parse("X-(120)X-(120)X-(120)", "X -> { X+X--X+X }", 6, 1000, 1.0 / 3.0, 60));
			
			defaults.put("Koch Curve", LSystems.parse("X", "X -> { X+X-X-X+X }", 5, 1400, 1.0 / 3.0, 90));

			defaults.put("Sierpinski Triangle", LSystems.parse("X++X++X", "X -> { X++X++X }", 7, 1000, 0.5, 60));
			
			defaults.put("Sierpinski Arrowhead", LSystems.parse("A", "A -> { +B-A-B } B -> { -A+B+A }", 8, 1000, 0.5, 60));
			
			defaults.put("Dragon Curve", LSystems.parse("X", "X -> { -X++Y } Y -> { +X--Y }", 14, 600, 0.70710678118, 45));
			
			defaults.put("Deterministic Plant 1", LSystems.parse("X", "X -> {X[XXX][-X--X+X][+X++X-X] }", 4, 1000, 0.25, 15));
			defaults.put("Deterministic Plant 2", LSystems.parse("X", "X -> { Z+[[X]-X]-Z[-ZX]+X } Z -> { ZZ }", 7, 400, 0.5, 25));
			
			defaults.put("Jeff's Snowflake", LSystems.parse("Y", "Y -> { f[X]+[X]+[X]+[X]+[X]+X } X -> { Y-Y++Y-Y }", 8, 1000, 0.5, 60));
			defaults.put("Deterministic Tree", LSystems.parse("X", "X -> { XX-X+X[-X+X+X]+[+X-X-X]+X-X+X }", 5, 400, 2.0 / 5.0, 22.0));
			defaults.put("Stochastic Tree", LSystems.parse("X", "X -> { {0.5:XX-X+X[-X+X+XX]+[+X-X-X]+X-X+X;XX+X-X[+X-X-XX]-[-X+X+X]-X+X-X} }", 5, 200, 0.5, 25));

			
		/*	defaults.put("Squares", LSystems.parse("F-F-F-F", "F-F+F+FF-F-F+F", 5, 400, 1.0 / 4.0, 90, false));

			defaults.put("Hexagonal", LSystems.parse("F-F-F-F-F-F-", "F-F++FF--F+F", 5, 300, 1.0 / 4.0, 60, false));

			defaults.put("Pinwheel", LSystems.parse("[F]+[F]+[F]+[F]", "F[-F]+F", 8, 400, 0.5, 90, false));

			defaults.put("Deterministic Tree", LSystems.parse("F", "FF-F+F[-F+F+F]+[+F-F-F]+F-F+F", 5, 200, 2.0 / 5.0, 22.0, true));

			defaults.put("Stochastic Tree 1", LSystems.parse("F", "{0.33:F[+F]F[-F]F;0.33:F[+F]F;F[-F]F}", 7, 400, 1.0 / 2.0, 30, true));

			defaults.put("Stochastic Tree 2", LSystems.parse("^(16)F", "v(2){0.5:FF-F+F[-F+F+FF]+[+F-F-F]+F-F+F;FF+F-F[+F-F-FF]-[-F+F+F]-F+F-F}", 5, 180, 0.5, 25, true));

			defaults.put("Flower 1", LSystems.parse("F+F+F+F+F+F+F+F", "+F(1.41421356237)--F(1.41421356237)", 10, 200, 0.5, 45.0, false));

			defaults.put("Flower 2", LSystems.parse("F+(45)F+(45)F+(45)F+(45)F+(45)F+(45)F+(45)F", "-F(1.15470053838)++F(1.15470053838)+++F(2)++++F(2)", 7, 200, 0.5, 30.0, false));

			defaults.put("Butterfly", LSystems.parse("F+F+F", "+F(1.41421356237)--F(1.41421356237)", 14, 200, 0.5, 45.0, false));

			defaults.put("Sierpinski Arrowhead", LSystems.parse("F", "ff++F+f[+(180)F]+F", 8, 500, 0.5, 60, false));
*/


			all = new TreeMap<>();
			all.putAll(defaults);

			system = all.get("Koch Snowflake");

			final Pane pane = new Pane();
			pane.getStyleClass().add("pane");
			left.getStyleClass().add("left");
			//right.setEffect(frostEffect);

			sig = new Label("Created by Jeffrey Manzione");
			sig.setFont(new Font("Calibri", 12));
			sig.setLayoutX(20);
			sig.setLayoutY(770);

			ToggleButton help = new ToggleButton("", new ImageView(new Image(Main.class.getResourceAsStream("/help.png"))));
			ToggleButton quick = new ToggleButton("", new ImageView(new Image(Main.class.getResourceAsStream("/quick.png"))));

			Map<String, String> mapping = new LinkedHashMap<>();
			mapping.put("X->{...}", "definition of rule X");
			mapping.put("+", "turn left by default angle");
			mapping.put("-", "turn right by default angle");
			mapping.put("+/-(theta)", "turn left or right by angle theta");
			mapping.put("F", "move forward w/ drawing");
			mapping.put("f", "move forward w/o drawing");
			mapping.put("F/f(scalar)", "move foward and/or draw by scalar");
			mapping.put("^", "increase line width");
			mapping.put("v", "decrease line width");
			mapping.put("^/v(px)", "increase/decrease line width by px.");
			mapping.put("[", "push current position onto stack");
			mapping.put("]", "pop position off of stack and go to it.");
			mapping.put("{p1:s1;... sn}", "probabilities (p1,...) and sequences (s1,...)");

			LegendPane lp = new LegendPane(mapping);
			lp.setLayoutX(help.getLayoutX() - lp.getWidth());
			lp.setLayoutY(10);
			lp.setVisible(false);
			lp.getStyleClass().add("trans-back");

			WebView wv = new WebView();
			WebEngine eng = wv.getEngine();
			eng.load(Main.class.getResource("/help.html").toExternalForm());

			wv.setMaxWidth(450);
			wv.setMaxHeight(550);
			wv.setBlendMode(BlendMode.ADD);
			StackPane helpPage = new StackPane(wv);
			helpPage.getStyleClass().add("trans-back");

			helpPage.setLayoutX(help.getLayoutX() - helpPage.getWidth());
			helpPage.setLayoutY(10);
			helpPage.setVisible(false);


			quick.selectedProperty().addListener(e -> {
				if (lp.isVisible()) {
					lp.setVisible(false);
				} else {
					if (help.isSelected()) {
						help.fire();
					}
					lp.setVisible(true);
				}
			});
			quick.setTooltip(new Tooltip("Symbol meanings (Ctrl+Q)"));
			quick.setMnemonicParsing(true);

			help.selectedProperty().addListener(e -> {
				if (helpPage.isVisible()) {
					helpPage.setVisible(false);
				} else {
					if (quick.isSelected()) {
						quick.fire();
					}
					helpPage.setVisible(true);
				}
			});
			help.setTooltip(new Tooltip("Help (Ctrl+H)"));
			help.setMnemonicParsing(true);


			Button open = new Button("", new ImageView(new Image(Main.class.getResourceAsStream("/open.png"))));
			open.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open L-Systems");
				File dir = new File(System.getProperty("user.home") + "/Documents/L-Systems");
				if (!dir.exists()) {
					dir.mkdir();
				}
				fileChooser.setInitialDirectory(dir);
				fileChooser.setInitialFileName("my_lsys.lsys");
				File openFrom = fileChooser.showOpenDialog(primaryStage);

				if (openFrom != null) {
					try (Scanner scan = new Scanner(openFrom)) {
						String entry;

						while (scan.hasNextLine()) {
							entry = scan.nextLine();

							String[] parts = entry.split("\t");
							LSystem system = LSystems.parse(parts[1].replaceAll("&", "\n"), parts[2].replaceAll("&", "\n"), Integer.parseInt(parts[3]), Double.parseDouble(parts[4]), Double.parseDouble(parts[5]), Double.parseDouble(parts[6]));
							all.put(parts[0], system);
							defs.getItems().add(parts[0]);

						}
					} catch (FileNotFoundException err) {
						errorL.setText("File not found!");
					}
				}

			});
			open.setTooltip(new Tooltip("Open L-System file (Ctrl+O)"));

			Button save = new Button("", new ImageView(new Image(Main.class.getResourceAsStream("/save.png"))));
			save.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save L-Systems");
				File dir = new File(System.getProperty("user.home") + "/Documents/L-Systems");
				if (!dir.exists()) {
					dir.mkdir();
				}
				fileChooser.setInitialDirectory(dir);
				fileChooser.setInitialFileName("my_lsys.lsys");
				File saveTo = fileChooser.showSaveDialog(primaryStage);

				if (saveTo != null) {
					if (!saveTo.exists()) {
						try {
							saveTo.createNewFile();

						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					try (PrintWriter out = new PrintWriter(saveTo)) {

						all.forEach((k, v) -> {
							if (!defaults.containsKey(k)) {
								out.printf("%s\t%s\t%s\t%d\t%f\t%f\t%f\t%s\n", k, v.getInitiator(), v.getRules(), v.iters, v.size, v.r, v.angle);
							}
						});
					} catch (Exception err) {
						errorL.setText("File not found!");
					}
				}

			});
			save.setTooltip(new Tooltip("Save L-System file (Ctrl+S)"));

			right.getChildren().add(pane);
			right.getChildren().add(system);

			root.getChildren().add(right);
			root.getChildren().add(left);
			root.getChildren().add(help);
			root.getChildren().add(quick);
			root.getChildren().add(helpPage);
			root.getChildren().add(open);
			root.getChildren().add(save);
			root.getChildren().add(lp);
			left.setMaxSize(400, DEFAULT_HEIGHT);
			left.setMinSize(400, DEFAULT_HEIGHT);


			GridPane fields = new GridPane();
			fields.setLayoutX(20);
			fields.setLayoutY(130);

			Text title = new Text("L-System Builder");
			title.getStyleClass().add("title");
			DropShadow ds = new DropShadow();
			ds.setOffsetY(3.0f);
			ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

			title.setEffect(ds);
			title.setCache(true);

			title.setLayoutX(20);
			title.setLayoutY(50);

			defs = new ComboBox<String>();
			defs.getItems().addAll(all.keySet());
			defs.setValue("Koch Snowflake");
			defs.setLayoutX(20);
			defs.setLayoutY(80);

			defs.valueProperty().addListener((ObservableValue<? extends String> ov, String oldVal, String newVal) -> setLSystem(newVal));

			TextField name = new TextField("My L-System");
			name.setLayoutX(220);
			name.setLayoutY(80);
			name.setMaxWidth(90);

			Button add = new Button("", new ImageView(new Image(Main.class.getResourceAsStream("/add.png"))));
			add.setLayoutX(320);
			add.setLayoutY(80);

			add.setOnAction(e -> {
				defs.getItems().add(name.getText());
				all.put(name.getText(), system);
			});

			fields.setAlignment(Pos.TOP_CENTER);
			fields.setHgap(10);
			fields.setVgap(12);

			initL = new Label("Initiator");
			ruleL = new Label("Rules");
			nL = new Label("Iterations");
			rL = new Label("R");
			angleL = new Label("Angle");
			sizeL = new Label("Size");
			coorL = new Label("Coors");
			startAngleL = new Label("Orientation");
			initF = new TextField("");
			ruleF = new TextArea("");
			ruleF.setMaxWidth(260);
			ruleF.setMaxHeight(500);
			ruleF.setWrapText(true);
			nF = new TextField("7");
			rF = new TextField("0.5");
			angleF = new TextField("30");
			sizeF = new TextField("400");
			coorXF = new TextField("300");
			coorXF.setMaxWidth(80);
			coorYF = new TextField("350");
			coorYF.setMaxWidth(80);
			startAngleF = new TextField("0.0");
			dimLL = new Label("Dimension");
			dimRL = new Label("");

			gen = new Button("Create");
			errorL = new Label("");
			errorL.setTextFill(Color.RED);
			errorL.setFont(new Font("Courier New", 12));
			errorL.setWrapText(true);
			errorL.setMaxWidth(260);
			errorL.setMinWidth(260);

			GridPane coorGrid = new GridPane();
			coorGrid.add(new Label("("), 0, 0);
			coorGrid.add(coorXF, 1, 0);
			coorGrid.add(new Label(","), 2, 0);
			coorGrid.add(coorYF, 3, 0);
			coorGrid.add(new Label(")"), 4, 0);
			coorGrid.setHgap(2);

			fields.add(initL, 0, 0);
			fields.add(initF, 1, 0);
			fields.add(ruleL, 0, 1);
			fields.add(ruleF, 1, 1);
			fields.add(nL, 0, 2);
			fields.add(nF, 1, 2);
			fields.add(rL, 0, 3);
			fields.add(rF, 1, 3);
			fields.add(angleL, 0, 4);
			fields.add(angleF, 1, 4);
			fields.add(sizeL, 0, 5);
			fields.add(sizeF, 1, 5);
			fields.add(startAngleL, 0, 6);
			fields.add(startAngleF, 1, 6);
			fields.add(coorL, 0, 7);
			fields.add(coorGrid, 1, 7);
			//fields.add(dimLL, 0, 9);
			//fields.add(dimRL, 1, 9);
			fields.add(gen, 0, 10);
			fields.add(errorL, 1, 10);

			left.getChildren().add(title);
			left.getChildren().add(defs);
			left.getChildren().add(name);
			left.getChildren().add(add);
			left.getChildren().add(fields);
			left.getChildren().add(sig);

			setLSystem("Koch Snowflake");

			Arrays.asList(initF, ruleF, nF, rF, angleF, sizeF).forEach(n -> n.setOnKeyPressed(e -> {
				if (e.getCode().equals(KeyCode.ENTER)) { 
					makeLSystem();
				}
			}));

			Arrays.asList(startAngleF, coorXF, coorYF).forEach(t -> t.setOnKeyPressed(e -> {
				if (e.getCode().equals(KeyCode.ENTER)) { 
					updateFields();
				}
			}));

			gen.setOnAction(e -> makeLSystem());


			new Thread(() -> {
				String err;

				try (PipedOutputStream pos = new PipedOutputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(new PipedInputStream(pos))); 
						PrintStream ps = new PrintStream(pos, true);) {

					System.setErr(ps);
					//System.err.println("Lol");

					while ((err = reader.readLine()) != null) {
						System.out.println("\'" + err + "\'");
						Platform.runLater(new Thread(new Holder(err)));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}).start();

			//left.getChildren().addAll(initL, ruleL, nL, rL, sizeL, onlyL, initF, ruleF, nF, rF, sizeF, onlyB);

			final Point prevMouse = new Point(-1, -1);

			right.setOnScroll(e -> {
				system.setRotate((system.getRotate() + e.getDeltaY() / 4) % 360);
				startAngleF.setText("" + system.getRotate());

				//updateFrost();
			});

			right.setOnMouseDragged(e -> {
				double dx = prevMouse.getX() - e.getX();
				double dy = prevMouse.getY() - e.getY();

				system.setTranslateX(system.getTranslateX() - dx);
				system.setTranslateY(system.getTranslateY() - dy);
				prevMouse.setLocation(e.getX(), e.getY());

				coorXF.setText("" + system.getTranslateX());
				coorYF.setText("" + system.getTranslateY());

				//updateFrost();
			});

			root.setOnMousePressed(e -> prevMouse.setLocation(e.getX(), e.getY()));

			final Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

			primaryStage.setTitle("L-System Builder");

			pane.setMinWidth(scene.getWidth());

			pane.setMinHeight(scene.getHeight());
			
			scene.getAccelerators().put(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN), () -> help.fire());
			scene.getAccelerators().put(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN), () -> quick.fire());
			scene.getAccelerators().put(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN), () -> open.fire());
			scene.getAccelerators().put(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN), () -> save.fire());
			
			ChangeListener<Number> resizeListener = (ObservableValue<? extends Number> num, Number old, Number newNum) -> {
				pane.setMinWidth(scene.getWidth());
				pane.setMinHeight(scene.getHeight());

				left.setMaxSize(400, scene.getHeight());
				left.setMinSize(400, scene.getHeight());

				help.setLayoutX(scene.getWidth() - 80);
				quick.setLayoutX(scene.getWidth() - 80);
				open.setLayoutX(scene.getWidth() - 80);
				save.setLayoutX(scene.getWidth() - 80);
				lp.setLayoutX(help.getLayoutX() - lp.getWidth());
				helpPage.setLayoutX(help.getLayoutX() - helpPage.getWidth());

				//updateFrost();
			};

			help.setLayoutX(scene.getWidth() - 80);
			help.setLayoutY(10);
			quick.setLayoutX(scene.getWidth() - 80);
			quick.setLayoutY(20 + help.getHeight());
			open.setLayoutX(scene.getWidth() - 80);
			open.setLayoutY(30 + help.getHeight() + quick.getHeight());
			save.setLayoutX(scene.getWidth() - 80);
			save.setLayoutY(40 + help.getHeight() + quick.getHeight() + open.getHeight());
			lp.setLayoutX(help.getLayoutX() - lp.getWidth());
			helpPage.setLayoutX(help.getLayoutX() - helpPage.getWidth());

			scene.widthProperty().addListener(resizeListener);
			scene.heightProperty().addListener(resizeListener);

			primaryStage.setOnCloseRequest(e -> System.exit(0));

		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	//private static final double BLUR_AMOUNT = 30;

	//private static final Effect frostEffect =
	//		new BoxBlur(BLUR_AMOUNT, BLUR_AMOUNT, 3);

	private class Holder implements Runnable {
		String str;
		public Holder(String str) {
			this.str = str;
		}

		public void run() {
			errorL.setText(str);
		}
	}

	private void makeLSystem() {
		Platform.runLater(new Thread(new Holder("")));

		double x = system.getTranslateX();
		double y = system.getTranslateY();
		double angle = system.getRotate();

		right.getChildren().remove(system);

		try {
			system = LSystems.parse(
					initF.getText(), 
					ruleF.getText(), 
					Integer.parseInt(nF.getText()), 
					Double.parseDouble(sizeF.getText()), 
					Double.parseDouble(rF.getText()), 
					Double.parseDouble(angleF.getText()));
			right.getChildren().add(system);

			dimRL.setText("" + Math.log(system.n()) / Math.log(1 / system.r));

			//System.out.println(system);

			system.setTranslateX(x);
			system.setTranslateY(y);
			system.setRotate(angle);

			//updateFrost();

		} catch (NumberFormatException e) {
			System.err.println("A numerical input was invalid.");
		}
	}

	private void setLSystem(final String name) {
		Platform.runLater(new Thread(new Holder("")));
		Platform.runLater(new Runnable() {
			public void run() {
				double x = system.getTranslateX();
				double y = system.getTranslateY();
				double angle = system.getRotate();

				right.getChildren().remove(system);

				system = all.get(name);
				right.getChildren().add(system);

				initF.setText(system.getInitiator());
				ruleF.setText(system.getRules().replaceAll("&", "\n"));

				//System.out.println(angle);

				system.setTranslateX(x);
				system.setTranslateY(y);
				system.setRotate(angle);

				nF.setText("" + system.iters);
				sizeF.setText("" + system.size); 
				rF.setText("" + system.r);
				angleF.setText("" + system.angle); 

				updateFields();
			}
		});
	}

	private void updateFields() {
		try {
			Platform.runLater(new Thread(new Holder("")));
			double x = Double.parseDouble(coorXF.getText());
			double y = Double.parseDouble(coorYF.getText());
			double angle = Double.parseDouble(startAngleF.getText());

			//System.out.println(angle);

			system.setTranslateX(x);
			system.setTranslateY(y);
			system.setRotate(angle);

			dimRL.setText("" + Math.log(system.n()) / Math.log(1 / system.r));
		} catch (Exception e) {
			System.err.println("A numerical input was invalid.");
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
