package imageviewer;

import imageviewer.model.Image;
import imageviewer.ui.ImageDisplay;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

    public SwingImagePanel(Image image) {
        super();
        this.image = image;
        this.getDefaultSizes();
        this.repaint();
    }
    
    private void getDefaultSizes(){
        BufferedImage img = (BufferedImage) this.image.getBitmap();
        originalImageHeight=img.getHeight();
        originalImageWidth=img.getWidth();
        this.zoom = 1.0;
        this.degrees = 0;
    }
    
    @Override
    public void resize(){
        int imageHeight = originalImageHeight;
        int imageWidth = originalImageWidth;
        if( degrees != 0 && degrees != 180 ){
            imageHeight = originalImageWidth;
            imageWidth = originalImageHeight;
        }
        int difHeight = imageHeight - this.getHeight();
        int difWidth = imageWidth - this.getWidth();
            if (difHeight <= 0 && difWidth <= 0){
                this.displayHeight = imageHeight;
                this.displayWidth = imageWidth;
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
        if(displayHeight == 0 || displayWidth == 0){
            this.resize();
        }
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        g2.translate(this.getWidth()/2, this.getHeight()/2);
        g2.scale(zoom, zoom);
        g2.translate(-(this.getWidth()/2), -(this.getHeight()/2));
        g2.rotate(Math.toRadians(this.degrees), this.getWidth()/2, this.getHeight()/2);
        g2.drawImage((BufferedImage) image.getBitmap(), this.getWidth()/2 - displayWidth/2, this.getHeight()/2 - displayHeight/2, displayWidth, displayHeight, this);
    }
    
    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public void nextImage() {
        this.image = image.getNext();
        this.getDefaultSizes();
        this.resize();
        this.repaint();
    }

    @Override
    public void prevImage() {
        this.image = image.getPrev();
        this.getDefaultSizes();
        this.resize();
        this.repaint();
    }

    @Override
    public void show(Image image) {
        this.image = image;
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
}
