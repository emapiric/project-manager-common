/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanager.domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Ema
 */
public class ProjectTask implements GenericEntity{
    private int id;
    private Project project;
    private Date createdOn;
    private String description;
    private Task task;
    private User assignee;
    private Status status;
    private User author;

    public ProjectTask() {
    }
    
    public ProjectTask(int id) {
        this.id = id;
    }

    public ProjectTask(int id, Project project, Date createdOn, String description, Task task, User assignee, Status status, User author) {
        this.id = id;
        this.project = project;
        this.createdOn = createdOn;
        this.description = description;
        this.task = task;
        this.assignee = assignee;
        this.status = status;
        this.author = author;
    }
    
    public ProjectTask(Project project) {
        this.project = project;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "ProjectTask{" + "id=" + id + ", project=" + project + ", createdOn=" + createdOn + ", description=" + description + ", task=" + task + ", assignee=" + assignee + ", status=" + status + ", author=" + author + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id;
        hash = 79 * hash + Objects.hashCode(this.project);
        hash = 79 * hash + Objects.hashCode(this.createdOn);
        hash = 79 * hash + Objects.hashCode(this.description);
        hash = 79 * hash + Objects.hashCode(this.task);
        hash = 79 * hash + Objects.hashCode(this.assignee);
        hash = 79 * hash + Objects.hashCode(this.status);
        hash = 79 * hash + Objects.hashCode(this.author);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProjectTask other = (ProjectTask) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.project, other.project)) {
            return false;
        }
        if (!Objects.equals(this.createdOn, other.createdOn)) {
            return false;
        }
        if (!Objects.equals(this.task, other.task)) {
            return false;
        }
        if (!Objects.equals(this.assignee, other.assignee)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        return true;
    }

    @Override
    public String getTableName() {
        return "project_task";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "projectId, createdOn, description, taskId, assigneeProjectId, assigneeId, statusId, authorId";
    }

    @Override
    public String getInsertValues() {
        return project.getId()+",'"+new java.sql.Date(new Date().getTime())+"','"+description+"',"+task.getId()+","+project.getId()+","+assignee.getId()+","+status.ordinal()+","+author.getId();
    }

    @Override
    public String setAtrValue() {
        return "description=" + (description == null ? null : "'" + description + "'")
                +", taskId="+task.getId()+", assigneeId="+assignee.getId()+", statusId="+status.ordinal();
    }

    @Override
    public String getWhereCondition() {
        return "id="+id;
    }

    @Override
    public String getJoin() {
        return " INNER JOIN USER o ON (project_task.authorId = o.id) INNER JOIN USER a ON(project_task.assigneeId = a.id)" +
                " INNER JOIN task t ON (project_task.taskId = t.id) INNER JOIN project p ON (project_task.projectId = p.id)";
    }

    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        User owner = new User(rs.getInt("o.id"),rs.getString("o.firstname"),rs.getString("o.lastname"),rs.getString("o.username"),rs.getString("o.password"),rs.getString("o.email"));
        Project project = new Project(rs.getInt("p.id"),rs.getString("p.name"),rs.getString("p.description"),owner, null);
        User assignee = new User(rs.getInt("a.id"),rs.getString("a.firstname"),rs.getString("a.lastname"),rs.getString("a.username"),rs.getString("a.password"),rs.getString("a.email"));
        Task task = new Task(rs.getInt("t.id"),rs.getString("t.name"),rs.getString("t.description"));
        return new ProjectTask(rs.getInt("project_task.id"),project,rs.getDate("project_task.createdOn"),rs.getString("project_task.description"),task,assignee,Status.values()[rs.getInt("statusId")],owner);
    }
    
    
}
