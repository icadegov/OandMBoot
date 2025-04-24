package in.OAndM.context;
public class ThreadLocalWithApiContext {
	public static final ThreadLocal<ApiContext> apiIdentityContextThreadLocal = new ThreadLocal<>();

	private ThreadLocalWithApiContext(){
		
	}

	public static void setUserIdentityContext(ApiContext apiContext) {
		apiIdentityContextThreadLocal.set(apiContext);
	}
	
	public static ApiContext getUserIdentityContext() {
		return apiIdentityContextThreadLocal.get();
	}
	
	public static Integer getUserId() {
		ApiContext apiContext = apiIdentityContextThreadLocal.get();
		if (apiContext != null) {
			return apiContext.getUserId();
		}
		return null;
	}
	
	public static void setUserId(Integer userId) {
		ApiContext apiContext = apiIdentityContextThreadLocal.get();
		if (apiContext != null) {
			apiContext.setUserId(userId);
		}
	}
	
	public static Integer getApplicationId() {
		ApiContext apiContext = apiIdentityContextThreadLocal.get();
		if (apiContext != null) {
			return apiContext.getApplicationId();
		}
		return null;
	}
	
	public static Integer getClientId() {
		ApiContext apiContext = apiIdentityContextThreadLocal.get();
		if (apiContext != null) {
			return apiContext.getClientId();
		}
		return null;
	}
}
