package BusinessObjects;

import Enums.Sex;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import org.bson.types.ObjectId;

/**
 * @author Juan Enrique Solis Perla
 * @ID: 165920 Advanced Databases Class, ISW, ITSON
 */
public class User {

    private ObjectId id;
    private String username;
    private String password;
    private String email;
    private Date birthDate;
    private Sex sex;
    private ArrayList<FavoriteMovie> favMovies;
    private ArrayList<FavoriteMusicGenre> favMusicGenre;

    public User() {
    }

    public User(ObjectId id, String username, String password, String email, Date birthDate, Sex sex, ArrayList<FavoriteMovie> favMovies, ArrayList<FavoriteMusicGenre> favMusicGenre) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.sex = sex;
        this.favMovies = favMovies;
        this.favMusicGenre = favMusicGenre;
    }

    public User(String username, String password, String email, Date birthDate, Sex sex, ArrayList<FavoriteMovie> favMovies, ArrayList<FavoriteMusicGenre> favMusicGenre) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.sex = sex;
        this.favMovies = favMovies;
        this.favMusicGenre = favMusicGenre;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer calculateAge() {
        return 0;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public ArrayList<FavoriteMovie> getFavMovies() {
        return favMovies;
    }

    public void setFavMovies(ArrayList<FavoriteMovie> favMovies) {
        this.favMovies = favMovies;
    }

    public ArrayList<FavoriteMusicGenre> getFavMusicGenre() {
        return favMusicGenre;
    }

    public void setFavMusicGenre(ArrayList<FavoriteMusicGenre> favMusicGenre) {
        this.favMusicGenre = favMusicGenre;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final User other = (User) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", birthDate=" + birthDate + ", sex=" + sex + ", favMovies=" + favMovies + ", favMusicGenre=" + favMusicGenre + '}';
    }
}
