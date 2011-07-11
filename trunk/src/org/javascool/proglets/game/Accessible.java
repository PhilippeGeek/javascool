/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.game;

import java.util.ArrayList;

/**
 *
 * @author gmatheron
 */
public abstract class Accessible extends LinkedEventGroup {
    private boolean m_superDestroyed=false;
    private boolean m_superDeleted=false;
    private ArrayList<StoredProperty<?>> m_props;
    
    @SuppressWarnings("CollectionWithoutInitialCapacity")
    public Accessible() {
        m_props=new ArrayList<StoredProperty<?>>();
    }
    
    public void addProperty(StoredProperty<?> p) {
        for (StoredProperty<?> s : m_props) {
            if (s.getName().equals(p.getName())) {
                //TODO throw error
            }
        }
        m_props.add(p);
    }
    
    public void removeProperty(String name) {
        for (StoredProperty<?> s : m_props) {
            if (s.getName().equals("name")) {
                m_props.remove(s);
            }
        }
    }
    
    public StoredProperty<?> getProperty(String name) {
        for (StoredProperty<?> s : m_props) {
            if (s.getName().equals(name)) {
                return (s);
            }
        }
        return null;
    }
    
    public void setProperty(StoredProperty<?> p) {
        boolean exists=false;
        StoredProperty<?> prop=null;
        for (StoredProperty<?> s : m_props) {
            if (s.getName().equals(p.getName())) {
                exists=true;
                prop=s;
            }
        }
        if (!exists) {
            addProperty(p);
        }
        else {
            m_props.remove(prop);
            addProperty(p);
        }
    }
    
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
