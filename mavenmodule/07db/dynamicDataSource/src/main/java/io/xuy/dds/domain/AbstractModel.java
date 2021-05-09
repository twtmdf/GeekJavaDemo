package io.xuy.dds.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.xuy.dds.config.AuditingListener;
import lombok.Data;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.Instant;

@Data
@MappedSuperclass
@EntityListeners(AuditingListener.class)
public class AbstractModel implements Serializable {

    @Version
    @JsonIgnore
    private int version;

    @Column(name = "create_time", updatable = false)
    private Instant createTime;

    @Column(name = "update_time")
    private Instant updateTime;

    @Column(name = "trace_id")
    private String traceId;

    private String server;

    private String client;
}