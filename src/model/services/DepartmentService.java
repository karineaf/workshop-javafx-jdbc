package model.services;

import model.dao.DAOFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;

import java.util.List;

public class DepartmentService {

    private DepartmentDAO dao = DAOFactory.createDepartmentDAO();

    public List<Department> findAll(){
        return dao.findAll();
    }

    public void sarOrUpdate(Department department){
        if (department.getId() == null)
            dao.insert(department);
        else
            dao.update(department);
    }
}