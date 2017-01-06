package communication;

import java.util.*;
import model.*;

public class GetProjectsComOut 
{
	private List<ModelProject> projects;
	
	/**
	 * 
	 */
	public GetProjectsComOut()
	{
		this.projects = new ArrayList<ModelProject>();
	}
	
	/**
	 * 
	 * @param valid
	 * @param output
	 * @param projectID
	 * @param projectTitle
	 */
	public GetProjectsComOut(List<ModelProject> projects)
	{
		this.projects = projects;
	}
	
	/**
	 * Return the proper output statement for the method execution
	 */
	public String toString()
	{
		String output = "";
		for (ModelProject project : projects)
		{
			output = output + project.getId() + "\n" + project.getTitle() + "\n";
		}
		return output;
	}

	/**
	 * @return the projects
	 */
	public List<ModelProject> getProjects() {
		return projects;
	}

	/**
	 * @param projects the projects to set
	 */
	public void setProjects(List<ModelProject> projects) {
		this.projects = projects;
	}	
	
	

}
