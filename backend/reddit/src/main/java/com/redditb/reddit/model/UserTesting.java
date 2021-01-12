package com.redditb.reddit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="testing")
public class UserTesting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long testId;
    private String name;
    private String descp;

    // public Long getTestId() {
    //     return testId;
    // }

    // public void setTestId(Long testId) {
    //     this.testId = testId;
    // }

    // public String getName() {
    //     return name;
    // }

    // public void setName(String name) {
    //     this.name = name;
    // }

    // public String getDesc() {
    //     return desc;
    // }

    // public void setDesc(String desc) {
    //     this.desc = desc;
    // }

    // public UserTesting(Long testId, String name, String desc) {
    //     this.testId = testId;
    //     this.name = name;
    //     this.desc = desc;
    // }

    // public UserTesting() {
    // }
    
}
