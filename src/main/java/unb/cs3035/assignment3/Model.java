package unb.cs3035.assignment3;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;


public class Model {
	private SimpleListProperty<Shape> shapeListProperty;

	private double shapeLength;

	private String shape;

	public Model(int shapeLength)
	{
		ArrayList<Shape> list = new ArrayList<>();
		ObservableList<Shape> observableList = (ObservableList<Shape>) FXCollections.observableArrayList(list);
		shapeListProperty = new SimpleListProperty<Shape>(observableList);

		this.shapeLength = shapeLength;
		this.shape = "SQUARE";
	}

	public SimpleListProperty<Shape> shapeListProperty()
	{
		return shapeListProperty;
	}

	public double getShapeLength()
	{
		return shapeLength;
	}


	public void addShape(double x, double y)
	{
		if(this.shape.equals("SQUARE"))
		{
			double squareX = x - Main.model.getShapeLength()/2;
			double squareY = y - Main.model.getShapeLength()/2;
			double squareLength = Main.model.getShapeLength();

			Rectangle newSquare = new Rectangle(squareX, squareY, squareLength, squareLength);
			shapeListProperty.add(newSquare);
		}
		if(this.shape.equals("CIRCLE"))
		{
			double circleX = x;
			double circleY = y;
			double circleRadius = Main.model.getShapeLength()/2;

			Circle newCircle = new Circle(circleX, circleY, circleRadius);
			shapeListProperty.add(newCircle);

		}
		if(this.shape.equals("TRIANGLE"))
		{
			double triangleHeight = Main.model.getShapeLength();
			double triangleLength = (1.1547) * (triangleHeight);

			double pointA_X = x;
			double pointA_Y = y - (2.0/3.0) * (triangleHeight);

			double pointB_X = x - (1.0/2.0) * (triangleLength);
			double pointB_Y = y + (1.0/3.0) * (triangleHeight);

			double pointC_X = x + (1.0/2.0) * (triangleLength);
			double pointC_Y = y + (1.0/3.0) * (triangleHeight);

			Polygon newTriangle = new Polygon();
			newTriangle.getPoints().addAll(new Double[]{
					pointA_X, pointA_Y,
					pointB_X, pointB_Y,
					pointC_X, pointC_Y });
			shapeListProperty.add(newTriangle);

		}

	}

	private Shape getShapeAt(int x, int y)
	{
		Shape shape = null;

		for (Shape s : shapeListProperty)
		{
			if (s.contains(x, y))
			{
				shape = s;
			}
		}

		return shape;
	}

	public void setShape(String s)
	{
		this.shape = s;
	}

	public SimpleListProperty<Shape> getShapeListProperty()
	{
		return  shapeListProperty;
	}

}
