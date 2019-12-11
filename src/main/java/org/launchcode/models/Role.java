package org.launchcode.models;

import javax.persistence.*;

@Entity
@Table(name="role")
public class Role {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="role_id")
    private int id;

    @Column(name="role_name")
    private String role;

    @Column(name="role_desc")
    private String desc;

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
