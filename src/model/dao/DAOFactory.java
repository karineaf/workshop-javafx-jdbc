package model.dao;

import db.DB;
import model.dao.impl.DepartmentDAOImplJDBC;
import model.dao.impl.SellerDAOImplJDBC;

public class DAOFactory {
    public static SellerDAO createSellerDAO(){
        return new SellerDAOImplJDBC(DB.getConnection());
    }

    public static DepartmentDAOImplJDBC createDepartmentDAO(){
        return new DepartmentDAOImplJDBC(DB.getConnection());
    }

}
