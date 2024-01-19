package unb.cs3035.assignment3;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class View extends Pane {
	public static final Color FILL_COLOR = Color.GREEN;
	public static final Color SELECTED_FILL_COLOR = Color.BLUE;
	private static Group root;

	public View()
	{

		ToolBar toolBar = new ToolBar();
		toolBar.setPrefWidth(400);

		Image squareImg = new Image(getClass().getClassLoader().getResourceAsStream("square.png"));
		ToggleButton squareButton = new ToggleButton("", new ImageView(squareImg));
		final Tooltip squareTip = new Tooltip();

		Image circleImg = new Image(getClass().getClassLoader().getResourceAsStream("circle.png"));
		ToggleButton circleButton = new ToggleButton("", new ImageView(circleImg));
		final Tooltip circleTip = new Tooltip();

		Image triangleImg = new Image(getClass().getClassLoader().getResourceAsStream("triangle.png"));
		ToggleButton triangleButton = new ToggleButton("", new ImageView(triangleImg));
		final Tooltip triangleTip = new Tooltip();

		Image cutImg = new Image(getClass().getClassLoader().getResourceAsStream("cut.png"));
		Button cutButton = new Button("", new ImageView(cutImg));
		final Tooltip cutTip = new Tooltip();

		Image pasteImg = new Image(getClass().getClassLoader().getResourceAsStream("paste.png"));
		Button pasteButton = new Button("", new ImageView(pasteImg));
		final Tooltip pasteTip = new Tooltip();

		final ToggleGroup group = new ToggleGroup();
		squareButton.setToggleGroup(group);
		circleButton.setToggleGroup(group);
		triangleButton.setToggleGroup(group);
		squareButton.setSelected(true);

		squareTip.setText("square");
		squareButton.setTooltip(squareTip);

		circleTip.setText("circle");
		circleButton.setTooltip(circleTip);

		triangleTip.setText("triangle");
		triangleButton.setTooltip(triangleTip);

		cutTip.setText("cut");
		cutButton.setTooltip(cutTip);

		pasteTip.setText("paste");
		pasteButton.setTooltip(pasteTip);

		toolBar.getItems().add(squareButton);
		toolBar.getItems().add(circleButton);
		toolBar.getItems().add(triangleButton);
		toolBar.getItems().add(new Separator());

		toolBar.getItems().add(cutButton);
		toolBar.getItems().add(pasteButton);

		this.getChildren().add(toolBar);

		cutButton.setOnAction(e -> {
			Main.controller.cutSelectedShapes();
		});

		pasteButton.setOnAction(e -> {
			Main.controller.pasteSelectedShapes();
		});

		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue)
			{

				if(newValue == squareButton)
				{
					Main.model.setShape("SQUARE");
				}
				else if(newValue == circleButton)
				{
					Main.model.setShape("CIRCLE");
				}
				else if(newValue == triangleButton)
				{
					Main.model.setShape("TRIANGLE");
				}
			}
		});

		Main.model.shapeListProperty().addListener(new ListChangeListener<Shape>() {
			@Override
			public void onChanged(Change<? extends Shape> c) {
				while (c.next())
				{
					for (Shape r : c.getRemoved())
						root.getChildren().remove(r);

					for (Shape r : c.getAddedSubList())
					{
						r.setStroke(Color.BLACK);
						r.setFill(FILL_COLOR);
						root.getChildren().add(r);
					}
				}
			}
		});
		Main.iModel.getSelectedShapes().itemsProperty().addListener(new ListChangeListener<Shape>() {
			@Override
			public void onChanged(Change<? extends Shape> c) {
				deselectAll();
				for (Shape r : Main.iModel.getSelectedShapes().itemsProperty())
				{
					selectShape(r);
				}
			}
		});

		Main.iModel.selectRegionProperty().addListener(new ChangeListener<Rectangle>() {
			@Override
			public void changed(ObservableValue<? extends Rectangle> observable, Rectangle oldValue,
								Rectangle newValue) {
				root.getChildren().remove(oldValue);

				if (newValue !=null)
				{
					root.getChildren().add(newValue);
					newValue.setFill(new Color(0,0,.5,.3));
					newValue.setStroke(new Color(0,0,.5,1));
				}
			}
		});

		root = new Group();
		getChildren().add(root);
	}


	public void deselect(Shape r)
	{
		r.setFill(FILL_COLOR);
		r.setStrokeWidth(1);
	}

	public void deselectAll() {
		for (Shape s : Main.model.shapeListProperty())
		{
			deselect(s);
		}
	}

	public void selectShape(Shape node) {
		node.setFill(View.SELECTED_FILL_COLOR);
		node.setStrokeWidth(4);
	}
}
