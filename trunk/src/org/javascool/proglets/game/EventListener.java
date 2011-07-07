/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.game;

import java.util.logging.Logger;

/**
 * This class stores an event : it contains a callback method that will be called
 * if the event is triggered and an EventCatcher that should be asked if the event
 * concerns it.
 * Note that this class does not perform any task, it is just a group of variables.
 * @author gmatheron
 */
public class EventListener {
    private String m_method;
    private EventCatcher m_object;
    
    /**
     * Creates a new EventListener
     * @param method The callback method
     * @param object The EventCatcher that will tell if the event should be triggered
     */
    public EventListener(String method, EventCatcher object) {
        m_method=method;
        m_object=object;
    }
    
    /**
     * Gets the callback method's name
     * @return the callback method's name
     */
    public String getMethod() {
        return m_method;
    }
    
    /**
     * Gets the EventCatcher
     * @return the EventCatcher
     */
    public EventCatcher getObject() {
        return m_object;
    }
    
    private static final Logger LOG = Logger.getLogger(EventListener.class.getName());
}
