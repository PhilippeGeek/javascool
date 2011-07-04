package org.javascool.proglets.game;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Functions {

    class Manager {

        Manager() {
        }
        private java.util.ArrayList<CallbackFunction> m_onClick;

        public void onClick(String s) {
        }

        public void start() {
            new java.awt.event.MouseListener() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    for (int i = 0; i < m_onClick.size(); i++) {
                        m_onClick.get(i).call(new MouseState(evt));
                    }
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                }

                @Override
                public void mousePressed(java.awt.event.MouseEvent evt) {
                }

                @Override
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                }
            };
        }
    }

    class CallbackFunction {

        private java.lang.reflect.Method m_method;

        public CallbackFunction(java.lang.reflect.Method m) {
            m_method = m;
        }

        public void call(State s) {
            try {
                try {
                    m_method.invoke(s);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(CallbackFunction.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IllegalAccessException ex) {
                Logger.getLogger(CallbackFunction.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(CallbackFunction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    class State {
    }

    class MouseState extends State {

        private java.awt.event.MouseEvent m_evt;

        MouseState(java.awt.event.MouseEvent evt) {
            m_evt = evt;
        }
    }

    class Sprite {

        private javax.swing.JPanel m_panel;

        public void call() {
        }
    }

    public void start() {
        Manager m = new Manager();
        m.start();
    }
}