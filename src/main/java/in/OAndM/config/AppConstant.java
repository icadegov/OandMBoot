package in.OAndM.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:appconstant.properties")
public class AppConstant {
	
	
	public static final String APP_ID="app.id";
	public static final String APP_SECRET="app.secret";
	public static final String CLIENT_ID="client.id";
	
	public static final String GET_SERVICE_STARTED="get.service.started";
	public static final String GET_SERVICE_SUCCESS="get.service.success";
	public static final String GET_SERVICE_FAILED="get.service.failed";
	
	public static final String CREATE_SERVICE_STARTED="create.service.started";
	public static final String CREATE_SERVICE_SUCCESS="create.service.success";
	public static final String CREATE_SERVICE_FAILED="create.service.failed";
	
	public static final String UPDATE_SERVICE_STARTED="update.service.started";
	public static final String UPDATE_SERVICE_SUCCESS="update.service.success";
	public static final String UPDATE_SERVICE_FAILED="update.service.failed";
	
	public static final String DELETE_SERVICE_STARTED="delete.service.started";
	public static final String DELETE_SERVICE_SUCCESS="delete.service.success";
	public static final String DELETE_SERVICE_FAILED="delete.service.failed";
	
	public static final String VALIDATION_ERROR="validation.error";
	public static final String ENCRYPTION_ERROR="encryption.error";
	public static final String DECRYPTION_ERROR="decryption.error";
	
	
	public static final String GET_APPLICATION_STARTED="get.application.started";
	public static final String GET_APPLICATION_SUCCESS="get.application.success";
	public static final String GET_APPLICATION_FAILED="get.application.failed";
	public static final String APPLICATION_NOT_FOUND="application.not.found";
	
	public static final String CREATE_APPLICATION_SUCCESS="create.application.success";
	public static final String CREATE_APPLICATION_FAILED="create.application.failed";
	public static final String CREATE_APPLICATION_STARTED="create.application.started";
	
	public static final String UPDATE_APPLICATION_STARTED="update.application.started";
	public static final String UPDATE_APPLICATION_SUCCESS="update.application.success";
	public static final String UPDATE_APPLICATION_FAILED="update.application.failed";
	
	public static final String DELETE_APPLICATION_SUCCESS="delete.application.success";
	public static final String DELETE_APPLICATION_FAILED="delete.application.failed";
	public static final String DELETE_APPLICATION_STARTED="delete.application.started";

	public static final String CREATE_CLIENTUSERROLE_SUCCESS="create.clientuserrole.success";
	public static final String CREATE_CLIENTUSERROLE_FAILED="create.clientuserrole.failed";
	public static final String CREATE_CLIENTUSERROLE_STARTED="create.clientuserrole.started";
	
	public static final String GET_CLIENTUSERROLE_SUCCESS="get.clientuserrole.success";
	public static final String GET_CLIENTUSERROLE_FAILED="get.clientuserrole.failed";
	public static final String GET_CLIENTUSERROLE_STARTED="get.clientuserrole.started";
	
	public static final String UPDATE_CLIENTUSERROLE_SUCCESS="update.clientuserrole.success";
	public static final String UPDATE_CLIENTUSERROLE_FAILED="update.clientuserrole.failed";
	public static final String UPDATE_CLIENTUSERROLE_STARTED="update.clientuserrole.started";
	
	public static final String DELETE_CLIENTUSERROLE_SUCCESS="delete.clientuserrole.success";
	public static final String DELETE_CLIENTUSERROLE_FAILED="delete.clientuserrole.failed";
	public static final String DELETE_CLIENTUSERROLE_STARTED="delete.clientuserrole.started";
	
	public static final String GET_PAGES_STARTED="get.pages.started";
	public static final String GET_PAGES_SUCCESS="get.pages.success";
	public static final String GET_PAGES_FAILED="get.pages.failed";
	public static final String PAGES_NOT_FOUND="pages.not.found";

	public static final String CREATE_PAGE_SUCCESS="create.page.success";
	public static final String CREATE_PAGE_FAILED="create.page.failed";
	public static final String CREATE_PAGE_STARTED="create.page.started";

	public static final String UPDATE_PAGE_STARTED="update.page.started";
	public static final String UPDATE_PAGE_SUCCESS="update.page.success";
	public static final String UPDATE_PAGE_FAILED="update.page.failed";

	public static final String DELETE_PAGE_STARTED="delete.page.started";
	public static final String DELETE_PAGE_SUCCESS="delete.page.success";
	public static final String DELETE_PAGE_FAILED="delete.page.failed";

	public static final String GET_FEATURES_STARTED="get.features.started";
	public static final String GET_FEATURES_SUCCESS="get.features.success";
	public static final String GET_FEATURES_FAILED="get.features.failed";
	public static final String FEATURES_NOT_FOUND="features.not.found";

