package imageviewer.control;

import imageviewer.ui.ImageDisplay;

public class PrevCommand implements Command{

    private final ImageDisplay imageDisplay;
    
    public PrevCommand(ImageDisplay imageDisplay) {
        this.imageDisplay = imageDisplay;
    }
    
    @Override
    public void execute() {
        imageDisplay.prevImage();
    }

}
