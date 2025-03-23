package app.restgourmet.api.sales.models;

import app.restgourmet.api.masterdata.models.Address;
import app.restgourmet.api.usermanagement.models.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends AuditableEntity {
    @Column(nullable = false)
    private String name;

    @Column
    private String email;

    @Column
    private String phone;
    
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