	public static final String CREATE_FEATURE_SUCCESS="create.feature.success";
	public static final String CREATE_FEATURE_FAILED="create.feature.failed";
	public static final String CREATE_FEATURE_STARTED="create.feature.started";

	public static final String UPDATE_FEATURE_STARTED="update.feature.started";
	public static final String UPDATE_FEATURE_SUCCESS="update.feature.success";
	public static final String UPDATE_FEATURE_FAILED="update.feature.failed";

	public static final String DELETE_FEATURE_STARTED="delete.feature.started";
	public static final String DELETE_FEATURE_SUCCESS="delete.feature.success";
	public static final String DELETE_FEATURE_FAILED="delete.feature.failed";

	public static final String CREATE_PROJECTUSERROLE_SUCCESS="create.projectuserrole.success";
	public static final String CREATE_PROJECTUSERROLE_FAILED="create.projectuserrole.failed";
	public static final String CREATE_PROJECTUSERROLE_STARTED="create.projectuserrole.started";
		
	public static final String DELETE_PROJECTUSERROLE_SUCCESS="delete.projectuserrole.success";
	public static final String DELETE_PROJECTUSERROLE_FAILED="delete.projectuserrole.failed";
	public static final String DELETE_PROJECTUSERROLE_STARTED="delete.projectuserrole.started";
		
	public static final String GET_PROJECTUSERROLE_SUCCESS="get.projectuserrole.success";
	public static final String GET_PROJECTUSERROLE_FAILED="get.projectuserrole.failed";
	public static final String GET_PROJECTUSERROLE_STARTED="get.projectuserrole.started";
	
	public static final String CREATE_ROLE_FAILED="create.role.failed";
	public static final String CREATE_ROLE_SUCCESS="create.role.success";
	public static final String CREATE_ROLE_STARTED="create.role.started";

	public static final String UPDATE_ROLE_FAILED="update.role.failed";
	public static final String UPDATE_ROLE_SUCCESS="update.role.success";
	public static final String UPDATE_ROLE_STARTED="update.role.started";

	public static final String DELETE_ROLE_FAILED="delete.role.failed";
	public static final String DELETE_ROLE_SUCCESS="delete.role.success"; 
	public static final String DELETE_ROLE_STARTED="delete.role.started";

	public static final String GET_ROLE_FAILED="get.role.failed";
	public static final String GET_ROLE_SUCCESS="get.role.success";
	public static final String GET_ROLE_STARTED="get.role.started";
	public static final String ROLE_NOT_FOUND="role.not.found";

	public static final String CLONE_ROLES_FAILED="clone.roles.failed";
	public static final String CLONE_ROLES_SUCCESS="clone.roles.success";
	public static final String CLONE_ROLES_STARTED="clone.roles.started";

	public static final String GET_ROLEPAGEFEATUREPERM_SUCCESS="get.rolepagefeatureperm.success";
	public static final String GET_ROLEPAGEFEATUREPERM_FAILED="get.rolepagefeatureperm.failed";
	public static final String ROLEPAGEFEATUREPERM_NOT_FOUND="rolepagefeatureperm.not.found";
	public static final String GET_ROLEPAGEFEATUREPERM_STARTED="get.rolepagefeatureperm.started";
		
	public static final String CREATE_ROLEPAGEFEATUREPERM_SUCCESS="create.rolepagefeatureperm.success";
	public static final String CREATE_ROLEPAGEFEATUREPERM_FAILED="create.rolepagefeatureperm.failed";
	public static final String CREATE_ROLEPAGEFEATUREPERM_STARTED="create.rolepagefeatureperm.started";
		
	public static final String DELETE_ROLEPAGEFEATUREPERM_STARTED="delete.rolepagefeatureperm.started";
	public static final String DELETE_ROLEPAGEFEATUREPERM_SUCCESS="delete.rolepagefeatureperm.success";
	public static final String DELETE_ROLEPAGEFEATUREPERM_FAILED="delete.rolepagefeatureperm.failed";
		
	public static final String UPDATE_ROLEPAGEFEATUREPERM_STARTED="update.rolepagefeatureperm.started";
	public static final String UPDATE_ROLEPAGEFEATUREPERM_SUCCESS="update.rolepagefeatureperm.success";
	public static final String UPDATE_ROLEPAGEFEATUREPERM_FAILED="update.rolepagefeatureperm.failed";

	public static final String GET_ROLEPAGEPERM_SUCCESS="get.rolepageperm.success";
	public static final String GET_ROLEPAGEPERM_FAILED="get.rolepageperm.failed";
	public static final String ROLEPAGEPERM_NOT_FOUND="rolepageperm.not.found";
	public static final String GET_ROLEPAGEPERM_STARTED="get.rolepageperm.started";

