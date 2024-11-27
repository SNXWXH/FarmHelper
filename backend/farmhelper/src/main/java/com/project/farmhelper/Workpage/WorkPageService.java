package com.project.farmhelper.Workpage;

import com.project.farmhelper.User.UserRepository;
import com.project.farmhelper.common.Repository.CropRepository;
import com.project.farmhelper.common.Repository.WorkLogRepository;
import com.project.farmhelper.common.entity.Crop;
import com.project.farmhelper.common.entity.User;
import com.project.farmhelper.common.entity.WorkLog;
import com.project.farmhelper.main.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;


@Service
@Slf4j
public class WorkPageService {

    private final WeatherService weatherService;
    private final WorkLogRepository workLogRepository;
    private final UserRepository userRepository;
    private final CropRepository cropRepository;

    public WorkPageService(WeatherService weatherService, WorkLogRepository workLogRepository,
                          UserRepository userRepository, CropRepository cropRepository) {
        this.weatherService = weatherService;
        this.workLogRepository = workLogRepository;
        this.userRepository = userRepository;
        this.cropRepository = cropRepository;
    }

    public void createWorkLog(WorkPageRequest workRequest) {
        // 오늘 날짜
        LocalDate today = LocalDate.now();

        // 날씨와 온도 조회
        Map<String, Object> todayWeather = weatherService.getCurrentWeatherByIP(workRequest.getIpAddress()); // 도시 이름은 적절히 수정

        if (todayWeather.containsKey("error")) {
            throw new RuntimeException("날씨 정보를 가져오는 데 실패했습니다: " + todayWeather.get("error"));
        }

        String weather = (String) todayWeather.get("weather");
        Double temperature = (Double) todayWeather.get("temperature");

        // User와 Crop 조회
        User user = userRepository.findById(workRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. userId: " + workRequest.getUserId()));

        Crop crop = cropRepository.findById(workRequest.getCropId())
                .orElseThrow(() -> new RuntimeException("작물을 찾을 수 없습니다. cropId: " + workRequest.getCropId()));

        // WorkLog 생성 및 저장
        WorkLog workLog = new WorkLog();
        workLog.setUser(user); // user 설정
        workLog.setCrop(crop); // crop 설정
        workLog.setWorkDate(today);
        workLog.setWorkContent(workRequest.getWorkContent());
        workLog.setWorkTemperature(temperature);
        workLog.setWorkWeather(weather);

        workLogRepository.save(workLog);
    }

    public boolean updateWorkLog(WorkPageRequest workRequest) {
        // workId로 작업일지 조회 (workId는 Long 타입)
        WorkLog workLog = workLogRepository.findById(workRequest.getWorkId())
                .orElseThrow(() -> new RuntimeException("작업일지를 찾을 수 없습니다. workId: " + workRequest.getWorkId()));

        // User와 Crop 조회 (userId는 String, cropId는 Long)
        User user = userRepository.findById(workRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. userId: " + workRequest.getUserId()));

        Crop crop = cropRepository.findById(workRequest.getCropId())
                .orElseThrow(() -> new RuntimeException("작물을 찾을 수 없습니다. cropId: " + workRequest.getCropId()));

        // userId와 cropId가 일치하는지 확인
        if (!workLog.getUser().getUserId().equals(workRequest.getUserId()) ||
                !workLog.getCrop().getCropId().equals(workRequest.getCropId())) {
            return false; // 수정할 데이터가 없으면 false 반환
        }

        // 수정 가능한 필드만 업데이트
        workLog.setWorkContent(workRequest.getWorkContent());

        // 수정된 데이터 저장
        workLogRepository.save(workLog);

        return true;
    }

    public boolean deleteWorkLog(WorkPageRequest workRequest) {
        // workId, userId, cropId로 작업일지 조회
        WorkLog workLog = workLogRepository.findById(workRequest.getWorkId())
                .orElse(null);

        if (workLog == null ||
                !workLog.getUser().getUserId().equals(workRequest.getUserId()) ||
                !workLog.getCrop().getCropId().equals(workRequest.getCropId())) {
            return false; // 삭제할 데이터가 없으면 false 반환
        }

        // 삭제
        workLogRepository.delete(workLog);
        return true;
    }


}
