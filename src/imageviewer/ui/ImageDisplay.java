package imageviewer.ui;

import imageviewer.model.Image;

public interface ImageDisplay {
    public Image getImage();
    public void nextImage();
    public void prevImage();
    public void show(Image image);
    public void zoomIn();
    public void zoomOut();
    public void resize();
}
