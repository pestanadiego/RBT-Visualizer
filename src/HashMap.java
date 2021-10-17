/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mey
 */
public class HashMap {

    private NodeHash pFirst;
    private NodeHash pLast;
    private int size;

    // Constructor
    public HashMap() {
        this.pFirst = null;
        this.pLast = null;
        this.size = 0;
    }
    /**
     * Agrega un objeto como llave y otro objecto como valor, de tal manera que para
     * acceder al valor, necesitas insertar la llave
     * @param key Llave de tipo Object para poder aceptar cualquier tipo de dato
     * @param Value Valor de tipo Object para poder aceptar cualquier tipo de dato
     */
    public void put(Object key, Object Value){
        Object tInfo[] = new Object[2];
        tInfo[0] = key;
        tInfo[1] = Value;
        insertLast(tInfo);
    }
    /**
     * Limpia el HashMap, eliminando todo su contenido
     */
    public void clear(){
        this.pFirst = this.pLast = null;
        this.size = 0;
    }
    /**
     * Te permite obtener el Value ingresanto como parametro a la Llave
     * @param key Llave de tipo Node que permitirá obtener el Value
     * @return Value de tipo Objeto
     */
    public Object get(Node key){
        return searchKey(key);
    }
    /**
     * Funcion auxiliar que permite hallar el Value ingresando la Key
     * @param key Llave de tipo Object 
     * @return Value de tipo object
     */
    private Object searchKey(Object key){
        NodeHash aux = this.getpFirst();
        while (aux != null && key != aux.gettInfo()[0]){
            aux = aux.getpNext();
        }
        if (aux == null){
            return aux;
        }else{
        return aux.gettInfo()[1];
        }
    }
    
    /**
     * Auxiliar de la funcion Put, el cual inserta de ultimo en el hashmaplos valores de key y value.
     * @param tInfo Arrglo que ingresará en el espacio 0 al key y en el 1 al value
     */
    private void insertLast(Object tInfo[]) {
        NodeHash newNode = new NodeHash(tInfo);
        if (isEmpty()) {
            pFirst = newNode;
            pLast = newNode;
        } else {
            pLast.setpNext(newNode);
            pLast = newNode;
        }
        size++;
    }
    
    /**
     * Permite saber si el hashmap esta vacío
     * @return Booleano que sera true en caso de estar vacio y falso en caso contrario
     */
    private boolean isEmpty() {
        return pFirst == null;
    }
    
    public NodeHash getpFirst() {
        return pFirst;
    }

    /**
     * @param pFirst the pFirst to set
     */
    public void setpFirst(NodeHash pFirst) {
        this.pFirst = pFirst;
    }

    /**
     * @return the pLast
     */
    public NodeHash getpLast() {
        return pLast;
    }

    /**
     * @param pLast the pLast to set
     */
    public void setpLast(NodeHash pLast) {
        this.pLast = pLast;
    }

    /**
     * @return the iN
     */
    public int getsize() {
        return size;
    }

    /**
     * @param iN the iN to set
     */
    public void setsize(int size) {
        this.size = size;
    }
}

