package unb.cs3035.assignment3;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class Controller {
	public enum State {READY, DRAG_SELECTION_STARTED, DRAG_ITEMS_STARTED, CUTTING}

	private State state;

	private List<Shape> cutObjects = new ArrayList<>();

	public Controller()
	{
		Main.view.addEventHandler(MouseEvent.ANY, new MouseHandler());
		state = State.READY;
	}

	public class MouseHandler implements EventHandler<MouseEvent>
	{
		private double prevX = 0, prevY = 0;
		@Override
		public void handle(MouseEvent e) {
			switch(state)
			{
				case READY:

					if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
						prevX = e.getSceneX();
						prevY = e.getSceneY();

						if (Shape.class.isAssignableFrom(e.getTarget().getClass()))
						{
							Shape node = ((Shape) e.getTarget());
							node.toFront();

							if (!Main.iModel.getSelectedShapes().itemsProperty().contains(node))
							{
								if (!e.isControlDown())
									Main.iModel.getSelectedShapes().itemsProperty().clear();

								Main.iModel.getSelectedShapes().itemsProperty().add(node);
							}
							else if (e.isControlDown())
							{
								Main.iModel.getSelectedShapes().itemsProperty().remove(node);
							}
						}
						else
						{
							Main.iModel.getSelectedShapes().itemsProperty().clear();
						}
					}
					else if (e.getEventType()==MouseEvent.DRAG_DETECTED)
					{
						if (e.getTarget().getClass() == Main.view.getClass())
						{
							state = State.DRAG_SELECTION_STARTED;
							Main.iModel.startSelectRegion(e.getX(), e.getY());
						}
						else if (Shape.class.isAssignableFrom(e.getTarget().getClass()))
						{
							state = State.DRAG_ITEMS_STARTED;
						}
					}
					else if (e.getEventType()==MouseEvent.MOUSE_RELEASED)
					{
						if (e.getTarget().getClass()==Main.view.getClass())
						{
							Main.model.addShape(e.getX(), e.getY());
						}

						if (Shape.class.isAssignableFrom(e.getTarget().getClass()))
						{
							if (!e.isControlDown())
							{
								Main.iModel.getSelectedShapes().itemsProperty().clear();
							}
						}
					}
					break;	//end State.READY

				case DRAG_SELECTION_STARTED:
					if (e.getEventType() == MouseEvent.MOUSE_DRAGGED)
					{
						Main.iModel.updateSelectRegion(prevX, prevY, e.getX(), e.getY());
					}
					else if (e.getEventType() == MouseEvent.MOUSE_RELEASED)
					{
						state = State.READY;
						selectObjectsWithRegion();
						Main.iModel.selectRegionProperty().setValue(null);
					}
					break;// end State.DRAG_SELECTION_STARTED

				case DRAG_ITEMS_STARTED:
					if (e.getEventType()==MouseEvent.MOUSE_DRAGGED)
					{
						moveSelected(e.getSceneX() - prevX, e.getSceneY() - prevY);
						prevX = e.getSceneX();
						prevY = e.getSceneY();
					}

					else if (e.getEventType()==MouseEvent.MOUSE_RELEASED)
					{
						state = State.READY;
					}
					break; //end State.DRAG_ITEMS_STARTED
				case CUTTING:
					if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
						state = State.READY;
					}
					break;
			}//end switch(state)
		}

	}

	private void selectObjectsWithRegion()
	{
		Rectangle selectRegion = Main.iModel.selectRegionProperty().getValue();
		Main.iModel.getSelectedShapes().itemsProperty().clear();

		if (selectRegion != null)
		{
			for (Shape s : Main.model.shapeListProperty())
			{
				Point2D topLeft = new Point2D(s.getBoundsInParent().getMinX(), s.getBoundsInParent().getMinY());
				Point2D bottomRight = new Point2D(s.getBoundsInParent().getMaxX(), s.getBoundsInParent().getMaxY());

				if (selectRegion.contains(topLeft) && selectRegion.contains(bottomRight))
				{
					Main.iModel.getSelectedShapes().itemsProperty().add(s);
				}
			}
		}
	}
	private void moveSelected(double addX, double addY)
	{
		for (Shape r : Main.iModel.getSelectedShapes().itemsProperty())
		{
			r.setTranslateX(r.getLayoutX() + r.getTranslateX() + addX);
			r.setTranslateY(r.getLayoutY() + r.getTranslateY() + addY);
		}
	}

	public void cutSelectedShapes() {
		
		List<Shape> selectedShapes = new ArrayList<>(Main.iModel.getSelectedShapes().itemsProperty());
		cutObjects.clear();
		cutObjects.addAll(selectedShapes);
		Main.model.getShapeListProperty().removeAll(selectedShapes);
		
		this.state = State.CUTTING;
	}

	public void pasteSelectedShapes()
	{
		if (!cutObjects.isEmpty()) {
			for (Shape shape : cutObjects) {
				Main.model.shapeListProperty().add(shape);
				shape.setFill(View.SELECTED_FILL_COLOR);
			}
			cutObjects.clear();
			this.state = State.READY;

		}

	}
}
