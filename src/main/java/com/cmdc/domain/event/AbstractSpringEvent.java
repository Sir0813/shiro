package com.cmdc.domain.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;
import java.io.Serializable;

@Getter
@Setter
@ToString
public abstract class AbstractSpringEvent extends ApplicationEvent implements Serializable{

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    private String eventId;

    public AbstractSpringEvent(Object source) {
        super(source);
    }
}
