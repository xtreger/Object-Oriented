package main;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class CustomerModel extends AbstractTableModel {

	        String col[]; //column names
	        ArrayList<Customer> data; //arrayList to populate table 
	        
	        //(1)To do make the constructor accept an arraylist of person objects
	        //assign data to this reference arraylist
	        public CustomerModel(ArrayList<Customer> ref) {
	            this.col = new String[]{"Name", "Age","Email"};
	            this.data = ref;

	
	        }

	        //get number of records
	        @Override
	        public int getRowCount() {
	            return data.size();
	        }

	        //get number of columns
	        @Override
	        public int getColumnCount() {
	            return col.length;
	        }

	        //get a value form the table
	        @Override
	        public Object getValueAt(int rowIndex, int columnIndex) {
	        	Customer person = data.get(rowIndex);
	            if (columnIndex == 0) {
	                return person.getName();
	            } else if (columnIndex == 1) {
	                return person.getAge();
	            }else if (columnIndex == 2) {
	                return person.getEmail();
	            }
	            return null;
	        }

	        //set value at a particular cell 
	        public void setValueAt(Object value, int row, int col) {
	        	//Account rowList = data.get(row);
	        	Customer acc = data.get(row); 
	        
	        	if(col == 0) 
	        		acc.setName((String)value);
	        	else if (col == 1)
	        		acc.setAge((int)value);
	        	else if (col == 2)
	        		acc.setEmail((String)value); 
	        	data.set(row, acc); 
	        	// notify model listeners of cell change 
	        	fireTableCellUpdated(row, col); 
	        }
	        //get column name
	        public String getColumnName(int column) {
	            return col[column];
	        }
	        @Override
	  	  public boolean isCellEditable(int row, int col) {
	        	return false;
	  	    }
	    }

