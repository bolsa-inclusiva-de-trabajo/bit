package ar.org.cpci.bit.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ar.org.cpci.bit.model.location.City;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "education", nullable = false)
    private String education;

    @Column(name = "skills", nullable = false)
    private String skills;

    @Column(name = "full_time", nullable = false)
    private Boolean fullTime;

    @Column(name = "part_time", nullable = false)
    private Boolean partTime;

    @Column(name = "home_work", nullable = false)
    private Boolean homeWork;

    @Column(name = "apply_for_job", nullable = false)
    private Boolean applyForJob;

    @Column(name = "disabled", nullable = false)
    private Boolean disabled;

    /* --- */

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Job> createdJobs;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "interest",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "job_id"))
    private Set<Job> interestingJobs;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "apply",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "job_id"))
    private Set<Job> applyJobs;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "contact",
               joinColumns = @JoinColumn(name = "user_1_id"),
               inverseJoinColumns = @JoinColumn(name = "user_2_id"))
    private Set<User> contacts;

    /* --- */

    public User() {
        createdJobs = new HashSet<>();
        interestingJobs = new HashSet<>();
        applyJobs = new HashSet<>();
        contacts = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
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

    public Boolean getApplyForJob() {
        return applyForJob;
    }

    public void setApplyForJob(Boolean applyForJob) {
        this.applyForJob = applyForJob;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    /* ---- */

    public Set<Job> getCreatedJobs() {
        return Collections.unmodifiableSet(createdJobs);
    }

    public void setCreatedJobs(Iterable<Job> jobs) {
        createdJobs.clear();
        for (Job job : jobs) {
            addCreatedJob(job);
        }
    }

    public void addCreatedJob(Job job) {
        createdJobs.add(job);
    }

    public void remCreatedJob(Job job) {
        createdJobs.remove(job);
    }

    public void remAllCreatedJobs() {
        createdJobs.clear();
    }

    /* ---- */

    public Set<Job> getInterestingJobs() {
        return Collections.unmodifiableSet(interestingJobs);
    }

    public void setInterestingJobs(Iterable<Job> jobs) {
        interestingJobs.clear();
        for (Job job : jobs) {
            addInterestingJob(job);
        }
    }

    public void addInterestingJob(Job job) {
        interestingJobs.add(job);
    }

    public void remInterestingJob(Job job) {
        interestingJobs.remove(job);
    }

    public void remAllInterestingJobs() {
        interestingJobs.clear();
    }

    /* ---- */

    public Set<Job> getApplyJobs() {
        return Collections.unmodifiableSet(applyJobs);
    }

    public void setApplyJobs(Iterable<Job> jobs) {
        applyJobs.clear();
        for (Job job : jobs) {
            addApplyJob(job);
        }
    }

    public void addApplyJob(Job job) {
        applyJobs.add(job);
    }

    public void remApplyJob(Job job) {
        applyJobs.remove(job);
    }

    public void remAllApplyJobs() {
        applyJobs.clear();
    }

    /* ---- */

    public Set<User> getContacts() {
        return Collections.unmodifiableSet(contacts);
    }

    public void setContacts(Iterable<User> users) {
        contacts.clear();
        for (User user : users) {
            addContact(user);
        }
    }

    public void addContact(User user) {
        contacts.add(user);
    }

    public void remContact(User user) {
        contacts.remove(user);
    }

    public void remAllContacts() {
        contacts.clear();
    }

    /* ---- */

    @Override
    public String toString() {
        return String.format("User [id=%s, username=%s, password=%s, email=%s, firstName=%s, lastName=%s, " +
                             "city=%s, education=%s, skills=%s, fullTime=%s, partTime=%s, homeWork=%s, " +
                             "applyForJob=%s, disabled=%s]",
                             id, username, password, email, firstName, lastName, city, education,
                             skills, fullTime, partTime, homeWork, applyForJob, disabled);
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
        User other = (User) obj;
        return Objects.equals(id, other.id);
    }

}