	public static final String CREATE_ROLEPAGEPERM_SUCCESS="create.rolepageperm.success";
	public static final String CREATE_ROLEPAGEPERM_FAILED="create.rolepageperm.failed";
	public static final String CREATE_ROLEPAGEPERM_STARTED="create.rolepageperm.started";
		
	public static final String DELETE_ROLEPAGEPERM_STARTED="delete.rolepageperm.started";
	public static final String DELETE_ROLEPAGEPERM_SUCCESS="delete.rolepageperm.success";
	public static final String DELETE_ROLEPAGEPERM_FAILED="delete.rolepageperm.failed";
		
	public static final String UPDATE_ROLEPAGEPERM_STARTED="update.rolepageperm.started";
	public static final String UPDATE_ROLEPAGEPERM_SUCCESS="update.rolepageperm.success";
	public static final String UPDATE_ROLEPAGEPERM_FAILED="update.rolepageperm.failed";

	public static final String CREATE_USER_FAILED="create.user.failed";
	public static final String CREATE_USER_SUCCESS="create.user.success";
	public static final String CREATE_USER_STARTED="create.user.started";

	public static final String UPDATE_USER_FAILED="update.user.failed";
	public static final String UPDATE_USER_SUCCESS="update.user.success";
	public static final String UPDATE_USER_STARTED="update.user.started";

	public static final String DELETE_USER_FAILED="delete.user.failed";
	public static final String DELETE_USER_SUCCESS="delete.user.success";
	public static final String DELETE_USER_STARTED="delete.user.started";

	public static final String GET_USER_FAILED="get.user.failed";
	public static final String GET_USER_SUCCESS="get.user.success";
	public static final String GET_USER_STARTED="get.user.started";
	public static final String USER_NOT_FOUND="user.not.found";

	public static final String GET_USERPAGEFEATUREPERM_SUCCESS="get.userpagefeatureperm.success";
	public static final String GET_USERPAGEFEATUREPERM_FAILED="get.userpagefeatureperm.failed";
	public static final String USERPAGEFEATUREPERM_NOT_FOUND="userpagefeatureperm.not.found";
	public static final String GET_USERPAGEFEATUREPERM_STARTED="get.userpagefeatureperm.started";
		
	public static final String CREATE_USERPAGEFEATUREPERM_SUCCESS="create.userpagefeatureperm.success";
	public static final String CREATE_USERPAGEFEATUREPERM_FAILED="create.userpagefeatureperm.failed";
	public static final String CREATE_USERPAGEFEATUREPERM_STARTED="create.userpagefeatureperm.started";
		
	public static final String DELETE_USERPAGEFEATUREPERM_STARTED="delete.userpagefeatureperm.started";
	public static final String DELETE_USERPAGEFEATUREPERM_SUCCESS="delete.userpagefeatureperm.success";
	public static final String DELETE_USERPAGEFEATUREPERM_FAILED="delete.userpagefeatureperm.failed";
		
	public static final String UPDATE_USERPAGEFEATUREPERM_STARTED="update.userpagefeatureperm.started";
	public static final String UPDATE_USERPAGEFEATUREPERM_SUCCESS="update.userpagefeatureperm.success";
	public static final String UPDATE_USERPAGEFEATUREPERM_FAILED="update.userpagefeatureperm.failed";

	public static final String GET_USERPAGEPERM_SUCCESS="get.userpageperm.success";
	public static final String GET_USERPAGEPERM_FAILED="get.userpageperm.failed";
	public static final String USERPAGEPERM_NOT_FOUND="userpageperm.not.found";
	public static final String GET_USERPAGEPERM_STARTED="get.userpageperm.started";

	public static final String CREATE_USERPAGEPERM_SUCCESS="create.userpageperm.success";
	public static final String CREATE_USERPAGEPERM_FAILED="create.userpageperm.failed";
	public static final String CREATE_USERPAGEPERM_STARTED="create.userpageperm.started";
	
	public static final String DELETE_USERPAGEPERM_STARTED="delete.userpageperm.started";
	public static final String DELETE_USERPAGEPERM_SUCCESS="delete.userpageperm.success";
	public static final String DELETE_USERPAGEPERM_FAILED="delete.userpageperm.failed";
		
	public static final String UPDATE_USERPAGEPERM_STARTED="update.userpageperm.started";
	public static final String UPDATE_USERPAGEPERM_SUCCESS="update.userpageperm.success";
	public static final String UPDATE_USERPAGEPERM_FAILED="update.userpageperm.failed";

	public static final String INSTAGRAM="INSTAGRAM";
	public static final String YOUTUBE="YOUTUBE";
	public static final String TIKTOK="TIKTOK";

	public static final String STATISTICS_NO_DATA="No statistics data";
	public static final String INTERNAL_SERVER_ERROR="Internal server error";

	@Autowired
    private Environment env;

	public String getValue(String key) {
		return env.getProperty(key, "0");
	}
	
}
