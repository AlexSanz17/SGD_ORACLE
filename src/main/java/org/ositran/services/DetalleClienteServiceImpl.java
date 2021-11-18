/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.List;
import org.ositran.daos.DetalleClienteDAO;
import com.btg.ositran.siged.domain.DetalleCliente;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author consultor_jti15
 */
public class DetalleClienteServiceImpl implements DetalleClienteService{
    
    private DetalleClienteDAO detalleClienteDAO;
    
    public List<DetalleCliente> findByDetalleCliente(Integer idCliente){
        return detalleClienteDAO.findByDetalleCliente(idCliente);
    }
    
    /*
    public DetalleCliente findByDetalleCliente(Integer idCliente, Integer idRemitente){
        return detalleClienteDAO.findByDetalleCliente(idCliente, idRemitente);
    }*/
    
    public DetalleCliente findByDetalleClienteId(Integer idDetalleClienteId){
        return detalleClienteDAO.findByDetalleClienteId(idDetalleClienteId);
    }

    public DetalleClienteDAO getDetalleClienteDAO() {
        return detalleClienteDAO;
    }

    public void setDetalleClienteDAO(DetalleClienteDAO detalleClienteDAO) {
        this.detalleClienteDAO = detalleClienteDAO;
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED)
    public DetalleCliente guardarObj(DetalleCliente objRemitente){
        if (objRemitente.getIdDetalleCliente()==null){
         //JC24   
         // Integer idRemitente = detalleClienteDAO.findByMaxRemitente(objRemitente);
         // objRemitente.setIdRemitente(idRemitente==null?1:idRemitente+1);
        }else{ //JC24
          //DetalleCliente detalleCliente =detalleClienteDAO.findByDetalleClienteId(objRemitente.getIdDetalleCliente());
          //objRemitente.setIdRemitente(detalleCliente.getIdRemitente());
        }
        return this.detalleClienteDAO.guardarObj(objRemitente);
    }
  
}
