/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mey
 */
import javax.swing.JPanel;

/**
 *
 * @author Mey
 */
public class RBTSimulator {

    RedBlackTree RBTree;
    Functions funcs = new Functions();

    /**
     * Usa una clave de tipo int de parametro para insertarla en el arbol
     *
     * @param id int usado como parametro en la funcion para poder insertar el
     * nodo en el arbol
     * @return Retorna un booleano para comprobar que se ingresó y que no era un
     * valor repetido
     */
    public void insert(String name, String surname, int id) {
        RBTree.insert(name, surname, id);
    }

    /**
     * Pide un entero como parametro para saber la clave que se quiere borrar
     *
     * @param id int usado como parametro de la funcion para poder eliminar el
     * nodo con esa clave
     */
    public void delete(int id) {
        RBTree.deleteNode(id);
    }

    public String getInfo(int id) {
        return RBTree.getInformation(id);
    }

    /**
     * Obtiene el dibujo del arbol
     *
     * @return JPanel para poder plasmar la información en el interfaz
     */
    public JPanel getDrawing() {
        return this.RBTree.getdrawing();
    }
}
