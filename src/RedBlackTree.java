
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class RedBlackTree {

    Node root;
    Node TNULL;

    public RedBlackTree() {
        TNULL = new Node();
        TNULL.color = 0;
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }

    private Node searchTreeHelper(Node node, int key) {
        if (node == TNULL || key == node.data) {
            return node;
        }

        if (key < node.data) {
            return searchTreeHelper(node.left, key);
        }
        return searchTreeHelper(node.right, key);
    }

    // Arregla el árbol RBT luego de una eliminación
    private void fixDelete(Node x) {
        Node s;
        while (x != root && x.color == 0) {
            if (x == x.parent.left) {
                s = x.parent.right;
                if (s.color == 1) {
                    // Cuando el hermano del nodo es rojo
                    s.color = 0;
                    x.parent.color = 1;
                    leftRotate(x.parent);
                    s = x.parent.right;
                }

                if (s.left.color == 0 && s.right.color == 0) {
                    // Cuando el hermano es negro y los hijos del hermano son negros
                    s.color = 1;
                    x = x.parent;
                } else {
                    if (s.right.color == 0) {
                        // Cuando el hermano es negro, el hijo izquierdo de su hermano es rojo y el derecho negro
                        s.left.color = 0;
                        s.color = 1;
                        rightRotate(s);
                        s = x.parent.right;
                    }

                    // Contrario al caso anterior
                    s.color = x.parent.color;
                    x.parent.color = 0;
                    s.right.color = 0;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                s = x.parent.left;
                if (s.color == 1) {
                    // Cuando el hermano del nodo es rojo
                    s.color = 0;
                    x.parent.color = 1;
                    rightRotate(x.parent);
                    s = x.parent.left;
                }

                if (s.right.color == 0 && s.right.color == 0) {
                    // Cuando el hermano es negro y los hijos del hermano son negros
                    s.color = 1;
                    x = x.parent;
                } else {
                    if (s.left.color == 0) {
                        // Cuando el hermano es negro, el hijo izquierdo de su hermano es rojo y el derecho negro
                        s.right.color = 0;
                        s.color = 1;
                        leftRotate(s);
                        s = x.parent.left;
                    }

                    // Contrario al caso anterior
                    s.color = x.parent.color;
                    x.parent.color = 0;
                    s.left.color = 0;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = 0;
    }

    // Auxiliar de deleteNodeHelper. Se encarga de cambiar los apuntadores 
    // luego de una eliminación
    private void rbTransplant(Node u, Node v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    private void deleteNodeHelper(Node node, int key) {
        // Busca el nodo que contiene el key 
        Node z = TNULL;
        Node x, y;
        while (node != TNULL) {
            if (node.data == key) {
                z = node;
            }

            if (node.data <= key) {
                node = node.right;
            } else {
                node = node.left;
            }
        }

        if (z == TNULL) {
            JOptionPane.showMessageDialog(null, "No se encontró dicha cédula en el árbol");
            return;
        }

        y = z;
        int yOriginalColor = y.color;
        if (z.left == TNULL) {
            x = z.right;
            rbTransplant(z, z.right);
        } else if (z.right == TNULL) {
            x = z.left;
            rbTransplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                rbTransplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == 0) {
            fixDelete(x);
        }
    }

    // Arregla el árbol RBT luego de una inserción
    private void fixInsert(Node k) {
        Node u;
        while (k.parent.color == 1) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left; // tío
                if (u.color == 1) {
                    // Cuando el padre es rojo y el tío también
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        // Cuando el padre es el hijo derecho del abuelo y el nodo es hijo izquierdo
                        k = k.parent;
                        rightRotate(k);
                    }
                    // Cuando el padre es el hijo derecho del abuelo y el nodo es hijo derecho
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right; // tío

                if (u.color == 1) {
                    // Cuando el padre es rojo y el tío también
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        // Cuando el padre es el hijo derecho del abuelo y el nodo es hijo izquierdo
                        k = k.parent;
                        leftRotate(k);
                    }
                    // Cuando el padre es el hijo derecho del abuelo y el nodo es hijo derecho
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = 0;
    }

    // Busca en el árbol al nodo que contiene k y lo retorna
    public Node searchTree(int k) {
        return searchTreeHelper(this.root, k);
    }

    public String getInformation(int id) {
        Node node = searchTree(id);
        if (node != null) {
            String name = node.name;
            String surname = node.surname;
            if (name.equals("null")) {
                return "null";
            } else {
                return "Nombre:" + " " + name + "\n " + "Apellido:" + " " + surname;
            }
        } else {
            JOptionPane.showMessageDialog(null, "No existe esa cedula, por favor intente de nuevo");
            return "";
        }
    }

    // Encuentra el nodo de menor valor
    public Node minimum(Node node) {
        while (node.left != TNULL) {
            node = node.left;
        }
        return node;
    }

//    // Encuentra el nodo de mayor valor
    public Node maximum(Node node) {
        while (node.right != TNULL) {
            node = node.right;
        }
        return node;
    }

    // Rotación hacia la izquierda
    public void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    // Rotación hacia la derecha
    public void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    // Inserta un nuevo nodo al árbol y lo arregla si es necesario
    public void insert(String n, String s, int key) {
        if (searchTree(key) == TNULL) {
            // Inserción básica de un BST
            Node node = new Node();
            node.parent = null;
            node.data = key;
            node.name = n;
            node.surname = s;
            node.left = TNULL;
            node.right = TNULL;
            node.color = 1; // Todos los nuevos nodos son rojos (regla)

            Node y = null;
            Node x = this.root;

            while (x != TNULL) {
                y = x;
                if (node.data < x.data) {
                    x = x.left;
                } else {
                    x = x.right;
                }
            }
            node.parent = y;
            if (y == null) {
                root = node;
            } else if (node.data < y.data) {
                y.left = node;
            } else {
                y.right = node;
            }
            // Si el nuevo nodo es la raíz
            if (node.parent == null) {
                node.color = 0; // La raíz es negra (regla)
                return;
            }
            if (node.parent.parent == null) {
                return;
            }
            fixInsert(node);
        } else {
            JOptionPane.showMessageDialog(null, "Ya hay una persona con esa cédula en el árbol");
        }
    }

    // Elimina un nodo del árbol 
    public void deleteNode(int data) {
        deleteNodeHelper(this.root, data);
    }

    // Grafica el árbol
    public JPanel getdrawing() {
        return new RBTGraphicExpresion(this);
    }

    public boolean isEmpty() {
        return root == TNULL;
    }
}
