package imageviewer.control;

import imageviewer.ui.ImageDisplay;

public class ZoomInCommand implements Command {

    private final ImageDisplay imageDisplay;

    public ZoomInCommand(ImageDisplay imageDisplay) {
        this.imageDisplay = imageDisplay;
    }
    
    @Override
    public void execute() {
        imageDisplay.zoomIn();
    }
}
