package app.restgourmet.api.financials.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.financials.models.ExpenseNature;

@Repository
public interface ExpenseNatureRepository extends JpaRepository<ExpenseNature, UUID>, JpaSpecificationExecutor<ExpenseNature> {
}
