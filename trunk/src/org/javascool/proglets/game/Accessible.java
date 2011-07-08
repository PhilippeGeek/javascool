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
    private boolean m_destroyed=false;
    
    public void destroy() {
        m_destroyed=true;
    }
    
    @Override
    public boolean isDestroyed() {
        return m_destroyed;
    }
}
