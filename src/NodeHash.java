/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mey
 */
public class NodeHash {

    private NodeHash pNext;
    private Object[] tInfo;

    // Constructor
    public NodeHash(Object tInfo[]) {
        this.pNext = null;
        this.tInfo = tInfo;
    }

    public NodeHash getpNext() {
        return pNext;
    }

    /**
     * @param pNext the pNext to set
     */
    public void setpNext(NodeHash pNext) {
        this.pNext = pNext;
    }

    /**
     * @return the tInfo
     */
    public Object[] gettInfo() {
        return tInfo;
    }

    /**
     * @param tInfo the tInfo to set
     */
    public void settInfo(Object tInfo[]) {
        this.tInfo = tInfo;
    }

}
