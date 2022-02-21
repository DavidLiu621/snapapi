package com.snap.restapi.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.URL;

//import com.dailycodebuffer.spring.data.jpa.tutorial.entity.Guardian;
//import com.dailycodebuffer.spring.data.jpa.tutorial.entity.Student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "key_url")


public class KeyUrl {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	
	@Column(name = "app_key", nullable = false)
	private String appKey;
	
	@Column(name = "url", nullable = false)
	@URL(message = "url should be valid format")
	private String url;
	
	@Column(name = "destination_url", nullable = false)
	@URL(message = "url should be valid format")
	private String destinationUrl;
	
	@Column(name = "path_id")
	@Size(min = 3, message = "pathId should have at least 3 characters")
	private String pathId;
	
	@Column(name = "`create_at`")
	@CreationTimestamp
	private Timestamp timestamp;
}
