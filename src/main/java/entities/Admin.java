package entities;

import enums.UsersRole;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "admin")
@NamedQuery(name = "get all admins", query = "SELECT a FROM Admin a")
public class Admin extends User{

    public Admin(){
        super();
    }

    public Admin(String name, String email, String password, String phone, String address, UsersRole userRole) {
        super(name, email, password, phone, address, userRole);
    }

    @Override
    public long getId() {
        return super.getId();
    }

    @Override
    public void setId(long id) {
        super.setId(id);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public String getPhone() {
        return super.getPhone();
    }

    @Override
    public void setPhone(String phone) {
        super.setPhone(phone);
    }

    @Override
    public String getAddress() {
        return super.getAddress();
    }

    @Override
    public void setAddress(String address) {
        super.setAddress(address);
    }

    @Override
    public UsersRole getUserRole() {
        return super.getUserRole();
    }

    @Override
    public void setUserRole(UsersRole userRole) {
        super.setUserRole(userRole);
    }

    @Override
    public List<Notification> getNotifications() {
        return super.getNotifications();
    }

    @Override
    public void setNotifications(List<Notification> notifications) {
        super.setNotifications(notifications);
    }
}
