import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.io.*;
import java.util.*;
//run this one


public class CalcGraphic extends Application {
	public static Stage primaryStage;
	static Scene color, calc, curr;
	public static HashMap<Button, String> buttons = new HashMap<Button, String>();
	public static Calc calculator;
	public static TextField out = new TextField();
	//	public static String tempStyle = "-fx-background-color: grey; -fx-font-size: 30px; -fx-font-family: Consolas; -fx-font-weight: bold";
	public static int currStyle;
	public static HashMap<Button, String[]> colors = new HashMap<Button, String[]>();
	public static GridPane root;
	public static Button ss;

	public static String currButtonStyle;
	public static String[] styles = {
			"-fx-background-color: #d3d3d3; -fx-font-size: 30px; -fx-font-family: Consolas; -fx-font-weight: bold",
			"-fx-background-color: #bebebe; -fx-font-size: 30px; -fx-font-family: Consolas; -fx-font-weight: bold",
			"-fx-background-color: #a9a9a9; -fx-font-size: 30px; -fx-font-family: Consolas; -fx-font-weight: bold",
			"-fx-background-color: #808080; -fx-font-size: 30px; -fx-font-family: Consolas; -fx-font-weight: bold",
			"-fx-background-color: #484848; -fx-font-size: 30px; -fx-font-family: Consolas; -fx-font-weight: bold",
			"-fx-background-color: #202020; -fx-font-size: 30px; -fx-font-family: Consolas; -fx-font-weight: bold"
	};

	public static void setUpStyles(String[] theStyles) {
		String button = theStyles[0];
		for(Button b:buttons.keySet()) {
			b.setStyle(button);
		}
		out.setStyle(theStyles[1]);
		root.setStyle(theStyles[2]);
		ss.setStyle(theStyles[3]);
		
		currButtonStyle = theStyles[0];
		switchScene();
	}

	public void start(Stage ps) throws FileNotFoundException{
		HBox hb = new HBox();
		hb.setStyle("-fx-background-color: #ffffff");
		hb.setAlignment(Pos.CENTER);
		Button blue = new Button("B");
		Button red = new Button("R");
		Button green = new Button("G");

		//60%, 95%, 40%
		String[] bb = {
				"-fx-background-color: #6699cc; -fx-font-size: 30px; -fx-font-family: Consolas; -fx-font-weight: bold",
				"-fx-background-color: #ecf2f9; -fx-font-size: 30px; -fx-font-family: Consolas",
				"-fx-background-color: #336699",
				"-fx-background-color: #6699cc; -fx-font-size: 30px; -fx-font-family: Consolas; -fx-font-weight: bold",
				
		};
		blue.setStyle(bb[0]);
		colors.put(blue, bb);
		blue.setOnAction(	b -> setUpStyles(colors.get((Button)b.getSource()))		);

		String[] rr = {
				"-fx-background-color: #cc6666; -fx-font-size: 30px; -fx-font-family: Consolas; -fx-font-weight: bold",
				"-fx-background-color: #f9ecec; -fx-font-size: 30px; -fx-font-family: Consolas",
				"-fx-background-color: #993333",
				"-fx-background-color: #cc6666; -fx-font-size: 30px; -fx-font-family: Consolas; -fx-font-weight: bold",
		};
		red.setStyle(rr[0]);
		colors.put(red, rr);
		red.setOnAction(b -> setUpStyles(colors.get((Button)b.getSource())));

		String[] gg = {
				"-fx-background-color: #bbbb77; -fx-font-size: 30px; -fx-font-family: Consolas; -fx-font-weight: bold",
				"-fx-background-color: #f6f6ee; -fx-font-size: 30px; -fx-font-family: Consolas",
				"-fx-background-color: #888844",
				"-fx-background-color: #bbbb77; -fx-font-size: 30px; -fx-font-family: Consolas; -fx-font-weight: bold"
		};
		green.setStyle(gg[0]);
		colors.put(green, gg);
		green.setOnAction(b -> setUpStyles(colors.get((Button)b.getSource())));
		hb.getChildren().addAll(red,green,blue);
		color = new Scene(hb,350,400);
		primaryStage = ps;
		primaryStage.setTitle("MyCalculator 1.0");


		primaryStage.setScene(color);

		root = new GridPane();
		root.setPrefHeight(500);
		root.setPrefWidth(500);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(10,10,10,10));
		root.setStyle("-fx-background-color: #336699");
//		root.setStyle("-fx-background-color: white");

