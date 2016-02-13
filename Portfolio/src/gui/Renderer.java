package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import data.storage.FileNode;

public class Renderer extends DefaultTreeCellRenderer {
	
	public Renderer(){
	}
	
	@Override
	  public Component getTreeCellRendererComponent(JTree tree, Object value,
	      boolean selected, boolean expanded, boolean leaf, int row,
	      boolean hasFocus) {
	    
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		    FileNode fn = (FileNode) node.getUserObject();
		    return super.getTreeCellRendererComponent(tree,
	                                              fn.fileName,
	                                              selected,
	                                              expanded,
	                                              leaf,
	                                              row,
	                                              hasFocus);
	}
}
