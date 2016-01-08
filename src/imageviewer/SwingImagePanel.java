package imageviewer;

import imageviewer.model.Image;
import imageviewer.ui.ImageDisplay;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class SwingImagePanel extends JPanel implements ImageDisplay {
    
    private Image image;
    private int displayHeight = 0;
    private int displayWidth = 0;
    private int originalImageHeight;
    private int originalImageWidth;
    private double zoom;
    private int degrees = 0;
    private int dragX = 0;
    private int dragY = 0;
    private int refDragX = 0;
    private int refDragY = 0;
    private BufferedImage imageBitMap;

    public SwingImagePanel(Image image) {
        super();
        this.image = image;
        this.imageBitMap = (BufferedImage) image.getBitmap();
        this.getDefaultSizes();
        this.repaint();
    }

    public SwingImagePanel() {
        super();
    }
    
    private void getDefaultSizes(){
        BufferedImage img = (BufferedImage) this.image.getBitmap();
        originalImageHeight=img.getHeight();
        originalImageWidth=img.getWidth();
        this.zoom = 1.0;
        this.degrees = 0;
        this.dragX = 0;
        this.dragY = 0;
    }
    
    @Override
    public void resize(){
        int difHeight = originalImageHeight - this.getHeight();
        int difWidth = originalImageWidth - this.getWidth();
            if (difHeight <= 0 && difWidth <= 0){
                this.displayHeight = originalImageHeight;
                this.displayWidth = originalImageWidth;
            }else{
                double aspectRatio = (double)originalImageHeight/originalImageWidth;
                this.displayHeight = (this.degrees == 0 || this.degrees == 180 )  ? this.getHeight() : this.getWidth();
                this.displayWidth = (int) (this.displayHeight/aspectRatio);
                if (displayWidth > this.getWidth()){
                    this.displayWidth = (this.degrees == 0 || this.degrees == 180) ? this.getWidth() : this.getHeight();
                    this.displayHeight = (int) (this.displayWidth * aspectRatio); 
                }
            }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image != null){
            if(displayHeight == 0 || displayWidth == 0){
                this.resize();
            }
            Graphics2D g2 = (Graphics2D) g;
            super.paintComponent(g2);
            g2.translate(this.getWidth()/2, this.getHeight()/2);
            g2.scale(zoom, zoom);
            g2.translate(-(this.getWidth()/2), -(this.getHeight()/2));
            g2.rotate(Math.toRadians(this.degrees), this.getWidth()/2, this.getHeight()/2);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(imageBitMap, this.getWidth()/2 - displayWidth/2 + dragX, this.getHeight()/2 - displayHeight/2 + dragY, displayWidth, displayHeight, this);
        }
    }
    
    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public void nextImage() {
        this.show(image.getNext());
    }

    @Override
    public void prevImage() {
        this.show(image.getPrev());
    }

    @Override
    public void show(Image image) {
        this.image = image;
        this.imageBitMap = (BufferedImage) image.getBitmap();
        this.getDefaultSizes();
        this.resize();
        this.repaint();
    }

    @Override
    public void zoomIn() {
        if (zoom < 5.0) {
            this.zoom = zoom + 0.2;
            this.repaint();
        }
    }

    @Override
    public void zoomOut() {
        if (zoom > 1.0) {
            this.zoom = zoom - 0.2;
            if (zoom < 1.1){
                this.dragX = 0;
                this.dragY = 0;
            }
            this.repaint();
        }
    }

    @Override
    public void rotateLeft() {
        this.degrees += 90;
        if(this.degrees > 270)
            this.degrees = 0;
        this.resize();
        this.repaint();
    }

    @Override
    public void rotateRight() {
        this.degrees -= 90;
        if(this.degrees < 0)
            this.degrees = 270;
        this.resize();
        this.repaint();
    }

    @Override
    public void setDrag(int x, int y) {
        if (image != null) {
            this.dragX = (Math.abs(dragX + x - refDragX) < displayWidth/2 && zoom > 1.0) ? dragX + x - refDragX : dragX;
            this.dragY = (Math.abs(dragY + y - refDragY) < displayHeight/2 && zoom > 1.0) ? dragY + y - refDragY : dragY;
            this.refDragX = x;
            this.refDragY = y;
            this.repaint();
        }
    }
    
    public void setRefPoint(int x, int y){
        this.refDragX = x;
        this.refDragY = y;
    }
}
