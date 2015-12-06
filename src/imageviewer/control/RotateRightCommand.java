package imageviewer.control;

import imageviewer.ui.ImageDisplay;

public class RotateRightCommand implements Command {

    private final ImageDisplay imageDisplay;

    public RotateRightCommand(ImageDisplay imageDisplay) {
        this.imageDisplay = imageDisplay;
    }
    
    @Override
    public void execute() {
        this.imageDisplay.rotateRight();
    }

}
