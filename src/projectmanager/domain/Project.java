/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanager.domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author EMA
 */
public class Project implements GenericEntity{
    private int id;
    private String name;
    private String description;
    private User owner;
    private List<User> assignees;

    public Project() {
    }
    
    public Project(int id) {
        this.id = id;
    }

    public Project(int id, String name, String description, User owner, User assignee) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.assignees = new ArrayList<>();
        this.assignees.add(assignee);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<User> assignees) {
        this.assignees = assignees;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.description);
        hash = 79 * hash + Objects.hashCode(this.owner);
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
        final Project other = (Project) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.owner, other.owner)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", name=" + name + ", description=" + description + ", owner=" + owner + '}';
    }

    @Override
    public String getTableName() {
        return "project";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "name, description, userId";
    }

    @Override
    public String getInsertValues() {
        return "'"+name+"','"+description+"',"+owner.getId();
    }
    
    @Override
    public String setAtrValue() {
       return "name=" + (name == null ? null : "'" + name + "'") + ", " + "description=" + (description == null ? null : "'" + description + "'");
    }

    @Override
    public String getWhereCondition() {
        return "id="+id;
    }

    @Override
    public String getJoin() {
        return " INNER JOIN USER ON (project.userId = user.id)";
    }

    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        User user = new User(rs.getInt("user.id"),rs.getString("user.firstname"),rs.getString("user.lastname"),rs.getString("user.username"),rs.getString("user.password"),rs.getString("user.email"));
        return new Project(rs.getInt("project.id"),rs.getString("project.name"),rs.getString("project.description"),user, null);
    }
    
}
