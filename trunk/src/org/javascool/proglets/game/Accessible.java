/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.game;

/**
 *
 * @author gmatheron
 */
public abstract class Accessible extends LinkedEventGroup {
    private boolean m_superDestroyed=false;
    private boolean m_superDeleted=false;
    
    public void destroy() {
        m_superDestroyed=true;
    }
    
    @Override
    public boolean isDestroyed() {
        return m_superDestroyed;
    }
    
    public boolean isDeleted() {
        return m_superDeleted;
    }
    public void delete() {
        m_superDeleted=true;
        Group.updateAll();
    }
}
