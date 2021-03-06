package org.ositran.services;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import com.btg.ositran.siged.domain.Accion;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Trazabilidadexpediente;
import com.btg.ositran.siged.domain.Usuario;

public interface TrazabilidadexpedienteService{

	public Trazabilidadexpediente findByMaxNroRegistro(Integer iIdExpediente,String sAccion) throws NoResultException;

	public Trazabilidadexpediente saveTrazabilidadExpediente(Expediente objExpediente,Usuario objRemitente,Date datFechaRecibido);

	public Trazabilidadexpediente saveTrazabilidadExpediente(Expediente expediente,Usuario remitente,Usuario destinatario,Accion accion,String observacion);

	public Trazabilidadexpediente save(Trazabilidadexpediente trazabilidadexpediente);

	public Trazabilidadexpediente findUltimoRegistroByExpediente(Integer idExpediente);

	public Trazabilidadexpediente findByNroRegistroByExpediente(Integer idExpediente,Integer nroRegistro);

	public Trazabilidadexpediente findByIdTrazabilidadExpediente(Integer idTrazabilidadExpediente);

	public List<Trazabilidadexpediente> findByIdExpediente(int idtrazabilidadexpediente);

	public Trazabilidadexpediente guardarTrazabilidad(Expediente expediente,Usuario remitente,Usuario destinatario,String accion,String observacion);
	
	public Trazabilidadexpediente guardarTrazabilidad(Expediente expediente,Usuario remitente,Usuario destinatario,Accion accion,String observacion);

}
