package Control;

import BusinessObjects.User;
import DataAccess.UsersDAO;
import java.util.ArrayList;
import org.bson.types.ObjectId;

/**
 * @author Juan Enrique Solis Perla
 * @ID: 165920 Advanced Databases Class, ISW, ITSON
 */
public class UserControl {

    private UsersDAO usersDAO;

    public UserControl() {
        this.usersDAO = new UsersDAO();
    }

    public ArrayList<User> findAll() {
        return this.usersDAO.findAll();
    }

    public User findByID(ObjectId id) {
        return this.usersDAO.findByID(id);
    }

    public User findByEmail(String email) {
        return this.usersDAO.findByEmail(email);
    }

    public boolean save(User user) {
        return this.usersDAO.save(user);
    }

    public boolean update(User user) {
        return this.usersDAO.update(user);
    }

    public void remove(User user) {
        this.usersDAO.remove(user);
    }
}
