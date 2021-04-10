package model.services;

import model.dao.DAOFactory;
import model.dao.DepartmentDAO;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public class SellerService {

    private SellerDAO dao = DAOFactory.createSellerDAO();

    public List<Seller> findAll(){
        return dao.findAll();
    }

    public void saveOrUpdate(Seller seller){
        if (seller.getId() == null)
            dao.insert(seller);
        else
            dao.update(seller);
    }

    public void remove(Seller seller){
        dao.deleteById(seller.getId());
    }
}
