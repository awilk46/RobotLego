package core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import matrix.Matrix;
import window.ContentPane;
import window.Rule;

/**
 * Class contain Frame and hadle his actions
 *
 * @author Kalwador
 * @author Wilk
 * @since 02.04.2017
 */
public class VisualizationGUI extends JFrame implements MouseListener {

    /**
     * Size of visualization window
     */
    public static int windowPreferedWidth = 1200;
    public static int windowPreferedHeight = 700;

    /**
     * Default dimension of every rectangle in pixels
     */
    public static short RESOLUTION;

    public static window.ContentPane contentPane;

    /**
     * Matrix contains data from robot
     *
     * @see 0 - obszar pusty;
     * @see 1 - obszar zajety;
     * @see 2 - obszar nieznany;
     */
    public Matrix<Short> matrix;

    /**
     * Main Frame, contains everything;
     */
    private JFrame frame;
    public JScrollPane scroll;

    /**
     * Those variables contain actual mouse position in window. There are
     * actualized on 'click' action.
     */
    public static double mouseX;
    public static double mouseY;

    /**
     * Top bar, and side bar
     */
    private window.MenuBar menuBar;
    private window.ToolBar toolBar;

    /**
     * Rules outside the cavnas
     */
    private Rule columnView;
    private Rule rowView;

    /**
     * Class that contains camera movement information for drawing
     */
    public static utils.Camera camera;

    /**
     * Default Constructor set up main options
     */
    public VisualizationGUI() {

        /**
         * Default size and zoom of every rectangle
         */
        RESOLUTION = 10;

        matrix = utils.TXTMatrixLoader.loadData("matrix.txt");
//        matrix = new Matrix<>(400, 400);

        camera = new utils.Camera(matrix.getWidth(), matrix.getHeight());

        contentPane = new ContentPane(matrix, camera);

        columnView = new Rule(Rule.HORIZONTAL, true);
        rowView = new Rule(Rule.VERTICAL, true);
    }

    /**
     * Main method that runs all program
     */
    public void run() {

        /**
         * Create Window
         */
        frame = new JFrame("Obstacle Visualization for EV3 Robot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /**
         * Top Menu
         */
        menuBar = new window.MenuBar();
        frame.setJMenuBar(menuBar.getMenuBar(this.getRootPane()));

        toolBar = new window.ToolBar();
        frame.add(toolBar.getToolBar(this.getRootPane()), BorderLayout.WEST);

         /**
         * Canvas
         */
        scroll = new JScrollPane(contentPane);
        scroll.setViewportBorder(
                BorderFactory.createLineBorder(Color.black));
        scroll.getVerticalScrollBar().setUnitIncrement(30); //Scroll speed
        scroll.addMouseListener(this); //Scroll Pane mouse Listener

        /**
         * Rules
         */
        columnView.setPreferredWidth(camera.contentPaneWidth);
        rowView.setPreferredHeight(camera.contentPaneHeight);

        scroll.setColumnHeaderView(columnView);
        scroll.setRowHeaderView(rowView);
        
        frame.add(scroll, BorderLayout.CENTER);

        /**
         * Display the window.
         */
        frame.pack();
        frame.setSize(windowPreferedWidth, windowPreferedHeight);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseX = MouseInfo.getPointerInfo().getLocation().getX() - frame.getLocationOnScreen().x;
        mouseY = MouseInfo.getPointerInfo().getLocation().getY() - frame.getLocationOnScreen().y;

        /**
         * Kilka pikseli wynikających z obramowania okna
         */
        mouseX -= 84;
        mouseY -= 89;

        Short a = 1;
        matrix.putObject(
                ((int) ((mouseY + scroll.getVerticalScrollBar().getValue()) / 10)),
                ((int) ((mouseX + scroll.getHorizontalScrollBar().getValue()) / 10)),
                a);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = MouseInfo.getPointerInfo().getLocation().getX() - frame.getLocationOnScreen().x;
        mouseY = MouseInfo.getPointerInfo().getLocation().getY() - frame.getLocationOnScreen().y;

        /**
         * Kilka pikseli wynikających z obramowania okna
         */
        mouseX -= 84;
        mouseY -= 89;

        Short a = 1;
        matrix.putObject(
                ((int) ((mouseY + scroll.getVerticalScrollBar().getValue()) / 10)),
                ((int) ((mouseX + scroll.getHorizontalScrollBar().getValue()) / 10)),
                a);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        scroll.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
