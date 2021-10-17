/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mey
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 *
 * @author Mey
 */
public class RBTGraphicExpresion extends javax.swing.JPanel {

    private RedBlackTree RBTree;
    private HashMap nodePosition = null;
    private HashMap subtreeSizes = null;
    private boolean dirty = true;
    private int parent2child = 20, child2child = 30;
    private Dimension empty = new Dimension(0, 0);
    private FontMetrics fm = null;

    /**
     * Creates new form TreeGraphicExpresion
     */
    /**
     * Constructor de la clase ArbolExpresionGrafico. El constructor permite
     * inicializar los atributos de la clase ArbolExpresionGrafico y llama al
     * método repaint(), que es el encargado de pintar el Arbol.
     *
     * @param miExpresion: dato de tipo ArbolExpresion que contiene el Arbol a
     * dibujar.
     */
    public RBTGraphicExpresion(RedBlackTree RBTree) {
        initComponents();
        this.RBTree = RBTree;
        this.setBackground(Color.WHITE);
        nodePosition = new HashMap();
        subtreeSizes = new HashMap();
        dirty = true;
        repaint();
    }

    /**
     * Obtiene las posiciones de los respectivos subárboles y de cada nodo que
     * forma parte de él, para conocer en que posición van a ir dibujados los
     * rectángulos que representarán el árbol de la expresión.
     */
    private void returnPositions() {
        nodePosition.clear();
        subtreeSizes.clear();
        Node root = this.RBTree.root;
        if (root != null) {
            SubtreeSize(root);
            calculatePosition(root, Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
        }
    }

    /**
     * Calcula el tamaño de cada subárbol y lo agrega al objeto subtreeSizes de
     * la clase que contendrá la coleccion de todos los subárboles que contiene
     * el arbol.
     *
     * @param node:Objeto de la clase Node que se utiliza como referencia
     * calcular el tamaño de cada subárbol.
     * @return Dimension con el tamaño de cada subárbol.
     */
    private Dimension SubtreeSize(Node node) {
        if (node == null) {
            return new Dimension(0, 0);
        }

        Dimension ld = SubtreeSize(node.left);
        Dimension rd = SubtreeSize(node.right);

        int height = fm.getHeight() + parent2child + Math.max(ld.height, rd.height);
        int width = ld.width + child2child + rd.width;

        Dimension dimension = new Dimension(width, height);
        subtreeSizes.put(node, dimension);

        return dimension;
    }

    /**
     * Obtiene la ubicación de cada nodo de cada subárbol inserta cada nodo que
     * tiene la ubicación y la información específica de dónde va a ser
     * dibujado.
     *
     * @param node: Objeto de tipo Node que se utiliza como referencia para
     * calcular la ubicación de los nodos del arbol.
     * @param left: int apuntando a la izquierda.
     * @param right: int apuntando a la derecha.
     * @param top: int con el tope.
     */
    private void calculatePosition(Node node, int left, int right, int top) {
        if (node == null) {
            return;
        }

        Dimension leftDimension = (Dimension) subtreeSizes.get(node.left);
        if (leftDimension == null) {
            leftDimension = empty;
        }

        Dimension rightDimension = (Dimension) subtreeSizes.get(node.right);
        if (rightDimension == null) {
            rightDimension = empty;
        }

        int center = 0;

        if (right != Integer.MAX_VALUE) {
            center = right - rightDimension.width - child2child / 2;
        } else if (left != Integer.MAX_VALUE) {
            center = left + leftDimension.width + child2child / 2;
        }
        int width = fm.stringWidth(node.data + "");

        nodePosition.put(node, new Rectangle(center - width / 2 - 3, top, width + 6, fm.getHeight()));

        calculatePosition(node.left, Integer.MAX_VALUE, center - child2child / 2, top + fm.getHeight() + parent2child);
        calculatePosition(node.right, center + child2child / 2, Integer.MAX_VALUE, top + fm.getHeight() + parent2child);
    }

    /**
     * Dibuja el árbol con las ubicaciones de los nodos y los subarboles
     * calculados en funciones pasadas.
     *
     * @param g: Objeto de la clase Graphics2D que permite realizar el dibujo de
     * las líneas, rectangulos y del String de la información que contiene el
     * Nodo.
     * @param node: Objeto de la clase Node que se utiliza como referencia para
     * dibujar el árbol.
     * @param puntox: int con la posición en el eje x desde donde se va a
     * dibujar la línea hasta el destino.
     * @param puntoy: int con la posición en el eje y desde donde se va a
     * dibujar la línea hasta el destino.
     * @param yoffs: int con la altura del FontMetrics.
     */
    private void drawTree(Graphics2D g, Node node, int puntox, int puntoy, int yoffs) {
        if (node == null) {
            return;
        }
        Rectangle rectangle = (Rectangle) nodePosition.get(node);
        if(node.color == 0){
            g.setColor(Color.black);
        }else{
            g.setColor(Color.red);
        }
        if(node != RBTree.TNULL) {
            g.draw(rectangle);
            g.drawString(node.data + "", rectangle.x + 3, rectangle.y + yoffs);
            if (puntox != Integer.MAX_VALUE) {
                g.setColor(Color.black);
                g.drawLine(puntox, puntoy, (int) (rectangle.x + rectangle.width / 2), rectangle.y);
            }
        }
        drawTree(g, node.left, (int) (rectangle.x + rectangle.width / 2), rectangle.y + rectangle.height, yoffs);
        drawTree(g, node.right, (int) (rectangle.x + rectangle.width / 2), rectangle.y + rectangle.height, yoffs);

    }

    /**
     * Super el metodo paint y se encarga de pintar el árbol.
     *
     * @param g: Objeto de la clase Graphics.
     */
    public void paint(Graphics g) {
        super.paint(g);
        fm = g.getFontMetrics();

        if (dirty) {
            returnPositions();
            dirty = false;
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(getWidth() / 2, parent2child);
        drawTree(g2d, this.RBTree.root, Integer.MAX_VALUE, Integer.MAX_VALUE,
                fm.getLeading() + fm.getAscent());
        fm = null;
    }

        /**
         * This method is called from within the constructor to initialize the
         * form. WARNING: Do NOT modify this code. The content of this method is
         * always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
