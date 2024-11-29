package com.project.farmhelper.main;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWorkLogDTO {
    private String workName;
    private String workContent;

    // 생성자
    public UserWorkLogDTO(String workName, String workContent) {
        this.workName = workName;
        this.workContent = workContent;
    }

    // Getter와 Setter
    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }
}
