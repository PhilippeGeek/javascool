/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.game;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author gmatheron
 */
public class Group extends Accessible implements Iterable<Accessible> {
    private boolean m_destroyed;
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(Group.class.getName());
    private ArrayList<Accessible> m_items;

    @SuppressWarnings("CollectionWithoutInitialCapacity")
    public Group() {
        m_items = new ArrayList<Accessible>();
    }

    public int size() {
        return m_items.size();
    }
    
    public void add(Accessible a) {
        m_items.add(a);
    }

    public Accessible get(int i) {
        return m_items.get(i);
    }
    
    @Override
    public GroupIterator iterator() {
        return new GroupIterator(this);
    }

    @Override
    public boolean isForMe() {
        throw new UnsupportedOperationException("Not supported.");
    }
    
    public void remove(int i) {
        m_items.remove(i);
    }
    
    public void remove(Accessible a) {
        m_items.remove(a);
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     * @param always If set to true, the callback will always be performed for each
     * element of the collection even if the event did not occur on one of the elements
     */
    @Override
    public void onClick(String s, boolean always) {
        for (int i = 0; i < m_items.size(); i++) {
            Functions.getSingleton().m_onClick.add(new EventListener(s, m_items.get(i), always));
        }
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    @Override
    public void onMouseEntered(String s, boolean always) {
        for (int i = 0; i < m_items.size(); i++) {
            Functions.getSingleton().m_onMouseEntered.add(new EventListener(s, m_items.get(i), always));
        }
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    @Override
    public void onMouseExited(String s, boolean always) {
        for (int i = 0; i < m_items.size(); i++) {
            Functions.getSingleton().m_onMouseExited.add(new EventListener(s, m_items.get(i), always));
        }
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    @Override
    public void onMousePressed(String s, boolean always) {
        for (int i = 0; i < m_items.size(); i++) {
            Functions.getSingleton().m_onMousePressed.add(new EventListener(s, m_items.get(i), always));
        }
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    @Override
    public void onMouseReleased(String s, boolean always) {
        for (int i = 0; i < m_items.size(); i++) {
            Functions.getSingleton().m_onMouseReleased.add(new EventListener(s, m_items.get(i), always));
        }
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    @Override
    public void onMouseDown(String s, boolean always) {
        for (int i = 0; i < m_items.size(); i++) {
            Functions.getSingleton().m_onMouseDown.add(new EventListener(s, m_items.get(i), always));
        }
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    @Override
    public void onMouseUp(String s, boolean always) {
        for (int i = 0; i < m_items.size(); i++) {
            Functions.getSingleton().m_onMouseUp.add(new EventListener(s, m_items.get(i), always));
        }
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    @Override
    public void onMouseMoved(String s, boolean always) {
        for (int i = 0; i < m_items.size(); i++) {
            Functions.getSingleton().m_onMouseMoved.add(new EventListener(s, m_items.get(i), always));
        }
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    @Override
    public void onMouseDragged(String s, boolean always) {
        for (int i = 0; i < m_items.size(); i++) {
            Functions.getSingleton().m_onMouseDragged.add(new EventListener(s, m_items.get(i), always));
        }
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseWheelState argument
     * @param s The function to callback
     */
    @Override
    public void onMouseWheelUp(String s, boolean always) {
        for (int i = 0; i < m_items.size(); i++) {
            Functions.getSingleton().m_onMouseWheelUp.add(new EventListener(s, m_items.get(i), always));
        }
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseWheelState argument
     * @param s The function to callback
     */
    @Override
    public void onMouseWheelDown(String s, boolean always) {
        for (int i = 0; i < m_items.size(); i++) {
            Functions.getSingleton().m_onMouseWheelDown.add(new EventListener(s, m_items.get(i), always));
        }
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseWheelState argument
     * @param s The function to callback
     */
    @Override
    public void onMouseWheelMoved(String s, boolean always) {
        for (int i = 0; i < m_items.size(); i++) {
            Functions.getSingleton().m_onMouseWheelMoved.add(new EventListener(s, m_items.get(i), always));
        }
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseWheelState argument
     * @param s The function to callback
     */
    public void onFrame(String s, boolean always) {
        for (int i = 0; i < m_items.size(); i++) {
            Functions.getSingleton().m_onFrame.add(new EventListener(s, m_items.get(i), always));
        }
    }
}
