package BusinessObjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import org.bson.types.ObjectId;

public class User {
    private ObjectId id;
    private String name;
    private Date birthDate;
    private Integer age;
    private String sex;
    private ArrayList<FavoriteMovie> favMovies;
    private ArrayList<FavoriteMusicGenre> favMusicGenre;

    public User() {
    }

    public User(ObjectId id, String name, Date birthDate, Integer age, String sex, ArrayList<FavoriteMovie> favMovies, ArrayList<FavoriteMusicGenre> favMusicGenre) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.age = age;
        this.sex = sex;
        this.favMovies = favMovies;
        this.favMusicGenre = favMusicGenre;
    }

    public User(String name, Date birthDate, Integer age, String sex, ArrayList<FavoriteMovie> favMovies, ArrayList<FavoriteMusicGenre> favMusicGenre) {
        this.name = name;
        this.birthDate = birthDate;
        this.age = age;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
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
        return "User{" + "id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", age=" + age + ", sex=" + sex + ", favMovies=" + favMovies + ", favMusicGenre=" + favMusicGenre + '}';
    }
}
