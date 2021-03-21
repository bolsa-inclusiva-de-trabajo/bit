package ar.org.cpci.bit.model;

import java.time.ZonedDateTime;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "expiration", nullable = false)
    private ZonedDateTime expiration;

    @Column(name = "full_time", nullable = false)
    private Boolean fullTime;

    @Column(name = "part_time", nullable = false)
    private Boolean partTime;

    @Column(name = "home_work", nullable = false)
    private Boolean homeWork;

    @Column(name = "independent", nullable = false)
    private Boolean independent;

    @Column(name = "dependent", nullable = false)
    private Boolean dependent;

    @Column(name = "disabled", nullable = false)
    private Boolean disabled;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToMany(mappedBy = "interestingJobs", fetch = FetchType.EAGER)
    private Set<User> interestedUsers;

    @ManyToMany(mappedBy = "applyJobs", fetch = FetchType.EAGER)
    private Set<User> applyUsers;

    /* ---- */

    public Job() {
        interestedUsers = new HashSet<>();
        applyUsers = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(ZonedDateTime expiration) {
        this.expiration = expiration;
    }

    public Boolean getFullTime() {
        return fullTime;
    }

    public void setFullTime(Boolean fullTime) {
        this.fullTime = fullTime;
    }

    public Boolean getPartTime() {
        return partTime;
    }

    public void setPartTime(Boolean partTime) {
        this.partTime = partTime;
    }

    public Boolean getHomeWork() {
        return homeWork;
    }

    public void setHomeWork(Boolean homeWork) {
        this.homeWork = homeWork;
    }

    public Boolean getIndependent() {
        return independent;
    }

    public void setIndependent(Boolean independent) {
        this.independent = independent;
    }

    public Boolean getDependent() {
        return dependent;
    }

    public void setDependent(Boolean dependent) {
        this.dependent = dependent;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    /* ---- */

    public Set<User> getInterestedUsers() {
        return Collections.unmodifiableSet(interestedUsers);
    }

    public void setInterestedUsers(Iterable<User> users) {
        interestedUsers.clear();
        for (User user : users) {
            addInterestedUser(user);
        }
    }

    public void addInterestedUser(User user) {
        interestedUsers.add(user);
    }

    public void remInterestedUser(User user) {
        interestedUsers.remove(user);
    }

    public void remAllInterestedUser() {
        interestedUsers.clear();
    }

    public int getInterestedUsersCount() {
        return interestedUsers.size();
    }

    /* ---- */

    public Set<User> getApplyUsers() {
        return Collections.unmodifiableSet(applyUsers);
    }

    public void setApplyUsers(Iterable<User> users) {
        applyUsers.clear();
        for (User user : users) {
            addAplyUser(user);
        }
    }

    public void addAplyUser(User user) {
        applyUsers.add(user);
    }

    public void remAplyUser(User user) {
        applyUsers.remove(user);
    }

    public void remAllAplyUser() {
        applyUsers.clear();
    }

    public int getApplyUsersCount() {
        return applyUsers.size();
    }

    /* ---- */

    @Override
    public String toString() {
        return String.format("Job [id=%s, title=%s, description=%s, expiration=%s, " +
                             "fullTime=%s, partTime=%s, homeWork=%s, independent=%s, dependent=%s, " +
                             "disabled=%s, owner=%s]",
                             id, title, description, expiration, fullTime, partTime, homeWork,
                             independent, dependent, disabled, owner);
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
        Job other = (Job) obj;
        return Objects.equals(id, other.id);
    }

}
