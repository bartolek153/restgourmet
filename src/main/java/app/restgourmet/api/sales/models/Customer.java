package app.restgourmet.api.sales.models;

import app.restgourmet.api.masterdata.models.BusinessPartner;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends BusinessPartner {
}
