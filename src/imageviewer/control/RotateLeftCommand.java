package imageviewer.control;

import imageviewer.ui.ImageDisplay;

public class RotateLeftCommand implements Command {
    
    private final ImageDisplay imageDisplay;

    public RotateLeftCommand(ImageDisplay imageDisplay) {
        this.imageDisplay = imageDisplay;
    }

    @Override
    public void execute() {
        this.imageDisplay.rotateLeft();
    }
}
