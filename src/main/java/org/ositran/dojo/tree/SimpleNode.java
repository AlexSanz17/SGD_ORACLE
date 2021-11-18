/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.dojo.tree;

import java.util.List;

public class SimpleNode extends Nodo2 {
  List <SimpleNodeLeaf> children;

public List<SimpleNodeLeaf> getChildren() {
	return children;
}

public void setChildren(List<SimpleNodeLeaf> children) {
	this.children = children;
}


  
}
