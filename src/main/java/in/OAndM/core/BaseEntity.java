package in.OAndM.core;


import java.io.Serializable;
import java.util.Date;

import in.OAndM.context.ThreadLocalWithApiContext;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;

@MappedSuperclass
public abstract class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = -2068976643536582974L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	private T id;
	
	@Column(name = "ACTIVE", nullable = false)
	private boolean active = true;

	@Version
	@Column(name = "VERSION", nullable = true)
	protected long version = 0;
	
	@Column(name = "CREATED_AT", nullable =false)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createdAt;

	@Column(name = "CREATED_BY", nullable = true)
	protected Integer createdBy;

	@Column(name = "LAST_MODIFIED_AT", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastModifiedAt;

	@Column(name = "LAST_MODIFIED_BY", nullable = true)
	protected Integer lastModifiedBy;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	public Integer getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Integer lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
	@PrePersist
	public void initAuditData() {
		version++;
		createdAt = new Date();		
		createdBy = ThreadLocalWithApiContext.getUserId();
		lastModifiedAt = new Date();		
		lastModifiedBy = ThreadLocalWithApiContext.getUserId();
	}
	
	@PreUpdate
	public void updateAuditData() {
		version++;
		lastModifiedAt = new Date();		
		lastModifiedBy = ThreadLocalWithApiContext.getUserId();
	}
}
