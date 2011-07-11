/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.game;

import java.util.logging.Logger;

/**
 *
 * @author gmatheron
 */
public class StoredProperty<T> {
    private String m_name;
    private T m_value;
    
    public StoredProperty() {
        m_name="";
        m_value=null;
    }
    public StoredProperty(String s, T o) {
        m_name=s;
        m_value=o;
    }
    public boolean isNull() {
        return (m_value==null);
    }
    public String getName() {
        return (m_name);
    }
    public T getObject() {
        return (m_value);
    }
    public void setObject(T o) {
        m_value=o;
    }
    
    private static final Logger LOG = Logger.getLogger(StoredProperty.class.getName());
}
