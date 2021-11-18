/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import com.btg.ositran.siged.domain.DetalleCliente;
import java.util.List;

/**
 *
 * @author consultor_jti15
 */
public interface DetalleClienteService {
    public List<DetalleCliente> findByDetalleCliente(Integer idCliente);
   // public DetalleCliente findByDetalleCliente(Integer idCliente, Integer idRemitente);
    public DetalleCliente guardarObj(DetalleCliente objRemitente);
    public DetalleCliente findByDetalleClienteId(Integer idDetalleClienteId);
}
