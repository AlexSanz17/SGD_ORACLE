package org.ositran.services;

import com.btg.ositran.siged.domain.FilaBandejaSeguimiento;
import java.util.ArrayList;
import java.util.List;
import org.ositran.daos.SeguimientoXUsuarioDAO;
import org.ositran.daos.TrazabilidadapoyoDAO;
import com.btg.ositran.siged.domain.Usuario;
import org.ositran.dojo.grid.ItemUF;
import org.springframework.transaction.annotation.Transactional;
import com.btg.ositran.siged.domain.SeguimientoXUsuario;

public class SeguimientoXUsuarioServiceImpl implements SeguimientoXUsuarioService {

	private SeguimientoXUsuarioDAO seguimientoXUsuarioDAO;
	private TrazabilidadapoyoDAO trazabilidadapoyoDAO;
	
	@Override
	@Transactional
	public SeguimientoXUsuario guardarSeguimiento(SeguimientoXUsuario seguimiento) {
                seguimiento = seguimientoXUsuarioDAO.guardarSeguimiento(seguimiento);
		return seguimiento;
	}
        
        public List<SeguimientoXUsuario> buscarSeguimientoXUsuario(SeguimientoXUsuario seguimiento){
            return seguimientoXUsuarioDAO.buscarSeguimientoXUsuario(seguimiento);
        }

	@Override
	@Transactional
	public void eliminarSeguimiento(SeguimientoXUsuario seguimiento) {
		seguimientoXUsuarioDAO.eliminarSeguimiento(seguimiento);
	}
	
	@Override
	public List<SeguimientoXUsuario> buscarSeguimiento(SeguimientoXUsuario seguimiento){
		//SeguimientoXUsuario seguimiento = new SeguimientoXUsuario();
		//seguimiento.setIdUsuario(idUsuario);
		//seguimiento.setIdDocumento(idDocumento);
		return seguimientoXUsuarioDAO.buscarSeguimientoXUsuario(seguimiento);
	} 
        
        public  SeguimientoXUsuario findByIdSeguimiento(Integer idSeguimiento){
             return seguimientoXUsuarioDAO.findByIdSeguimiento(idSeguimiento);
        }
	
	@Override
	public List<ItemUF> getItemsSeguimientoXUsuario(Usuario objUsuario){
                List<FilaBandejaSeguimiento> filas = seguimientoXUsuarioDAO.buscarGridSeguimiento(objUsuario);
		List<ItemUF> items = new ArrayList<ItemUF>();
		
                if(filas != null && !filas.isEmpty()){
			for(FilaBandejaSeguimiento fila : filas){
                		ItemUF uuf = new ItemUF();
				uuf.setId(fila.getId());
				uuf.setAsunto(fila.getAsunto());
				uuf.setAsuntoExpediente(fila.getAsuntoExpediente());
				uuf.setDocumento(fila.getDocumento());
				uuf.setEstado(fila.getEstado());
				uuf.setNroTramite(fila.getNroTramite());
				uuf.setExpediente(fila.getExpediente());
                                uuf.setExterno("-1");
                                uuf.setTipodocumento(fila.getIdtipodocumento().toString());
				uuf.setCliente(fila.getCliente()==null? "":fila.getCliente());
                                items.add(uuf);
			}
		}
		
		return items;
	}
	
	public SeguimientoXUsuarioDAO getSeguimientoXUsuarioDAO() {
		return seguimientoXUsuarioDAO;
	}

	public void setSeguimientoXUsuarioDAO(SeguimientoXUsuarioDAO seguimientoXUsuarioDAO) {
		this.seguimientoXUsuarioDAO = seguimientoXUsuarioDAO;
	}

	public TrazabilidadapoyoDAO getTrazabilidadapoyoDAO() {
		return trazabilidadapoyoDAO;
	}

	public void setTrazabilidadapoyoDAO(TrazabilidadapoyoDAO trazabilidadapoyoDAO) {
		this.trazabilidadapoyoDAO = trazabilidadapoyoDAO;
	}

}