		calc = new Scene(root, 350,400);

		for(int i=0; i<9; i++) {
			Button b = new Button();	
			b.setPrefSize(30, 30);
			b.setMnemonicParsing(true);
			b.setText("_" + String.valueOf(i+1));
			buttons.put(b, String.valueOf(i+1));
			GridPane.setConstraints(b, i%3 , 1+(i/3));
			root.getChildren().add(b);			
			b.setOnAction(e -> handle(e));
		}

		Button zero = new Button();
		zero.setPrefSize(20, 20);
		zero.setMnemonicParsing(true);
		zero.setText("_0");

		buttons.put(zero, "0");
		GridPane.setConstraints(zero,1,4);
		zero.setOnAction(e -> handle(e));

		Button plus = new Button("+"); 
		buttons.put(plus, "+");
		GridPane.setConstraints(plus, 6, 1);
		plus.setOnAction(e -> handle(e));


		Button sub = new Button("-"); 
		buttons.put(sub, "-");
		GridPane.setConstraints(sub,6, 2);
		sub.setOnAction(e -> handle(e));

		Button mult = new Button("*"); 
		buttons.put(mult, "*");
		GridPane.setConstraints(mult, 6, 3);
		mult.setOnAction(e -> handle(e));

		Button div = new Button("/"); 
		buttons.put(div, "/");
		GridPane.setConstraints(div, 6, 4);
		div.setOnAction(e -> handle(e));

		Button equ = new Button("="); 
		buttons.put(equ, "=");
		GridPane.setConstraints(equ, 6, 0);
		equ.setOnAction(e -> handle(e));

		Button clr = new Button("CLR");
		buttons.put(clr, "CLR");
		//	clr.setStyle(buttonStyle);
		GridPane.setConstraints(clr, 0, 8);
		clr.setOnAction(e -> handle(e));
		clr.setPrefSize(225, 10);
		GridPane.setColumnSpan(clr, 4);

		GridPane.setConstraints(out, 0, 0);
		GridPane.setColumnSpan(out, 4);
		out.setText(String.valueOf(calculator.curr));
		out.setPrefSize(100, 20);

		ss = new Button("~");
		GridPane.setConstraints(ss, 6, 8);
		ss.setStyle(currButtonStyle);
		ss.setPrefSize(30, 30);
		ss.setOnAction(e -> switchScene());

		root.getChildren().addAll(zero,plus,sub,mult,div,equ,clr,out,ss);
		root.setHgap(2);
		root.setVgap(3);

		primaryStage.show();
		curr = color;

	}

	public static void switchScene() {
		if(curr.equals(color)){ 
			curr = calc;
		}
		else {
			curr = color;
		}
		primaryStage.setScene(curr);
	}

	public void handle(ActionEvent e) {
		e.consume();

		((Button)e.getSource()).setStyle(styles[currStyle]);
		if(currStyle<styles.length-1) {
			currStyle++;
		}

		String output = buttons.get(e.getSource());
		calculator.update(output);
		try {
			calculator.run();
			String display;
			if(calculator.forNow.equals("")) {
				display = String.valueOf(calculator.curr);	
			}
			else{
				display = calculator.forNow;
			}
			double d = Double.parseDouble(display);
			if(d==(int)d){
				display = String.valueOf((int)d);
			}
			
			String longS = String.format("%.0f", d);
			if(longS.length()>11) {
				display = longS.substring(0,11);
			}
			out.setText(display);			
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
		
		if(output.equals("CLR")){
			clearStyles();
		}
		if(output.equals("=")){
			clearStyles();
		}
	}

	public void clearStyles() {
		currStyle = 0;
		for(Button b:buttons.keySet()) {
			b.setStyle(currButtonStyle);
		}
	}


	public static void main(String[] args) throws Exception {
		calculator = new Calc();
		launch(args);
	}
}
