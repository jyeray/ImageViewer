package imageviewer;

import imageviewer.control.*;
import imageviewer.persistence.FileImageLoader;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class SwingApplication extends JFrame implements MouseWheelListener, MouseMotionListener, MouseListener{
    
    public static void main(String[] args) {
        new SwingApplication(args);
    }

    private final Map<String,Command> commands= new HashMap<>();
    private final SwingImagePanel swingImagePanel;
    
    public SwingApplication(String[] args) {
        if (args.length != 0) {
            this.swingImagePanel = new SwingImagePanel(new FileImageLoader().readThis(args[0]));
        } else {
            this.swingImagePanel = new SwingImagePanel();
        }
        deployUI();
        setCommands();
        this.addMouseWheelListener(this);
        this.addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                commands.get("resize").execute();
            }
            @Override
            public void componentMoved(ComponentEvent e) {}
            @Override
            public void componentShown(ComponentEvent e) {}
            @Override
            public void componentHidden(ComponentEvent e) {}
        });
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }

    private void setCommands() {
        commands.put("next", new NextCommand(swingImagePanel));
        commands.put("prev", new PrevCommand(swingImagePanel));
        commands.put("open", new OpenCommand(swingImagePanel,new FileImageLoader()));
        commands.put("zoomIn", new ZoomInCommand(swingImagePanel));
        commands.put("zoomOut", new ZoomOutCommand(swingImagePanel));
        commands.put("resize", new ResizeCommand(swingImagePanel));
        commands.put("rotateLeft", new RotateLeftCommand(swingImagePanel));
        commands.put("rotateRight", new RotateRightCommand(swingImagePanel));
    }

    private void deployUI() {
        this.setTitle("Image Viewer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(400, 400));
        this.getContentPane().add(swingImagePanel, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(1000, 800));
        this.pack();
        this.getContentPane().add(bottomMenu(), BorderLayout.SOUTH);
        this.setJMenuBar(menuBar());
        this.setVisible(true);
    }

    private JPanel bottomMenu() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(prevButton());
        panel.add(nextButton());
        panel.add(rotateLeftButton());
        panel.add(rotateRightButton());
        return panel;
    }

    private JButton prevButton() {
        JButton button = new JButton("<");
        button.addActionListener(execute("prev"));
        return button;
    }
    
    private JButton nextButton() {
        JButton button = new JButton(">");
        button.addActionListener(execute("next"));
        return button;
    }
    
    private JButton rotateLeftButton() {
        JButton button = new JButton("↻");
        button.addActionListener(execute("rotateLeft"));
        return button;
    }

    private JButton rotateRightButton() {
        JButton button = new JButton("↺");
        button.addActionListener(execute("rotateRight"));
        return button;
    }

    private JMenuBar menuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu());
        return menuBar;
    }

    private JMenu fileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(openOperation());
        return fileMenu;
    }

    private JMenuItem openOperation() {
        JMenuItem openItem = new JMenuItem("Open...");
        openItem.addActionListener(execute("open"));
        return openItem;
    }

    private ActionListener execute(String command) {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get(command).execute();
            }
        };
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            commands.get("zoomIn").execute();
        }else{
            commands.get("zoomOut").execute();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        swingImagePanel.setDrag(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        swingImagePanel.setRefPoint(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
