/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.game;

/**
 *
 * @author gmatheron
 */
public class Key extends Accessible {
    public int m_code=0;
    public Key(int code) {
        m_code=code;
    }
    public int getCode() {
        return m_code;
    }
    public void setCode(int code) {
        m_code=code;
    }

    @Override
    public boolean isForMe() {
        return Functions.keyDown(m_code);
    }
}
