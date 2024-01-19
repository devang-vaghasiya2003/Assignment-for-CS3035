# Assignment from CS3035

## Video demonstration

- [Link for the Video](https://github.com/devang-vaghasiya2003/Assignment_From_CS3035/blob/main/Assignment_Video_MVC.mp4)

## Overview

- to gain experience with the
Model-View-Controller pattern and build an understanding of the inner
workings of common user interactions.

## Part 1: Toolbar

- The toolbar has two sets of buttons that contain only icons.

- The first set are ToggleButtons (representing shapes) that only allow one option to be selected at a time (when associated with a common ToggleGroup).

- The leftmost ToggleButton is depressed by default.

- The two sets of buttons are separated by a Separator object (see Toolbar Tutorial). The second set of buttons are for ‘Cut’ and ‘Paste’ operation.

- ToolTips is added to each of the buttons, so that after a brief hovering
over a button with the mouse, a label for the button appears.

## Part 2: Different Shapes

- A user will be able to select the button at the top of the app (Square, Circle or Button) and create the correct item.

- Creating items will work by simply depressing the desired shape button and clicking on an empty part of the canvas.

- All shapes have the same size; i.e., square side length = circle diameter = triangle height.

## Part 3: Cutting and Pasting

- selected items are cut from the scene. Any operations can then continue with the interface. If the Paste button is clicked, the “cut” items are returned to their original place, and are the active selection.

- If additional objects are cut while there are already other objects cut, then the previously cut objects are discarded (similar to how normal cut-and-paste works). The Paste button only works once.

- That is if items are pasted once, clicking Paste again, won’t paste additional copies of the objects to the scene. After pasting, the selected objects should be still selected.

- If items are selected, and an empty space on the canvas is clicked then any selected objects become unselected.

## Part 4: Rubber-band Drag Selections from Multiple Directions

- rubber band rectangle selection technique so that drag selections can be done in any direction.
