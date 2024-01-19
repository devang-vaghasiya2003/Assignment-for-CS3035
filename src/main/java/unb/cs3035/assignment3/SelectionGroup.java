package unb.cs3035.assignment3;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class SelectionGroup {
	private SimpleListProperty<Shape> items;
	private BoundingBox bounds = new BoundingBox(0, 0, 0, 0);

	public SelectionGroup()
	{
		ArrayList<Shape> list = new ArrayList<Shape>();
		ObservableList<Shape> observableList = (ObservableList<Shape>) FXCollections.observableArrayList(list);
		items = new SimpleListProperty<Shape>(observableList);

		itemsProperty().addListener(new ListChangeListener<Shape>() {
			@Override
			public void onChanged(Change<? extends Shape> c) {
				calculateBoundingBox();
			}
		});
	}

	public BoundingBox getBoundingBox()
	{
		calculateBoundingBox();
		return bounds;
	}

	public SimpleListProperty<Shape> itemsProperty()
	{
		return items;
	}

	private void calculateBoundingBox()
	{
		double 	minX = Double.MAX_VALUE, minY = Double.MAX_VALUE,
				maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
		for (Shape r : itemsProperty())
		{
			if(r.getClass() == Rectangle.class)
			{
				Rectangle rectangle = (Rectangle) r;

				if (rectangle.getX() < minX)
					minX = rectangle.getX();
				if (rectangle.getY() < minY)
					minY = rectangle.getY();
				if (rectangle.getX() + rectangle.getWidth() > maxX)
					maxX = rectangle.getX() + rectangle.getWidth();
				if (rectangle.getY() + rectangle.getHeight() > maxY)
					maxY = rectangle.getY() + rectangle.getHeight();

			}
			else if(r.getClass() == Circle.class)
			{
				Circle circle = (Circle) r;

				if((circle.getCenterX() - circle.getRadius()) < minX)
					minX = circle.getCenterX() - circle.getRadius();
				if((circle.getCenterY() - circle.getRadius()) < minY)
					minY = circle.getCenterY() - circle.getRadius();
				if((circle.getCenterX() + circle.getRadius()) > maxX)
					maxX = circle.getCenterX() + circle.getRadius();
				if((circle.getCenterY() + circle.getRadius()) > maxY)
					maxY = circle.getCenterY() + circle.getRadius();

			}
			else if(r.getClass() == Polygon.class)
			{
				Polygon triangle = (Polygon) r;

				for (int i = 0; i < triangle.getPoints().size(); i += 2)
				{
					double x = triangle.getPoints().get(i);
					double y = triangle.getPoints().get(i + 1);

					minX = Math.min(minX, x);
					minY = Math.min(minY, y);
					maxX = Math.max(maxX, x);
					maxY = Math.max(maxY, y);
				}

			}

		}
		bounds = new BoundingBox(minX, minY, maxX - minX, maxY - minY);
	}
}
