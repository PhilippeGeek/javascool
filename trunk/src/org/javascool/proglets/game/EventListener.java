/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.game;

/**
 *
 * @author gmatheron
 */
public class EventListener {
    private String m_method;
    private EventCatcher m_object;
    
    public EventListener(String method, EventCatcher object) {
        m_method=method;
        m_object=object;
    }
    
    public String getMethod() {
        return m_method;
    }
    public EventCatcher getObject() {
        return m_object;
    }
}
