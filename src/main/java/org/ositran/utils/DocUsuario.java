/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.utils;

import java.util.Date;


/**
 *
 * @author WMH
 */

public class DocUsuario {

    private int iddocumento;
    private int idusuario;
    
    public DocUsuario(int iddocumento,int idusuario) {
		this.iddocumento=iddocumento;
		this.idusuario=idusuario;
	}
    public DocUsuario() {
		
	}
    
	public void setIddocumento(int iddocumento) {
		this.iddocumento = iddocumento;
	}
	public int getIddocumento() {
		return iddocumento;
	}
	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}
	public int getIdusuario() {
		return idusuario;
	}
    
    
}
