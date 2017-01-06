package DAO;

import server.*;

public abstract class DAO 
{	
	protected DatabaseRep db;
	
	public DAO(DatabaseRep db)
	{
		this.db = db;
	}
	
}
