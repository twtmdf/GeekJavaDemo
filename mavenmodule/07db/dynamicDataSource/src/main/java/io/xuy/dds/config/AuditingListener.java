package io.xuy.dds.config;

import io.xuy.dds.domain.AbstractModel;
import io.xuy.dds.domain.enums.DeleteFlag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Configurable
public class AuditingListener {

    @PrePersist
    public void touchForCreate(Object target) {
        if (target instanceof AbstractModel) {
            AbstractModel model = (AbstractModel) target;
            Instant now = Instant.now();
            if (model.getCreateTime() == null){
                model.setCreateTime(now);
            }
            if (!MultipleDateSourceConfig.PUBLISH.equals(DataSourceTypeHolder.get())){
                model.setUpdateTime(now);
            }
            model.setVersion(1);
        }
        setDefaultDeleteFlag(target);
    }

    @PreUpdate
    public void touchForUpdate(Object target) {
        if (target instanceof AbstractModel) {
            AbstractModel model = (AbstractModel) target;

            if (!MultipleDateSourceConfig.PUBLISH.equals(DataSourceTypeHolder.get())){
                model.setUpdateTime(Instant.now());
            }
        }
    }

    private void setDefaultDeleteFlag(Object target) {
        try {
            Method getDeleteFlag = getMethod(target, "getDeleteFlag");
            if (getDeleteFlag != null && getDeleteFlag.invoke(target) != null) {
                return;
            }
            Method setDeleteFlag = getMethod(target, "setDeleteFlag");
            if (setDeleteFlag != null) {
                setDeleteFlag.invoke(target, DeleteFlag.VALID);
            }
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
    }

    private Method getMethod(Object target, String methodName) {
        return Arrays.stream(target.getClass().getMethods())
                .collect(Collectors.toMap(Method::getName, (p) -> p, (oldValue, newValue) -> oldValue))
                .get(methodName);
    }

}