package BusinessObjects;

import java.util.Objects;
import org.bson.types.ObjectId;

public class Tag {
   private ObjectId id;
   private String tag;

    public Tag() {
    }

    public Tag(ObjectId id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public Tag(String tag) {
        this.tag = tag;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final Tag other = (Tag) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tag{" + "id=" + id + ", tag=" + tag + '}';
    }
}
