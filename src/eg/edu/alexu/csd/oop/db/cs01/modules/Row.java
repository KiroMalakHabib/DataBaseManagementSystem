package eg.edu.alexu.csd.oop.db.cs01.modules;

import java.util.HashMap;
import java.util.Map;

public class Row {

	private Map<String, Cell>cells;
	
	public Row() {
	}
	public Row(Table table) {
		this.cells = new HashMap<String, Cell>();
		for(String s:table.getColumnNamesToLowerCase()) {
			cells.put(s, new Cell(null));
		}
	}
	/**
	 * @return the cells
	 */
	public Map<String, Cell> getCells() {
		return cells;
	}

	/**
	 * @param cells the cells to set
	 */
	public void setCells(Map<String, Cell> cells) {
		this.cells = cells;
	}
	
	public void updateCell(String columnName , Cell cell) {
		cells.put(columnName.toLowerCase(), cell);
	}
	public void deleteCell(String columnName) {
		updateCell(columnName.toLowerCase(), null);
	}
	public String getCellByColumn(String columnName) {
		if(cells.get(columnName.toLowerCase())!=null)
			return cells.get(columnName.toLowerCase()).getValue();
		return null;
	}
}
