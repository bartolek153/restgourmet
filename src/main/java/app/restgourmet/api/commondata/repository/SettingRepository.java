package app.restgourmet.api.commondata.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.restgourmet.api.commondata.models.Setting;

@Repository
public interface SettingRepository extends JpaRepository<Setting, UUID> {
  Optional<Setting> findByOptionKey(String optionKey);
}
