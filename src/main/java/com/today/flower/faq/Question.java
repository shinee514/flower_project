package com.today.flower.faq;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.today.flower.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


	@Getter
	@Setter
	@Entity
	@Table(name="question")
	@ToString
	public class Question {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    @Column(length = 200)
	    private String subject;

	    @Column(columnDefinition = "TEXT")
	    private String content;

	    private LocalDateTime createDate;
	    
	    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	    private List<Answer> answerList;
	    
	    @ManyToOne
	    private SiteUser author;
	    
	    @ManyToMany
	    Set<SiteUser> voter;
	
	}

