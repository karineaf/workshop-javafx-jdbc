package model.entities;

import model.dao.DAOFactory;
import model.dao.DepartmentDAO;

import java.util.List;

public class DepartmentService {

    private DepartmentDAO dao = DAOFactory.createDepartmentDAO();

    public List<Department> findAll(){
        return dao.findAll();
    }
}
