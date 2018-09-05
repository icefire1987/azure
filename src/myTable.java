import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class myTable extends JTable{
	     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		Map<Integer, Color> rowColor = new HashMap<Integer, Color>();
	     public DefaultTableModel model;

	     public myTable(DefaultTableModel model){
	          super(model);
	          this.model = model;
	     }

	     @Override
	     public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
	          Component c = super.prepareRenderer(renderer, row, column);

	          if (!isRowSelected(row))
	          {
	               Color color = rowColor.get( row );
	               c.setBackground(color == null ? getBackground() : color);
	          }

	          return c;
	     }

	     public void setRowColor(int row, Color color)
	     {
	          rowColor.put(row, color);
	     }
	     public void clear(){
	    	if(model!=null && model.getRowCount()>0){
	    		for(int ll= model.getRowCount();ll>=0;ll--){
	    			if(ll<model.getRowCount()){
	    				model.removeRow(ll);
	    			}
	    		}
	    	}
	     }
	}
