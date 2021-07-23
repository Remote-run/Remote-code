package no.ntnu.remotecode.model;

import lombok.Data;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@Entity
public class ComputeNode {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private double Id;


}
