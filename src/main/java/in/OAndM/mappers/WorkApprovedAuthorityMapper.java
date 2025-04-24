package in.OAndM.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import in.OAndM.DTO.AdminSanctionsModel;
import in.OAndM.DTO.WorkApprovedAuthorityModel;
import in.OAndM.DTO.WorkDetailsViewModel;
import in.OAndM.Entities.AdminSanctionsEntity;
import in.OAndM.Entities.WorkApprovedAuthorityMst;
import in.OAndM.Entities.WorkDetailsViewEntity;
import in.OAndM.core.BaseMapperImpl;

@Component
public class WorkApprovedAuthorityMapper extends BaseMapperImpl<WorkApprovedAuthorityMst, WorkApprovedAuthorityModel>{

	@Override
	public WorkApprovedAuthorityModel mapEntityToModel(WorkApprovedAuthorityMst entity) {
		// TODO Auto-generated method stub
		
		WorkApprovedAuthorityModel model = new WorkApprovedAuthorityModel();
		BeanUtils.copyProperties(entity, model);
		return model;
	}

	@Override
	public WorkApprovedAuthorityMst mapModelToEntity(WorkApprovedAuthorityModel model) {
		// TODO Auto-generated method stub
		WorkApprovedAuthorityMst entity = new WorkApprovedAuthorityMst();
		BeanUtils.copyProperties(model, entity);
		return entity;
	}

}
