package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import database.ConnectionFactory;

public abstract class BaseDAO {

	public Connection getConn() {
		return conn;
	}

	public BaseDAO(Connection conn) {

		this.conn = conn;

	}

	Connection conn;

	private String sql = "";
	private String whereFilter = "";
	private String group = "";

	protected void _set(PreparedStatement preparedStatement, int position, Object params) throws SQLException {
		if(params == null) {
			preparedStatement.setNull(position, Types.NULL);
		} else if(params instanceof String) {
			preparedStatement.setString(position, (String)params);
		}
	}

	protected BaseDAO delete() {
		this.setSql(getSql() + " DELETE ");
		return this;
	}

	protected BaseDAO setValue(String fields) {
		this.setSql(this.getSql() + " SET " + fields);
		return this;
	}

	protected BaseDAO update(String table) {
		this.setSql(this.getSql() + " UPDATE " + table);
		return this;
	}

	protected BaseDAO insertInto(String table, String statement) {
		this.setSql(this.getSql() + " INSERT INTO " + table + " (" + statement + ") ");
		return this;
	}

	protected BaseDAO values(String statement) {
		this.setSql(getSql() + " VALUES ( " + statement + " ) ");
		return this;
	}

	protected BaseDAO select(String statement) {
		this.setSql(this.getSql() + " SELECT " + statement); 
		return this;
	}

	protected BaseDAO from(String statement) {
		this.setSql(this.getSql() + " FROM " + statement);
		return this;
	}

	protected BaseDAO where(String statement, String operator, String value) {
		this.setSql(this.getSql() + " WHERE " + statement +" "+ operator +" "+ value);
		return this;
	}

	protected BaseDAO where() {
		this.setSql(this.getSql() + " WHERE " + getWhereFilter());
		return this;
	}

	protected BaseDAO filter(String statement, String operator, String value, boolean ignoreZero) {
		if(!value.trim().isEmpty()) {
			if(ignoreZero || ((statement.substring(0, 1) != "id") && (statement.substring(0, 1).equals("id")&&Integer.parseInt(value) > 0))) {
				setWhereFilter(statement +" "+ operator +" "+ value);				
			}
		}
		return this;
	}

	protected BaseDAO group(String group) {
		setGroup(group);
		return this;
	}

	protected BaseDAO orderBy(String statement) {
		this.setSql(this.getSql() + " ORDER BY " + statement);
		return this;
	}

	protected BaseDAO join(String statement) {
		this.setSql(this.getSql() + " JOIN " + statement);
		return this;
	}

	protected BaseDAO leftJoin(String statement) {
		this.setSql(this.getSql() + " LEFT JOIN " + statement);
		return this;
	}

	protected BaseDAO rightJoin(String statement) {
		this.setSql(this.getSql() + " RIGHT JOIN " + statement);
		return this;
	}

	protected BaseDAO groupBy() {
		setSql(getSql() + " GROUP BY " + getGroup());
		return this;
	}

	protected BaseDAO returning(String statement) {
		setSql(getSql() + " RETURNING "+ statement);
		return this;
	}
	
	protected BaseDAO limit(Integer rows) {
		setSql(getSql()+ " LIMIT "+ rows + " "); 
		return this;
	}

	private String getGroup() {
		return group;
	}

	private void setGroup(String group) {
		this.group = getGroup() + ((getGroup().isEmpty()) ? "" : ", ")  +  group; 
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	protected BaseDAO customSql(String sql) {
		this.sql = sql;
		return this;
	}

	private String getWhereFilter() {
		return whereFilter;
	}

	private void setWhereFilter(String sql) {
		whereFilter = ((whereFilter.isEmpty()) ? " " : whereFilter+" AND " ) + sql;		
	}

	public ResultSet freeSqlQuery(String sql) {
		this.sql = sql;

		return excecuteQuery();
	}

	protected ResultSet apply() {
		ResultSet result = this.excecuteQuery();
		return result;
	}

	protected Boolean commit() {
		
		return (this.excecuteUpdate() > 0);			
		
	}
	
	protected Boolean commit(File logo) {
		if(logo != null) {
			return (this.excecuteUpdate(logo) > 0);
		}else {
			return (this.excecuteUpdate() > 0);
		}
		
	}

	protected ResultSet excecuteQuery()  {
		PreparedStatement prepStatement = null;

		try {
			prepStatement = conn.prepareStatement(this.getSql());
			clean();
			return prepStatement.executeQuery();
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
			return null;
		}				
	}

	protected int excecuteUpdate() {
		PreparedStatement prepStatement = null;
		
		int row = 0;
		try {
			prepStatement = conn.prepareStatement(this.getSql());
			clean();
			row = prepStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		}	
		return row;
	}
	
	protected int excecuteUpdate(File logo) {
		PreparedStatement prepStatement = null;
		
		int row = 0;
		try {
			prepStatement = conn.prepareStatement(this.getSql());
			FileInputStream fis = new FileInputStream(logo);
			prepStatement.setBinaryStream(1, fis, (int)logo.length());
			clean();
			row = prepStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
		return row;
	}

	public void clean() {
		sql = "";
		whereFilter = "";
		group = "";
	}

	protected Connection connection() {
		conn = ConnectionFactory.getConnection
				(
						"master", 
						"admin", 
						"admin"
						);
		try {
			conn.setAutoCommit(true);
			System.out.println("Conectado com sucesso!");
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	//Util
	protected String quoteStr(String str) {
		return "'"+str+"'";
	}

}
