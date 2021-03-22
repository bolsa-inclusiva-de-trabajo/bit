package ar.org.cpci.bit.model.location;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    private Set<State> states;

    public Country() {
        states = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<State> getStates() {
        return Collections.unmodifiableSet(states);
    }

    public void setStates(Iterable<State> states) {
        this.states.clear();
        for (State state : states) {
            addState(state);
        }
    }

    public void addState(State state) {
        this.states.add(state);
    }

    @Override
    public String toString() {
        return String.format("Country [id=%s, name=%s, description=%s, states=%s]",
                             id, name, description, states.size());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Country other = (Country) obj;
        return Objects.equals(id, other.id);
    }

}
