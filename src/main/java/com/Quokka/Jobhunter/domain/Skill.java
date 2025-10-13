package com.Quokka.Jobhunter.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.Flow.Subscriber;

import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;


import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter 
@Setter
@Entity
@Table(name="skills")
public class Skill extends AbstractAuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "skills")
    @JsonIgnore
    private List<Job> jobs;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "skills")
    @JsonIgnore
    private List<Subscriber> subscribers;
}

