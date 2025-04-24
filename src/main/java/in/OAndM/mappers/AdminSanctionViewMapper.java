package in.OAndM.mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import in.OAndM.core.BaseMapperImpl;
import in.OAndM.DTO.AdminSanctionViewModel;
import in.OAndM.Entities.AdminSanctionViewEntity;

@Component
public class AdminSanctionViewMapper extends BaseMapperImpl<AdminSanctionViewEntity, AdminSanctionViewModel>{
	

		@Override
		public AdminSanctionViewModel mapEntityToModel(AdminSanctionViewEntity entity) {
			// TODO Auto-generated method stub
			
			AdminSanctionViewModel model = new AdminSanctionViewModel();
			BeanUtils.copyProperties(entity, model);
			return model;
		}

		@Override
		public AdminSanctionViewEntity mapModelToEntity(AdminSanctionViewModel model) {
			// TODO Auto-generated method stub
			AdminSanctionViewEntity entity = new AdminSanctionViewEntity();
			BeanUtils.copyProperties(model, entity);
			return entity;
		}

	}


