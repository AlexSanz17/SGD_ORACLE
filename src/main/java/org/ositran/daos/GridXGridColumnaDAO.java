/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.GridXGridColumna;

public interface GridXGridColumnaDAO {

   public List<GridXGridColumna> encontrarPorGrid(int idGrid);
}
