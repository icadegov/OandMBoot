package in.OAndM.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import in.OAndM.Entities.AdminAssignWorksEntity;
import in.OAndM.Entities.AdminSanctionsEntity;
import in.OAndM.core.BaseRepository;


public interface AssignAdminSanctionRepo  extends  BaseRepository<AdminAssignWorksEntity, Integer>{

}
