package com.project.farmhelper.Workpage;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkPageRequest {
    private String userId;
    private Long workId;
    private Long cropId;
    private String workContent;
    private String ipAddress;
}
