package in.OAndM.context;

import java.io.Serializable;

import java.io.Serializable;

public class ApiContext implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer userId;
    private Integer clientId;
    private Integer applicationId;

    public ApiContext() {
        super();
    }

    public ApiContext(Integer applicationId, Integer clientId, Integer userId ) {
        super();
        this.userId = userId;
        this.clientId = clientId;
        this.applicationId = applicationId;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getApplicationId() {
        return applicationId;
    }
    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }




}
