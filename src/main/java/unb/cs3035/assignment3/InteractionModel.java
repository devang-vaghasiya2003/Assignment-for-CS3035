package unb.cs3035.assignment3;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.shape.Rectangle;

public class InteractionModel {
    private SelectionGroup selectedShapes;
    private SimpleObjectProperty<Rectangle> selectRegion;
    private double selectStartX = 0, selectStartY = 0;
    private SelectionGroup copiedShapes;

    public InteractionModel()
    {
        selectedShapes = new SelectionGroup();
        copiedShapes = new SelectionGroup();
        selectRegion = new SimpleObjectProperty<Rectangle>();
    }

    public SimpleObjectProperty<Rectangle> selectRegionProperty()
    {
        return selectRegion;
    }

    public SelectionGroup getSelectedShapes(){
        return selectedShapes;
    }

    public SelectionGroup getCopiedShapes() {return copiedShapes;}

    public void startSelectRegion(double x, double y)
    {
        selectRegion.set(new Rectangle(x,y,0,0));
    }

    public void updateSelectRegion(double startX, double startY, double endX, double endY)
    {
        Rectangle selectRect = selectRegionProperty().getValue();

        selectRect.setX(Math.min(startX, endX));
        selectRect.setY(Math.min(startY, endY));
        selectRect.setWidth(Math.abs(endX - startX));
        selectRect.setHeight(Math.abs(endY - startY));
    }
}