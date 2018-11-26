package eg.edu.alexu.csd.oop.db.cs01;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.cs01.modules.Table;
import eg.edu.alexu.csd.oop.dp.cs01.queries.CreateDatabase;
import eg.edu.alexu.csd.oop.dp.cs01.queries.CreateTable;
import eg.edu.alexu.csd.oop.dp.cs01.queries.DropDatabase;
import eg.edu.alexu.csd.oop.dp.cs01.queries.DropTable;
import eg.edu.alexu.csd.oop.dp.cs01.queries.IQuery;

public class Parser {
	
	//Singleton pattern.
	private static Parser instance;
	
	private Parser() {
	}
	
	public static Parser getInstance() {
		if (instance == null) {
			instance = new Parser();
		}
		return instance;
	}
	
	private String theMainDataBase = null;
	
	public IQuery parseQuery(String theQuery) {
		final String createDataBasePattern = "create database (\\S+);";
		final String drobDataBasePattern = "drob database (\\S+);";
		final String createTablePattern = "create table (\\S+) \\(((S+) (int | varChar)),?+\\);";
		final String drobTablePattern = "drob table (\\S+);";
		
		List<String> allPatternStrings = null;
		allPatternStrings.add(createDataBasePattern);
		allPatternStrings.add(drobDataBasePattern);
		allPatternStrings.add(createTablePattern);
		allPatternStrings.add(drobTablePattern);
		
		List<Pattern> thePatterns = null;
		List<Matcher> theMatchers = null;
		for (int i = 0; i < allPatternStrings.size(); i++) {
			Pattern pattern;
			Matcher matcher;
			pattern = Pattern.compile(allPatternStrings.get(i));
			matcher = pattern.matcher(theQuery);
			thePatterns.add(pattern);
			theMatchers.add(matcher);
		}
		if (theMatchers.get(0).find()) {// if the query match create data base.
			IQuery createDataBaseQuery = new CreateDatabase(theMatchers.get(0).group(1), false);
			theMainDataBase = theMatchers.get(0).group(1);
			return createDataBaseQuery;
		} else if (theMatchers.get(1).find()) {// if the query match drop data base.
			IQuery drobDataBaseQuery = new DropDatabase(theMatchers.get(1).group(1));
			return drobDataBaseQuery;
		} else if (theMatchers.get(2).find()) {// if the query match create table.
			if (theMainDataBase == null) {
				System.out.println("There is NO DataBase selected");
				return null;
			}
			Table createTable = new Table(theMainDataBase, theMatchers.get(2).group(1), null, null);
			
			IQuery createTableQuery = new CreateTable(createTable);
			return createTableQuery;
		} else if (theMatchers.get(3).find()) {// if the query match drop table.
			if (theMainDataBase == null) {
				System.out.println("There is NO DataBase selected");
				return null;
			}
			Table dropTable = new Table(theMainDataBase, theMatchers.get(3).group(1));
			IQuery dropTableQuery = new DropTable(dropTable);
			return dropTableQuery;
		}
		return null;
	}
}
