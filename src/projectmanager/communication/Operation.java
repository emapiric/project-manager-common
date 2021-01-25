/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanager.communication;

import java.io.Serializable;

public enum Operation  implements Serializable{
    LOGIN,
    GET_ALL_USERS,
    GET_ALL_PROJECTS,
    ADD_PROJECT,
    EDIT_PROJECT,
    DELETE_PROJECT,
    GET_ALL_PROJECT_TASKS,
    ADD_PROJECT_TASK,
    EDIT_PROJECT_TASK,
    DELETE_PROJECT_TASK,
    GET_ALL_TASKS,
    GET_PROJECT_TASK_BY_ID
    
}
