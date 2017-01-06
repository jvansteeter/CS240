package server.DAO;

import static org.junit.Assert.*;

import java.util.List;

import model.ModelProject;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.DatabaseException;
import server.DatabaseRep;
import DAO.ProjectDAO;

public class ProjectDAOTest 
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{	
		// Load DatabaseRep driver	
		DatabaseRep.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}
		
	private DatabaseRep db;
	private ProjectDAO dbprojects;

	@Before
	public void setUp() throws Exception 
	{
		// Delete all projects from the DatabaseRep	
		db = new DatabaseRep();		
		db.startTransaction();
		
		List<ModelProject> projects = db.getProjectDAO().getAll();
		
		for (ModelProject c : projects) 
		{
			db.getProjectDAO().delete(c);
		}
		
		db.endTransaction(true);

		// Prepare DatabaseRep for test case	
		db = new DatabaseRep();
		db.startTransaction();
		dbprojects = db.getProjectDAO();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to DatabaseRep are undone
		db.endTransaction(false);
		
		db = null;
		dbprojects = null;
	}
	
	@Test
	public void testAdd() throws DatabaseException {
		
		ModelProject bob = new ModelProject("bob", 100, 1, 50);
		ModelProject amy = new ModelProject("amy", 200, 12, 51);
		
		dbprojects.add(bob);
		dbprojects.add(amy);
		
		List<ModelProject> all = dbprojects.getAll();
		assertEquals(2, all.size());
		
		assert(!bob.equals(amy));
		
		assert(all.get(0).equals(bob));
		assert(all.get(1).equals(amy));
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (ModelProject c : all) {
			
			assertFalse(c.getId() == -1);
			
			if (!foundBob) {
				foundBob = areEqual(c, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(c, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}
	
	@Test
	public void testGet() throws DatabaseException 
	{
		ModelProject project = new ModelProject("bob", 100, 1, 50);
		dbprojects.add(project);
		ModelProject test = dbprojects.get("bob");
		assertEquals(project, test);
	}
	
	@Test
	public void testGetAll() throws DatabaseException 
	{
		List<ModelProject> all = dbprojects.getAll();
		assertEquals(0, all.size());
		
		ModelProject bob = new ModelProject("bob", 100, 1, 50);
		ModelProject amy = new ModelProject("amy", 200, 12, 51);
		
		dbprojects.add(bob);
		dbprojects.add(amy);
		
		all = dbprojects.getAll();
		assertEquals(2, all.size());
	}

	@Test
	public void testUpdate() throws DatabaseException 
	{
		ModelProject bob = new ModelProject("bob", 100, 1, 50);
		ModelProject amy = new ModelProject("amy", 200, 12, 51);
		
		dbprojects.add(bob);
		dbprojects.add(amy);
		
		bob.setTitle("Robert White");
		bob.setRecordHeight(1000);
		bob.setRecordsPerImage(521);
		bob.setFirstYCoord(0);
		
		amy.setTitle("Amy Wilson White");
		amy.setRecordHeight(11111);
		amy.setRecordsPerImage(125);
		amy.setFirstYCoord(87);
		
		dbprojects.update(bob);
		dbprojects.update(amy);
		
		List<ModelProject> all = dbprojects.getAll();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (ModelProject c : all) {
			
			if (!foundBob) {
				foundBob = areEqual(c, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(c, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}

	@Test
	public void testDelete() throws DatabaseException 
	{
		
		ModelProject bob = new ModelProject("bob", 100, 1, 50);
		ModelProject amy = new ModelProject("amy", 200, 12, 51);
		
		dbprojects.add(bob);
		dbprojects.add(amy);
		
		List<ModelProject> all = dbprojects.getAll();
		assertEquals(2, all.size());
		
		dbprojects.delete(bob);
		dbprojects.delete(amy);
		
		all = dbprojects.getAll();
		assertEquals(0, all.size());
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		ModelProject invalidModelProject = new ModelProject(null, 0, 0, 0);
		dbprojects.add(invalidModelProject);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		ModelProject invalidModelProject = new ModelProject(null, 0, 0, 0);
		dbprojects.update(invalidModelProject);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		ModelProject invalidModelProject = new ModelProject(null, 0, 0, 0);
		dbprojects.delete(invalidModelProject);
	}
	
	private boolean areEqual(ModelProject a, ModelProject b, boolean compareIDs) 
	{
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (a.equals(b));
	}

}
